package com.ontology.verbalizer;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.OWLOntology;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.ontology.verbalizer.utils.OntologyExtractorUtil;
import com.ontology.verbalizer.utils.OntologyExtractorUtilImpl;

public class OntologyExtractorUtilTest {

    @Test
    public void testExtractOntologyFromOwl() {
        // Arrange
        String owlPath = "src/main/resources/public/university1-1.owl";
        String content = "";

        OntologyExtractorUtilImpl _ontologyExtractor = new OntologyExtractorUtilImpl();
        
        try {
            content = readFileToString(owlPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Act
        OWLOntology ontology = _ontologyExtractor.extractOntologyFromOwl(content);
        System.out.println("ontology :  " + ontology);
        // Assert
        assertNotNull(ontology);
        // Add more assertions as needed based on the expected behavior of the method
        
    }

      public String readFileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
    }
}
