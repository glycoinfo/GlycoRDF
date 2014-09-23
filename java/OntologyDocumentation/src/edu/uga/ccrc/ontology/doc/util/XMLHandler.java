package edu.uga.ccrc.ontology.doc.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.jaxen.JaxenXPathFactory;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import edu.uga.ccrc.ontology.doc.data.OntologyClass;
import edu.uga.ccrc.ontology.doc.data.OntologyNamespaces;


public class XMLHandler
{
    private Document m_documentText = null;

    public XMLHandler(String a_file) throws JDOMException, IOException
    {
        this.m_documentText = new SAXBuilder().build(new File(a_file));
    }

    private static Element xpathElement(String a_query, Document a_document) throws JDOMException 
    {
        List<Element> t_elements = XMLHandler.xpathElements(a_query, a_document);
        if ( t_elements.size() > 1 )
        {
            throw new JDOMException("More than one element found for: " + a_query);
        }
        for (Element t_element : t_elements)
        {
            return t_element;
        }
        return null;
    }
    
    private static List<Element> xpathElements(String a_query, Document a_document) throws JDOMException 
    {
        XPathExpression<Element> t_xpathExpression= new JaxenXPathFactory().compile(a_query, Filters.element(),null, Namespace.getNamespace("ns", "urn:oasis:names:tc:entity:xmlns:xml:catalog"));
        List<Element> t_elements = t_xpathExpression.evaluate(a_document);
        return t_elements;
    }

    public String getText(String a_textName) throws JDOMException
    {
        String t_query = "/documentation/texts/text[@id='" + a_textName + "']";
        Element t_element = XMLHandler.xpathElement(t_query, this.m_documentText);
        return t_element.getText();
    }

    public List<OntologyNamespaces> getNamespaces() throws JDOMException
    {
        String t_query = "/documentation/namespaces/namespace";
        List<Element> t_list = XMLHandler.xpathElements(t_query, this.m_documentText);
        List<OntologyNamespaces> t_result = new ArrayList<OntologyNamespaces>();
        for (Element t_element : t_list)
        {
            OntologyNamespaces t_ns = new OntologyNamespaces();
            t_ns.setPrefix(t_element.getAttributeValue("prefix"));
            t_ns.setUri(t_element.getAttributeValue("uri"));
            t_result.add(t_ns);
        }
        return t_result;
    }

    public List<OntologyClass> getClasses() throws JDOMException
    {
        String t_query = "/documentation/classes";
        Element t_element = XMLHandler.xpathElement(t_query, this.m_documentText);
        Model t_model = ModelFactory.createDefaultModel();
        return this.getSubClasses(t_element,1,t_model);
    }

    public List<OntologyClass> getSubClasses(Element a_class, int a_level, Model a_model) throws JDOMException
    {
        List<OntologyClass> t_result = new ArrayList<OntologyClass>();
        List<Element> t_list = a_class.getChildren("class");
        for (Element t_element : t_list)
        {
            OntologyClass t_class = new OntologyClass();
            t_class.setName(t_element.getAttributeValue("name").trim());
            t_class.setResource(a_model.createResource(t_class.getName()));
            t_class.setOrder(Integer.parseInt(t_element.getAttributeValue("order").trim()));
            t_class.setLevel(a_level);
            String t_value = t_element.getAttributeValue("instances");
            if ( t_value != null )
            {
                if ( t_value.equalsIgnoreCase("true") )
                {
                    t_class.setInstances(true);
                }
            }
            t_class.setChildClasses(this.getSubClasses(t_element,a_level+1,a_model));
            t_result.add(t_class);
        }
        return t_result;
    }
}
