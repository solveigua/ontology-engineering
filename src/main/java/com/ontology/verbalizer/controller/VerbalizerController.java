package com.ontology.verbalizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ontology.verbalizer.service.VerbalizerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.ui.Model;
import com.ontology.verbalizer.utils.Response;

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
    @RequestMapping(value = "/upload", method = { RequestMethod.POST, RequestMethod.GET })
    public Response uploadFile(@RequestPart("inputFile") MultipartFile inputFile,
            @RequestParam("language") String language, HttpServletRequest request, HttpServletResponse response,
            Model m, @ModelAttribute("message") String message)
            throws IOException {
        String content = "";

        // System.out.println("File name: " + inputFile.getName());
        if (!inputFile.isEmpty()) {
            try {
                content += new String(inputFile.getBytes());
                // return "File uploaded successfully. Content: " + content;
            } catch (IOException e) {
                // return "Error occurred while processing the file.";
                System.out.println(e);
            }
        }

        if (content != null && !content.isEmpty()) {
            String verbalization = verbalizationService.getVerbalization(content,
                    language);
            m.addAttribute("message", verbalization);
            // System.out.println(m.getAttribute("message"));
            // loadTranslationPage(m, response, request, verbalization, "");
            Response r = new Response(verbalization);
            response.sendRedirect(request.getContextPath() + "/translation.html");
            System.out.println(r);
            return r;
        } else {
            return new Response("Could not create verbalization");
        }
    }

    @GetMapping("/translaion")
    public void loadTranslationPage(HttpServletResponse response,
            HttpServletRequest request)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/translation.html");
        System.out.println("HELLO FROM LOADTRANSLATION");
    }

}
