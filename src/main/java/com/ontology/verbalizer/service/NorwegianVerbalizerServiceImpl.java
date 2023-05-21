/**
 * This class just takes in the input file from the controller
 * and calls the ontology extractor to get the ontology, then 
 * processes the ontology for verbalization and returns the
 * verbalization back.
 * 
 * @Author: khompland, ihagen, saune
 * @Date: May 17, 2023
 * 
 */
package com.ontology.verbalizer.service;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ontology.verbalizer.utils.OntologyExtractorUtil;

@Service
public class NorwegianVerbalizerServiceImpl implements NorwegianVerbalizerService {
    @Autowired
    OntologyExtractorUtil _ontologyExtractor;

    @Override
    public String getNorwegianVerbalization(String owlFile) {
        OWLOntology ontology = _ontologyExtractor.extractOntologyFromOwl(owlFile);
        System.out.println(ontology);

        return owlFile; // TODO: use above ontology to call your verbalization code
    }

}