package edu.uga.ccrc.ontology.doc.data;

import com.hp.hpl.jena.rdf.model.Resource;

public class OntologyThing
{
    private String m_name = null;
    private Resource m_resource = null;
    public String getName()
    {
        return m_name;
    }
    public void setName(String a_name)
    {
        m_name = a_name;
    }
    public Resource getResource()
    {
        return m_resource;
    }
    public void setResource(Resource resource)
    {
        m_resource = resource;
    }
}
