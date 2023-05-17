package com.ontology.verbalizer.utils;

/**
   This class is purely for getting a path to an owl and
   returning an OWLOntology object form of it.

   @Author: pmakhupane
   @Version: 1.0
   @Date: May 16, 2023
**/
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class OntologyExtractorUtilImpl implements OntologyExtractorUtil
{
    private static final Logger logger = LogManager.getLogger(OntologyExtractorUtil.class);
    @Override
    public OWLOntology extractOntologyFromOwl(MultipartFile owlMultipart)
    {   
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = null;

        try{
        ontology = manager.loadOntologyFromOntologyDocument(owlMultipart.getInputStream());
        }catch(OWLOntologyCreationException e)
        {
            logger.error("Could not create OWLOntology from file ");
        }catch(IOException e)
        {
            logger.error("Could not load owl file");
        }

        return ontology;
    }
}