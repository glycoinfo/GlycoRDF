package org.glycomedb.rdf.glycordf.data;

import java.util.ArrayList;
import java.util.List;

public class Monosaccharide  extends Saccharide 
{

    protected Integer m_hasMsdbId = null;
    protected List<MonosaccharideAlias> m_hasAlias = new ArrayList<MonosaccharideAlias>();
    protected List<Integer> m_hasLinkagePosition = new ArrayList<Integer>();
    protected List<Substituent> m_hasSubstitution = new ArrayList<Substituent>();
    protected Double m_hasAverageMolecularWeight = null;
    protected Basetype m_hasBasetype = null;
    protected Double m_hasMonoisotopicMolecularWeight = null;

    public Monosaccharide(){}
    
    public Monosaccharide(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public Monosaccharide(String a_uri, boolean a_isInstance)
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

    public Integer getHasMsdbId()
    {
        return this.m_hasMsdbId;
    }

    public void setHasMsdbId(Integer a_hasMsdbId)
    {
        this.m_hasMsdbId = a_hasMsdbId;
    }

    public List<MonosaccharideAlias> getHasAlias()
    {
        return this.m_hasAlias;
    }

    public void setHasAlias(List<MonosaccharideAlias> a_hasAlias)
    {
        this.m_hasAlias = a_hasAlias;
    }

    public void addHasAlias(MonosaccharideAlias a_hasAlias)
    {
        this.m_hasAlias.add(a_hasAlias);
    }
    public List<Integer> getHasLinkagePosition()
    {
        return this.m_hasLinkagePosition;
    }

    public void setHasLinkagePosition(List<Integer> a_hasLinkagePosition)
    {
        this.m_hasLinkagePosition = a_hasLinkagePosition;
    }

    public void addHasLinkagePosition(Integer a_hasLinkagePosition)
    {
        this.m_hasLinkagePosition.add(a_hasLinkagePosition);
    }
    public List<Substituent> getHasSubstitution()
    {
        return this.m_hasSubstitution;
    }

    public void setHasSubstitution(List<Substituent> a_hasSubstitution)
    {
        this.m_hasSubstitution = a_hasSubstitution;
    }

    public void addHasSubstitution(Substituent a_hasSubstitution)
    {
        this.m_hasSubstitution.add(a_hasSubstitution);
    }
    public Double getHasAverageMolecularWeight()
    {
        return this.m_hasAverageMolecularWeight;
    }

    public void setHasAverageMolecularWeight(Double a_hasAverageMolecularWeight)
    {
        this.m_hasAverageMolecularWeight = a_hasAverageMolecularWeight;
    }

    public Basetype getHasBasetype()
    {
        return this.m_hasBasetype;
    }

    public void setHasBasetype(Basetype a_hasBasetype)
    {
        this.m_hasBasetype = a_hasBasetype;
    }

    public Double getHasMonoisotopicMolecularWeight()
    {
        return this.m_hasMonoisotopicMolecularWeight;
    }

    public void setHasMonoisotopicMolecularWeight(Double a_hasMonoisotopicMolecularWeight)
    {
        this.m_hasMonoisotopicMolecularWeight = a_hasMonoisotopicMolecularWeight;
    }

}