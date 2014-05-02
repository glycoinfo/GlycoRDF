package org.glycomedb.rdf.glycordf.util;

public class URIPattern
{
    public static String glycomedb_image = "http://www.glycome-db.org/getSugarImage.action?id=[?id?]";

    public static String glycomedb_glycan = "http://rdf.glycome-db.org/glycan/[?id?]";
    public static String glycomedb_sequence = "http://rdf.glycome-db.org/sequence/[?id?]";
    public static String glycomedb_component = "http://rdf.glycome-db.org/component/[?id?]";
    public static String glycomedb_ref_compound = "http://rdf.glycome-db.org/referencedcompound/[?id?]";
    public static String glycomedb_source = "http://rdf.glycome-db.org/source/[?id?]";

    public static String msdb_monosaccharide = "http://www.monosaccharidedb.org/query_monosaccharide_by_name.action?scheme=msdb&name=[?id?]";

    public static String database_bcsdb = "http://csdb.glycoscience.ru/bacterial/core/search_id.php?mode=record&id_list=[?id?]";
    public static String database_pfcsdb = "http://csdb.glycoscience.ru/plant_fungal/core/search_id.php?mode=record&id_list=[?id?]";
    public static String database_carbbank = "http://www.genome.jp/dbget-bin/www_bget?carbbank+[?id?]";
    public static String database_cfg = "http://www.functionalglycomics.org/glycomics/CarbohydrateServlet?pageType=view&view=view&operationType=view&carbId=[?id?]&sideMenu=no";
    public static String database_glyaffinity = "http://worm.mpi-cbg.de/affinity/structure.action?ID=[?id?]";
    public static String database_glyco = "http://glycomics.ccrc.uga.edu/ontologywebapi/service/glycan/id/[?id?]";
    public static String database_glycobase_lille = "http://glycobase.univ-lille1.fr/base/view_mol.php?id=[?id?]";
    public static String database_glycosciences_de = "http://www.glycosciences.de/database/start.php?action=explore_linucsid&linucsid=[?id?]&show=1#struct%0A%20%20%09%09";
    public static String database_jcggdb = "http://jcggdb.jp/idb/jcggdb/[?id?]";
    public static String database_kegg = "http://www.genome.jp/dbget-bin/www_bget?gl:[?id?]";
    public static String database_pdb = "http://www.rcsb.org/pdb/explore/explore.do?structureId=[?id?]";

    public static String uniprot_taxon = "http://www.uniprot.org/taxonomy/[?id?].rdf";

    public static String getURI(String a_pattern, String a_id)
    {
        return a_pattern.replace("[?id?]", a_id);
    }
}
