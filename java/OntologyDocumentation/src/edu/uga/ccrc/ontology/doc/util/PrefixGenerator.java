package edu.uga.ccrc.ontology.doc.util;

import java.util.HashMap;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Resource;

import edu.uga.ccrc.ontology.doc.data.OntologyNamespaces;

public class PrefixGenerator
{
    private HashMap<String, String> m_prefixList = new HashMap<String, String>();
    
    public PrefixGenerator(List<OntologyNamespaces> a_ns)
    {
        for (OntologyNamespaces t_ontologyNamespaces : a_ns)
        {
            this.m_prefixList.put(t_ontologyNamespaces.getUri(), t_ontologyNamespaces.getPrefix());
        }
    }
    
    public String prefixName(Resource a_resource)
    {
        String t_namespace = a_resource.getNameSpace();
        String t_prefix = this.m_prefixList.get(t_namespace);
        if ( t_prefix != null )
        {
            return t_prefix + ":" + a_resource.getLocalName();
        }
        return a_resource.getURI();
    }

    public HashMap<String, String> getPrefixList()
    {
        return m_prefixList;
    }
    
}
