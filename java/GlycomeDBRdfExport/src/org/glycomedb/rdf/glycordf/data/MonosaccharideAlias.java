package org.glycomedb.rdf.glycordf.data;

import java.util.ArrayList;
import java.util.List;

public class MonosaccharideAlias  extends MonosaccharideProperty 
{

    protected Boolean m_isTrivialName = null;
    protected Boolean m_isPrimaryName = null;
    protected List<String> m_hasAliasName = new ArrayList<String>();
    protected MonosaccharideNotationScheme m_hasMonosaccharideNotationScheme = null;
    protected List<Substituent> m_hasExternalSubstituent = new ArrayList<Substituent>();

    public MonosaccharideAlias(){}
    
    public MonosaccharideAlias(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public MonosaccharideAlias(String a_uri, boolean a_isInstance)
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

    public Boolean getIsTrivialName()
    {
        return this.m_isTrivialName;
    }

    public void setIsTrivialName(Boolean a_isTrivialName)
    {
        this.m_isTrivialName = a_isTrivialName;
    }

    public Boolean getIsPrimaryName()
    {
        return this.m_isPrimaryName;
    }

    public void setIsPrimaryName(Boolean a_isPrimaryName)
    {
        this.m_isPrimaryName = a_isPrimaryName;
    }

    public List<String> getHasAliasName()
    {
        return this.m_hasAliasName;
    }

    public void setHasAliasName(List<String> a_hasAliasName)
    {
        this.m_hasAliasName = a_hasAliasName;
    }

    public void addHasAliasName(String a_hasAliasName)
    {
        this.m_hasAliasName.add(a_hasAliasName);
    }
    public MonosaccharideNotationScheme getHasMonosaccharideNotationScheme()
    {
        return this.m_hasMonosaccharideNotationScheme;
    }

    public void setHasMonosaccharideNotationScheme(MonosaccharideNotationScheme a_hasMonosaccharideNotationScheme)
    {
        this.m_hasMonosaccharideNotationScheme = a_hasMonosaccharideNotationScheme;
    }

    public List<Substituent> getHasExternalSubstituent()
    {
        return this.m_hasExternalSubstituent;
    }

    public void setHasExternalSubstituent(List<Substituent> a_hasExternalSubstituent)
    {
        this.m_hasExternalSubstituent = a_hasExternalSubstituent;
    }

    public void addHasExternalSubstituent(Substituent a_hasExternalSubstituent)
    {
        this.m_hasExternalSubstituent.add(a_hasExternalSubstituent);
    }
}