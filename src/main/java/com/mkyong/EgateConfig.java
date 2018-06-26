package com.mkyong;

import egate.integracja.ISign;
import egate.integracja.SignDocument;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EgateConfig {

    @Bean
    ISign generyjISign(){
        SignDocument signDocumentService = new SignDocument();
        ISign port = signDocumentService.getBasicHttpBindingISign();
        Client client = ClientProxy.getClient(port);

        Endpoint cxfEndpoint = client.getEndpoint();

        // logowanie
        cxfEndpoint.getOutInterceptors().add(new LoggingInInterceptor());
        cxfEndpoint.getOutInterceptors().add(new LoggingOutInterceptor());
        return port;
    }
}
