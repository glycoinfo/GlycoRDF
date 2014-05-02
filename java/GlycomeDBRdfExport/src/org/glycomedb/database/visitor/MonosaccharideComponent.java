package org.glycomedb.database.visitor;

public class MonosaccharideComponent
{
    private String m_msdbString = null;
    private Integer m_number = 0;

    public String getMsdbString()
    {
        return m_msdbString;
    }
    public void setMsdbString(String a_msdbString)
    {
        m_msdbString = a_msdbString;
    }
    public Integer getNumber()
    {
        return m_number;
    }
    public void setNumber(Integer number)
    {
        m_number = number;
    }
    public void addNumber(Integer number)
    {
        if ( number == null )
        {
            this.m_number = null;
        }
        if ( this.m_number != null )
        {
            this.m_number += number;
        }
    }
}
