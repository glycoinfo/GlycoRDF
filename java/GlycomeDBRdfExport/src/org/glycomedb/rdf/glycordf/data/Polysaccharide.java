package org.glycomedb.rdf.glycordf.data;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Polysaccharide  extends Saccharide 
{

    protected List<URI> m_hasPolymerizationDegree = new ArrayList<URI>();

    public Polysaccharide(){}
    
    public Polysaccharide(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public Polysaccharide(String a_uri, boolean a_isInstance)
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

    public List<URI> getHasPolymerizationDegree()
    {
        return this.m_hasPolymerizationDegree;
    }

    public void setHasPolymerizationDegree(List<URI> a_hasPolymerizationDegree)
    {
        this.m_hasPolymerizationDegree = a_hasPolymerizationDegree;
    }

    public void addHasPolymerizationDegree(URI a_hasPolymerizationDegree)
    {
        this.m_hasPolymerizationDegree.add(a_hasPolymerizationDegree);
    }
}