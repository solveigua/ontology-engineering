/**
 * This class just takes in the input file from the controller
 * and calls the ontology extractor to get the ontology, then 
 * processes the ontology for verbalization and returns the
 * verbalization back.
 * 
 * @Author: pmakhupane
 * @Date: May 17, 2023
 * 
 */

package com.ontology.verbalizer.service;

import java.util.HashMap;
import java.util.List;

import org.semanticweb.owlapi.model.OWLOntology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ontology.verbalizer.utils.OntologyExtractorUtil;
import com.ontology.verbalizer.utils.GrammarEngine;

@Service
public class VerbalizerServiceImpl implements VerbalizerService {
    @Autowired
    OntologyExtractorUtil _ontologyExtractor;
    @Autowired
    GrammarEngine GrammarEngine;

    @Override
    public HashMap<String, List<String>> getVerbalization(String owlFile, String language) {
        OWLOntology ontology = _ontologyExtractor.extractOntologyFromOwl(owlFile);

        return GrammarEngine.getVerbalization(ontology, language);
    }
}
