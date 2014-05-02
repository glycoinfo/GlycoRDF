package org.glycomedb.rdf.glycordf.data;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ReferencedCompound extends Thing
{
    protected String m_instanceURI = null;
    protected boolean m_ontologyInstance = false;

    protected List<String> m_hasStructureLocation = new ArrayList<String>();
    protected Boolean m_elucidated = null;
    protected List<Source> m_isFromSource = new ArrayList<Source>();
    protected Saccharide m_hasGlycan = null;
    protected List<URI> m_hasEvidence = new ArrayList<URI>();
    protected URI m_publishedIn = null;

    public ReferencedCompound(){}
    
    public ReferencedCompound(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public ReferencedCompound(String a_uri, boolean a_isInstance)
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

    public List<String> getHasStructureLocation()
    {
        return this.m_hasStructureLocation;
    }

    public void setHasStructureLocation(List<String> a_hasStructureLocation)
    {
        this.m_hasStructureLocation = a_hasStructureLocation;
    }

    public void addHasStructureLocation(String a_hasStructureLocation)
    {
        this.m_hasStructureLocation.add(a_hasStructureLocation);
    }
    public Boolean getElucidated()
    {
        return this.m_elucidated;
    }

    public void setElucidated(Boolean a_elucidated)
    {
        this.m_elucidated = a_elucidated;
    }

    public List<Source> getIsFromSource()
    {
        return this.m_isFromSource;
    }

    public void setIsFromSource(List<Source> a_isFromSource)
    {
        this.m_isFromSource = a_isFromSource;
    }

    public void addIsFromSource(Source a_isFromSource)
    {
        this.m_isFromSource.add(a_isFromSource);
    }
    public Saccharide getHasGlycan()
    {
        return this.m_hasGlycan;
    }

    public void setHasGlycan(Saccharide a_hasGlycan)
    {
        this.m_hasGlycan = a_hasGlycan;
    }

    public List<URI> getHasEvidence()
    {
        return this.m_hasEvidence;
    }

    public void setHasEvidence(List<URI> a_hasEvidence)
    {
        this.m_hasEvidence = a_hasEvidence;
    }

    public void addHasEvidence(URI a_hasEvidence)
    {
        this.m_hasEvidence.add(a_hasEvidence);
    }
    public URI getPublishedIn()
    {
        return this.m_publishedIn;
    }

    public void setPublishedIn(URI a_publishedIn)
    {
        this.m_publishedIn = a_publishedIn;
    }

}