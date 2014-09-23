package edu.uga.ccrc.ontology.doc.util;

import java.util.Comparator;

import edu.uga.ccrc.ontology.doc.data.OntologyClass;

public class ComparatorOntologyClass implements Comparator<OntologyClass>
{

    public int compare(OntologyClass a_class0, OntologyClass a_class1)
    {
        return a_class0.getName().compareTo(a_class1.getName());
    }

}
