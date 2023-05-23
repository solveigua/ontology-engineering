package com.ontology.verbalizer.utils.norwegian;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ontology.verbalizer.utils.WordAndSentenceCleaner;

@Component
public class NorwegianSentenceVerbalizerImpl implements NorwegianSentenceVerbalizer {

    @Autowired
    NorwegianNounClassifier NorwegianNounClassifier;
    @Autowired
    WordAndSentenceCleaner WordAndSentenceCleaner;

    @Override
    public String verbalizeNorwegianSubclassAxiom(String subclassVerbalization, String superclassVerbalization) {
        String subclass = WordAndSentenceCleaner.splitClass(subclassVerbalization);
        String superclass = WordAndSentenceCleaner.splitClass(superclassVerbalization);
        if (NorwegianNounClassifier.getIsNounNeutral(superclassVerbalization)){
            superclass = "et " + superclass;
        }
        else {
            superclass = "en " + superclass;
        }
        if (NorwegianNounClassifier.getIsNounNeutral(subclassVerbalization)){
            subclass = "et " + subclass;
        }
        else {
            subclass = "en " + subclass;
        }
        return WordAndSentenceCleaner.cleanUpSentence(subclass+" er "+superclass);
    }

    @Override
    public String verbalizeNorwegianUnionAxiom(String unionClassVerbalization,
            List<String> disjointClassesVerbalization) {
                String sentence = String.join("unionClassVerbalization: "+unionClassVerbalization+" disjointClassesVerbalization: ", disjointClassesVerbalization);;
                return sentence;
    }

    @Override
    public String verbalizeNorwegianEquivalentClassesAxiom(List<String> classExpressions) {
        String sentence = String.join("classexpressions: "+classExpressions);
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
        String sentence = "fillername: "+fillerName+"propertyname: "+propertyName;
        return sentence;
    }
}
