package edu.uga.ccrc.ontology.doc.util;

import java.util.Comparator;

import edu.uga.ccrc.ontology.doc.data.OntologyPredicate;

public class ComparatorPredicates implements Comparator<OntologyPredicate>
{
    private PrefixGenerator m_prefixGenerator = null;

    public ComparatorPredicates(PrefixGenerator a_prefixGen)
    {
        this.m_prefixGenerator = a_prefixGen;
    }

    public int compare(OntologyPredicate a_predicate1, OntologyPredicate a_predicate2)
    {
        String t_one = this.m_prefixGenerator.prefixName(a_predicate1.getResource());
        String t_two = this.m_prefixGenerator.prefixName(a_predicate2.getResource());
        return t_one.compareTo(t_two);
    }

}
