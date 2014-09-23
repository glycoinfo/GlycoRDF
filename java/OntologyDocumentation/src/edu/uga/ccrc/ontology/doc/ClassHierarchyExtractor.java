package edu.uga.ccrc.ontology.doc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.jdom2.JDOMException;

import com.hp.hpl.jena.rdf.model.Resource;

import edu.uga.ccrc.ontology.doc.data.OntologyClass;
import edu.uga.ccrc.ontology.doc.data.OntologyThing;
import edu.uga.ccrc.ontology.doc.util.ComparatorOntologyClass;
import edu.uga.ccrc.ontology.doc.util.OntologyHandler;

public class ClassHierarchyExtractor
{

    public static void main(String[] args) throws IOException, JDOMException
    {
        OntologyHandler t_ontology = new OntologyHandler("ontology/glycan.owl", "http://purl.jp/bio/12/glyco/glycan#", "TURTLE");
        List<OntologyClass> t_classes = t_ontology.getAllClassesFlat();
        HashMap<Resource, OntologyClass> t_registry = new HashMap<Resource, OntologyClass>();
        for (OntologyClass t_ontologyClass : t_classes)
        {
            t_registry.put(t_ontologyClass.getResource(), t_ontologyClass);
        }
        // create structure: parent -> children
        HashMap<OntologyClass, List<OntologyClass>> t_classHierarchy = new HashMap<OntologyClass, List<OntologyClass>>(); 
        HashMap<OntologyClass,Boolean> t_children = new HashMap<OntologyClass,Boolean>();
        for (OntologyClass t_ontologyClass : t_classes)
        {
            List<OntologyThing> t_parents = t_ontology.getParentClass(t_ontologyClass.getResource());
            for (OntologyThing t_ontologyThing : t_parents)
            {
                if ( !t_ontologyThing.getName().equals("Thing") )
                {
                    OntologyClass t_class = t_registry.get(t_ontologyThing.getResource());
                    ClassHierarchyExtractor.addParentChild(t_class,t_ontologyClass,t_classHierarchy);
                    t_children.put(t_ontologyClass, Boolean.TRUE);
                }
            }
        }
        List<OntologyClass> t_toplevelClasses = new ArrayList<OntologyClass>();
        for (OntologyClass t_ontologyClass : t_classes)
        {
            if ( t_children.get(t_ontologyClass) == null )
            {
                t_toplevelClasses.add(t_ontologyClass);
            }
        }
        ClassHierarchyExtractor.print("        ",t_toplevelClasses,t_classHierarchy);
    }

    private static void addParentChild(OntologyClass a_parent, OntologyClass a_child, HashMap<OntologyClass, List<OntologyClass>> a_classHierarchy)
    {
        List<OntologyClass> t_children = a_classHierarchy.get(a_parent);
        if ( t_children == null )
        {
            t_children = new ArrayList<OntologyClass>();
            a_classHierarchy.put(a_parent, t_children);
        }
        t_children.add(a_child);
    }

    private static void print(String a_spaces, List<OntologyClass> a_classes, HashMap<OntologyClass, List<OntologyClass>> a_classHierarchy)
    {
        ClassHierarchyExtractor.sortList(a_classes);
        for (OntologyClass t_ontologyClass : a_classes)
        {
            System.out.println(a_spaces + "<class order=\"" + t_ontologyClass.getLevel().toString() + "\" name=\""
                    + t_ontologyClass.getResource().toString() + "\" instances=\"true\">");
            List<OntologyClass> t_children = a_classHierarchy.get(t_ontologyClass);
            if ( t_children != null )
            {
                ClassHierarchyExtractor.print(a_spaces + "    ", t_children,a_classHierarchy);
            }
            System.out.println(a_spaces + "</class>");
        }
    }

    private static void sortList(List<OntologyClass> a_classes)
    {
        Collections.sort(a_classes, new ComparatorOntologyClass());
        int t_counter = 1;
        for (OntologyClass t_ontologyClass : a_classes)
        {
            t_ontologyClass.setLevel(t_counter);
            ClassHierarchyExtractor.sortList(t_ontologyClass.getChildClasses());
            t_counter++;
        }
    }

}
