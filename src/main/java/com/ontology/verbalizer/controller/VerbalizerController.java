package com.ontology.verbalizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ontology.verbalizer.service.NorwegianVerbalizerService;
import com.ontology.verbalizer.service.SesothoVerbalizerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

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
    @PostMapping("/upload")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestPart("inputFile") MultipartFile inputFile,
            @RequestParam("language") String language) {
        String content = "";
        System.out.println("File name: " + inputFile.getName());
        if (!inputFile.isEmpty()) {
            try {
                content += new String(inputFile.getBytes());
                // return "File uploaded successfully. Content: " + content;
            } catch (IOException e) {
                return "Error occurred while processing the file.";
            }
        }

        if (content != null && !content.isEmpty()) {
            String verbalization = "";

            // If language choice is Norwegian, redirect to Norwegian engine
            // If language choice is Sesotho, redirect to Sesotho engine
            if (language.equals("NB")) {
                verbalization = norwegianVerbalizationService.getNorwegianVerbalization(content);
            } else if (language.equals("ST")) {
                verbalization = sesothoVerbalizationService.getSesothoVerbalization(content);
            } else {
                return "Invalid language.";
            }
            return verbalization;
        } else {
            return "Please provide the file content.";
        }

    }
}
