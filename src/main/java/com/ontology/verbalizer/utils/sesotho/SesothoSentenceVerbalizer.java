package com.ontology.verbalizer.utils.sesotho;

import java.util.List;

public interface SesothoSentenceVerbalizer {
    public String verbalizeSesothoSubclassAxiom(String subclassVerbalization, String superclassVerbalization);
    public String verbalizeSesothoUnionAxiom(String unionClassVerbalization, List<String> disjointClassesVerbalization);
    public String verbalizeSesothoEquivalentClassesAxiom(List<String> classExpressions);
    public String verbalizeSesothoDisjointClassesAxiom(List<String> classExpressions);
    public String verbalizeSesothoClassExpression(String fillerName, String propertyName);
    public String verbalizeSesothoIrrefObjProp(List<String> property);
    public String verbalizeSesothoAsymObjProp(List<String> property);
    public String verbalizeSesothoSymObjProp(List<String> property);
    public String verbalizeSesothoTransObjProp(List<String> property);
    public String verbalizeSesothoInverseObjProp(List<String> property);
    public String verbalizeSesothoFuncObjProp(List<String> property);
    public String verbalizeSesothoRefObjProp(List<String> property);
}
