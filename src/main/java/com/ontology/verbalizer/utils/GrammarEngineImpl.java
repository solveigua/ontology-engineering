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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
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
    HashMap<String, List<String>> verbalizations = new HashMap<>();

    @Override
    public HashMap<String, List<String>> getVerbalization(OWLOntology ontology, String language) {
        this.language = language;
        initiateHashMap();
        return getAllVerbals(ontology);
        // return this.verbalizations;
    }

    private HashMap<String, List<String>> getAllVerbals(OWLOntology ontology) {
        // Verbalize all axioms
        this.ontology = ontology;
        for (OWLAxiom axiom : ontology.getAxioms()) {
            verbalizeAxiom(axiom);
        }
        // System.out.println(verbalizations);

        // Create the concatenated verbalizations as a multi-line string

        // trengs denne?
        /*
         * StringBuilder concatenatedVerbalizations = new StringBuilder();
         * 
         * for (String verbalization : verbalizations) {
         * concatenatedVerbalizations.append(verbalization);
         * }
         * return concatenatedVerbalizations;
         */
        return this.verbalizations;

    }

    // Changed to not static to gain access of local language parameter.
    private void verbalizeAxiom(OWLAxiom axiom) {

        if (axiom instanceof OWLSubClassOfAxiom) {
            OWLSubClassOfAxiom subclassAxiom = (OWLSubClassOfAxiom) axiom;
            verbalizeSubclassAxiom(subclassAxiom);
        } else if (axiom instanceof OWLDisjointUnionAxiom) {
            // currently not used in african wildlife
            OWLDisjointUnionAxiom disjointUnionAxiom = (OWLDisjointUnionAxiom) axiom;
            verbalizeDisjointUnionAxiom(disjointUnionAxiom);
        } else if (axiom instanceof OWLEquivalentClassesAxiom) {
            OWLEquivalentClassesAxiom equivalentClassesAxiom = (OWLEquivalentClassesAxiom) axiom;
            verbalizeEquivalentClassesAxiom(equivalentClassesAxiom);
        } else if (axiom instanceof OWLDisjointClassesAxiom) {
            OWLDisjointClassesAxiom disjointClassesAxiom = (OWLDisjointClassesAxiom) axiom;
            verbalizeDisjointClassesAxiom(disjointClassesAxiom);
        } else if (axiom instanceof OWLReflexiveObjectPropertyAxiom) {
            OWLReflexiveObjectPropertyAxiom reflexiveObjectPropertyAxiom = (OWLReflexiveObjectPropertyAxiom) axiom;
            verbalizeReflexiveObjPropAx(reflexiveObjectPropertyAxiom);
        } else if (axiom instanceof OWLFunctionalObjectPropertyAxiom) {
            OWLFunctionalObjectPropertyAxiom functionalObjectPropertyAxiom = (OWLFunctionalObjectPropertyAxiom) axiom;
            verbalizeFunctionalObjPropAx(functionalObjectPropertyAxiom);
        } else if (axiom instanceof OWLInverseFunctionalObjectPropertyAxiom) {
            OWLInverseFunctionalObjectPropertyAxiom inverseFunctionalObjectPropertyAxiom = (OWLInverseFunctionalObjectPropertyAxiom) axiom;
            verbalizeInverseFuncObjPropAx(inverseFunctionalObjectPropertyAxiom);
        } else if (axiom instanceof OWLTransitiveObjectPropertyAxiom) {
            OWLTransitiveObjectPropertyAxiom transitiveObjectPropertyAxiom = (OWLTransitiveObjectPropertyAxiom) axiom;
            verbalizeTransitiveObjPropAx(transitiveObjectPropertyAxiom);
        } else if (axiom instanceof OWLSymmetricObjectPropertyAxiom) {
            OWLSymmetricObjectPropertyAxiom symmetricObjectPropertyAxiom = (OWLSymmetricObjectPropertyAxiom) axiom;
            verbalizeSymmetricObjPropAx(symmetricObjectPropertyAxiom);
        } else if (axiom instanceof OWLAsymmetricObjectPropertyAxiom) {
            OWLAsymmetricObjectPropertyAxiom asymmetricObjectPropertyAxiom = (OWLAsymmetricObjectPropertyAxiom) axiom;
            verbalizeAsymmetricObjPropAx(asymmetricObjectPropertyAxiom);
        } else if (axiom instanceof OWLIrreflexiveObjectPropertyAxiom) {
            OWLIrreflexiveObjectPropertyAxiom irreflexiveObjectPropertyAxiom = (OWLIrreflexiveObjectPropertyAxiom) axiom;
            verbalizeIrreflexiveObjPropAx(irreflexiveObjectPropertyAxiom);
        } else if (axiom instanceof OWLObjectPropertyRangeAxiom) {
            // System.out.println("OWLObjectPropertyRangeAxiom: " + axiom);
            OWLObjectPropertyRangeAxiom objectPropertyRangeAxiom = (OWLObjectPropertyRangeAxiom) axiom;
            verbalizeObjectPropRangeAx(objectPropertyRangeAxiom);
        } else if (axiom instanceof OWLSubObjectPropertyOfAxiom) {

            OWLSubObjectPropertyOfAxiom subObjectPropertyOfAxiom = (OWLSubObjectPropertyOfAxiom) axiom;
            verbalizeSubObjectPropAx(subObjectPropertyOfAxiom);
        } else if (axiom instanceof OWLInverseObjectPropertiesAxiom) {
            OWLInverseObjectPropertiesAxiom inverseObjectPropertiesAxiom = (OWLInverseObjectPropertiesAxiom) axiom;
            verbalizeInversePropAx(inverseObjectPropertiesAxiom);

        } else {
            // System.out.println("ELSE: "+axiom+" ");
            // These are in african wildlife and currently not being handled:
            if (!((axiom.getAxiomType().toString() == "AnnotationAssertion")
                    || (axiom.getAxiomType().toString() == "Declaration"))) {
                this.verbalizations.get("unknown").add(axiom.getAxiomType().toString());
            }

        }
    }

    private void verbalizeInversePropAx(OWLInverseObjectPropertiesAxiom axiom) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String sentence;
        if (this.language.equals("st")) {
            sentence = _sesothoSentenceVerbalizer.verbalizeSesothoInversePropAx(property);
        } else {
            sentence = _norwegianSentenceVerbalizer.verbalizeNorwegianInversePropAx(property);
        }
        this.verbalizations.get("inverse").add(sentence);
    }

    private void verbalizeSubclassAxiom(OWLSubClassOfAxiom axiom) {
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
        this.verbalizations.get("subclass").add(sentence);
    }

    private void verbalizeDisjointUnionAxiom(OWLDisjointUnionAxiom axiom) {
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
        this.verbalizations.get("union").add(verbalization);
    }

    private void verbalizeEquivalentClassesAxiom(OWLEquivalentClassesAxiom axiom) {
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
        this.verbalizations.get("equivalent").add(verbalization);
    }

    private void verbalizeDisjointClassesAxiom(OWLDisjointClassesAxiom axiom) {
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
        this.verbalizations.get("disjoint").add(verbalization);

    }

    private void verbalizeIrreflexiveObjPropAx(OWLIrreflexiveObjectPropertyAxiom axiom) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoIrrefObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianIrrefObjProp(property);
        }
        this.verbalizations.get("irreflexive").add(verbalization);
    }

    private void verbalizeAsymmetricObjPropAx(OWLAsymmetricObjectPropertyAxiom axiom) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoAsymObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegiaAsymObjProp(property);
        }
        this.verbalizations.get("asymmetric").add(verbalization);
    }

    private void verbalizeSymmetricObjPropAx(OWLSymmetricObjectPropertyAxiom axiom) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoSymObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianSymObjProp(property);
        }
        this.verbalizations.get("symmetric").add(verbalization);
    }

    private void verbalizeTransitiveObjPropAx(OWLTransitiveObjectPropertyAxiom axiom) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoTransObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianTransObjProp(property);
        }
        this.verbalizations.get("transitive").add(verbalization);
    }

    private void verbalizeInverseFuncObjPropAx(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoInverseObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianInverseObjProp(property);
        }
        this.verbalizations.get("inverseFunctional").add(verbalization);
    }

    private void verbalizeFunctionalObjPropAx(OWLFunctionalObjectPropertyAxiom axiom) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoFuncObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianFunObjProp(property);
        }
        this.verbalizations.get("functional").add(verbalization);
    }

    private void verbalizeReflexiveObjPropAx(OWLReflexiveObjectPropertyAxiom axiom) {
        List<String> property = axiom.getObjectPropertiesInSignature().stream()
                .map(propExpression -> getPropertyVerbalization(propExpression))
                .collect(Collectors.toList());
        String verbalization;
        if (this.language.equals("st")) {
            verbalization = _sesothoSentenceVerbalizer.verbalizeSesothoRefObjProp(property);
        } else {
            verbalization = _norwegianSentenceVerbalizer.verbalizeNorwegianRefObjProp(property);
        }
        this.verbalizations.get("reflexive").add(verbalization);
    }

    private void verbalizeObjectPropRangeAx(OWLObjectPropertyRangeAxiom axiom) {

    }

    private void verbalizeSubObjectPropAx(OWLSubObjectPropertyOfAxiom axiom) {
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
        this.verbalizations.get("subObjectProperty").add(sentence);
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
        return "(missing translation)";
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
            return propertyNameInLanguage;
        } else {
        }
        return "(missing translation)";
    }

    private String getClassExpressionVerbalization(OWLClassExpression classExpression) {
        // Verbalize the class expression based on your grammar rules
        // You can recursively call the verbalizeClassExpression method to handle nested
        // expressions
        return verbalizeClassExpression(classExpression);
    }

    /**
     * 
     */
    private void initiateHashMap() {
        this.verbalizations.put("union", new ArrayList<String>());
        this.verbalizations.put("equivalent", new ArrayList<String>());
        this.verbalizations.put("irreflexive", new ArrayList<String>());
        this.verbalizations.put("subclass", new ArrayList<String>());
        this.verbalizations.put("disjoint", new ArrayList<String>());
        this.verbalizations.put("transitive", new ArrayList<String>());
        this.verbalizations.put("subObjectProperty", new ArrayList<String>());
        this.verbalizations.put("functional", new ArrayList<String>());
        this.verbalizations.put("inverseFunctional", new ArrayList<String>());
        this.verbalizations.put("reflexive", new ArrayList<String>());
        this.verbalizations.put("asymmetric", new ArrayList<String>());
        this.verbalizations.put("symmetric", new ArrayList<String>());
        this.verbalizations.put("inverse", new ArrayList<String>());

        this.verbalizations.put("unknown", new ArrayList<String>());
    }

}
