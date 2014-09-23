package edu.uga.ccrc.ontology.doc.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jdom2.JDOMException;

import edu.uga.ccrc.ontology.doc.data.OntologyClass;
import edu.uga.ccrc.ontology.doc.data.OntologyClassProperties;
import edu.uga.ccrc.ontology.doc.data.OntologyPredicate;
import edu.uga.ccrc.ontology.doc.writer.IWriter;

public class DocumentationGenerator
{
    private OntologyHandler m_ontology = null;
    private XMLHandler m_xml = null;
    private HashMap<String, List<OntologyPredicate>> m_complementary = new HashMap<String, List<OntologyPredicate>>();
    private PrefixGenerator m_prefixGen = null;
    
    public DocumentationGenerator(OntologyHandler a_ontology, String a_excelFile, String a_xmlFile) throws JDOMException, IOException
    {
        this.m_ontology = a_ontology;
        this.m_xml = new XMLHandler( a_xmlFile );
        ExcelHandler t_excel = new ExcelHandler();
        try
        {
            this.m_complementary = t_excel.readFile(a_excelFile);
        }
        catch (Exception t_e) 
        {
            System.out.println(t_e.getMessage());
        }
        this.m_prefixGen = new PrefixGenerator(this.m_xml.getNamespaces());
    }

    public void generate(IWriter a_writer) throws IOException, JDOMException
    {
        List<OntologyClass> t_classes = this.m_xml.getClasses();
        this.fillClassProperties(t_classes);

        a_writer.writeHeadline( this.m_xml.getText("document_title") );
        a_writer.writeNameSpaces( this.m_xml.getText("namespace"), this.m_prefixGen.getPrefixList() );
        a_writer.writeClasses(t_classes,this.m_prefixGen);
        a_writer.close();
    }

    private void fillClassProperties(List<OntologyClass> a_classes) throws IOException
    {
        for (OntologyClass t_class : a_classes)
        {
            OntologyClassProperties t_properties = this.m_ontology.getClassProperties(t_class.getName(), t_class.isInstances());
            List<OntologyPredicate> t_additionals = this.m_complementary.get(t_class.getName());
            if ( t_additionals != null )
            {
                t_properties.getPredicates().addAll(t_additionals);
            }
            t_class.setProperties(t_properties);
            this.fillClassProperties(t_class.getChildClasses());
        }
    }

}
