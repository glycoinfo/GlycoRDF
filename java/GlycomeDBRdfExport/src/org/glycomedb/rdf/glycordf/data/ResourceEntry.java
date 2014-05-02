package org.glycomedb.rdf.glycordf.data;


public class ResourceEntry extends Thing
{
    protected String m_instanceURI = null;
    protected boolean m_ontologyInstance = false;

    protected GlycanDatabase m_inGlycanDatabase = null;
    protected String m_identifier = null;

    public ResourceEntry(){}
    
    public ResourceEntry(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public ResourceEntry(String a_uri, boolean a_isInstance)
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

    public GlycanDatabase getInGlycanDatabase()
    {
        return this.m_inGlycanDatabase;
    }

    public void setInGlycanDatabase(GlycanDatabase a_inGlycanDatabase)
    {
        this.m_inGlycanDatabase = a_inGlycanDatabase;
    }

    public String getIdentifier()
    {
        return this.m_identifier;
    }

    public void setIdentifier(String a_identifier)
    {
        this.m_identifier = a_identifier;
    }

}