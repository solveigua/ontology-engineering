package com.ontology.verbalizer.utils.sesotho;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ontology.verbalizer.utils.WordAndSentenceCleaner;

@Component
public class SesothoSentenceVerbalizerImpl implements SesothoSentenceVerbalizer {

    @Autowired
    WordAndSentenceCleaner WordAndSentenceCleaner;

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
            String sentence = WordAndSentenceCleaner.listToSentence(classExpressions, "'and' (change to SeSotho)") + 
            " 'is not the same' (change to Sesotho)";
            return WordAndSentenceCleaner.cleanUpSentence(sentence);
    }

    @Override
    public String verbalizeSesothoClassExpression(String fillerName, String propertyName) {
        String sentence = fillerName + " e na le " + propertyName;
        return sentence;
    }

    @Override
    public String verbalizeSesothoIrrefObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'"+WordAndSentenceCleaner.splitObjProp(property.get(0))+"'"+" is a xx object property (in SeSotho :))");
    }

    @Override
    public String verbalizeSesothoAsymObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'"+WordAndSentenceCleaner.splitObjProp(property.get(0))+"'"+" is a xx object property (in SeSotho :))");
    }

    @Override
    public String verbalizeSesothoSymObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'"+WordAndSentenceCleaner.splitObjProp(property.get(0))+"'"+" is a xx object property (in SeSotho :))");
    }

    @Override
    public String verbalizeSesothoTransObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'"+WordAndSentenceCleaner.splitObjProp(property.get(0))+"'"+" is a xx object property (in SeSotho :))");
    }

    @Override
    public String verbalizeSesothoInverseObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'"+WordAndSentenceCleaner.splitObjProp(property.get(0))+"'"+" is a xx object property (in SeSotho :))");
    }

    @Override
    public String verbalizeSesothoFuncObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'"+WordAndSentenceCleaner.splitObjProp(property.get(0))+"'"+" is a xx object property (in SeSotho :))");
    }

    @Override
    public String verbalizeSesothoRefObjProp(List<String> property) {
        return WordAndSentenceCleaner.cleanUpSentence("'"+WordAndSentenceCleaner.splitObjProp(property.get(0))+"'"+" is a xx object property (in SeSotho :))");
    }

    @Override
    //TODO
    public String verbalizeSesothoSubPropAxiom(String subProperty, String superProperty) {
        return "";
    }

    @Override
    public String verbalizeObjectPropRangeAx(String property, String range) {
        return "";
    }
}
