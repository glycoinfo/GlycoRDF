package org.glycomedb.rdf.glycordf.util;

public class RDFGeneratorGlycanConfig
{
    private boolean m_remoteEntries = true;
    private boolean m_sequenceGlycoCt = true;
    private boolean m_sequenceGlydeII = true;
    private boolean m_sequenceKCF = true;
    private boolean m_sequenceLinucs = true;
    private boolean m_images = true;
    private boolean m_composition = true;
    private boolean m_motif = true;
    private boolean m_sequenceCarbBank = true;
    private boolean m_referencedCompound = true;
    private boolean m_flatSequence = false;
    private boolean m_flatReferencedCompound = false;
    
    public boolean isRemoteEntries()
    {
        return m_remoteEntries;
    }
    public void setRemoteEntries(boolean a_remoteEntries)
    {
        m_remoteEntries = a_remoteEntries;
    }
    public boolean isSequenceGlycoCt()
    {
        return m_sequenceGlycoCt;
    }
    public void setSequenceGlycoCt(boolean a_sequenceGlycoCt)
    {
        m_sequenceGlycoCt = a_sequenceGlycoCt;
    }
    public boolean isSequenceGlydeII()
    {
        return m_sequenceGlydeII;
    }
    public void setSequenceGlydeII(boolean a_sequenceGlydeII)
    {
        m_sequenceGlydeII = a_sequenceGlydeII;
    }
    public boolean isSequenceKCF()
    {
        return m_sequenceKCF;
    }
    public void setSequenceKCF(boolean a_sequenceKCF)
    {
        m_sequenceKCF = a_sequenceKCF;
    }
    public boolean isSequenceLinucs()
    {
        return m_sequenceLinucs;
    }
    public void setSequenceLinucs(boolean a_sequenceLinucs)
    {
        m_sequenceLinucs = a_sequenceLinucs;
    }
    public boolean isImages()
    {
        return m_images;
    }
    public void setImages(boolean images)
    {
        m_images = images;
    }
    public boolean isComposition()
    {
        return m_composition;
    }
    public void setComposition(boolean composition)
    {
        m_composition = composition;
    }
    public boolean isMotif()
    {
        return m_motif;
    }
    public void setMotif(boolean motif)
    {
        m_motif = motif;
    }
    public boolean isSequenceCarbBank()
    {
        return m_sequenceCarbBank;
    }
    public void setSequenceCarbBank(boolean sequenceCarbBank)
    {
        m_sequenceCarbBank = sequenceCarbBank;
    }
    public boolean isReferencedCompound()
    {
        return m_referencedCompound;
    }
    public void setReferencedCompound(boolean referencedCompound)
    {
        m_referencedCompound = referencedCompound;
    }
    public boolean isFlatSequence()
    {
        return m_flatSequence;
    }
    public void setFlatSequence(boolean flatSequence)
    {
        m_flatSequence = flatSequence;
    }
    public boolean isFlatReferencedCompound()
    {
        return m_flatReferencedCompound;
    }
    public void setFlatReferencedCompound(boolean flatReferencedCompound)
    {
        m_flatReferencedCompound = flatReferencedCompound;
    }
}
