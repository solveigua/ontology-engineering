package com.ontology.verbalizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ontology.verbalizer.service.VerbalizerService;
import java.io.IOException;
import com.ontology.verbalizer.utils.Response;

@RestController
@RequestMapping("/verbalizer")
public class VerbalizerController {

    @Autowired
    VerbalizerService verbalizationService;

    @PostMapping("/upload")
    public Response uploadFile(@RequestPart("inputFile") MultipartFile inputFile,
            @RequestParam("language") String language) throws IOException {
        String content = "";

        if (!inputFile.isEmpty()) {
            try {
                content += new String(inputFile.getBytes());
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        if (content != null && !content.isEmpty()) {
            String verbalization = verbalizationService.getVerbalization(content, language);
            return new Response(verbalization);
        } else {
            return new Response("Could not create verbalization");
        }
    }
}