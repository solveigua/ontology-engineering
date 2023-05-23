/**
 * The purpose of this class is to pluralize nouns for the 
 * Norwegian verbalization. It uses a list of common irregular
 * nouns in pluralized form located in resources/public/irregular_nouns_nb.json.
 * 
 * @Author: karenhompland
 * @Date: 21 May, 2023
 */

package com.ontology.verbalizer.utils.norwegian;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NorwegianPluralizerImpl implements NorwegianPluralizer {

    static JSONObject irrNouns;

    @Override
    public String getNorwegianPluralizedNoun(String noun) {
        readIrrNouns();
        if (irrNouns.containsKey(noun)){
            return irrNouns.get(noun).toString();
        }
        if (noun.endsWith("e")) {
            return noun + "r";
        }
        if (noun.endsWith("el")) {
            return noun.substring(0, noun.length()-2) + "ler";
        } else {
            return noun + "er";
        }
    }

    private static void readIrrNouns() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/public/irregular_nouns_nb.json"));
            irrNouns = (JSONObject) obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}