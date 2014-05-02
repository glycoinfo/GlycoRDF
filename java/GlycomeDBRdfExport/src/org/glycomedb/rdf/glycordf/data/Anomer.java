package org.glycomedb.rdf.glycordf.data;


public class Anomer  extends MonosaccharideProperty 
{
    public static final Anomer anomer_unknown = new Anomer( "http://purl.jp/bio/12/glyco/glycan#anomer_unknown" , true);
    public static final Anomer anomer_none = new Anomer( "http://purl.jp/bio/12/glyco/glycan#anomer_none" , true);
    public static final Anomer anomer_beta = new Anomer( "http://purl.jp/bio/12/glyco/glycan#anomer_beta" , true);
    public static final Anomer anomer_alpha = new Anomer( "http://purl.jp/bio/12/glyco/glycan#anomer_alpha" , true);


    public Anomer(){}
    
    public Anomer(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public Anomer(String a_uri, boolean a_isInstance)
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