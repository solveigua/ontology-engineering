package com.ontology.verbalizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ontology.verbalizer.service.VerbalizerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class VerbalizerController {

    @Autowired
    VerbalizerService verbalizationService;

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
            String verbalization = verbalizationService.getVerbalization(content, language);
            return verbalization;
        } else {
            return "Please provide the file content.";
        }

    }
}
