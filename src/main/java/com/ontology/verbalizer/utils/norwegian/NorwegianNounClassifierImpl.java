/**
 * The purpose of this class is to
 * check if the noun is masculine or neutral.
 * 
 * Norwegian nouns can be either masculine feminine 
 * or neutral. This determines which 'article' comes before 
 * the noun in singular indefinite form. 
 * The feminine one is optional to use, so we have not
 * included it in this project. Therefore we need to determine
 * if a noun is masculine or neutral. Most nouns are masculine
 * so we will check for the common neutral rules and nouns. 
 * 
 * Reference for grammar rules used here:
 * ToppNorsk. (2018, May 14). Hvilket kjønn er ordet? Noen regler. 
 * ToppNorsk. Retrieved May 22 2023 from 
 * https://toppnorsk.com/2018/05/14/hvilket-kjonn-er-ordet-noen-regler/
 * 
 * @Author: karenhompland
 * @Date: 22 May, 2023
 */

package com.ontology.verbalizer.utils.norwegian;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class NorwegianNounClassifierImpl implements NorwegianNounClassifier {

    private static ArrayList<String> neuEndings = new ArrayList<>(Arrays.asList("bud", "bær", "eri", "fall", "hold", "grep",
            "legg", "løp", "ment", "mål", "slag", "tak", "tek", "um"));

    @Override
    public boolean getIsNounNeutral(String nounn) {
        String noun = nounn.toLowerCase();
        ArrayList<String> nounEndings = new ArrayList<>();
        int length = noun.length();
        int lengthCount = 2;
        while (lengthCount <= length && lengthCount < 5) {
            nounEndings.add(noun.substring(length - lengthCount, length));
            lengthCount++;
        }
        if (neuEndings.stream().filter(item -> nounEndings.contains(item)).count() != 0) {
            return true;
        }
        if (checkWordList(noun)) {
            return true;
        }
        return false;

    }

    private static boolean checkWordList(String noun) {
        try (FileReader fileReader = new FileReader("src/main/resources/public/neutral_nouns_nb.txt");
                BufferedReader buffReader = new BufferedReader(fileReader)) {
            String readLine = buffReader.readLine();
            while (readLine != null) {
                if (readLine.contains(noun)) {
                    return true;
                }
                readLine = buffReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
