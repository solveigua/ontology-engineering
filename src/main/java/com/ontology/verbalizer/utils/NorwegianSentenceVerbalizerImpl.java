package com.ontology.verbalizer.utils;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class NorwegianSentenceVerbalizerImpl implements NorwegianSentenceVerbalizer {

    @Override
    public String verbalizeNorwegianSubclassAxiom(String subclassVerbalization, String superclassVerbalization) {
        String sentence = subclassVerbalization + " er en/et " + superclassVerbalization;
        return sentence;
    }

    @Override
    public String verbalizeNorwegianUnionAxiom(String unionClassVerbalization,
            List<String> disjointClassesVerbalization) {
                String sentence = "DO IT";
                return sentence;
    }

    @Override
    public String verbalizeNorwegianEquivalentClassesAxiom(List<String> classExpressions) {
        String sentence = "DO IT";
        return sentence;
    }

    @Override
    public String verbalizeNorwegianDisjointClassesAxiom(List<String> classExpressions) {
        String sentence = String.join(" er ikke det samme som en/et  ", classExpressions);
        return sentence;
    }

    @Override
    public String verbalizeNorwegianClassExpression(String fillerName, String propertyName) {
        String sentence = "DO IT";
        return sentence;
    }
}
