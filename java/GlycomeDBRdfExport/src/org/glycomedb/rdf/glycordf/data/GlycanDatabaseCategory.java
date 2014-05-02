package org.glycomedb.rdf.glycordf.data;


public class GlycanDatabaseCategory extends Thing
{
    protected String m_instanceURI = null;
    protected boolean m_ontologyInstance = false;
    public static final GlycanDatabaseCategory database_category_chemical_compound = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_chemical_compound" , true);
    public static final GlycanDatabaseCategory database_category_monosaccharide = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_monosaccharide" , true);
    public static final GlycanDatabaseCategory database_category_cabohydrate_structure = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_cabohydrate_structure" , true);
    public static final GlycanDatabaseCategory database_category_taxonomy = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_taxonomy" , true);
    public static final GlycanDatabaseCategory database_category_bibliography = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_bibliography" , true);
    public static final GlycanDatabaseCategory database_category_carbohydrate_conformation = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_carbohydrate_conformation" , true);
    public static final GlycanDatabaseCategory database_category_carbohydrate_interaction = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_carbohydrate_interaction" , true);
    public static final GlycanDatabaseCategory database_category_synthesis = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_synthesis" , true);
    public static final GlycanDatabaseCategory database_category_tissue = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_tissue" , true);
    public static final GlycanDatabaseCategory database_category_experimental = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_experimental" , true);
    public static final GlycanDatabaseCategory database_category_glycan_binding_protein = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_glycan_binding_protein" , true);
    public static final GlycanDatabaseCategory database_category_glycoenzyme = new GlycanDatabaseCategory( "http://purl.jp/bio/12/glyco/glycan#database_category_glycoenzyme" , true);


    public GlycanDatabaseCategory(){}
    
    public GlycanDatabaseCategory(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public GlycanDatabaseCategory(String a_uri, boolean a_isInstance)
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