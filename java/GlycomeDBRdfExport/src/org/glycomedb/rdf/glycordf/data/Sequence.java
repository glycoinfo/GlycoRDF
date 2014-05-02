package org.glycomedb.rdf.glycordf.data;


public class Sequence extends Thing
{
    protected String m_instanceURI = null;
    protected boolean m_ontologyInstance = false;

    protected String m_hasSequence = null;
    protected CarbohydrateFormat m_inCarbohydrateFormat = null;

    public Sequence(){}
    
    public Sequence(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public Sequence(String a_uri, boolean a_isInstance)
    {
        this.setInstanceURI(a_uri);
        this.setOntologyInstance(a_isInstance);
    }

    public String getInstanceURI()
    {
        return this.m_instanceURI;
    }

    public void setOntologyInstance(boolean a_isInstance)
    {
        this.m_ontologyInstance = a_isInstance;
    }

    public boolean isOntologyInstance()
    {
        return this.m_ontologyInstance;
    }

    public void setInstanceURI(String instanceURI)
    {
        this.m_instanceURI = instanceURI;
    }

    public String getHasSequence()
    {
        return this.m_hasSequence;
    }

    public void setHasSequence(String a_hasSequence)
    {
        this.m_hasSequence = a_hasSequence;
    }

    public CarbohydrateFormat getInCarbohydrateFormat()
    {
        return this.m_inCarbohydrateFormat;
    }

    public void setInCarbohydrateFormat(CarbohydrateFormat a_inCarbohydrateFormat)
    {
        this.m_inCarbohydrateFormat = a_inCarbohydrateFormat;
    }

}