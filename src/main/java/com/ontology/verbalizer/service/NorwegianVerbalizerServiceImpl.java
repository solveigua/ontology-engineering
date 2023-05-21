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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.checkerframework.checker.units.qual.A;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ontology.verbalizer.utils.OntologyExtractorUtil;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class NorwegianVerbalizerServiceImpl implements NorwegianVerbalizerService {
    @Autowired
    OntologyExtractorUtil _ontologyExtractor;

    @Override
    public String getNorwegianVerbalization(String owlFile) {
        
        

        
        return owlFile; // TODO: use above ontology to call your verbalization code
    }

}