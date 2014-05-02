package org.glycomedb.rdf.glycordf.data;


public class Configuration  extends MonosaccharideProperty 
{
    public static final Configuration configuration_x = new Configuration( "http://purl.jp/bio/12/glyco/glycan#configuration_x" , true);

    protected RelativeConfiguration m_hasRelativeConfiguration = null;
    protected AbsoluteConfiguration m_hasAbsoluteConfiguration = null;

    public Configuration(){}
    
    public Configuration(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public Configuration(String a_uri, boolean a_isInstance)
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

    public RelativeConfiguration getHasRelativeConfiguration()
    {
        return this.m_hasRelativeConfiguration;
    }

    public void setHasRelativeConfiguration(RelativeConfiguration a_hasRelativeConfiguration)
    {
        this.m_hasRelativeConfiguration = a_hasRelativeConfiguration;
    }

    public AbsoluteConfiguration getHasAbsoluteConfiguration()
    {
        return this.m_hasAbsoluteConfiguration;
    }

    public void setHasAbsoluteConfiguration(AbsoluteConfiguration a_hasAbsoluteConfiguration)
    {
        this.m_hasAbsoluteConfiguration = a_hasAbsoluteConfiguration;
    }

}