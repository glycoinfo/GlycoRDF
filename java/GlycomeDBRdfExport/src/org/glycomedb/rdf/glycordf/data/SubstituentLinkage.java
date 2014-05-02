package org.glycomedb.rdf.glycordf.data;


public class SubstituentLinkage  extends MonosaccharideProperty 
{

    protected Integer m_hasSubstituentLinkagePosition2 = null;
    protected Integer m_hasSubstituentLinkagePosition = null;
    protected Integer m_hasBasetypeLinkagePosition = null;
    protected LinkageType m_hasLinkageType = null;

    public SubstituentLinkage(){}
    
    public SubstituentLinkage(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public SubstituentLinkage(String a_uri, boolean a_isInstance)
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

    public Integer getHasSubstituentLinkagePosition2()
    {
        return this.m_hasSubstituentLinkagePosition2;
    }

    public void setHasSubstituentLinkagePosition2(Integer a_hasSubstituentLinkagePosition2)
    {
        this.m_hasSubstituentLinkagePosition2 = a_hasSubstituentLinkagePosition2;
    }

    public Integer getHasSubstituentLinkagePosition()
    {
        return this.m_hasSubstituentLinkagePosition;
    }

    public void setHasSubstituentLinkagePosition(Integer a_hasSubstituentLinkagePosition)
    {
        this.m_hasSubstituentLinkagePosition = a_hasSubstituentLinkagePosition;
    }

    public Integer getHasBasetypeLinkagePosition()
    {
        return this.m_hasBasetypeLinkagePosition;
    }

    public void setHasBasetypeLinkagePosition(Integer a_hasBasetypeLinkagePosition)
    {
        this.m_hasBasetypeLinkagePosition = a_hasBasetypeLinkagePosition;
    }

    public LinkageType getHasLinkageType()
    {
        return this.m_hasLinkageType;
    }

    public void setHasLinkageType(LinkageType a_hasLinkageType)
    {
        this.m_hasLinkageType = a_hasLinkageType;
    }

}