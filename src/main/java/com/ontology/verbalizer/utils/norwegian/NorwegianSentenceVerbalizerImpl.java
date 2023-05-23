package com.ontology.verbalizer.utils.norwegian;

import java.util.List;
import java.util.stream.Collectors;

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
        
        String sentence = classExpressions.stream()
            .map(n -> String.valueOf(n))
            .limit(classExpressions.size()-1)
            .collect(Collectors.joining(", "));

        return sentence + " og " + classExpressions.get(classExpressions.size()-1) + " er ikke det samme.";
    }

    @Override
    public String verbalizeNorwegianClassExpression(String fillerName, String propertyName) {
        String sentence = "DO IT";
        return sentence;
    }
}
