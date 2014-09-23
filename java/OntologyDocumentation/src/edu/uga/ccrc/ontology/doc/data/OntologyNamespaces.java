package edu.uga.ccrc.ontology.doc.data;

public class OntologyNamespaces
{
    private String m_uri = null;
    private String m_prefix = null;
    public String getUri()
    {
        return m_uri;
    }
    public void setUri(String a_uri)
    {
        m_uri = a_uri;
    }
    public String getPrefix()
    {
        return m_prefix;
    }
    public void setPrefix(String a_prefix)
    {
        m_prefix = a_prefix;
    }
}
