package com.mkyong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pz.klasywsdl.TPSigning;
import pz.klasywsdl.WSSigningException_Exception;

@Service
@Slf4j
public class PodpisService {

    @Autowired
    TPSigning tpSigning;

    public String przekazDokumentDoPodpisania(byte[] dokument, String successURL, String failureURL, String additionalInfo){
        try {
            return tpSigning.addDocumentToSigning(dokument, successURL, failureURL, additionalInfo);
        } catch (WSSigningException_Exception e) {
            log.info("nie poszlo " +e);
        }
        return null;
    }
}
