package org.glycomedb.rdf.glycordf.data;


public class CoreModificationType  extends MonosaccharideProperty 
{
    public static final CoreModificationType core_modification_type_enx = new CoreModificationType( "http://purl.jp/bio/12/glyco/glycan#core_modification_type_enx" , true);
    public static final CoreModificationType core_modification_type_deoxy = new CoreModificationType( "http://purl.jp/bio/12/glyco/glycan#core_modification_type_deoxy" , true);
    public static final CoreModificationType core_modification_type_en = new CoreModificationType( "http://purl.jp/bio/12/glyco/glycan#core_modification_type_en" , true);
    public static final CoreModificationType core_modification_type_yn = new CoreModificationType( "http://purl.jp/bio/12/glyco/glycan#core_modification_type_yn" , true);
    public static final CoreModificationType core_modification_type_keto = new CoreModificationType( "http://purl.jp/bio/12/glyco/glycan#core_modification_type_keto" , true);
    public static final CoreModificationType core_modification_type_acid = new CoreModificationType( "http://purl.jp/bio/12/glyco/glycan#core_modification_type_acid" , true);
    public static final CoreModificationType core_modification_type_sp = new CoreModificationType( "http://purl.jp/bio/12/glyco/glycan#core_modification_type_sp" , true);
    public static final CoreModificationType core_modification_type_geminal = new CoreModificationType( "http://purl.jp/bio/12/glyco/glycan#core_modification_type_geminal" , true);
    public static final CoreModificationType core_modification_type_sp2 = new CoreModificationType( "http://purl.jp/bio/12/glyco/glycan#core_modification_type_sp2" , true);
    public static final CoreModificationType core_modification_type_aldi = new CoreModificationType( "http://purl.jp/bio/12/glyco/glycan#core_modification_type_aldi" , true);
    public static final CoreModificationType core_modification_type_anhydro = new CoreModificationType( "http://purl.jp/bio/12/glyco/glycan#core_modification_type_anhydro" , true);


    public CoreModificationType(){}
    
    public CoreModificationType(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public CoreModificationType(String a_uri, boolean a_isInstance)
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