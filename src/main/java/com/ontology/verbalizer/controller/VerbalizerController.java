package com.ontology.verbalizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String language = ""; // TODO: obtain language from request
            String verbalization = "";

            // If language choice is Norwegian, redirect to Norwegian engine
            // If language choice is Sesotho, redirect to Sesotho engine
            if (language.equals("Norwegian")) {
                verbalization = norwegianVerbalizationService.getNorwegianVerbalization(file);
            } else if (language.equals("SeSotho")) {
                verbalization = sesothoVerbalizationService.getSesothoVerbalization(file);
            } else {
                return "Invalid language.";
            }
            return verbalization;
        } else {
            return "Please select a file to upload.";
        }
    }
}
