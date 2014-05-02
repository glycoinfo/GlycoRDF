package org.glycomedb.rdf.glycordf.data;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Thing
{
    private String m_FoafName = null;
    private List<URI> m_seeAlso = new ArrayList<URI>();
    private List<URI> m_sameAs = new ArrayList<URI>();
    public List<URI> getSeeAlso()
    {
        return m_seeAlso;
    }
    public void setSeeAlso(List<URI> seeAlso)
    {
        m_seeAlso = seeAlso;
    }
    public List<URI> getSameAs()
    {
        return m_sameAs;
    }
    public void setSameAs(List<URI> sameAs)
    {
        m_sameAs = sameAs;
    }
    public boolean addSameAs(URI sameAs)
    {
        return this.m_sameAs.add(sameAs);
    }
    public boolean addSeeAlso(URI seeAlso)
    {
        return this.m_seeAlso.add(seeAlso);
    }
    public String getFoafName()
    {
        return m_FoafName;
    }
    public void setFoafName(String foafName)
    {
        m_FoafName = foafName;
    }
}
