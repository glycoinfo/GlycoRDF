package org.glycomedb.rdf.glycordf.util;

public enum SequenceFromat
{
    CARBBANK("CarbBank"),
    LINUCS("LINUCS"),
    KCF("KCF"),
    Glyde_II("Glyde-II"),
    GlycoCT("GlycoCT");

    private String m_name = null;
    
    private SequenceFromat( String a_name )
    {
        this.m_name = a_name;
    }
    
    public static SequenceFromat forName( String a_name )
    {
        for ( SequenceFromat a : SequenceFromat.values() )
        {
            if ( a_name.equals(a.m_name) )
            {
                return a;
            }
        }
        return null;
    }
    
    public static SequenceFromat forNameIgnoreCase( String a_name )
    {
        for ( SequenceFromat a : SequenceFromat.values() )
        {
            if ( a_name.equalsIgnoreCase(a.m_name) )
            {
                return a;
            }
        }
        return null;
    }
}
