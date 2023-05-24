package com.ontology.verbalizer.utils;

import java.util.List;

public interface WordAndSentenceCleaner {
    public String splitClass(String name);
    public String splitObjProp(String name);
    public String cleanUpSentence(String sentence);
    public String listToSentence(List<String> list, String finishWord);
}