package com.ontology.verbalizer.controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
public class VerbalizerController {

    @GetMapping("/")
    public void landingPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String content = new String(file.getBytes());
                // Process the file content or perform any necessary operations
                return "File uploaded successfully. Content: " + content;
            } catch (IOException e) {
                return "Error occurred while processing the file.";
            }
        } else {
            return "Please select a file to upload.";
        }
    }
}
