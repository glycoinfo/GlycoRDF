package edu.uga.ccrc.ontology.doc.data;


public class OntologyInstance extends OntologyThing
{
    private String m_comment = null;
    private String m_label = null;
    public String getLabel()
    {
        return m_label;
    }
    public void setLabel(String label)
    {
        m_label = label;
    }
    public String getComment()
    {
        return m_comment;
    }
    public void setComment(String a_comment)
    {
        m_comment = a_comment;
    }
}
