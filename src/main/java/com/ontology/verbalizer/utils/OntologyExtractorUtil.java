package com.ontology.verbalizer.utils;

import org.semanticweb.owlapi.model.OWLOntology;

public interface OntologyExtractorUtil
{
    public OWLOntology extractOntologyFromOwl(String owlString);
}