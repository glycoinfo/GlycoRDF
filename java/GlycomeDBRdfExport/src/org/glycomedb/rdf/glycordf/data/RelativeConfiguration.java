package org.glycomedb.rdf.glycordf.data;


public class RelativeConfiguration  extends MonosaccharideProperty 
{
    public static final RelativeConfiguration manno = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#manno" , true);
    public static final RelativeConfiguration allo = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#allo" , true);
    public static final RelativeConfiguration talo = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#talo" , true);
    public static final RelativeConfiguration xylo = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#xylo" , true);
    public static final RelativeConfiguration lyxo = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#lyxo" , true);
    public static final RelativeConfiguration erythro = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#erythro" , true);
    public static final RelativeConfiguration gluco = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#gluco" , true);
    public static final RelativeConfiguration arabino = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#arabino" , true);
    public static final RelativeConfiguration altro = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#altro" , true);
    public static final RelativeConfiguration ribo = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#ribo" , true);
    public static final RelativeConfiguration ido = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#ido" , true);
    public static final RelativeConfiguration threo = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#threo" , true);
    public static final RelativeConfiguration galacto = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#galacto" , true);
    public static final RelativeConfiguration gulo = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#gulo" , true);
    public static final RelativeConfiguration glycero = new RelativeConfiguration( "http://purl.jp/bio/12/glyco/glycan#glycero" , true);


    public RelativeConfiguration(){}
    
    public RelativeConfiguration(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public RelativeConfiguration(String a_uri, boolean a_isInstance)
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