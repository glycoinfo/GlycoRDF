package org.glycomedb.rdf.glycordf.data;


public class Component extends Thing
{
    protected String m_instanceURI = null;
    protected boolean m_ontologyInstance = false;

    protected Integer m_hasCardinalityPerRepeat = null;
    protected Integer m_hasCardinality = null;
    protected Monosaccharide m_hasMonosaccharide = null;

    public Component(){}
    
    public Component(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public Component(String a_uri, boolean a_isInstance)
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

    public Integer getHasCardinalityPerRepeat()
    {
        return this.m_hasCardinalityPerRepeat;
    }

    public void setHasCardinalityPerRepeat(Integer a_hasCardinalityPerRepeat)
    {
        this.m_hasCardinalityPerRepeat = a_hasCardinalityPerRepeat;
    }

    public Integer getHasCardinality()
    {
        return this.m_hasCardinality;
    }

    public void setHasCardinality(Integer a_hasCardinality)
    {
        this.m_hasCardinality = a_hasCardinality;
    }

    public Monosaccharide getHasMonosaccharide()
    {
        return this.m_hasMonosaccharide;
    }

    public void setHasMonosaccharide(Monosaccharide a_hasMonosaccharide)
    {
        this.m_hasMonosaccharide = a_hasMonosaccharide;
    }

}