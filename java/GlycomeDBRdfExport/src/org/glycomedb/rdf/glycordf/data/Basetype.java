package org.glycomedb.rdf.glycordf.data;

import java.util.ArrayList;
import java.util.List;

public class Basetype  extends MonosaccharideProperty 
{

    protected Configuration m_hasThirdConfiguration = null;
    protected List<CoreModification> m_hasCoreModification = new ArrayList<CoreModification>();
    protected Configuration m_hasFirstConfiguration = null;
    protected Integer m_hasRingStart = null;
    protected Configuration m_hasSecondConfiguration = null;
    protected Integer m_hasRingEnd = null;
    protected String m_hasStereocode = null;
    protected List<Configuration> m_hasConfiguration = new ArrayList<Configuration>();
    protected RingType m_hasRingType = null;
    protected Integer m_hasBasetypeId = null;
    protected Anomer m_hasAnomer = null;
    protected String m_hasExtendedStereocode = null;
    protected Integer m_hasSize = null;

    public Basetype(){}
    
    public Basetype(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public Basetype(String a_uri, boolean a_isInstance)
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

    public Configuration getHasThirdConfiguration()
    {
        return this.m_hasThirdConfiguration;
    }

    public void setHasThirdConfiguration(Configuration a_hasThirdConfiguration)
    {
        this.m_hasThirdConfiguration = a_hasThirdConfiguration;
    }

    public List<CoreModification> getHasCoreModification()
    {
        return this.m_hasCoreModification;
    }

    public void setHasCoreModification(List<CoreModification> a_hasCoreModification)
    {
        this.m_hasCoreModification = a_hasCoreModification;
    }

    public void addHasCoreModification(CoreModification a_hasCoreModification)
    {
        this.m_hasCoreModification.add(a_hasCoreModification);
    }
    public Configuration getHasFirstConfiguration()
    {
        return this.m_hasFirstConfiguration;
    }

    public void setHasFirstConfiguration(Configuration a_hasFirstConfiguration)
    {
        this.m_hasFirstConfiguration = a_hasFirstConfiguration;
    }

    public Integer getHasRingStart()
    {
        return this.m_hasRingStart;
    }

    public void setHasRingStart(Integer a_hasRingStart)
    {
        this.m_hasRingStart = a_hasRingStart;
    }

    public Configuration getHasSecondConfiguration()
    {
        return this.m_hasSecondConfiguration;
    }

    public void setHasSecondConfiguration(Configuration a_hasSecondConfiguration)
    {
        this.m_hasSecondConfiguration = a_hasSecondConfiguration;
    }

    public Integer getHasRingEnd()
    {
        return this.m_hasRingEnd;
    }

    public void setHasRingEnd(Integer a_hasRingEnd)
    {
        this.m_hasRingEnd = a_hasRingEnd;
    }

    public String getHasStereocode()
    {
        return this.m_hasStereocode;
    }

    public void setHasStereocode(String a_hasStereocode)
    {
        this.m_hasStereocode = a_hasStereocode;
    }

    public List<Configuration> getHasConfiguration()
    {
        return this.m_hasConfiguration;
    }

    public void setHasConfiguration(List<Configuration> a_hasConfiguration)
    {
        this.m_hasConfiguration = a_hasConfiguration;
    }

    public void addHasConfiguration(Configuration a_hasConfiguration)
    {
        this.m_hasConfiguration.add(a_hasConfiguration);
    }
    public RingType getHasRingType()
    {
        return this.m_hasRingType;
    }

    public void setHasRingType(RingType a_hasRingType)
    {
        this.m_hasRingType = a_hasRingType;
    }

    public Integer getHasBasetypeId()
    {
        return this.m_hasBasetypeId;
    }

    public void setHasBasetypeId(Integer a_hasBasetypeId)
    {
        this.m_hasBasetypeId = a_hasBasetypeId;
    }

    public Anomer getHasAnomer()
    {
        return this.m_hasAnomer;
    }

    public void setHasAnomer(Anomer a_hasAnomer)
    {
        this.m_hasAnomer = a_hasAnomer;
    }

    public String getHasExtendedStereocode()
    {
        return this.m_hasExtendedStereocode;
    }

    public void setHasExtendedStereocode(String a_hasExtendedStereocode)
    {
        this.m_hasExtendedStereocode = a_hasExtendedStereocode;
    }

    public Integer getHasSize()
    {
        return this.m_hasSize;
    }

    public void setHasSize(Integer a_hasSize)
    {
        this.m_hasSize = a_hasSize;
    }

}