/**
 * Building Norwegian sentences with the other 
 * tools provided in the repo.
 * 
 * @Author: hageningrid and karenhompland
 * @Date: 23 May, 2023
 */

package com.ontology.verbalizer.utils.norwegian;

import java.util.ArrayList;
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
    @Autowired
    NorwegianPluralizer NorwegianPluralizer;

    @Override
    public String verbalizeNorwegianSubclassAxiom(String subclassVerbalization, String superclassVerbalization) {
        // Verbalize subclass
        String subclass = (subclassVerbalization);
        if (NorwegianNounClassifier.getIsNounNeutral(subclassVerbalization)) {
            subclass = "ethvert " + subclass;
        } else {
            subclass = "enhver " + subclass;
        }
        if (superclassVerbalization.contains(" ")) {
            return WordAndSentenceCleaner.cleanUpSentence(subclass + " " + superclassVerbalization);
        } else {
            String superclass = (superclassVerbalization);
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
        // Verbalize union
        String sentence = String.join(
                "unionClassVerbalization: " + unionClassVerbalization + " disjointClassesVerbalization: ",
                disjointClassesVerbalization);
        ;
        return sentence;
    }

    @Override
    public String verbalizeNorwegianDisjointClassesAxiom(List<String> classExpressions) {
        // Verbalize disjoint axioms
        String sentence = WordAndSentenceCleaner.listToSentence(classExpressions, "og") + " er ikke det samme";
        return WordAndSentenceCleaner.cleanUpSentence(sentence);
    }

    @Override
    public String verbalizeNorwegianClassExpression(String fillerName, String propertyName) {
        // Verbalize property and class
        if (NorwegianNounClassifier.getIsNounNeutral(fillerName)) {
            fillerName = "minst ett " + fillerName;
        } else {
            fillerName = "minst én " + fillerName;
        }
        String property = (propertyName).toLowerCase();
        String className = (fillerName);
        return property + " " + className;
    }

    @Override
    public String verbalizeNorwegianIrrefObjProp(List<String> property) {
        // Verbalize irreflexive property
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + (property.get(0)) + "'" + " er en irrefleksiv objektrelasjon");
    }

    @Override
    public String verbalizeNorwegiaAsymObjProp(List<String> property) {
        // Verbalize asymmetric property
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + (property.get(0)) + "'" + " er en asymmterisk objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianSymObjProp(List<String> property) {
        // Verbalize symmetric property
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + (property.get(0)) + "'" + " er en symmetrisk objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianTransObjProp(List<String> property) {
        // Verbalize transitive relation
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + (property.get(0)) + "'" + " er en transitiv objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianInverseObjProp(List<String> property) {
        // Verbalize inverse property
        return WordAndSentenceCleaner.cleanUpSentence("'" + (property.get(0))
                + " er en invers funksjonell objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianFunObjProp(List<String> property) {
        // Verbalize functional property
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + (property.get(0)) + "'" + " er en funksjonell objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianRefObjProp(List<String> property) {
        // Verbalize reflexise property
        return WordAndSentenceCleaner.cleanUpSentence(
                "'" + property.get(0) + "'" + " er en refleksiv objektrelasjon");
    }

    @Override
    public String verbalizeNorwegianSubPropAxiom(String subProperty, String superProperty) {
        // Verbalize subproperty
        String sentence = "'" + subProperty + "' er en underrelasjon av " + "'"
                + superProperty + "'";
        return WordAndSentenceCleaner.cleanUpSentence(sentence);
    }

    @Override
    public String verbalizeObjectPropRangeAx(String property, String range) {
        // Verbalize range
        return WordAndSentenceCleaner.cleanUpSentence("'" + property + "'"
                + " har dette området: " + range);
    }

    @Override
    public String verbalizeNorwegianInversePropAx(List<String> property) {
        // Verbalize inverse
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'"
                + " er det motsatte av " + "'" + WordAndSentenceCleaner.splitObjProp(property.get(1) + "'"));
    }

    public String verbalizeEquivalentClassesAxiom(List<String> classExpressions) {
        // Verbalize equivalent
        String sentence = classExpressions.stream()
                .map(n -> String.valueOf(n))
                .skip(1)
                .limit(classExpressions.size() - 1)
                .collect(Collectors.joining(", "));
        sentence = classExpressions.get(0) + " er definert ved: " + sentence + " og " +
                classExpressions.get(classExpressions.size() - 1);
        return WordAndSentenceCleaner.cleanUpSentence(sentence);
    }

    @Override
    public String verbalizeNorwegianForAllExpression(String fillerName, String propertyName) {
        // Verbalize for all
        String filler = NorwegianPluralizer.getNorwegianPluralizedNoun(fillerName);
        return WordAndSentenceCleaner.cleanUpSentence(propertyName+" alle " + filler);
    }

    @Override
    public String verbalizeNorwegianUnionOf(ArrayList<String> classesInUnion) {
        // Verbalize union
        for (String text : classesInUnion) {
            text=WordAndSentenceCleaner.splitClass(text);
        }
        return " "+WordAndSentenceCleaner.listToSentence(classesInUnion, "eller").toLowerCase();
    }

    @Override
    public String verbalizeNorwegianIntersectionOf(ArrayList<String> classesInIntersection) {
        // Verbalize intersect
        for (String text : classesInIntersection) {
            text=WordAndSentenceCleaner.splitClass(text);
        }
        return " "+WordAndSentenceCleaner.listToSentence(classesInIntersection, "og").toLowerCase();
    }

    @Override
    public String verbalizeNorwegianComplementOf(String className) {
        // Verbalize complement of
        return " ikke "+ className.toLowerCase(); 
    }
}
