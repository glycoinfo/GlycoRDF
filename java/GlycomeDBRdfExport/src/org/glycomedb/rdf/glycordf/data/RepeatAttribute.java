package org.glycomedb.rdf.glycordf.data;


public class RepeatAttribute extends Thing
{
    protected String m_instanceURI = null;
    protected boolean m_ontologyInstance = false;
    public static final RepeatAttribute repeat_attribute_unknown = new RepeatAttribute( "http://purl.jp/bio/12/glyco/glycan#repeat_attribute_unknown" , true);
    public static final RepeatAttribute repeat_attribute_min = new RepeatAttribute( "http://purl.jp/bio/12/glyco/glycan#repeat_attribute_min" , true);
    public static final RepeatAttribute repeat_attribute_max = new RepeatAttribute( "http://purl.jp/bio/12/glyco/glycan#repeat_attribute_max" , true);
    public static final RepeatAttribute repeat_attribute_exact = new RepeatAttribute( "http://purl.jp/bio/12/glyco/glycan#repeat_attribute_exact" , true);
    public static final RepeatAttribute repeat_attribute_average = new RepeatAttribute( "http://purl.jp/bio/12/glyco/glycan#repeat_attribute_average" , true);


    public RepeatAttribute(){}
    
    public RepeatAttribute(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public RepeatAttribute(String a_uri, boolean a_isInstance)
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