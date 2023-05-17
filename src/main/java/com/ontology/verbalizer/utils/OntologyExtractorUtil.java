package com.ontology.verbalizer.utils;

import org.semanticweb.owlapi.model.OWLOntology;
import org.springframework.web.multipart.MultipartFile;

public interface OntologyExtractorUtil
{
    public OWLOntology extractOntologyFromOwl(MultipartFile owlMultipart);
}