package com.ontology.verbalizer.utils.norwegian;

import java.util.List;

public interface NorwegianSentenceVerbalizer {
    public String verbalizeNorwegianSubclassAxiom(String subclassVerbalization, String superclassVerbalization);
    public String verbalizeNorwegianUnionAxiom(String unionClassVerbalization, List<String> disjointClassesVerbalization);
    public String verbalizeNorwegianEquivalentClassesAxiom(List<String> classExpressions);
    public String verbalizeNorwegianDisjointClassesAxiom(List<String> classExpressions);
    public String verbalizeNorwegianClassExpression(String fillerName, String propertyName);
    public String verbalizeNorwegianIrrefObjProp(List<String> property);
    public String verbalizeNorwegiaAsymObjProp(List<String> property);
    public String verbalizeNorwegianSymObjProp(List<String> property);
    public String verbalizeNorwegianTransObjProp(List<String> property);
    public String verbalizeNorwegianInverseObjProp(List<String> property);
    public String verbalizeNorwegianFunObjProp(List<String> property);
    public String verbalizeNorwegianRefObjProp(List<String> property);
    public String verbalizeNorwegianSubPropAxiom(String subProperty, String superProperty);
    public String verbalizeObjectPropRangeAx(String property, String range);
    public String verbalizeEquivalentClassesAxiom(List<String> classExpressions);
}
