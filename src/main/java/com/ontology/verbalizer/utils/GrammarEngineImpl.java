/**
 * The purpose of this class is to process an ontology represented in owl
 * and verbalize all the axioms specified in the owl. These will be 
 * returned as a concatenated string back to the client for display.
 * 
 * @Author: pmakhupane, hageningrid and karenhompland
 * @Date: 21 May, 2023
 */

package com.ontology.verbalizer.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.core.IsInstanceOf;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ontology.verbalizer.utils.norwegian.NorwegianSentenceVerbalizer;
import com.ontology.verbalizer.utils.sesotho.SesothoSentenceVerbalizer;

@Component
public class GrammarEngineImpl implements GrammarEngine {

    @Autowired
    SesothoSentenceVerbalizer _sesothoSentenceVerbalizer;
    @Autowired
    NorwegianSentenceVerbalizer _norwegianSentenceVerbalizer;

    String language;
    OWLOntology ontology;
    OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
    OWLDataFactory factory = manager.getOWLDataFactory();

    @Override
    public String getVerbalization(OWLOntology ontology, String language) {
        this.language = language;
        return getAllVerbals(ontology);
    }

    private String getAllVerbals(OWLOntology ontology) {
        // Verbalize all axioms
        List<String> verbalizations = new ArrayList<>();
        this.ontology = ontology;

        for (OWLAxiom axiom : ontology.getAxioms()) {
            verbalizeAxiom(axiom, verbalizations);

        }

        // Create the concatenated verbalizations as a multi-line string
        StringBuilder concatenatedVerbalizations = new StringBuilder();
        for (String verbalization : verbalizations) {
            concatenatedVerbalizations.append(verbalization).append("\n");
        }
        return concatenatedVerbalizations.toString();
    }

    // Changed to not static to gain access of local language parameter.
    private void verbalizeAxiom(OWLAxiom axiom, List<String> verbalizations) {

        if (axiom instanceof OWLSubClassOfAxiom) {
            OWLSubClassOfAxiom subclassAxiom = (OWLSubClassOfAxiom) axiom;
            verbalizeSubclassAxiom(subclassAxiom, verbalizations);
        } else if (axiom instanceof OWLDisjointUnionAxiom) {
            // currently not used in african wildlife
            OWLDisjointUnionAxiom disjointUnionAxiom = (OWLDisjointUnionAxiom) axiom;
            verbalizeDisjointUnionAxiom(disjointUnionAxiom, verbalizations);
        } else if (axiom instanceof OWLEquivalentClassesAxiom) {
            OWLEquivalentClassesAxiom equivalentClassesAxiom = (OWLEquivalentClassesAxiom) axiom;
            verbalizeEquivalentClassesAxiom(equivalentClassesAxiom, verbalizations);
        } else if (axiom instanceof OWLDisjointClassesAxiom) {
            OWLDisjointClassesAxiom disjointClassesAxiom = (OWLDisjointClassesAxiom) axiom;
            verbalizeDisjointClassesAxiom(disjointClassesAxiom, verbalizations);
        } else if (axiom instanceof OWLReflexiveObjectPropertyAxiom) {
            OWLReflexiveObjectPropertyAxiom reflexiveObjectPropertyAxiom = (OWLReflexiveObjectPropertyAxiom) axiom;
            verbalizeReflexiveObjPropAx(reflexiveObjectPropertyAxiom, verbalizations);
        } else if (axiom instanceof OWLFunctionalObjectPropertyAxiom) {
            OWLFunctionalObjectPropertyAxiom functionalObjectPropertyAxiom = (OWLFunctionalObjectPropertyAxiom) axiom;
            verbalizeFunctionalObjPropAx(functionalObjectPropertyAxiom, verbalizations);
        } else if (axiom instanceof OWLInverseFunctionalObjectPropertyAxiom) {
            OWLInverseFunctionalObjectPropertyAxiom inverseFunctionalObjectPropertyAxiom = (OWLInverseFunctionalObjectPropertyAxiom) axiom;
            verbalizeInverseFuncObjPropAx(inverseFunctionalObjectPropertyAxiom, verbalizations);
        } else if (axiom instanceof OWLTransitiveObjectPropertyAxiom) {
            OWLTransitiveObjectPropertyAxiom transitiveObjectPropertyAxiom = (OWLTransitiveObjectPropertyAxiom) axiom;
            verbalizeTransitiveObjPropAx(transitiveObjectPropertyAxiom, verbalizations);
        } else if (axiom instanceof OWLSymmetricObjectPropertyAxiom) {
            OWLSymmetricObjectPropertyAxiom symmetricObjectPropertyAxiom = (OWLSymmetricObjectPropertyAxiom) axiom;
            verbalizeSymmetricObjPropAx(symmetricObjectPropertyAxiom, verbalizations);
        } else if (axiom instanceof OWLAsymmetricObjectPropertyAxiom) {
            OWLAsymmetricObjectPropertyAxiom asymmetricObjectPropertyAxiom = (OWLAsymmetricObjectPropertyAxiom) axiom;
            verbalizeAsymmetricObjPropAx(asymmetricObjectPropertyAxiom, verbalizations);
        } else if (axiom instanceof OWLIrreflexiveObjectPropertyAxiom) {
            OWLIrreflexiveObjectPropertyAxiom irreflexiveObjectPropertyAxiom = (OWLIrreflexiveObjectPropertyAxiom) axiom;
            verbalizeIrreflexiveObjPropAx(irreflexiveObjectPropertyAxiom, verbalizations);
        } 
        else if(axiom instanceof OWLObjectPropertyRangeAxiom) {
            //System.out.println("OWLObjectPropertyRangeAxiom: " + axiom);
            OWLObjectPropertyRangeAxiom objectPropertyRangeAxiom = (OWLObjectPropertyRangeAxiom) axiom;
            verbalizeObjectPropRangeAx(objectPropertyRangeAxiom, verbalizations);
        }
        else if(axiom instanceof OWLSubObjectPropertyOfAxiom) {
            System.out.println("OWLSubObjectPropertyOfAxiom: " + axiom);
            OWLSubObjectPropertyOfAxiom subObjectPropertyOfAxiom = (OWLSubObjectPropertyOfAxiom) axiom;
            verbalizeSubObjectPropAx(subObjectPropertyOfAxiom, verbalizations);
        }
        else {
            //System.out.println("ELSE: "+axiom+" ");
            // These are in african wildlife and currently not being handled:
            // SubObjectPropertyOf:
            // <SubObjectPropertyOf>
            //     <ObjectProperty IRI="#is-proper-part-of"/>
            //     <ObjectProperty IRI="#is-part-of"/>
            // </SubObjectPropertyOf>

            // ObjectPropertyRange:
            // <ObjectPropertyRange>
            //     <ObjectProperty IRI="#eats"/>
            //     <ObjectUnionOf>
            //         <Class IRI="#animal"/>
            //         <Class IRI="#plant"/>
            //         <ObjectSomeValuesFrom>
            //             <ObjectProperty IRI="#is-part-of"/>
            //             <Class IRI="#animal"/>
            //         </ObjectSomeValuesFrom>
            //         <ObjectSomeValuesFrom>
            //             <ObjectProperty IRI="#is-part-of"/>
            //             <Class IRI="#plant"/>
            //         </ObjectSomeValuesFrom>
            //     </ObjectUnionOf>
            // </ObjectPropertyRange>
        }
    }

    private void verbalizeSubclassAxiom(OWLSubClassOfAxiom axiom, List<String> verbalizations) {
        // Verbalize subclass axiom
        String subclassVerbalization = verbalizeClassExpression(axiom.getSubClass());
        String superclassVerbalization = verbalizeClassExpression(axiom.getSuperClass());
        String sentence;
        if (this.language.equals("st")) {
            sentence = _sesothoSentenceVerbalizer.verbalizeSesothoSubclassAxiom(subclassVerbalization,
                    superclassVerbalization);
        } else {
            sentence = _norwegianSentenceVerbalizer.verbalizeNorwegianSubclassAxiom(subclassVerbalization,
                    superclassVerbalization);
        }
        verbalizations.add(sentence);
    }

    private void verbalizeDisjointUnionAxiom(OWLDisjointUnionAxiom axiom, List<String> verbalizations) {
        // Verbalize union axiom
        String unionClassVerbalization = verbalizeClassExpression(axiom.getOWLClass());
        List<String> disjointClassesVerbalization = axiom.getClassExpressions()
                .stream()
                .map(classExpression -> verbalizeClassExpression(classExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoUnionAxiom(unionClassVerbalization,
                    disjointClassesVerbalization);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianUnionAxiom(unionClassVerbalization,
                    disjointClassesVerbalization);
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
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoEquivalentClassesAxiom(classExpressions);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianEquivalentClassesAxiom(classExpressions);
        }
        verbalizations.add(verbalization);
    }

    private void verbalizeDisjointClassesAxiom(OWLDisjointClassesAxiom axiom, List<String> verbalizations) {
        // Verbalize disjoint classes axiom
        List<String> classExpressions = axiom.getClassExpressions()
                .stream()
                .map(classExpression -> verbalizeClassExpression(classExpression))
                .collect(Collectors.toList());

        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoDisjointClassesAxiom(classExpressions);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianDisjointClassesAxiom(classExpressions);
        }
        verbalizations.add(verbalization);

    }

    private void verbalizeIrreflexiveObjPropAx(OWLIrreflexiveObjectPropertyAxiom axiom,
            List<String> verbalizations) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoIrrefObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianIrrefObjProp(property);
        }
        verbalizations.add(verbalization);
    }

    private void verbalizeAsymmetricObjPropAx(OWLAsymmetricObjectPropertyAxiom axiom,
            List<String> verbalizations) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoAsymObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegiaAsymObjProp(property);
        }
        verbalizations.add(verbalization);
    }

    private void verbalizeSymmetricObjPropAx(OWLSymmetricObjectPropertyAxiom axiom,
            List<String> verbalizations) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoSymObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianSymObjProp(property);
        }
        verbalizations.add(verbalization);
    }

    private void verbalizeTransitiveObjPropAx(OWLTransitiveObjectPropertyAxiom axiom,
            List<String> verbalizations) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoTransObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianTransObjProp(property);
        }
        verbalizations.add(verbalization);
    }

    private void verbalizeInverseFuncObjPropAx(OWLInverseFunctionalObjectPropertyAxiom axiom, List<String> verbalizations) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoInverseObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianInverseObjProp(property);
        }
        verbalizations.add(verbalization);
    }

    private void verbalizeFunctionalObjPropAx(OWLFunctionalObjectPropertyAxiom axiom,
            List<String> verbalizations) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoFuncObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianFunObjProp(property);
        }
        verbalizations.add(verbalization);
    }

    private void verbalizeReflexiveObjPropAx(OWLReflexiveObjectPropertyAxiom axiom,
            List<String> verbalizations) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoRefObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianRefObjProp(property);
        }
        verbalizations.add(verbalization);
    }

    private void verbalizeObjectPropRangeAx(OWLObjectPropertyRangeAxiom axiom, List<String> verbalizations){


    }

    private void verbalizeSubObjectPropAx(OWLSubObjectPropertyOfAxiom axiom, List<String> verbalizations){
            String subPropVerbalization = getPropertyVerbalization(axiom.getSubProperty().getNamedProperty());
            String superPropVerbalization = getPropertyVerbalization(axiom.getSuperProperty().getNamedProperty());
            String sentence;
            if (this.language.equals("st")) {
                sentence = _sesothoSentenceVerbalizer.verbalizeSesothoSubPropAxiom(subPropVerbalization,
                        superPropVerbalization);
            } else {
                sentence = _norwegianSentenceVerbalizer.verbalizeNorwegianSubPropAxiom(subPropVerbalization,
                        superPropVerbalization);
            }
            verbalizations.add(sentence);
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
                    if (this.language.equals("st")) {
                        verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoClassExpression(fillerName,
                                propertyName);
                    } else {
                        verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianClassExpression(fillerName,
                                propertyName);
                    }
                    return verbalization;
                }
            }

            // System.out.println(classExpression.toString());
            // Handle other types of anonymous class expressions if necessary
            return "AnonymousClass";
        } else if (classExpression instanceof OWLClass) {
            // Get the class in correct language
            OWLClass owlClass = (OWLClass) classExpression;
            String languageTag = this.language;

            OWLAnnotationProperty labelAnnotationProperty = factory.getRDFSLabel();
            OWLAnnotation labelAnnotation = EntitySearcher
                    .getAnnotations(owlClass, this.ontology, labelAnnotationProperty)
                    .filter(annotation -> annotation.getValue() instanceof OWLLiteral)
                    .filter(annotation -> ((OWLLiteral) annotation.getValue()).hasLang(languageTag))
                    .findFirst()
                    .orElse(null);

            if (labelAnnotation != null) {
                OWLLiteral labelLiteral = (OWLLiteral) labelAnnotation.getValue();
                String classNameInLanguage = labelLiteral.getLiteral();
                // System.out.println("Class name in " + languageTag + ": " +
                // classNameInLanguage);
                return classNameInLanguage;
            } else {
                System.out.println("Class name not found in " + languageTag);
            }
        }
        return "";
    }

    private String getPropertyVerbalization(OWLObjectProperty property) {
        // Get the property in the correct language
        OWLAnnotationProperty labelAnnotationProperty = factory.getRDFSLabel();
        OWLAnnotation labelAnnotation = EntitySearcher.getAnnotations(property, ontology, labelAnnotationProperty)
                .filter(annotation -> annotation.getValue() instanceof OWLLiteral)
                .filter(annotation -> ((OWLLiteral) annotation.getValue()).hasLang(this.language))
                .findFirst()
                .orElse(null);

        if (labelAnnotation != null) {
            OWLLiteral labelLiteral = (OWLLiteral) labelAnnotation.getValue();
            String propertyNameInLanguage = labelLiteral.getLiteral();
            // System.out.println("Property name in " + language + ": " +
            // propertyNameInLanguage);
            return propertyNameInLanguage;
        } else {
            // System.out.println("Property name not found in " + language);
        }
        return "";
    }

    private String getClassExpressionVerbalization(OWLClassExpression classExpression) {
        // Verbalize the class expression based on your grammar rules
        // You can recursively call the verbalizeClassExpression method to handle nested
        // expressions
        return verbalizeClassExpression(classExpression);
    }

}
