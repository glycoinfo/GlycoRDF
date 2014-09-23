package edu.uga.ccrc.ontology.doc.writer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import edu.uga.ccrc.ontology.doc.data.OntologyClass;
import edu.uga.ccrc.ontology.doc.util.PrefixGenerator;

public interface IWriter
{

    public abstract void close() throws IOException;

    public abstract void writeNameSpaces(String a_text,
            HashMap<String, String> a_namespaces);

    public abstract void writeHeadline(String a_text);

    public abstract void writeClasses(List<OntologyClass> a_classes, PrefixGenerator a_prefixGen) throws IOException;

}