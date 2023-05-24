/**
 * Building Norwegian sentences with the other 
 * tools provided in the repo.
 * 
 * @Author: hageningrid and karenhompland
 * @Date: 23 May, 2023
 */

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
        if (NorwegianNounClassifier.getIsNounNeutral(subclassVerbalization)) {
            subclass = "et " + subclass;
        } else {
            subclass = "en " + subclass;
        }
        if (superclassVerbalization.contains(" ")) {
            return WordAndSentenceCleaner.cleanUpSentence(subclass + " " + superclassVerbalization);
        } else {
            String superclass = WordAndSentenceCleaner.splitClass(superclassVerbalization);
            if (NorwegianNounClassifier.getIsNounNeutral(superclassVerbalization)) {
                superclass = "et " + superclass;
            } else {
                superclass = "en " + superclass;
            }
            return WordAndSentenceCleaner.cleanUpSentence(subclass + " er " + superclass);
        }
    }

    @Override
    public String verbalizeNorwegianUnionAxiom(String unionClassVerbalization,
            List<String> disjointClassesVerbalization) {
        String sentence = String.join(
                "unionClassVerbalization: " + unionClassVerbalization + " disjointClassesVerbalization: ",
                disjointClassesVerbalization);
        ;
        return sentence;
    }

    @Override
    public String verbalizeNorwegianEquivalentClassesAxiom(List<String> classExpressions) {
        String sentence = String.join("classexpressions: " + classExpressions);
        return sentence;
    }

    @Override
    public String verbalizeNorwegianDisjointClassesAxiom(List<String> classExpressions) {
        String sentence = WordAndSentenceCleaner.listToSentence(classExpressions, "og") + " er ikke det samme";
        return WordAndSentenceCleaner.cleanUpSentence(sentence);
    }

    @Override
    public String verbalizeNorwegianClassExpression(String fillerName, String propertyName) {
        String property = WordAndSentenceCleaner.splitObjProp(propertyName).toLowerCase();
        String className = WordAndSentenceCleaner.splitClass(fillerName);
        return property + " " + className;
    }

    @Override
    public String verbalizeNorwegianIrrefObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'" + " er en irrefleksiv objektrelasjon");
    }

    @Override
    public String verbalizeNorwegiaAsymObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'" + " er en asymmterisk objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianSymObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'" + " er en symmetrisk objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianTransObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'" + " er en transitiv objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianInverseObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property.get(0))
                + " er en invers funksjonell objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianFunObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'" + " er en funksjonell objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianRefObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'" + " er en refleksiv objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianSubPropAxiom(String subProperty, String superProperty) {
        String sentence = "'" + WordAndSentenceCleaner.splitObjProp(subProperty) + "' er en underrelasjon av " + "'"
                + WordAndSentenceCleaner.splitObjProp(superProperty) + "'";
        return WordAndSentenceCleaner.cleanUpSentence(sentence);
    }

    @Override
    public String verbalizeObjectPropRangeAx(String property, String range) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property) + "'"
                + " har dette området: " + WordAndSentenceCleaner.splitObjProp(range));
    }

    @Override
    public String verbalizeNorwegianInversePropAx(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'"
                + " er det motsatte av " + "'" + WordAndSentenceCleaner.splitObjProp(property.get(1) + "'"));
    }

    public String verbalizeEquivalentClassesAxiom(List<String> classExpressions) {
        String sentence = classExpressions.stream()
                .map(n -> String.valueOf(n))
                .skip(1)
                .limit(classExpressions.size() - 1)
                .collect(Collectors.joining(", "));
        sentence = classExpressions.get(0) + " er definert ved: " + sentence + " og " +
                classExpressions.get(classExpressions.size() - 1);
        System.out.println(WordAndSentenceCleaner.cleanUpSentence("Sentence" + sentence));
        return WordAndSentenceCleaner.cleanUpSentence(sentence);
    }
}
