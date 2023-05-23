/**
 * The purpose of this class is to process an ontology represented in owl
 * and verbalize all the axioms specified in the owl. These will be 
 * returned as a concatenated string back to the client for display.
 * 
 * @Author: pmakhupane
 * @Date: 21 May, 2023
 */

package com.ontology.verbalizer.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ontology.verbalizer.utils.sesotho.SesothoSentenceVerbalizer;

@Component
public class GrammarEngineImpl implements GrammarEngine {

    @Autowired
    SesothoSentenceVerbalizer _sesothoSentenceVerbalizer;
    @Autowired 
    NorwegianSentenceVerbalizer _norwegianSentenceVerbalizer;

    String language;

    @Override
    public String getVerbalization(OWLOntology ontology, String language) {
        this.language = language;
        return getAllVerbals(ontology);
    }

    private String getAllVerbals(OWLOntology ontology) {
        // Verbalize all axioms
        List<String> verbalizations = new ArrayList<>();

        for (OWLAxiom axiom : ontology.getAxioms()) {
            verbalizeAxiom(axiom, verbalizations, ontology);
            
        }

        // Create the concatenated verbalizations as a multi-line string
        StringBuilder concatenatedVerbalizations = new StringBuilder();
        for (String verbalization : verbalizations) {
            concatenatedVerbalizations.append(verbalization).append("\n");
        }
        return concatenatedVerbalizations.toString();
    }


    //Changed to not static to gain access of local language parameter.
    private void verbalizeAxiom(OWLAxiom axiom, List<String> verbalizations, OWLOntology ontology) {

        if (axiom instanceof OWLSubClassOfAxiom) {
            OWLSubClassOfAxiom subclassAxiom = (OWLSubClassOfAxiom) axiom;
            verbalizeSubclassAxiom(subclassAxiom, verbalizations);
        } else if (axiom instanceof OWLDisjointUnionAxiom) {
            OWLDisjointUnionAxiom unionAxiom = (OWLDisjointUnionAxiom) axiom;
            verbalizeUnionAxiom(unionAxiom, verbalizations);
        } else if (axiom instanceof OWLEquivalentClassesAxiom) {
            OWLEquivalentClassesAxiom equivalentClassesAxiom = (OWLEquivalentClassesAxiom) axiom;
            verbalizeEquivalentClassesAxiom(equivalentClassesAxiom, verbalizations);
        } else if (axiom instanceof OWLDisjointClassesAxiom) {
            OWLDisjointClassesAxiom disjointClassesAxiom = (OWLDisjointClassesAxiom) axiom;
            verbalizeDisjointClassesAxiom(disjointClassesAxiom, verbalizations);
        } else {
            // Handle other types of axioms if necessary
        }
    }

    private void verbalizeSubclassAxiom(OWLSubClassOfAxiom axiom, List<String> verbalizations) {
        // Verbalize subclass axiom
        String subclassVerbalization = verbalizeClassExpression(axiom.getSubClass());
        String superclassVerbalization = verbalizeClassExpression(axiom.getSuperClass());
        String sentence;
        if(this.language.equals("ST")){
            sentence = _sesothoSentenceVerbalizer.verbalizeSesothoSubclassAxiom(subclassVerbalization, superclassVerbalization);
        }
        else {
            sentence = _norwegianSentenceVerbalizer.verbalizeNorwegianSubclassAxiom(subclassVerbalization, superclassVerbalization);
        }
        verbalizations.add(sentence);
    }

    private void verbalizeUnionAxiom(OWLDisjointUnionAxiom axiom, List<String> verbalizations) {
        // Verbalize union axiom
        String unionClassVerbalization = verbalizeClassExpression(axiom.getOWLClass());
        List<String> disjointClassesVerbalization = axiom.getClassExpressions()
                .stream()
                .map(classExpression -> verbalizeClassExpression(classExpression))
                .collect(Collectors.toList());
        String verbalization;
        if(this.language.equals("ST")){
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoUnionAxiom(unionClassVerbalization, disjointClassesVerbalization);
        }
        else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianUnionAxiom(unionClassVerbalization, disjointClassesVerbalization);
        }
        verbalizations.add(verbalization);
    }

    private void verbalizeEquivalentClassesAxiom(OWLEquivalentClassesAxiom axiom, List<String> verbalizations) {
        // Verbalize equivalent classes axiom
        List<String> classExpressions = axiom.getClassExpressions()
                .stream()
                .map(classExpression -> verbalizeClassExpression(classExpression))
                .collect(Collectors.toList());
                String verbalization;
        if(this.language.equals("ST")){
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoEquivalentClassesAxiom(classExpressions);
        }
        else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianEquivalentClassesAxiom(classExpressions);
        }        verbalizations.add(verbalization);
    }

    private void verbalizeDisjointClassesAxiom(OWLDisjointClassesAxiom axiom, List<String> verbalizations) {
        // Verbalize disjoint classes axiom
        List<String> classExpressions = axiom.getClassExpressions()
                .stream()
                .map(classExpression -> verbalizeClassExpression(classExpression))
                .collect(Collectors.toList());
        
                String verbalization;
                if(this.language.equals("ST")){
                    verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoDisjointClassesAxiom(classExpressions);
                }
                else {
                    verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianDisjointClassesAxiom(classExpressions);
                }
        verbalizations.add(verbalization);
        
    }


    private String verbalizeClassExpression(OWLClassExpression classExpression) {
        if (classExpression.isAnonymous()) {
            // Handle anonymous class expressions
            if (classExpression instanceof OWLObjectSomeValuesFrom) {
                OWLObjectSomeValuesFrom someValuesFrom = (OWLObjectSomeValuesFrom) classExpression;
                OWLObjectPropertyExpression property = someValuesFrom.getProperty();
                OWLClassExpression filler = someValuesFrom.getFiller();

                if (property instanceof OWLObjectProperty && filler instanceof OWLClass) {
                    String propertyName = getPropertyVerbalization((OWLObjectProperty) property);
                    String fillerName = getClassExpressionVerbalization(filler);
                    String verbalization;
                    if(this.language.equals("ST")){
                        verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoClassExpression(fillerName, propertyName);
                    }
                    else {
                        verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianClassExpression(fillerName, propertyName);
                    }
                    return verbalization;
                }
            }

            // Handle other types of anonymous class expressions if necessary
            return "AnonymousClass";
        } else if (classExpression instanceof OWLClass) {
            OWLClass owlClass = (OWLClass) classExpression;
            // Get the IRI of the class and extract the fragment name
            String fragmentName = owlClass.getIRI().getFragment();
            if (fragmentName != null) {
                return fragmentName;
            } else {
                // If the fragment name is null, you can return the full IRI or handle it as
                // needed
                return owlClass.getIRI().toString();
            }
        }

        return "";
    }

    private String getPropertyVerbalization(OWLObjectProperty property) {
        // Verbalize the object property based on your grammar rules
        // You can update this method to handle different verbalizations for different
        // properties
        return property.getIRI().getFragment();
    }

    private String getClassExpressionVerbalization(OWLClassExpression classExpression) {
        // Verbalize the class expression based on your grammar rules
        // You can recursively call the verbalizeClassExpression method to handle nested
        // expressions
        return verbalizeClassExpression(classExpression);
    }

}
