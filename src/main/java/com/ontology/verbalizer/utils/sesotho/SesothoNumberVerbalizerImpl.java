/**
 * The purpose of this class is to take in an integer and 
 * return the sesotho version of it.
 * @Author: pmakhupane
 * @Date: 22 May, 2023
 */
package com.ontology.verbalizer.utils.sesotho;

import org.springframework.stereotype.Component;

@Component
public class SesothoNumberVerbalizerImpl implements SesothoNumberVerbalizer{

    @Override
    public String getSesothoNumber(Integer number) {
        String SesothoNumber = "";
        switch (number)
        {
            case 1: 
                SesothoNumber = "ngwe";
                break;
            case 2: 
                SesothoNumber = "pedi";
                break;
            case 3: 
                SesothoNumber = "tharu";
                break;
            case 4: 
                SesothoNumber = "nne";
                break;
            case 5: 
                SesothoNumber = "hlanu";
                break;
            case 6: 
                SesothoNumber = "tshelela";
                break;
            case 7: 
                SesothoNumber = "supa";
                break;
            case 8: 
                SesothoNumber = "robedi";
                break;
            case 9: 
                SesothoNumber = "robong";
                break;
            case 10: 
                SesothoNumber = "leshome";
                break;
            default:
                SesothoNumber = ""; // Handle the default case
                break;
        }
        
        return SesothoNumber;
        
    }
    
}
