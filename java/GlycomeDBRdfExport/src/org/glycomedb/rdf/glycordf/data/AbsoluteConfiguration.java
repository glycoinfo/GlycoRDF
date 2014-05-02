package org.glycomedb.rdf.glycordf.data;


public class AbsoluteConfiguration  extends MonosaccharideProperty 
{
    public static final AbsoluteConfiguration absolute_configuration_unknown = new AbsoluteConfiguration( "http://purl.jp/bio/12/glyco/glycan#absolute_configuration_unknown" , true);
    public static final AbsoluteConfiguration absolute_configuration_laevus = new AbsoluteConfiguration( "http://purl.jp/bio/12/glyco/glycan#absolute_configuration_laevus" , true);
    public static final AbsoluteConfiguration absolute_configuration_dexter = new AbsoluteConfiguration( "http://purl.jp/bio/12/glyco/glycan#absolute_configuration_dexter" , true);


    public AbsoluteConfiguration(){}
    
    public AbsoluteConfiguration(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public AbsoluteConfiguration(String a_uri, boolean a_isInstance)
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

}