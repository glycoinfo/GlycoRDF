package org.glycomedb.rdf.glycordf.data;


public class CoreModification  extends MonosaccharideProperty 
{

    protected Integer m_hasModificationPosition2 = null;
    protected Integer m_hasModificationPosition = null;
    protected CoreModificationType m_hasCoreModificationType = null;

    public CoreModification(){}
    
    public CoreModification(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public CoreModification(String a_uri, boolean a_isInstance)
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

    public Integer getHasModificationPosition2()
    {
        return this.m_hasModificationPosition2;
    }

    public void setHasModificationPosition2(Integer a_hasModificationPosition2)
    {
        this.m_hasModificationPosition2 = a_hasModificationPosition2;
    }

    public Integer getHasModificationPosition()
    {
        return this.m_hasModificationPosition;
    }

    public void setHasModificationPosition(Integer a_hasModificationPosition)
    {
        this.m_hasModificationPosition = a_hasModificationPosition;
    }

    public CoreModificationType getHasCoreModificationType()
    {
        return this.m_hasCoreModificationType;
    }

    public void setHasCoreModificationType(CoreModificationType a_hasCoreModificationType)
    {
        this.m_hasCoreModificationType = a_hasCoreModificationType;
    }

}