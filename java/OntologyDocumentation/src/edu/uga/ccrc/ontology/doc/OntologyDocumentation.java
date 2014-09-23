package edu.uga.ccrc.ontology.doc;

import edu.uga.ccrc.ontology.doc.util.DocumentationGenerator;
import edu.uga.ccrc.ontology.doc.util.OntologyHandler;
import edu.uga.ccrc.ontology.doc.writer.IWriter;
import edu.uga.ccrc.ontology.doc.writer.WordWriter;

public class OntologyDocumentation
{

    public static void main(String[] args) throws Exception
    {
        String t_excelFile = "conf/complementary.xlsx";
        String t_xmlFile = "conf/config.xml";
        OntologyHandler t_ontology = new OntologyHandler("ontology/glycan.owl", "http://purl.jp/bio/12/glyco/glycan#", "TURTLE");
        IWriter t_writer = new WordWriter("conf/template.docx", "conf/template.dotx", "ontology/documentation.docx");
        // lets do that
        DocumentationGenerator t_generator = new DocumentationGenerator(t_ontology,t_excelFile,t_xmlFile);
        t_generator.generate(t_writer);
    }

}
