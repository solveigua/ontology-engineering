package com.ontology.verbalizer.service;

import java.util.HashMap;
import java.util.List;

public interface VerbalizerService {
    public HashMap<String, List<String>> getVerbalization(String owlFile, String language);
}