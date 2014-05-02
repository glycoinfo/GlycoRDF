package org.glycomedb.rdf.glycordf.data;

import java.util.ArrayList;
import java.util.List;

public class Substituent  extends MonosaccharideProperty 
{

    protected List<Boolean> m_isLinkable = new ArrayList<Boolean>();
    protected List<Boolean> m_isFuzzy = new ArrayList<Boolean>();
    protected List<Integer> m_hasValence = new ArrayList<Integer>();
    protected List<String> m_hasSubstitutionName = new ArrayList<String>();
    protected SubstituentType m_hasSubstituentType = null;
    protected List<SubstituentLinkage> m_hasSubstituentLinkage = new ArrayList<SubstituentLinkage>();

    public Substituent(){}
    
    public Substituent(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public Substituent(String a_uri, boolean a_isInstance)
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

    public List<Boolean> getIsLinkable()
    {
        return this.m_isLinkable;
    }

    public void setIsLinkable(List<Boolean> a_isLinkable)
    {
        this.m_isLinkable = a_isLinkable;
    }

    public void addIsLinkable(Boolean a_isLinkable)
    {
        this.m_isLinkable.add(a_isLinkable);
    }
    public List<Boolean> getIsFuzzy()
    {
        return this.m_isFuzzy;
    }

    public void setIsFuzzy(List<Boolean> a_isFuzzy)
    {
        this.m_isFuzzy = a_isFuzzy;
    }

    public void addIsFuzzy(Boolean a_isFuzzy)
    {
        this.m_isFuzzy.add(a_isFuzzy);
    }
    public List<Integer> getHasValence()
    {
        return this.m_hasValence;
    }

    public void setHasValence(List<Integer> a_hasValence)
    {
        this.m_hasValence = a_hasValence;
    }

    public void addHasValence(Integer a_hasValence)
    {
        this.m_hasValence.add(a_hasValence);
    }
    public List<String> getHasSubstitutionName()
    {
        return this.m_hasSubstitutionName;
    }

    public void setHasSubstitutionName(List<String> a_hasSubstitutionName)
    {
        this.m_hasSubstitutionName = a_hasSubstitutionName;
    }

    public void addHasSubstitutionName(String a_hasSubstitutionName)
    {
        this.m_hasSubstitutionName.add(a_hasSubstitutionName);
    }
    public SubstituentType getHasSubstituentType()
    {
        return this.m_hasSubstituentType;
    }

    public void setHasSubstituentType(SubstituentType a_hasSubstituentType)
    {
        this.m_hasSubstituentType = a_hasSubstituentType;
    }

    public List<SubstituentLinkage> getHasSubstituentLinkage()
    {
        return this.m_hasSubstituentLinkage;
    }

    public void setHasSubstituentLinkage(List<SubstituentLinkage> a_hasSubstituentLinkage)
    {
        this.m_hasSubstituentLinkage = a_hasSubstituentLinkage;
    }

    public void addHasSubstituentLinkage(SubstituentLinkage a_hasSubstituentLinkage)
    {
        this.m_hasSubstituentLinkage.add(a_hasSubstituentLinkage);
    }
}