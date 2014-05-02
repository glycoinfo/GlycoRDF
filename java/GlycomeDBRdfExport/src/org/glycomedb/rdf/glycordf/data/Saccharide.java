package org.glycomedb.rdf.glycordf.data;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Saccharide  extends Compound 
{

    protected Boolean m_isAmbiguous = null;
    protected List<Component> m_hasComponent = new ArrayList<Component>();
    protected List<Image> m_hasImage = new ArrayList<Image>();
    protected List<URI> m_hasAffinityTo = new ArrayList<URI>();
    protected List<Glycosequence> m_hasGlycosequence = new ArrayList<Glycosequence>();
    protected List<GlycanEpitope> m_hasEpitope = new ArrayList<GlycanEpitope>();
    protected List<GlycoconjugateSequence> m_hasGlycoconjugateSequence = new ArrayList<GlycoconjugateSequence>();
    protected List<GlycanMotif> m_hasMotif = new ArrayList<GlycanMotif>();
    protected List<URI> m_degradedBy = new ArrayList<URI>();
    protected List<URI> m_synthesizedBy = new ArrayList<URI>();
    protected List<URI> m_catalyzedBy = new ArrayList<URI>();

    public Saccharide(){}
    
    public Saccharide(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public Saccharide(String a_uri, boolean a_isInstance)
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

    public Boolean getIsAmbiguous()
    {
        return this.m_isAmbiguous;
    }

    public void setIsAmbiguous(Boolean a_isAmbiguous)
    {
        this.m_isAmbiguous = a_isAmbiguous;
    }

    public List<Component> getHasComponent()
    {
        return this.m_hasComponent;
    }

    public void setHasComponent(List<Component> a_hasComponent)
    {
        this.m_hasComponent = a_hasComponent;
    }

    public void addHasComponent(Component a_hasComponent)
    {
        this.m_hasComponent.add(a_hasComponent);
    }
    public List<Image> getHasImage()
    {
        return this.m_hasImage;
    }

    public void setHasImage(List<Image> a_hasImage)
    {
        this.m_hasImage = a_hasImage;
    }

    public void addHasImage(Image a_hasImage)
    {
        this.m_hasImage.add(a_hasImage);
    }
    public List<URI> getHasAffinityTo()
    {
        return this.m_hasAffinityTo;
    }

    public void setHasAffinityTo(List<URI> a_hasAffinityTo)
    {
        this.m_hasAffinityTo = a_hasAffinityTo;
    }

    public void addHasAffinityTo(URI a_hasAffinityTo)
    {
        this.m_hasAffinityTo.add(a_hasAffinityTo);
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
    public List<GlycanEpitope> getHasEpitope()
    {
        return this.m_hasEpitope;
    }

    public void setHasEpitope(List<GlycanEpitope> a_hasEpitope)
    {
        this.m_hasEpitope = a_hasEpitope;
    }

    public void addHasEpitope(GlycanEpitope a_hasEpitope)
    {
        this.m_hasEpitope.add(a_hasEpitope);
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
    public List<GlycanMotif> getHasMotif()
    {
        return this.m_hasMotif;
    }

    public void setHasMotif(List<GlycanMotif> a_hasMotif)
    {
        this.m_hasMotif = a_hasMotif;
    }

    public void addHasMotif(GlycanMotif a_hasMotif)
    {
        this.m_hasMotif.add(a_hasMotif);
    }
    public List<URI> getDegradedBy()
    {
        return this.m_degradedBy;
    }

    public void setDegradedBy(List<URI> a_degradedBy)
    {
        this.m_degradedBy = a_degradedBy;
    }

    public void addDegradedBy(URI a_degradedBy)
    {
        this.m_degradedBy.add(a_degradedBy);
    }
    public List<URI> getSynthesizedBy()
    {
        return this.m_synthesizedBy;
    }

    public void setSynthesizedBy(List<URI> a_synthesizedBy)
    {
        this.m_synthesizedBy = a_synthesizedBy;
    }

    public void addSynthesizedBy(URI a_synthesizedBy)
    {
        this.m_synthesizedBy.add(a_synthesizedBy);
    }
    public List<URI> getCatalyzedBy()
    {
        return this.m_catalyzedBy;
    }

    public void setCatalyzedBy(List<URI> a_catalyzedBy)
    {
        this.m_catalyzedBy = a_catalyzedBy;
    }

    public void addCatalyzedBy(URI a_catalyzedBy)
    {
        this.m_catalyzedBy.add(a_catalyzedBy);
    }
}