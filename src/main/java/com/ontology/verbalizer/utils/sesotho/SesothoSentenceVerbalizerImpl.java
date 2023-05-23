package com.ontology.verbalizer.utils.sesotho;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SesothoSentenceVerbalizerImpl implements SesothoSentenceVerbalizer {

    @Override
    public String verbalizeSesothoSubclassAxiom(String subclassVerbalization, String superclassVerbalization) {
        String sentence = subclassVerbalization + " ke " + superclassVerbalization;
        return sentence;
    }

    @Override
    public String verbalizeSesothoUnionAxiom(String unionClassVerbalization,
            List<String> disjointClassesVerbalization) {
        String sentence = unionClassVerbalization + " ke " + String.join(" le ", disjointClassesVerbalization);
        return sentence;
    }

    @Override
    public String verbalizeSesothoEquivalentClassesAxiom(List<String> classExpressions) {
        String sentence = String.join(" e tshwana le ", classExpressions);
        return sentence;
    }

    @Override
    public String verbalizeSesothoDisjointClassesAxiom(List<String> classExpressions) {
        String sentence = String.join(" ha he tswhane tu le ", classExpressions);
        return sentence;
    }

    @Override
    public String verbalizeSesothoClassExpression(String fillerName, String propertyName) {
        String sentence = fillerName + " e na le " + propertyName;
        return sentence;
    }
}
