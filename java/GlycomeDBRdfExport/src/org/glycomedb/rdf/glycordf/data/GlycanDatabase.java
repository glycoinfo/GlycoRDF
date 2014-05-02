package org.glycomedb.rdf.glycordf.data;

import java.util.ArrayList;
import java.util.List;

public class GlycanDatabase extends Thing
{
    protected String m_instanceURI = null;
    protected boolean m_ontologyInstance = false;
    public static final GlycanDatabase database_unicarb_db = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_unicarb_db" , true);
    public static final GlycanDatabase database_glyco = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_glyco" , true);
    public static final GlycanDatabase database_glyconmr = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_glyconmr" , true);
    public static final GlycanDatabase database_glycobase_dublin = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_glycobase_dublin" , true);
    public static final GlycanDatabase database_cazy = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_cazy" , true);
    public static final GlycanDatabase database_glyconavi = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_glyconavi" , true);
    public static final GlycanDatabase database_glycobase_lille = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_glycobase_lille" , true);
    public static final GlycanDatabase database_pdb = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_pdb" , true);
    public static final GlycanDatabase database_cfg_glycoenzymes = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_cfg_glycoenzymes" , true);
    public static final GlycanDatabase database_smsa = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_smsa" , true);
    public static final GlycanDatabase database_pubchem = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_pubchem" , true);
    public static final GlycanDatabase database_chebi = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_chebi" , true);
    public static final GlycanDatabase database_carint = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_carint" , true);
    public static final GlycanDatabase database_cfg_gbp = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_cfg_gbp" , true);
    public static final GlycanDatabase database_monosaccharidedb = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_monosaccharidedb" , true);
    public static final GlycanDatabase database_glyaffinity = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_glyaffinity" , true);
    public static final GlycanDatabase database_pfcsdb = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_pfcsdb" , true);
    public static final GlycanDatabase database_bcsdb = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_bcsdb" , true);
    public static final GlycanDatabase database_jcggdb = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_jcggdb" , true);
    public static final GlycanDatabase database_kegg = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_kegg" , true);
    public static final GlycanDatabase database_glycosciences_de = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_glycosciences_de" , true);
    public static final GlycanDatabase database_glycomapsdb = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_glycomapsdb" , true);
    public static final GlycanDatabase database_carbbank = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_carbbank" , true);
    public static final GlycanDatabase database_unicarbkb = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_unicarbkb" , true);
    public static final GlycanDatabase database_glycomedb = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_glycomedb" , true);
    public static final GlycanDatabase database_cfg = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_cfg" , true);
    public static final GlycanDatabase database_chemspider = new GlycanDatabase( "http://purl.jp/bio/12/glyco/glycan#database_chemspider" , true);

    protected String m_hasUrlTemplate = null;
    protected String m_hasAbbreviation = null;
    protected List<GlycanDatabaseCategory> m_hasCategory = new ArrayList<GlycanDatabaseCategory>();

    public GlycanDatabase(){}
    
    public GlycanDatabase(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public GlycanDatabase(String a_uri, boolean a_isInstance)
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

    public String getHasUrlTemplate()
    {
        return this.m_hasUrlTemplate;
    }

    public void setHasUrlTemplate(String a_hasUrlTemplate)
    {
        this.m_hasUrlTemplate = a_hasUrlTemplate;
    }

    public String getHasAbbreviation()
    {
        return this.m_hasAbbreviation;
    }

    public void setHasAbbreviation(String a_hasAbbreviation)
    {
        this.m_hasAbbreviation = a_hasAbbreviation;
    }

    public List<GlycanDatabaseCategory> getHasCategory()
    {
        return this.m_hasCategory;
    }

    public void setHasCategory(List<GlycanDatabaseCategory> a_hasCategory)
    {
        this.m_hasCategory = a_hasCategory;
    }

    public void addHasCategory(GlycanDatabaseCategory a_hasCategory)
    {
        this.m_hasCategory.add(a_hasCategory);
    }
}