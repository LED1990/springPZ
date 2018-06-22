package com.mkyong;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pz.klasywsdl.TPSigning;
import pz.klasywsdl.TPSigningService;

import javax.xml.ws.BindingProvider;
import java.util.Map;
import java.util.Properties;

import static org.apache.wss4j.common.ConfigurationConstants.SIG_DIGEST_ALGO;

@Configuration
public class PzConfig {

    @Autowired
    private PzProperties pzProperties;

    @Bean
    TPSigning generateTpSigning() {
        TPSigningService service = new TPSigningService();
        TPSigning port = service.getTPSigning();
        Client client = ClientProxy.getClient(port);
        this.setEncryptionProperties(client);
        this.setSignatureProperties(client);

        // logowanie
        Endpoint cxfEndpoint = client.getEndpoint();
        cxfEndpoint.getOutInterceptors()
                .add(new LoggingInInterceptor());
        cxfEndpoint.getOutInterceptors()
                .add(new LoggingOutInterceptor());

        // nie wykorzystujemy adresu z WSDLa, gdyż na zmianę używamy adresu testowego i produkcyjnego
        BindingProvider bp = (BindingProvider) port;
        bp.getRequestContext()
                .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, pzProperties.getTpSigningUrl());

        return port;
    }

    /**
     * Konfiguracja podpisywania WS-Security dla wskazanego klienta
     *
     * @param client klient usługi
     */
    private void setSignatureProperties(Client client) {
        // dane certyfikatu klienta - aplikacji SOW
        Properties securityProperties = new Properties();
        securityProperties.setProperty("org.apache.ws.security.certificate", pzProperties.getKeystoreKeyAlias());
        securityProperties.setProperty("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
        securityProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", pzProperties.getKeystoreType());
        securityProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", pzProperties.getKeystorePassword());
        securityProperties.setProperty("org.apache.ws.security.crypto.merlin.file", pzProperties.getKeystorePath());
        securityProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.private.password", pzProperties.getKeystoreKeyPassword());
        securityProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.alias", pzProperties.getKeystoreKeyAlias());
        securityProperties.setProperty("signatureAlgorithm","http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
        securityProperties.setProperty(SIG_DIGEST_ALGO,"http://www.w3.org/2001/04/xmlenc#sha256");

        Map<String, Object> requestContext = client.getRequestContext();
        requestContext.put("ws-security.signature.properties", securityProperties);
        requestContext.put("ws-security.asymmetric.signature.algorithm","http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
    }

    /**
     * Konfiguracja weryfikacji podpisu dla odpowiedzi dla wskazanego klienta
     *
     * @param client klient usługi
     */
    private void setEncryptionProperties(Client client) {
        // dane certyfikatu PZ
        Properties responseProperties = new Properties();
        responseProperties.setProperty("org.apache.ws.security.certificate", pzProperties.getTrustedKeystoreKeyAlias());
        responseProperties.setProperty("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
        responseProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", "jks");
        responseProperties.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", pzProperties.getTrustedKeystorePassword());
        responseProperties.setProperty("org.apache.ws.security.crypto.merlin.file", pzProperties.getTrustedKeystorePath());
        responseProperties.setProperty("signatureAlgorithm","http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
        responseProperties.setProperty(SIG_DIGEST_ALGO,"http://www.w3.org/2001/04/xmlenc#sha256");

        Map<String, Object> requestContext = client.getRequestContext();
        requestContext.put("ws-security.encryption.properties", responseProperties);
        requestContext.put("ws-security.asymmetric.signature.algorithm","http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
    }
}
