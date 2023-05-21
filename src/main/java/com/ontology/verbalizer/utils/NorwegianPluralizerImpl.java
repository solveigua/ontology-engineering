/**
 * The purpose of this class is to pluralize nouns for the 
 * Norwegian verbalization.
 * 
 * @Author: karenhompland
 * @Date: 21 May, 2023
 */

package com.ontology.verbalizer.utils;

import java.io.FileReader;

import org.apache.tomcat.util.json.JSONParser;
import org.json.*;

public class NorwegianPluralizerImpl implements NorwegianPluralizer {

    String result;
    Object a = JSONParser.parse();
    
    public String getNorwegianPluralizedNoun(String noun) {
        if ()
        if(noun.endsWith("e")){
            return result = noun+"r";
        }
        if(noun.endsWith("el")){
            return result = noun.substring(0,-2)+"ler";
        }
        else  {
            return result = noun+"er";
        }
    }
}