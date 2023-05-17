package com.ontology.verbalizer.service;

import org.springframework.web.multipart.MultipartFile;

public interface SesothoVerbalizerService
{
    public String getSesothoVerbalization(MultipartFile owlFile);
}