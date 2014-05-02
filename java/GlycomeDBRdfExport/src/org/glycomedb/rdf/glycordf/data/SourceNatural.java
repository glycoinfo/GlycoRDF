package org.glycomedb.rdf.glycordf.data;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SourceNatural  extends Source 
{

    protected URI m_hasTissue = null;
    protected URI m_hasLifeStage = null;
    protected SourceNatural m_hostedBy = null;
    protected List<URI> m_hasFluid = new ArrayList<URI>();
    protected URI m_hasTaxon = null;
    protected URI m_hasCellLine = null;
    protected List<URI> m_hasSampleType = new ArrayList<URI>();
    protected URI m_hasCellType = null;
    protected List<SourceNatural> m_hybridWith = new ArrayList<SourceNatural>();
    protected URI m_hasOrgan = null;
    protected List<URI> m_hasDisease = new ArrayList<URI>();

    public SourceNatural(){}
    
    public SourceNatural(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public SourceNatural(String a_uri, boolean a_isInstance)
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

    public URI getHasTissue()
    {
        return this.m_hasTissue;
    }

    public void setHasTissue(URI a_hasTissue)
    {
        this.m_hasTissue = a_hasTissue;
    }

    public URI getHasLifeStage()
    {
        return this.m_hasLifeStage;
    }

    public void setHasLifeStage(URI a_hasLifeStage)
    {
        this.m_hasLifeStage = a_hasLifeStage;
    }

    public SourceNatural getHostedBy()
    {
        return this.m_hostedBy;
    }

    public void setHostedBy(SourceNatural a_hostedBy)
    {
        this.m_hostedBy = a_hostedBy;
    }

    public List<URI> getHasFluid()
    {
        return this.m_hasFluid;
    }

    public void setHasFluid(List<URI> a_hasFluid)
    {
        this.m_hasFluid = a_hasFluid;
    }

    public void addHasFluid(URI a_hasFluid)
    {
        this.m_hasFluid.add(a_hasFluid);
    }
    public URI getHasTaxon()
    {
        return this.m_hasTaxon;
    }

    public void setHasTaxon(URI a_hasTaxon)
    {
        this.m_hasTaxon = a_hasTaxon;
    }

    public URI getHasCellLine()
    {
        return this.m_hasCellLine;
    }

    public void setHasCellLine(URI a_hasCellLine)
    {
        this.m_hasCellLine = a_hasCellLine;
    }

    public List<URI> getHasSampleType()
    {
        return this.m_hasSampleType;
    }

    public void setHasSampleType(List<URI> a_hasSampleType)
    {
        this.m_hasSampleType = a_hasSampleType;
    }

    public void addHasSampleType(URI a_hasSampleType)
    {
        this.m_hasSampleType.add(a_hasSampleType);
    }
    public URI getHasCellType()
    {
        return this.m_hasCellType;
    }

    public void setHasCellType(URI a_hasCellType)
    {
        this.m_hasCellType = a_hasCellType;
    }

    public List<SourceNatural> getHybridWith()
    {
        return this.m_hybridWith;
    }

    public void setHybridWith(List<SourceNatural> a_hybridWith)
    {
        this.m_hybridWith = a_hybridWith;
    }

    public void addHybridWith(SourceNatural a_hybridWith)
    {
        this.m_hybridWith.add(a_hybridWith);
    }
    public URI getHasOrgan()
    {
        return this.m_hasOrgan;
    }

    public void setHasOrgan(URI a_hasOrgan)
    {
        this.m_hasOrgan = a_hasOrgan;
    }

    public List<URI> getHasDisease()
    {
        return this.m_hasDisease;
    }

    public void setHasDisease(List<URI> a_hasDisease)
    {
        this.m_hasDisease = a_hasDisease;
    }

    public void addHasDisease(URI a_hasDisease)
    {
        this.m_hasDisease.add(a_hasDisease);
    }
}