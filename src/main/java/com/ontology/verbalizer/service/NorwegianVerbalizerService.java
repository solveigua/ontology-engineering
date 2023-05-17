package com.ontology.verbalizer.service;

import org.springframework.web.multipart.MultipartFile;

public interface NorwegianVerbalizerService
{
    public String getNorwegianVerbalization(MultipartFile owlFile);
}