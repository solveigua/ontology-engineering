package com.ontology.verbalizer.utils;

import org.semanticweb.owlapi.model.OWLOntology;

public interface GrammarEngine {
    public String getVerbalization(OWLOntology ontology, String language);
}
