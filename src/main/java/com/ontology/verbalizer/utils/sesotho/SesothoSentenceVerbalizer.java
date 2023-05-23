package com.ontology.verbalizer.utils.sesotho;

import java.util.List;

public interface SesothoSentenceVerbalizer {
    public String verbalizeSesothoSubclassAxiom(String subclassVerbalization, String superclassVerbalization);
    public String verbalizeSesothoUnionAxiom(String unionClassVerbalization, List<String> disjointClassesVerbalization);
    public String verbalizeSesothoEquivalentClassesAxiom(List<String> classExpressions);
    public String verbalizeSesothoDisjointClassesAxiom(List<String> classExpressions);
    public String verbalizeSesothoClassExpression(String fillerName, String propertyName);
}
