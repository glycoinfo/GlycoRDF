package edu.uga.ccrc.ontology.doc.util;

import java.util.Comparator;

import edu.uga.ccrc.ontology.doc.data.OntologyClass;

public class ComperatorOntologyClasses implements Comparator<OntologyClass>
{

    public int compare(OntologyClass a_class1, OntologyClass a_class2)
    {
        return a_class1.getOrder().compareTo(a_class2.getOrder());
    }

}
