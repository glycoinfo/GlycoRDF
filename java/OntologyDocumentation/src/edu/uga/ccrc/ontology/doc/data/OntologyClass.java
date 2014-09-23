package edu.uga.ccrc.ontology.doc.data;

import java.util.ArrayList;
import java.util.List;

public class OntologyClass extends OntologyThing
{
    private Integer m_order = null;
    private Integer m_level = null;

    private boolean m_instances = false;
    private List<OntologyClass> m_childClasses = new ArrayList<OntologyClass>(); 
    private OntologyClassProperties m_properties = null;

    public Integer getOrder()
    {
        return m_order;
    }
    public void setOrder(Integer a_order)
    {
        m_order = a_order;
    }
    public Integer getLevel()
    {
        return m_level;
    }
    public void setLevel(Integer a_level)
    {
        m_level = a_level;
    }
    public boolean isInstances()
    {
        return m_instances;
    }
    public void setInstances(boolean a_instances)
    {
        m_instances = a_instances;
    }
    public void setChildClasses(List<OntologyClass> classes)
    {
        m_childClasses = classes;
    }
    public List<OntologyClass> getChildClasses()
    {
        return m_childClasses;
    }
    public void setProperties(OntologyClassProperties properties)
    {
        m_properties = properties;
    }
    public OntologyClassProperties getProperties()
    {
        return m_properties;
    }
}
