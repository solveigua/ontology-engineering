/**
 * The purpose of this class is to write out the words and sentences 
 * of the verbalization in a correct way, by splitting up classnames 
 * and object properties with two or more words in it.
 * To clean up a sentence in the end we will remove all capital 
 * letters but the first one and add a dot in the end. 
 * 
 * @Author: karenhompland and hageningrid
 * @Date: 23 May, 2023
 */

package com.ontology.verbalizer.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class WordAndSentenceCleanerImpl implements WordAndSentenceCleaner {

    @Override
    public String splitClass(String name) {
        // Split word in CamelCase
        return StringUtils.join(name.split("(?=\\p{Upper})"), ' ');
    }

    @Override
    public String splitObjProp(String name) {
        // Split word divided by "-"
        return name.replace('-', ' ');
    }

    @Override
    public String cleanUpSentence(String sentence) {
        // Clean up sentence
        List<String> finishSentence = new ArrayList();
        for(String subString: sentence.split(" ")){
            finishSentence.add(splitObjProp(splitClass(subString)));
        }
        String complete = String.join(" ", finishSentence).replace(".", "");
        return complete.substring(0, 1).toUpperCase() + complete.substring(1).toLowerCase() + ". \n";
    }

    @Override
    public String listToSentence(List<String> list, String finishWord){
        // Makes a string out of the list provided. 
        // Seperates elements with ',' and a chosen finishWord (eg. and)
        String sentence = list.stream()
                .map(n -> String.valueOf(n))
                .limit(list.size() - 1)
                .collect(Collectors.joining(", "));
        return sentence + " " + finishWord + " " + list.get(list.size() - 1);
    }
    @Override
    public String deleteDuplicateAdjacentWords(String name) {
        // Deletes duplicate adjacent words
        StringBuilder sb = new StringBuilder();
        String[] words = name.split("\\s+");

        for (int i = 0; i < words.length - 1; i++) {
            if (!words[i].equals(words[i + 1])) {
                sb.append(words[i]).append(" ");
            }
        }
        
        sb.append(words[words.length - 1]);  // Append the last word

        return sb.toString();
    }


}
