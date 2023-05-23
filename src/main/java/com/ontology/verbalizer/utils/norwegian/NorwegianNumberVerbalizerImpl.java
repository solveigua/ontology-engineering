/**
 * The purpose of this class is write out
 * numbers in the verbalization. After the number
 * 10 the number will be written as numerals. 
 * 
 * @Author: karenhompland
 * @Date: 21 May, 2023
 */

package com.ontology.verbalizer.utils.norwegian;

public class NorwegianNumberVerbalizerImpl implements NorwegianNumberVerbalizer {

    @Override
    public String getNumber(Integer i) {
        if (i==1) {
            return "en";
        }
        else if (i==2) {
            return "to";
        }
        else if (i==3) {
            return "tre";
        }
        else if (i==4) {
            return "fire";
        }
        else if (i==5) {
            return "fem";
        }
        else if (i==6) {
            return "seks";
        }
        else if (i==7) {
            return "syv";
        }
        else if (i==8) {
            return "åtte";
        }
        else if (i==9) {
            return "ni";
        }
        else if (i==10) {
            return "ti";
        }
        else {
            return i.toString();
        }
    }
    
}
