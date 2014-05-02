package org.glycomedb.rdf.glycordf.data;


public class Image  extends Thing 
{
    protected String m_instanceURI = null;
    protected boolean m_ontologyInstance = false;

    protected SymbolFormat m_hasSymbolFormat = null;
    protected String m_format = null;

    public String getFormat()
    {
        return m_format;
    }

    public void setFormat(String a_format)
    {
        m_format = a_format;
    }

    public Image(){}
    
    public Image(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public Image(String a_uri, boolean a_isInstance)
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

    public SymbolFormat getHasSymbolFormat()
    {
        return this.m_hasSymbolFormat;
    }

    public void setHasSymbolFormat(SymbolFormat a_hasSymbolFormat)
    {
        this.m_hasSymbolFormat = a_hasSymbolFormat;
    }

}