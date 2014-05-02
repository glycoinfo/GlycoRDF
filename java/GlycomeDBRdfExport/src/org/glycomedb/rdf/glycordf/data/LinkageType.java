package org.glycomedb.rdf.glycordf.data;


public class LinkageType  extends MonosaccharideProperty 
{
    public static final LinkageType linkage_type_s_config = new LinkageType( "http://purl.jp/bio/12/glyco/glycan#linkage_type_s_config" , true);
    public static final LinkageType linkage_type_r_config = new LinkageType( "http://purl.jp/bio/12/glyco/glycan#linkage_type_r_config" , true);
    public static final LinkageType linkage_type_h_loss = new LinkageType( "http://purl.jp/bio/12/glyco/glycan#linkage_type_h_loss" , true);
    public static final LinkageType linkage_type_h_at_oh = new LinkageType( "http://purl.jp/bio/12/glyco/glycan#linkage_type_h_at_oh" , true);
    public static final LinkageType linkage_type_deoxy = new LinkageType( "http://purl.jp/bio/12/glyco/glycan#linkage_type_deoxy" , true);


    public LinkageType(){}
    
    public LinkageType(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public LinkageType(String a_uri, boolean a_isInstance)
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