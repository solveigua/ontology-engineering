/*
 * The purpose of this class is to take in a singular sesotho noun
 * and then based on the noun classes, return the proper
 * pluralization.
 * 
 * @Author: pmakhupane
 * @Date: 22 May, 2023
 */

package com.ontology.verbalizer.utils.sesotho;

import org.springframework.stereotype.Component;

@Component
public class SesothoPluralizerImpl implements SesothoPluralizer {

    @Override
    public String getPlural(String singular) {
        String prefix = getPrefix(singular);
        String stem = getStem(singular);
        String pluralForm = "";

        switch (prefix) {
            case "mo":
                pluralForm = "ba" + stem;
                break;
            case "le":
                pluralForm = "di" + stem;
                break;
            case "se":
                pluralForm = "di" + stem;
                break;
            case "sa":
                pluralForm = "di" + stem;
                break;
            case "n":
                pluralForm = "din" + stem;
            case "t":
                pluralForm = "dit" + stem;
                break;
            default:
                pluralForm = singular; // No plural form found, return the original singular
        }

        return pluralForm;
    }

    private static String getPrefix(String noun) {
        return noun.substring(0, 2);
    }

    private static String getStem(String noun) {
        return noun.substring(2);
    }
}
