package org.glycomedb.rdf.glycordf.data;


public class RingType  extends MonosaccharideProperty 
{
    public static final RingType ring_type_unknown = new RingType( "http://purl.jp/bio/12/glyco/glycan#ring_type_unknown" , true);
    public static final RingType pyranose = new RingType( "http://purl.jp/bio/12/glyco/glycan#pyranose" , true);
    public static final RingType open = new RingType( "http://purl.jp/bio/12/glyco/glycan#open" , true);
    public static final RingType furanose = new RingType( "http://purl.jp/bio/12/glyco/glycan#furanose" , true);


    public RingType(){}
    
    public RingType(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public RingType(String a_uri, boolean a_isInstance)
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