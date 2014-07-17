package org.glycomedb.database.data;

import java.util.ArrayList;
import java.util.List;

public class Structure
{
    private Integer m_id;
    private String m_sequence;
    private List<Integer> m_taxonUniprot = new ArrayList<Integer>();
    private List<RemoteEntry> m_remoteEntries = new ArrayList<RemoteEntry>();
    private boolean m_hasCfgImage = false;
    private boolean m_hasOxImag = false;
    private boolean m_hasIupacImage = false;
    
    private String accessionNumber;

    public String getAccessionNumber() {
		return accessionNumber;
	}
	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}
	public Integer getId() {
        return m_id;
    }
    public void setId(Integer a_id) {
        m_id = a_id;
    }
    public String getSequence() {
        return m_sequence;
    }
    public void setSequence(String a_sequence) {
        m_sequence = a_sequence;
    }
    public List<Integer> getTaxonUniprot() {
        return m_taxonUniprot;
    }
    public void setTaxonUniprot(List<Integer> a_taxonUniprot) {
        m_taxonUniprot = a_taxonUniprot;
    }
    public List<RemoteEntry> getRemoteEntries() {
        return m_remoteEntries;
    }
    public void setRemoteEntries(List<RemoteEntry> a_remoteEntries) {
        m_remoteEntries = a_remoteEntries;
    }
    public boolean isHasCfgImage()
    {
        return m_hasCfgImage;
    }
    public void setHasCfgImage(boolean hasCfgImage)
    {
        m_hasCfgImage = hasCfgImage;
    }
    public boolean isHasOxImag()
    {
        return m_hasOxImag;
    }
    public void setHasOxImag(boolean hasOxImag)
    {
        m_hasOxImag = hasOxImag;
    }
    public boolean isHasIupacImage()
    {
        return m_hasIupacImage;
    }
    public void setHasIupacImage(boolean hasIupacImage)
    {
        m_hasIupacImage = hasIupacImage;
    }
}