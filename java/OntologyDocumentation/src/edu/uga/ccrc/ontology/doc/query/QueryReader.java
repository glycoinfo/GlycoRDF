/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uga.ccrc.ontology.doc.query;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.hp.hpl.jena.rdf.model.Resource;

public class QueryReader 
{
    private Map<String, String> m_query = new HashMap<String, String>();

    public QueryReader() throws IOException, JDOMException  
    {
        Class<?> t_class = this.getClass();

        URL t_file = t_class.getResource("queries.xml");
        InputStreamReader t_reader = new InputStreamReader(t_file.openStream());

        SAXBuilder t_builder = new SAXBuilder();
        Document t_document = t_builder.build(t_reader);
        Element t_root = t_document.getRootElement();        
        List<Element> t_queryList = t_root.getChildren("query");

        for (Element t_element : t_queryList)
        {
            this.m_query.put(t_element.getAttributeValue("name"), t_element.getText());
        }
        t_reader.close();
    }

    public String getQuery(String a_name, Resource a_value)
    {
        String t_query = this.getQuery(a_name).trim();
        return t_query.replace("##replace##", a_value.getURI());
    }

    public String getQuery(String a_name)
    {
        return this.m_query.get(a_name);
    }

} 