package org.glycomedb.rdf.glycordf.data;


public class CarbohydrateFormat extends Thing
{
    protected String m_instanceURI = null;
    protected boolean m_ontologyInstance = false;
    public static final CarbohydrateFormat carbohydrate_format_wurcs = new CarbohydrateFormat( "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format_wurcs" , true);
    public static final CarbohydrateFormat carbohydrate_format_glycoct = new CarbohydrateFormat( "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format_glycoct" , true);
    public static final CarbohydrateFormat carbohydrate_format_iupac_short = new CarbohydrateFormat( "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format_iupac_short" , true);
    public static final CarbohydrateFormat carbohydrate_format_iupac_condensed = new CarbohydrateFormat( "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format_iupac_condensed" , true);
    public static final CarbohydrateFormat carbohydrate_format_csdb = new CarbohydrateFormat( "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format_csdb" , true);
    public static final CarbohydrateFormat carbohydrate_format_linearcode = new CarbohydrateFormat( "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format_linearcode" , true);
    public static final CarbohydrateFormat carbohydrate_format_glyde2 = new CarbohydrateFormat( "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format_glyde2" , true);
    public static final CarbohydrateFormat carbohydrate_format_iupac_extended = new CarbohydrateFormat( "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format_iupac_extended" , true);
    public static final CarbohydrateFormat carbohydrate_format_kcf = new CarbohydrateFormat( "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format_kcf" , true);
    public static final CarbohydrateFormat carbohydrate_format_carbbank = new CarbohydrateFormat( "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format_carbbank" , true);
    public static final CarbohydrateFormat carbohydrate_format_linucs = new CarbohydrateFormat( "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format_linucs" , true);


    public CarbohydrateFormat(){}
    
    public CarbohydrateFormat(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public CarbohydrateFormat(String a_uri, boolean a_isInstance)
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