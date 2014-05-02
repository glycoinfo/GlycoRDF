package org.glycomedb.rdf.glycordf.data;

import java.util.ArrayList;
import java.util.List;

public class GlycanMotif  extends Motif 
{

    protected List<Saccharide> m_containedIn = new ArrayList<Saccharide>();

    public GlycanMotif(){}
    
    public GlycanMotif(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public GlycanMotif(String a_uri, boolean a_isInstance)
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

    public List<Saccharide> getContainedIn()
    {
        return this.m_containedIn;
    }

    public void setContainedIn(List<Saccharide> a_containedIn)
    {
        this.m_containedIn = a_containedIn;
    }

    public void addContainedIn(Saccharide a_containedIn)
    {
        this.m_containedIn.add(a_containedIn);
    }
}