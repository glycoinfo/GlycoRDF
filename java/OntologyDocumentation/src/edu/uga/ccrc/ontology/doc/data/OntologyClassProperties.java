package edu.uga.ccrc.ontology.doc.data;

import java.util.ArrayList;
import java.util.List;

public class OntologyClassProperties
{
    private String m_description = null;
    private List<OntologyPredicate> m_predicates = new ArrayList<OntologyPredicate>();
    private List<OntologyInstance> m_instances = new ArrayList<OntologyInstance>();
    private List<OntologyThing> m_parentClass = new ArrayList<OntologyThing>();
    private String m_label = null;
    public String getLabel()
    {
        return m_label;
    }
    public void setLabel(String label)
    {
        m_label = label;
    }
    public String getDescription()
    {
        return m_description;
    }
    public void setDescription(String a_description)
    {
        m_description = a_description;
    }
    public List<OntologyPredicate> getPredicates()
    {
        return m_predicates;
    }
    public void setPredicates(List<OntologyPredicate> a_predicates)
    {
        m_predicates = a_predicates;
    }
    public List<OntologyInstance> getInstances()
    {
        return m_instances;
    }
    public void setInstances(List<OntologyInstance> a_instances)
    {
        m_instances = a_instances;
    }
    public void setParentClass(List<OntologyThing> parentClass)
    {
        m_parentClass = parentClass;
    }
    public List<OntologyThing> getParentClass()
    {
        return m_parentClass;
    }
}
