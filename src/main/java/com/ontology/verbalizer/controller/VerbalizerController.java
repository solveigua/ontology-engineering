package com.ontology.verbalizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ontology.verbalizer.service.VerbalizerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class VerbalizerController extends HttpServlet {

    @Autowired
    VerbalizerService verbalizationService;

    @GetMapping("/")
    public void landingPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = { RequestMethod.POST, RequestMethod.GET })
    public String uploadFile(@RequestPart("inputFile") MultipartFile inputFile,
            @RequestParam("language") String language, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String content = "";
        System.out.println("File name: " + inputFile.getName());
        if (!inputFile.isEmpty()) {
            try {
                content += new String(inputFile.getBytes());
            } catch (IOException e) {
                return "Error occurred while processing the file.";
            }
        }

        if (content != null && !content.isEmpty()) {
            String verbalization = verbalizationService.getVerbalization(content, language);
            request.setAttribute("msg", verbalization);
            System.out.println(request.getAttribute("msg"));
            request.getRequestDispatcher("/translation.html").include(request, response);
            translationPage(request, response);
            return verbalization;
        } else {
            return "Please provide the file content.";
        }

    }

    @RequestMapping(value = "/translation", method = RequestMethod.GET)
    public String translationPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/translation.html");
        return "translation";
    }
}
