package edu.uga.ccrc.ontology.doc.data;

import java.util.ArrayList;
import java.util.List;

public class OntologyPredicate extends OntologyThing
{
    private String m_comment = null;
    private List<OntologyThing> m_rangeResource = new ArrayList<OntologyThing>();
    private boolean m_functional = false; 
    
    public String getComment()
    {
        return m_comment;
    }
    public void setComment(String a_comment)
    {
        m_comment = a_comment;
    }
    public List<OntologyThing> getRangeResource()
    {
        return m_rangeResource;
    }
    public void setRangeResource(List<OntologyThing> a_rangeResource)
    {
        m_rangeResource = a_rangeResource;
    }
    public void addRange(OntologyThing a_resource)
    {
        this.m_rangeResource.add(a_resource);
    }
    public void setFunctional(boolean functional)
    {
        m_functional = functional;
    }
    public boolean isFunctional()
    {
        return m_functional;
    }
}
