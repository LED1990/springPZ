package com.mkyong;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Konfiguracja klienta Profilu Zaufanego
 *
 * @author Pentacomp Systemy Informatyczne S.A.
 */
@Component
@Getter
@Setter
@ConfigurationProperties("pz")
public class PzProperties {

    private String identyfikatorSystemu;

    private String keystoreType;
    private String keystorePath;
    private String keystorePassword;
    private String keystoreKeyAlias;
    private String keystoreKeyPassword;

    private String trustedKeystorePath;
    private String trustedKeystorePassword;
    private String trustedKeystoreKeyAlias;

    private String singleSignOnUrl;
    private String artifactResolveUrl;
    private String tpSigningUrl;

}
