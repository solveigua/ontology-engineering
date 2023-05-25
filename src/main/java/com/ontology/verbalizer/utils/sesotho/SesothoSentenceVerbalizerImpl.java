/**
 * The purpose of this class is to verbalize axioms that have
 * been detected by the grammar engine into sesotho
 * sentences.
 * @Author: pmakhupane
 * @Date: May,2023
 */
package com.ontology.verbalizer.utils.sesotho;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ontology.verbalizer.utils.WordAndSentenceCleaner;

@Component
public class SesothoSentenceVerbalizerImpl implements SesothoSentenceVerbalizer {

    @Autowired
    WordAndSentenceCleaner WordAndSentenceCleaner;

    @Autowired
    SesothoPluralizer _sesothoPluralizer;

    @Override
    public String verbalizeSesothoSubclassAxiom(String subclassVerbalization, String superclassVerbalization) {
        String sentence = subclassVerbalization + " ke " + superclassVerbalization;
        return WordAndSentenceCleaner.deleteDuplicateAdjacentWords(WordAndSentenceCleaner.cleanUpSentence(sentence));
    }

    @Override
    public String verbalizeSesothoUnionAxiom(String unionClassVerbalization,
            List<String> disjointClassesVerbalization) {
        String sentence = unionClassVerbalization + " ke " + String.join(" le ", disjointClassesVerbalization);
        return sentence;
    }

    @Override
    public String verbalizeSesothoDisjointClassesAxiom(List<String> classExpressions) {
        String sentence = WordAndSentenceCleaner.listToSentence(classExpressions, "le ") +
                " ha di tshwane";
        return WordAndSentenceCleaner.cleanUpSentence(sentence);
    }

    @Override
    public String verbalizeSesothoClassExpression(String fillerName, String propertyName) {
        String sentence = "";
        
        if(propertyName.equals("ke-karolo-ya"))
        {
            sentence = WordAndSentenceCleaner.splitObjProp(propertyName) + " " + fillerName;
        }
        else if(propertyName.equals("e-ja"))
        {
            sentence = WordAndSentenceCleaner.splitObjProp(propertyName) + "ng " + fillerName;
        }
        return sentence;
    }

    @Override
    public String verbalizeSesothoIrrefObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'"
                + " ke kamano e sa amoheleng kamano le boqo");
    }

    @Override
    public String verbalizeSesothoAsymObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'"
                + " e sa tshwaneng ho ya pele le moraho");
    }

    @Override
    public String verbalizeSesothoSymObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'"
                + " ke kamano e tshwanang ho ya pele le moraho");
    }

    @Override
    public String verbalizeSesothoTransObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'"
                + " ke kamano e theohelang ho ditloholo");
    }

    @Override
    public String verbalizeSesothoInverseObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'"
                + " ke kamano e fetolelang moraho");
    }

    @Override
    public String verbalizeSesothoFuncObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'"
                + " ke kamano mahareng a dintho tse pedi");
    }

    @Override
    public String verbalizeSesothoRefObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'"
                + " ke kamano e amohelang kamano le boqo");
    }

    @Override
    public String verbalizeSesothoSubPropAxiom(String subProperty, String superProperty) {
        String sentence = "'" + WordAndSentenceCleaner.splitObjProp(subProperty) + "' 'ke kamano ya' "
                + "'"
                + WordAndSentenceCleaner.splitObjProp(superProperty) + "'";
        return WordAndSentenceCleaner.cleanUpSentence(sentence);
    }

    @Override
    public String verbalizeObjectPropRangeAx(String property, String range) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property) + "'"
                + " 'e mahareng a: ' " + WordAndSentenceCleaner.splitObjProp(range));
    }

    @Override
    public String verbalizeSesothoInversePropAx(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'" + WordAndSentenceCleaner.splitObjProp(property.get(0)) + "'"
                + " ke lefeto la " + "'"
                + WordAndSentenceCleaner.splitObjProp(property.get(1) + "'"));
    }

    public String verbalizeEquivalentClassesAxiom(List<String> classExpressions) {
        String sentence = classExpressions.stream()
                .map(n -> String.valueOf(n))
                .skip(1)
                .limit(classExpressions.size() - 1)
                .collect(Collectors.joining(", "));
        sentence = classExpressions.get(0) + " e ya tshwana le: " + sentence + " le " +
                classExpressions.get(classExpressions.size() - 1);
        return WordAndSentenceCleaner.cleanUpSentence(sentence);
    }


    @Override
    public String verbalizeSesothoForAllExpression(String fillerName, String propertyName) {
        String filler = _sesothoPluralizer.getPlural(fillerName);
        return WordAndSentenceCleaner.cleanUpSentence(WordAndSentenceCleaner.splitObjProp(propertyName)+" kaofela "+WordAndSentenceCleaner.splitClass(filler));
    }

    @Override
    public String verbalizeSesothoUnionOf(ArrayList<String> classesInUnion) {
        for (String text : classesInUnion) {
            text=WordAndSentenceCleaner.splitClass(text);
        }
        return " "+WordAndSentenceCleaner.listToSentence(classesInUnion, "kapa").toLowerCase();
    }

    @Override
    public String verbalizeSesothoIntersectionOf(ArrayList<String> classesInIntersection) {
        for (String text : classesInIntersection) {
            text=WordAndSentenceCleaner.splitClass(text);
        }
        return " "+WordAndSentenceCleaner.listToSentence(classesInIntersection, "le").toLowerCase();
    }

    @Override
    public String verbalizeSesothoComplementOf(String className) {
        return " eseng "+WordAndSentenceCleaner.splitClass(className).toLowerCase();
    }
}
