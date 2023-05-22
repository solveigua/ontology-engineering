package com.ontology.verbalizer.utils;

import java.util.List;

public interface NorwegianSentenceVerbalizer {
    public String verbalizeNorwegianSubclassAxiom(String subclassVerbalization, String superclassVerbalization);
    public String verbalizeNorwegianUnionAxiom(String unionClassVerbalization, List<String> disjointClassesVerbalization);
    public String verbalizeNorwegianEquivalentClassesAxiom(List<String> classExpressions);
    public String verbalizeNorwegianDisjointClassesAxiom(List<String> classExpressions);
    public String verbalizeNorwegianClassExpression(String fillerName, String propertyName);
}
