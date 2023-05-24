package com.ontology.verbalizer.utils;

import java.util.HashMap;
import java.util.List;

import org.semanticweb.owlapi.model.OWLOntology;

public interface GrammarEngine {
    public HashMap<String, List<String>> getVerbalization(OWLOntology ontology, String language);
}
