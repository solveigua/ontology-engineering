package com.ontology.verbalizer.utils;

public interface WordAndSentenceCleaner {
    public String splitClass(String name);
    public String splitObjProp(String name);
    public String cleanUpSentence(String sentence);
}
