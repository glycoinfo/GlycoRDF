package org.glycomedb.database.data;

public class RemoteEntry 
{
    private String m_resource = null;
    private String m_resourceId = null;
    private String m_withAglycon = null;

    public String getResource()
    {
        return m_resource;
    }
    public void setResource(String resource)
    {
        this.m_resource = resource;
    }
    public String getResourceId() {
        return m_resourceId;
    }
    public void setResourceId(String resourceId)
    {
        this.m_resourceId = resourceId;
    }
    public String getWithAglycon()
    {
        return m_withAglycon;
    }
    public void setWithAglycon(String withAglycon)
    {
        m_withAglycon = withAglycon;
    }

}
