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
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.springframework.stereotype.Component;

@Component
public class SesothoGrammarEngineImpl implements SesothoGrammarEngine {

    String language;

    @Override
    public String getSesothoVerbalization(OWLOntology ontology, String language) {
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
        
       
        // System.out.println("Beginning loop");
        // for (OWLAnnotation annotation : ontology.getAnnotations()) {
        //     if (annotation.getValue() instanceof OWLLiteral) {
        //         OWLLiteral val = (OWLLiteral) annotation.getValue();
        //         // if (val.hasLang("en")) {
        //         System.out.println(val + " -> " + val.getLiteral());
        //         // }
        //     }
        // }
        //System.out.println("Finished loop");

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
            sentence = subclassVerbalization + " ke " + superclassVerbalization;
        }
        else {
            sentence = subclassVerbalization + " er en/et " + superclassVerbalization;
        }
        verbalizations.add(sentence);
    }

    private static void verbalizeUnionAxiom(OWLDisjointUnionAxiom axiom, List<String> verbalizations) {
        // Verbalize union axiom
        String unionClassVerbalization = verbalizeClassExpression(axiom.getOWLClass());
        List<String> disjointClassesVerbalization = axiom.getClassExpressions()
                .stream()
                .map(classExpression -> verbalizeClassExpression(classExpression))
                .collect(Collectors.toList());
        String verbalization = unionClassVerbalization + " ke " + String.join(" le ", disjointClassesVerbalization);
        verbalizations.add(verbalization);
    }

    private static void verbalizeEquivalentClassesAxiom(OWLEquivalentClassesAxiom axiom, List<String> verbalizations) {
        // Verbalize equivalent classes axiom
        List<String> classExpressions = axiom.getClassExpressions()
                .stream()
                .map(classExpression -> verbalizeClassExpression(classExpression))
                .collect(Collectors.toList());
        String verbalization = String.join(" e tshwana le ", classExpressions);
        verbalizations.add(verbalization);
    }

    private void verbalizeDisjointClassesAxiom(OWLDisjointClassesAxiom axiom, List<String> verbalizations) {
        // Verbalize disjoint classes axiom
        List<String> classExpressions = axiom.getClassExpressions()
                .stream()
                .map(classExpression -> verbalizeClassExpression(classExpression))
                .collect(Collectors.toList());
        
                String sentence;
        if(this.language.equals("ST")){
            sentence = String.join(" ha he tswhane tu le ", classExpressions);
        }
        else {
            sentence = String.join(" er ikke det samme som en/et  ", classExpressions);
        }
        verbalizations.add(sentence);
        
    }

    private static String verbalizeClassExpression(OWLClassExpression classExpression) {
        if (classExpression.isAnonymous()) {
            // Handle anonymous class expressions
            if (classExpression instanceof OWLObjectSomeValuesFrom) {
                OWLObjectSomeValuesFrom someValuesFrom = (OWLObjectSomeValuesFrom) classExpression;
                OWLObjectPropertyExpression property = someValuesFrom.getProperty();
                OWLClassExpression filler = someValuesFrom.getFiller();

                if (property instanceof OWLObjectProperty && filler instanceof OWLClass) {
                    String propertyName = getPropertyVerbalization((OWLObjectProperty) property);
                    String fillerName = getClassExpressionVerbalization(filler);

                    return fillerName + " e na le " + propertyName;
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

    private static String getPropertyVerbalization(OWLObjectProperty property) {
        // Verbalize the object property based on your grammar rules
        // You can update this method to handle different verbalizations for different
        // properties
        return property.getIRI().getFragment();
    }

    private static String getClassExpressionVerbalization(OWLClassExpression classExpression) {
        // Verbalize the class expression based on your grammar rules
        // You can recursively call the verbalizeClassExpression method to handle nested
        // expressions
        return verbalizeClassExpression(classExpression);
    }

}
