/**
   This class is purely for getting a path to an owl and
   returning an OWLOntology object form of it.

   @Author: pmakhupane
   @Version: 1.0
   @Date: May 16, 2023
**/
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class OntologyExtractorUtil
{
    private static final Logger logger = LogManager.getLogger(OntologyExtractorUtil.class);
    public static OWLOntology extractOntologyFromOwl(String owlPath)
    {   
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File file = new File(owlPath);
        
        OWLOntology ontology = null;

        try{
        ontology = manager.loadOntologyFromOntologyDocument(file);
        }catch(OWLOntologyCreationException e)
        {
            logger.info("Could not create OWLOntology from file " + owlPath);
        }

        return ontology;
    }
}