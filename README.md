# OWL Verbalizator for 🇳🇴Norwegian and 🇱🇸SeSotho
**UCT CSC5028Z Ontology Engineering Mini Project May 2023.** <br/>
By Phuthang Makhupane, Ingrid Hagen, Karen Hompland and Solveig Aune.

🧭 The application has a web interface that lets you upload an OWL-file. Here you can choose between SeSotho and Norwegian (both on popular demand) verbalization of the OWL-file. By submitting the OWL-file will be verbalized into written sentences in the chosen language.

### Prerequisities for the Ontology
😄 The verbalizer works best with classnames written in PascalCase or camelCase (e.g. CarnivorousPlant) and object properties written with a "-" between words (e.g. is-part-of). <br/>
📁 It does only accept OWL-files. <br/>
🌐 The ontology need to contain labels with the language-tag of the desired language for the verbalization. <br/>

## How to build and run [WIP]
1. Ensure you have maven installed
2. Ensure you run (Only the first developer needs to do this): mvn -Nio.takari:maven:wrapper
3. Run 'mvn install'
