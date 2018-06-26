package com.mkyong;

import egate.integracja.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EgateService {

    @Autowired
    ISign iSign;

    public SignRespond wykonajPodpisEgate(byte[] zgloszenie, String url) throws UnsupportedEncodingException {
        przygotujDaneDoZapytania(zgloszenie, url);
        return iSign.sign(przygotujDaneDoZapytania(zgloszenie,url));
    }

    private EntryToSign przygotujDaneDoZapytania(byte[] zgloszenie, String url) throws UnsupportedEncodingException {
        ArrayOfAttachment attachments = przygotujZalaczniki();
        EntryToSign entryToSign = new EntryToSign();

        entryToSign.setAttachments(attachments);
        entryToSign.setFileName("JAJAJAJAJA");
        entryToSign.setDocumentInfo("doc info");
        entryToSign.setRequestInfo("request info");
        entryToSign.setUrlCancel("url cancel");
        entryToSign.setUrlFail("url fail");
        entryToSign.setUrlSuccess("url success");
        entryToSign.setUnsignedContent(zgloszenie);

        return entryToSign;

    }

    private ArrayOfAttachment przygotujZalaczniki() throws UnsupportedEncodingException {
        Attachment attachment = new Attachment();
        attachment.setContent("".getBytes("UTF-8"));
        attachment.setExternalId("1");
        attachment.setFileName("example");

        List<Attachment> listaAttachemnt = new ArrayList<>();
        listaAttachemnt.add(attachment);

        ArrayOfAttachment arrayOfAttachment = new ArrayOfAttachment();

        return arrayOfAttachment;

    }
}
