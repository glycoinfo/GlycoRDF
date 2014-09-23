package edu.uga.ccrc.ontology.doc.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.JDOMException;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDFS;

import edu.uga.ccrc.ontology.doc.data.OntologyClass;
import edu.uga.ccrc.ontology.doc.data.OntologyClassProperties;
import edu.uga.ccrc.ontology.doc.data.OntologyInstance;
import edu.uga.ccrc.ontology.doc.data.OntologyPredicate;
import edu.uga.ccrc.ontology.doc.data.OntologyThing;
import edu.uga.ccrc.ontology.doc.query.QueryReader;

public class OntologyHandler
{
    private OntModel m_model = null;
    private QueryReader m_queries = null;
    private HashMap<String,Boolean> m_functionPredicates = new HashMap<String, Boolean>();
    
    public OntologyHandler(String a_fileName, String a_baseUrl, String a_fileFormat) throws IOException, JDOMException
    {
        InputStream t_input = new FileInputStream(a_fileName);
        this.m_model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, null);
        this.m_model.read(t_input, a_baseUrl, a_fileFormat);
        t_input.close();
        this.m_queries = new QueryReader();
        this.m_functionPredicates = this.getFunctionPredicates();
    }

    public OntologyClassProperties getClassProperties(String a_name, boolean a_instances) throws IOException
    {
        Resource t_classResource = this.m_model.createResource(a_name);
        OntologyClassProperties t_result = new OntologyClassProperties();
        // description
        t_result.setDescription( this.getComment(t_classResource) );
        t_result.setLabel( this.getLabel(t_classResource) );
        // parent class
        t_result.setParentClass(this.getParentClass(t_classResource));
        // predicates
        t_result.setPredicates(this.getPredicates(t_classResource));
        // instances
        if ( a_instances )
        {
            t_result.setInstances(this.getInstances(t_classResource));
        }
        return t_result;
    }

    private List<OntologyInstance> getInstances(Resource a_classResource) throws IOException
    {
        QueryExecution t_query = QueryExecutionFactory.create( this.m_queries.getQuery("instance", a_classResource), Syntax.syntaxARQ, this.m_model);
        ResultSet t_queryResult = t_query.execSelect();
        List<OntologyInstance> t_instances = new ArrayList<OntologyInstance>();
        while ( t_queryResult.hasNext() )
        {
            OntologyInstance t_instance = new OntologyInstance();
            QuerySolution t_solution = t_queryResult.next();
            Resource t_resourceInstance = t_solution.getResource("instance");
            t_instance.setResource(t_resourceInstance);
            t_instance.setComment(this.getComment(t_resourceInstance));
            t_instance.setLabel(this.getLabel(t_resourceInstance));
            t_instances.add(t_instance);
        }
        return t_instances;
    }

    private List<OntologyPredicate> getPredicates(Resource a_classResource)
    {
        QueryExecution t_query = QueryExecutionFactory.create( this.m_queries.getQuery("predicate", a_classResource), Syntax.syntaxARQ, this.m_model);
        ResultSet t_queryResult = t_query.execSelect();
        List<OntologyPredicate> t_predicates = new ArrayList<OntologyPredicate>();
        while ( t_queryResult.hasNext() )
        {
            OntologyPredicate t_predicate = new OntologyPredicate();
            QuerySolution t_solution = t_queryResult.next();
            Resource t_resource = t_solution.getResource("predicate");
            t_predicate.setResource(t_resource);
            this.addRange(t_resource, t_predicate);
            t_predicate.setFunctional(this.getFunctional(t_resource));
            t_predicate.setComment(this.getComment(t_resource));
            t_predicates.add(t_predicate);
        }
        return t_predicates;
    }

    private HashMap<String, Boolean> getFunctionPredicates()
    {
        QueryExecution t_query = QueryExecutionFactory.create( this.m_queries.getQuery("functional_predicates"), Syntax.syntaxARQ, this.m_model);
        ResultSet t_queryResult = t_query.execSelect();
        HashMap<String, Boolean> t_predicates = new HashMap<String, Boolean>();
        while ( t_queryResult.hasNext() )
        {
            QuerySolution t_solution = t_queryResult.next();
            t_predicates.put(t_solution.getResource("p").getURI(), Boolean.TRUE);
        }
        return t_predicates;
    }

    private boolean getFunctional(Resource a_resource)
    {
        if ( this.m_functionPredicates.get(a_resource.getURI()) != null )
        {
            return true;
        }
        return false;
    }

    public List<OntologyThing> getParentClass(Resource a_classResource)
    {
        QueryExecution t_query = QueryExecutionFactory.create( this.m_queries.getQuery("subclass", a_classResource), Syntax.syntaxARQ, this.m_model);
        ResultSet t_queryResult = t_query.execSelect();
        List<OntologyThing> t_parentClass = new ArrayList<OntologyThing>();
        while ( t_queryResult.hasNext() )
        {
            QuerySolution t_solution = t_queryResult.next();
            Resource t_resource = t_solution.getResource("c");
            if ( t_resource.getURI() == null )
            {
                // something else
            }
            else if ( !t_resource.getURI().equals(a_classResource.getURI()) 
                    && !t_resource.getURI().equals(RDFS.Resource.getURI()) 
                    && !t_resource.getURI().equals(OWL.Thing.getURI()))
            {
                OntologyThing t_thing = new OntologyThing();
                t_thing.setResource(t_resource);
                t_thing.setName(t_resource.getLocalName());
                t_parentClass.add(t_thing);
            }
        }
        if ( t_parentClass.size() == 0 )
        {
            OntologyThing t_thing = new OntologyThing();
            t_thing.setResource(OWL.Thing);
            t_thing.setName(t_thing.getResource().getLocalName());
            t_parentClass.add(t_thing);
        }
        return t_parentClass;
    }

    private void addRange(Resource a_resource, OntologyPredicate a_predicate)
    {
        QueryExecution t_query = QueryExecutionFactory.create( this.m_queries.getQuery("predicate_range", a_resource), Syntax.syntaxARQ, this.m_model);
        ResultSet t_queryResult = t_query.execSelect();
        while ( t_queryResult.hasNext() )
        {
            QuerySolution t_solution = t_queryResult.next();
            OntologyThing t_thing = new OntologyThing();
            t_thing.setResource(t_solution.getResource("range"));
            t_thing.setName(t_thing.getResource().getLocalName());
            a_predicate.addRange(t_thing);
        }
    }

    private String getComment(Resource a_resource)
    {
        QueryExecution t_query = QueryExecutionFactory.create( this.m_queries.getQuery("comment", a_resource), Syntax.syntaxARQ, this.m_model);
        ResultSet t_queryResult = t_query.execSelect();
        StringBuffer t_result = new StringBuffer("");
        boolean t_first = true;
        while ( t_queryResult.hasNext() )
        {
            if ( t_first )
            {
                t_first = false;
            }
            else
            {
                t_result.append("\n");
            }
            QuerySolution t_solution = t_queryResult.next();
            t_result.append(t_solution.getLiteral("comment").getString().trim());
        }
        String t_comment = t_result.toString().trim();
        if ( t_comment.length() == 0 )
        {
            return null;
        }
        return t_comment;
    }

    private String getLabel(Resource a_resource) throws IOException
    {
        QueryExecution t_query = QueryExecutionFactory.create( this.m_queries.getQuery("label", a_resource), Syntax.syntaxARQ, this.m_model);
        ResultSet t_queryResult = t_query.execSelect();
        String t_result = null;
        if ( t_queryResult.hasNext() )
        {
            t_queryResult.hasNext();
            QuerySolution t_solution = t_queryResult.next();
            t_result = t_solution.getLiteral("label").getString().trim();
            if ( t_queryResult.hasNext() )
            {
                throw new IOException("Resource with two labels found: " + a_resource.getURI());
            }
        }
        return t_result;
    }

    public List<OntologyClass> getAllClassesFlat()
    {
        List<OntologyClass> t_results = new ArrayList<OntologyClass>();
        QueryExecution t_query = QueryExecutionFactory.create( this.m_queries.getQuery("classes"), Syntax.syntaxARQ, this.m_model);
        ResultSet t_queryResult = t_query.execSelect();
        while ( t_queryResult.hasNext() )
        {
            QuerySolution t_solution = t_queryResult.next();
            // create an OntologyClass 
            OntologyClass t_class = new OntologyClass();
            Resource t_resource = t_solution.getResource("c");
            Node t_node = t_resource.asNode();
            if ( !t_node.isBlank() ) 
            {
                t_class.setResource(t_resource);
                t_class.setName(t_resource.getLocalName());
                // add to result list
                t_results.add(t_class);
            }
        }
        return t_results;
    }

    public List<OntologyInstance> getAllInstances() throws IOException
    {
        QueryExecution t_query = QueryExecutionFactory.create( this.m_queries.getQuery("all_instance"), Syntax.syntaxARQ, this.m_model);
        ResultSet t_queryResult = t_query.execSelect();
        List<OntologyInstance> t_instances = new ArrayList<OntologyInstance>();
        while ( t_queryResult.hasNext() )
        {
            OntologyInstance t_instance = new OntologyInstance();
            QuerySolution t_solution = t_queryResult.next();
            Resource t_resourceInstance = t_solution.getResource("instance");
            t_instance.setResource(t_resourceInstance);
            t_instance.setComment(this.getComment(t_resourceInstance));
            t_instance.setLabel(this.getLabel(t_resourceInstance));
            t_instances.add(t_instance);
        }
        return t_instances;
    }

    public void saveOntology(String a_fileName, String a_baseUrl, String a_fileFormat) throws IOException, JDOMException
    {
        OutputStream t_input = new FileOutputStream(a_fileName);
        this.m_model.write(t_input, a_fileFormat, a_baseUrl);
        t_input.close();
    }

    public void writeLabel(String a_label, Resource a_resource)
    {
        Literal t_literal = this.m_model.createTypedLiteral(a_label);
        this.m_model.addLiteral(a_resource, RDFS.label, t_literal);
    }

    public String getClassName(Resource a_resource)
    {
        QueryExecution t_query = QueryExecutionFactory.create( this.m_queries.getQuery("class_for_instance", a_resource), Syntax.syntaxARQ, this.m_model);
        ResultSet t_queryResult = t_query.execSelect();
        QuerySolution t_solution = t_queryResult.next();
        Resource t_resource = t_solution.getResource("c");
        return t_resource.getLocalName();
    }

    public List<OntologyClass> getAllClassesFlatLabel() throws IOException
    {
        List<OntologyClass> t_classes = this.getAllClassesFlat();
        for (OntologyClass t_ontologyClass : t_classes)
        {
            t_ontologyClass.setProperties(this.getClassProperties(t_ontologyClass.getName(), false));
        }
        return t_classes;
    }
}
