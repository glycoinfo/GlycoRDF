package org.glycomedb.rdf.glycordf.data;


public class MonosaccharideNotationScheme  extends MonosaccharideProperty 
{
    public static final MonosaccharideNotationScheme monosaccharide_notation_scheme_pdb = new MonosaccharideNotationScheme( "http://purl.jp/bio/12/glyco/glycan#monosaccharide_notation_scheme_pdb" , true);
    public static final MonosaccharideNotationScheme monosaccharide_notation_scheme_monosaccharidedb = new MonosaccharideNotationScheme( "http://purl.jp/bio/12/glyco/glycan#monosaccharide_notation_scheme_monosaccharidedb" , true);
    public static final MonosaccharideNotationScheme monosaccharide_notation_scheme_glycosciences_de = new MonosaccharideNotationScheme( "http://purl.jp/bio/12/glyco/glycan#monosaccharide_notation_scheme_glycosciences_de" , true);
    public static final MonosaccharideNotationScheme monosaccharide_notation_scheme_glycoct = new MonosaccharideNotationScheme( "http://purl.jp/bio/12/glyco/glycan#monosaccharide_notation_scheme_glycoct" , true);
    public static final MonosaccharideNotationScheme monosaccharide_notation_scheme_cfg = new MonosaccharideNotationScheme( "http://purl.jp/bio/12/glyco/glycan#monosaccharide_notation_scheme_cfg" , true);
    public static final MonosaccharideNotationScheme monosaccharide_notation_scheme_carbbank = new MonosaccharideNotationScheme( "http://purl.jp/bio/12/glyco/glycan#monosaccharide_notation_scheme_carbbank" , true);
    public static final MonosaccharideNotationScheme monosaccharide_notation_scheme_bcsdb = new MonosaccharideNotationScheme( "http://purl.jp/bio/12/glyco/glycan#monosaccharide_notation_scheme_bcsdb" , true);
    public static final MonosaccharideNotationScheme monosaccharide_notation_scheme_amber_glycam = new MonosaccharideNotationScheme( "http://purl.jp/bio/12/glyco/glycan#monosaccharide_notation_scheme_amber_glycam" , true);


    public MonosaccharideNotationScheme(){}
    
    public MonosaccharideNotationScheme(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public MonosaccharideNotationScheme(String a_uri, boolean a_isInstance)
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