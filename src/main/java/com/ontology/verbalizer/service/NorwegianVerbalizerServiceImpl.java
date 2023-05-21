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
        OWLOntology ontology = _ontologyExtractor.extractOntologyFromOwl(owlFile);
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();

        ArrayList<String> results = new ArrayList<String>();

        Set<OWLLogicalAxiom> axioms = ontology.getLogicalAxioms();

        for (OWLLogicalAxiom owlLogicalAxiom : axioms) {
            String type = owlLogicalAxiom.getAxiomType().toString();
            ArrayList<String> axiomClasses = new ArrayList<String>();

            for (OWLClass owlClass : owlLogicalAxiom.getClassesInSignature()) {
                axiomClasses.add(owlClass.getIRI().getFragment());
            }

            String verbalisation = "";

            if (type == "SubClassOf") {
                verbalisation = "En/Et" + axiomClasses.get(0) + "er en/et " + axiomClasses.get(1);
                System.out.println(verbalisation);
            }

            else if (type == "ObjectPropertyDomain") {

            }

            else if (type == "ClassAssertion") {

            }

            else if (type == "EquivalentClasses") {

            }

            else if (type == "InverseFunctionalObjectProperty") {

            }

            else if (type == "InverseObjectProperties") {

            }

            results.add(verbalisation);

            System.out.println();
            System.out.println("Axiom: " + owlLogicalAxiom);
            System.out.println("GetIndividualsInSignature: " + owlLogicalAxiom.getIndividualsInSignature());
            System.out.println("GetDataPropertiesInSignature: " + owlLogicalAxiom.getDataPropertiesInSignature());
            System.out.println("GetObjectPropertiesInSignature: " + owlLogicalAxiom.getObjectPropertiesInSignature());
            System.out.println("GetClasses: " + owlLogicalAxiom.getClassesInSignature());
            System.out.println("GetType: " + owlLogicalAxiom.getAxiomType());
            System.out.println();

        }
        return owlFile; // TODO: use above ontology to call your verbalization code
    }

}