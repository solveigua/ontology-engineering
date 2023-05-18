package com.ontology.verbalizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ontology.verbalizer.service.NorwegianVerbalizerService;
import com.ontology.verbalizer.service.SesothoVerbalizerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
public class VerbalizerController {

    @Autowired
    NorwegianVerbalizerService norwegianVerbalizationService;
    @Autowired
    SesothoVerbalizerService sesothoVerbalizationService;

    @GetMapping("/")
    public void landingPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    @GetMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam String fileContent, @RequestParam("language") String language) {
        if (fileContent != null && !fileContent.isEmpty()) {
            String verbalization = "";

            // If language choice is Norwegian, redirect to Norwegian engine
            // If language choice is Sesotho, redirect to Sesotho engine
            if (language.equals("Norwegian")) {
                verbalization = norwegianVerbalizationService.getNorwegianVerbalization(fileContent);
            } else if (language.equals("SeSotho")) {
                verbalization = sesothoVerbalizationService.getSesothoVerbalization(fileContent);
            } else {
                return "Invalid language.";
            }
            return verbalization;
        } else {
            return "Please provide the file content.";
        }
    }
}
