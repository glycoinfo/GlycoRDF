package org.glycomedb.rdf.glycordf.data;


public class SymbolFormat extends Thing
{
    protected String m_instanceURI = null;
    protected boolean m_ontologyInstance = false;
    public static final SymbolFormat symbol_format_uoxf_bw = new SymbolFormat( "http://purl.jp/bio/12/glyco/glycan#symbol_format_uoxf_bw" , true);
    public static final SymbolFormat symbol_format_uoxf = new SymbolFormat( "http://purl.jp/bio/12/glyco/glycan#symbol_format_uoxf" , true);
    public static final SymbolFormat symbol_format_text = new SymbolFormat( "http://purl.jp/bio/12/glyco/glycan#symbol_format_text" , true);
    public static final SymbolFormat symbol_format_cfg_bw = new SymbolFormat( "http://purl.jp/bio/12/glyco/glycan#symbol_format_cfg_bw" , true);
    public static final SymbolFormat symbol_format_cfg = new SymbolFormat( "http://purl.jp/bio/12/glyco/glycan#symbol_format_cfg" , true);


    public SymbolFormat(){}
    
    public SymbolFormat(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public SymbolFormat(String a_uri, boolean a_isInstance)
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