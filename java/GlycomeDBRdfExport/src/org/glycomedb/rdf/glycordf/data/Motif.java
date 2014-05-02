package org.glycomedb.rdf.glycordf.data;

import java.util.ArrayList;
import java.util.List;

public class Motif extends Thing
{
    protected String m_instanceURI = null;
    protected boolean m_ontologyInstance = false;

    protected List<Glycosequence> m_hasGlycosequence = new ArrayList<Glycosequence>();
    protected List<GlycoconjugateSequence> m_hasGlycoconjugateSequence = new ArrayList<GlycoconjugateSequence>();

    public Motif(){}
    
    public Motif(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public Motif(String a_uri, boolean a_isInstance)
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

    public List<Glycosequence> getHasGlycosequence()
    {
        return this.m_hasGlycosequence;
    }

    public void setHasGlycosequence(List<Glycosequence> a_hasGlycosequence)
    {
        this.m_hasGlycosequence = a_hasGlycosequence;
    }

    public void addHasGlycosequence(Glycosequence a_hasGlycosequence)
    {
        this.m_hasGlycosequence.add(a_hasGlycosequence);
    }
    public List<GlycoconjugateSequence> getHasGlycoconjugateSequence()
    {
        return this.m_hasGlycoconjugateSequence;
    }

    public void setHasGlycoconjugateSequence(List<GlycoconjugateSequence> a_hasGlycoconjugateSequence)
    {
        this.m_hasGlycoconjugateSequence = a_hasGlycoconjugateSequence;
    }

    public void addHasGlycoconjugateSequence(GlycoconjugateSequence a_hasGlycoconjugateSequence)
    {
        this.m_hasGlycoconjugateSequence.add(a_hasGlycoconjugateSequence);
    }
}