package com.ontology.verbalizer.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ontology.verbalizer.service.VerbalizerService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/verbalizer")
public class VerbalizerController {

    @Autowired
    VerbalizerService verbalizationService;

    @PostMapping("/upload")
    public JSONObject uploadFile(@RequestPart("inputFile") MultipartFile inputFile,
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
            HashMap<String, List<String>> verbalizations = verbalizationService.getVerbalization(content, language);
            JSONObject json = new JSONObject(verbalizations);
            return json;
        } else {

            return new JSONObject(null); // TODO fix error msg.
        }
    }
}