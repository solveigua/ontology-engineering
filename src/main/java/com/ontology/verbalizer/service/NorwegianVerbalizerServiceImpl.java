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

import org.semanticweb.owlapi.model.OWLOntology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ontology.verbalizer.utils.OntologyExtractorUtil;

@Service
public class NorwegianVerbalizerServiceImpl implements NorwegianVerbalizerService
{
    @Autowired
    OntologyExtractorUtil _ontologyExtractor;
    
    @Override
    public String getNorwegianVerbalization(MultipartFile owlFile) {
        OWLOntology ontology = _ontologyExtractor.extractOntologyFromOwl(owlFile);
        
        return "dumme svar"; //TODO: use above ontology to call your verbalization code
    }

}