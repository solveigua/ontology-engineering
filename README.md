# OWL Verbalizer for 🇳🇴Norwegian and 🇱🇸SeSotho
**UCT CSC5028Z Ontology Engineering Mini Project May 2023.** <br/>
By Phuthang Makhupane, Ingrid Hagen, Karen Hompland and Solveig Aune.

🧭 The application has a web interface that lets you upload an OWL-file. Here you can choose between SeSotho and Norwegian (both on popular demand) verbalization of the OWL-file. By submitting the OWL-file will be verbalized into written sentences in the chosen language.

### Prerequisities for the Ontology
😄 The verbalizer works best with classnames written in PascalCase or camelCase (e.g. CarnivorousPlant) and object properties written with a "-" between words (e.g. is-part-of). <br/>
📁 It does only accept OWL-files. <br/>
🌐 The ontology need to contain labels with the language-tag of the desired language for the verbalization. Supported language tags: nb (Norwegian bokmål) and st (SeSotho). <br/>

### OWL-file for testing
Would you like to test the verbalizer but do not have an OWL-file with Norwegian and SeSotho translations? Look no further! [African Wildlife Ontology](src/main/resources/public/african_wildlife_for_project.owl) is here to help you. 🦒  This ontology is made by our professor Maria Keet. We have added the translations and done some minor changes. You can find the original ontology [here.](http://meteck.org/teaching/OEbook/ontologies/AfricanWildlifeOntology1.owl) 🦁 🐘

## How to build and run
1. Ensure you have maven installed
2. Ensure you run (Only the first developer needs to do this): mvn -Nio.takari:maven:wrapper
3. Run 'mvn clean install'
4. Run the web application from the [/src/main/java/com/ontology/verbalizer/**VerbalizerApplication.java**](/src/main/java/com/ontology/verbalizer/VerbalizerApplication.java)
5. Open your broweser and go to [localhost port 8080](http://localhost:8080/index.html)
