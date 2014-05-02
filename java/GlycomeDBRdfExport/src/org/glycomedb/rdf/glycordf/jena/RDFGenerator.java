package org.glycomedb.rdf.glycordf.jena;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

import org.glycomedb.rdf.glycordf.data.AbsoluteConfiguration;
import org.glycomedb.rdf.glycordf.data.Anomer;
import org.glycomedb.rdf.glycordf.data.Basetype;
import org.glycomedb.rdf.glycordf.data.CarbohydrateFormat;
import org.glycomedb.rdf.glycordf.data.Component;
import org.glycomedb.rdf.glycordf.data.Compound;
import org.glycomedb.rdf.glycordf.data.Configuration;
import org.glycomedb.rdf.glycordf.data.CoreModification;
import org.glycomedb.rdf.glycordf.data.CoreModificationType;
import org.glycomedb.rdf.glycordf.data.CyclicGlycan;
import org.glycomedb.rdf.glycordf.data.GlycanDatabase;
import org.glycomedb.rdf.glycordf.data.GlycanDatabaseCategory;
import org.glycomedb.rdf.glycordf.data.GlycanEpitope;
import org.glycomedb.rdf.glycordf.data.GlycanMotif;
import org.glycomedb.rdf.glycordf.data.GlycoconjugateSequence;
import org.glycomedb.rdf.glycordf.data.Glycosequence;
import org.glycomedb.rdf.glycordf.data.Image;
import org.glycomedb.rdf.glycordf.data.LinkageType;
import org.glycomedb.rdf.glycordf.data.Monosaccharide;
import org.glycomedb.rdf.glycordf.data.MonosaccharideAlias;
import org.glycomedb.rdf.glycordf.data.MonosaccharideNotationScheme;
import org.glycomedb.rdf.glycordf.data.MonosaccharideProperty;
import org.glycomedb.rdf.glycordf.data.Motif;
import org.glycomedb.rdf.glycordf.data.Nglycan;
import org.glycomedb.rdf.glycordf.data.Oglycan;
import org.glycomedb.rdf.glycordf.data.Polysaccharide;
import org.glycomedb.rdf.glycordf.data.ReferencedCompound;
import org.glycomedb.rdf.glycordf.data.RelativeConfiguration;
import org.glycomedb.rdf.glycordf.data.RepeatAttribute;
import org.glycomedb.rdf.glycordf.data.ResourceEntry;
import org.glycomedb.rdf.glycordf.data.RingType;
import org.glycomedb.rdf.glycordf.data.Saccharide;
import org.glycomedb.rdf.glycordf.data.Sequence;
import org.glycomedb.rdf.glycordf.data.Source;
import org.glycomedb.rdf.glycordf.data.SourceNatural;
import org.glycomedb.rdf.glycordf.data.Substituent;
import org.glycomedb.rdf.glycordf.data.SubstituentLinkage;
import org.glycomedb.rdf.glycordf.data.SubstituentType;
import org.glycomedb.rdf.glycordf.data.SymbolFormat;
import org.glycomedb.rdf.glycordf.data.Thing;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.OWL2;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class RDFGenerator
{
    private Model m_model = null;
    private HashMap<String, Boolean> m_alreadyStored = new HashMap<String, Boolean>();

    public RDFGenerator(Model a_model)
    {
        this.m_alreadyStored.clear();
        this.m_model = a_model;
        this.addNameSpaces();
    }

    public void addNameSpaces()
    {
        this.m_model.setNsPrefix( "rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        this.m_model.setNsPrefix( "bibo", "http://purl.org/ontology/bibo/");
        this.m_model.setNsPrefix( "foaf", "http://xmlns.com/foaf/0.1/");
        this.m_model.setNsPrefix( "owl", "http://www.w3.org/2002/07/owl#");
        this.m_model.setNsPrefix( "glycan", "http://purl.jp/bio/12/glyco/glycan#");
        this.m_model.setNsPrefix( "dcterms", "http://purl.org/dc/terms/");
        this.m_model.setNsPrefix( "dc", "http://purl.org/dc/elements/1.1/");
        this.m_model.setNsPrefix( "xsd", "http://www.w3.org/2001/XMLSchema#");
        this.m_model.setNsPrefix( "rdfs", "http://www.w3.org/2000/01/rdf-schema#");
    }

    protected void addLiteral(Resource a_resource, String a_predicateUri, Object a_object, XSDDatatype a_datatype)
    {
        Literal t_literal = this.m_model.createTypedLiteral(a_object, a_datatype);
        Property t_property = this.m_model.createProperty(a_predicateUri);
        a_resource.addProperty(t_property, t_literal);
    }
    
    protected Resource addResource(Resource a_resource, String a_predicateUri, String a_resourceUri)
    {
        Resource t_resource = this.m_model.createResource(a_resourceUri);
        a_resource.addProperty(this.m_model.createProperty(a_predicateUri), t_resource);
        return t_resource;
    }

    protected void addResource(Resource a_resource, String a_predicateUri, Resource a_resourceUri)
    {
        this.addResource(a_resource, this.m_model.createProperty(a_predicateUri), a_resourceUri);
    }

    protected void addResource(Resource a_resource, Property a_predicate, Resource a_resourceUri)
    {
        a_resource.addProperty(a_predicate, a_resourceUri);
    }

    protected void addResource(Resource a_resource, Property a_predicate, String a_resourceUri)
    {
        Resource t_resource = this.m_model.createResource(a_resourceUri);
        this.addResource(a_resource, a_predicate, t_resource);
    }

    public void addImage(Image a_class)
    {
        this.addImage(a_class, null,false);
    }

    protected void addImage(Image a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#image");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#image");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addThing(a_class,t_itself);
        // HasSymbolFormat
        SymbolFormat t_hasSymbolFormat = a_class.getHasSymbolFormat();
        if ( t_hasSymbolFormat != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_symbol_format", t_hasSymbolFormat.getInstanceURI());
            this.addSymbolFormat(t_hasSymbolFormat, t_resource, false);
        }
        // Format
        String t_format = a_class.getFormat();
        if ( t_format != null )
        {
            this.addLiteral(t_itself, "http://purl.org/dc/elements/1.1/format", t_format, XSDDatatype.XSDstring);
        }
    }

    public void addCarbohydrateFormat(CarbohydrateFormat a_class)
    {
        this.addCarbohydrateFormat(a_class, null,false);
    }

    protected void addCarbohydrateFormat(CarbohydrateFormat a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#carbohydrate_format");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
    }

    public void addComponent(Component a_class)
    {
        this.addComponent(a_class, null,false);
    }

    protected void addComponent(Component a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#component");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#component");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
        // HasCardinalityPerRepeat
        Integer t_hasCardinalityPerRepeat = a_class.getHasCardinalityPerRepeat();
        if ( t_hasCardinalityPerRepeat != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_cardinality_per_repeat", t_hasCardinalityPerRepeat, XSDDatatype.XSDinteger);
        }
        // HasCardinality
        Integer t_hasCardinality = a_class.getHasCardinality();
        if ( t_hasCardinality != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_cardinality", t_hasCardinality, XSDDatatype.XSDinteger);
        }
        // HasMonosaccharide
        Monosaccharide t_hasMonosaccharide = a_class.getHasMonosaccharide();
        if ( t_hasMonosaccharide != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_monosaccharide", t_hasMonosaccharide.getInstanceURI());
            this.addMonosaccharide(t_hasMonosaccharide, t_resource, false);
        }
    }

    public void addCompound(Compound a_class)
    {
        this.addCompound(a_class, null,false);
    }

    protected void addCompound(Compound a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#compound");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#compound");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
        // HasResourceEntry
        List<ResourceEntry> t_hasResourceEntry = a_class.getHasResourceEntry();
        for (ResourceEntry t_var : t_hasResourceEntry)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_resource_entry", t_var.getInstanceURI());
            this.addResourceEntry(t_var, t_resource, false);
        }
        // HasReference
        List<ReferencedCompound> t_hasReference = a_class.getHasReference();
        for (ReferencedCompound t_var : t_hasReference)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_reference", t_var.getInstanceURI());
            this.addReferencedCompound(t_var, t_resource, false);
        }
    }

    public void addSaccharide(Saccharide a_class)
    {
        this.addSaccharide(a_class, null,false);
    }

    protected void addSaccharide(Saccharide a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#saccharide");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#saccharide");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addCompound(a_class,t_itself,true);
        // IsAmbiguous
        Boolean t_isAmbiguous = a_class.getIsAmbiguous();
        if ( t_isAmbiguous != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#is_ambiguous", t_isAmbiguous, XSDDatatype.XSDboolean);
        }
        // HasComponent
        List<Component> t_hasComponent = a_class.getHasComponent();
        for (Component t_var : t_hasComponent)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_component", t_var.getInstanceURI());
            this.addComponent(t_var, t_resource, false);
        }
        // HasImage
        List<Image> t_hasImage = a_class.getHasImage();
        for (Image t_var : t_hasImage)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_image", t_var.getInstanceURI());
            this.addImage(t_var, t_resource, false);
        }
        // HasAffinityTo
        List<URI> t_hasAffinityTo = a_class.getHasAffinityTo();
        for (URI t_var : t_hasAffinityTo)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_affinity_to", t_var, XSDDatatype.XSDanyURI);
        }
        // HasGlycosequence
        List<Glycosequence> t_hasGlycosequence = a_class.getHasGlycosequence();
        for (Glycosequence t_var : t_hasGlycosequence)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_glycosequence", t_var.getInstanceURI());
            this.addGlycosequence(t_var, t_resource, false);
        }
        // HasEpitope
        List<GlycanEpitope> t_hasEpitope = a_class.getHasEpitope();
        for (GlycanEpitope t_var : t_hasEpitope)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_epitope", t_var.getInstanceURI());
            this.addGlycanEpitope(t_var, t_resource, false);
        }
        // HasGlycoconjugateSequence
        List<GlycoconjugateSequence> t_hasGlycoconjugateSequence = a_class.getHasGlycoconjugateSequence();
        for (GlycoconjugateSequence t_var : t_hasGlycoconjugateSequence)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_glycoconjugate_sequence", t_var.getInstanceURI());
            this.addGlycoconjugateSequence(t_var, t_resource, false);
        }
        // HasMotif
        List<GlycanMotif> t_hasMotif = a_class.getHasMotif();
        for (GlycanMotif t_var : t_hasMotif)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_motif", t_var.getInstanceURI());
            this.addGlycanMotif(t_var, t_resource, false);
        }
        // DegradedBy
        List<URI> t_degradedBy = a_class.getDegradedBy();
        for (URI t_var : t_degradedBy)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#degraded_by", t_var, XSDDatatype.XSDanyURI);
        }
        // SynthesizedBy
        List<URI> t_synthesizedBy = a_class.getSynthesizedBy();
        for (URI t_var : t_synthesizedBy)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#synthesized_by", t_var, XSDDatatype.XSDanyURI);
        }
        // CatalyzedBy
        List<URI> t_catalyzedBy = a_class.getCatalyzedBy();
        for (URI t_var : t_catalyzedBy)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#catalyzed_by", t_var, XSDDatatype.XSDanyURI);
        }
    }

    public void addNglycan(Nglycan a_class)
    {
        this.addNglycan(a_class, null,false);
    }

    protected void addNglycan(Nglycan a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#N-glycan");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#N-glycan");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addSaccharide(a_class,t_itself,true);
    }

    public void addOglycan(Oglycan a_class)
    {
        this.addOglycan(a_class, null,false);
    }

    protected void addOglycan(Oglycan a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#O-glycan");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#O-glycan");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addSaccharide(a_class,t_itself,true);
    }

    public void addCyclicGlycan(CyclicGlycan a_class)
    {
        this.addCyclicGlycan(a_class, null,false);
    }

    protected void addCyclicGlycan(CyclicGlycan a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#cyclic_glycan");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#cyclic_glycan");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addSaccharide(a_class,t_itself,true);
    }

    public void addMonosaccharide(Monosaccharide a_class)
    {
        this.addMonosaccharide(a_class, null,false);
    }

    protected void addMonosaccharide(Monosaccharide a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#monosaccharide");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#monosaccharide");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addSaccharide(a_class,t_itself,true);
        // HasMsdbId
        Integer t_hasMsdbId = a_class.getHasMsdbId();
        if ( t_hasMsdbId != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_msdb_id", t_hasMsdbId, XSDDatatype.XSDinteger);
        }
        // HasAlias
        List<MonosaccharideAlias> t_hasAlias = a_class.getHasAlias();
        for (MonosaccharideAlias t_var : t_hasAlias)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_alias", t_var.getInstanceURI());
            this.addMonosaccharideAlias(t_var, t_resource, false);
        }
        // HasLinkagePosition
        List<Integer> t_hasLinkagePosition = a_class.getHasLinkagePosition();
        for (Integer t_var : t_hasLinkagePosition)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_linkage_position", t_var, XSDDatatype.XSDinteger);
        }
        // HasSubstitution
        List<Substituent> t_hasSubstitution = a_class.getHasSubstitution();
        for (Substituent t_var : t_hasSubstitution)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_substitution", t_var.getInstanceURI());
            this.addSubstituent(t_var, t_resource, false);
        }
        // HasAverageMolecularWeight
        Double t_hasAverageMolecularWeight = a_class.getHasAverageMolecularWeight();
        if ( t_hasAverageMolecularWeight != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_average_molecular_weight", t_hasAverageMolecularWeight, XSDDatatype.XSDdouble);
        }
        // HasBasetype
        Basetype t_hasBasetype = a_class.getHasBasetype();
        if ( t_hasBasetype != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_basetype", t_hasBasetype.getInstanceURI());
            this.addBasetype(t_hasBasetype, t_resource, false);
        }
        // HasMonoisotopicMolecularWeight
        Double t_hasMonoisotopicMolecularWeight = a_class.getHasMonoisotopicMolecularWeight();
        if ( t_hasMonoisotopicMolecularWeight != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_monoisotopic_molecular_weight", t_hasMonoisotopicMolecularWeight, XSDDatatype.XSDdouble);
        }
    }

    public void addPolysaccharide(Polysaccharide a_class)
    {
        this.addPolysaccharide(a_class, null,false);
    }

    protected void addPolysaccharide(Polysaccharide a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#polysaccharide");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#polysaccharide");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addSaccharide(a_class,t_itself,true);
        // HasPolymerizationDegree
        List<URI> t_hasPolymerizationDegree = a_class.getHasPolymerizationDegree();
        for (URI t_var : t_hasPolymerizationDegree)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_polymerization_degree", t_var, XSDDatatype.XSDanyURI);
        }
    }

    public void addGlycanDatabase(GlycanDatabase a_class)
    {
        this.addGlycanDatabase(a_class, null,false);
    }

    protected void addGlycanDatabase(GlycanDatabase a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycan_database");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycan_database");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
        // HasUrlTemplate
        String t_hasUrlTemplate = a_class.getHasUrlTemplate();
        if ( t_hasUrlTemplate != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_url_template", t_hasUrlTemplate, XSDDatatype.XSDstring);
        }
        // HasAbbreviation
        String t_hasAbbreviation = a_class.getHasAbbreviation();
        if ( t_hasAbbreviation != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_abbreviation", t_hasAbbreviation, XSDDatatype.XSDstring);
        }
        // HasCategory
        List<GlycanDatabaseCategory> t_hasCategory = a_class.getHasCategory();
        for (GlycanDatabaseCategory t_var : t_hasCategory)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_category", t_var.getInstanceURI());
            this.addGlycanDatabaseCategory(t_var, t_resource, false);
        }
    }

    public void addGlycanDatabaseCategory(GlycanDatabaseCategory a_class)
    {
        this.addGlycanDatabaseCategory(a_class, null,false);
    }

    protected void addGlycanDatabaseCategory(GlycanDatabaseCategory a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycan_database_category");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycan_database_category");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
    }

    public void addMonosaccharideProperty(MonosaccharideProperty a_class)
    {
        this.addMonosaccharideProperty(a_class, null,false);
    }

    protected void addMonosaccharideProperty(MonosaccharideProperty a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#monosaccharide_property");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#monosaccharide_property");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
    }

    public void addAbsoluteConfiguration(AbsoluteConfiguration a_class)
    {
        this.addAbsoluteConfiguration(a_class, null,false);
    }

    protected void addAbsoluteConfiguration(AbsoluteConfiguration a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#absolute_configuration");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#absolute_configuration");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
    }

    public void addAnomer(Anomer a_class)
    {
        this.addAnomer(a_class, null,false);
    }

    protected void addAnomer(Anomer a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#anomer");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#anomer");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
    }

    public void addBasetype(Basetype a_class)
    {
        this.addBasetype(a_class, null,false);
    }

    protected void addBasetype(Basetype a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#basetype");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#basetype");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
        // HasThirdConfiguration
        Configuration t_hasThirdConfiguration = a_class.getHasThirdConfiguration();
        if ( t_hasThirdConfiguration != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_third_configuration", t_hasThirdConfiguration.getInstanceURI());
            this.addConfiguration(t_hasThirdConfiguration, t_resource, false);
        }
        // HasCoreModification
        List<CoreModification> t_hasCoreModification = a_class.getHasCoreModification();
        for (CoreModification t_var : t_hasCoreModification)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_core_modification", t_var.getInstanceURI());
            this.addCoreModification(t_var, t_resource, false);
        }
        // HasFirstConfiguration
        Configuration t_hasFirstConfiguration = a_class.getHasFirstConfiguration();
        if ( t_hasFirstConfiguration != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_first_configuration", t_hasFirstConfiguration.getInstanceURI());
            this.addConfiguration(t_hasFirstConfiguration, t_resource, false);
        }
        // HasRingStart
        Integer t_hasRingStart = a_class.getHasRingStart();
        if ( t_hasRingStart != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_ring_start", t_hasRingStart, XSDDatatype.XSDinteger);
        }
        // HasSecondConfiguration
        Configuration t_hasSecondConfiguration = a_class.getHasSecondConfiguration();
        if ( t_hasSecondConfiguration != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_second_configuration", t_hasSecondConfiguration.getInstanceURI());
            this.addConfiguration(t_hasSecondConfiguration, t_resource, false);
        }
        // HasRingEnd
        Integer t_hasRingEnd = a_class.getHasRingEnd();
        if ( t_hasRingEnd != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_ring_end", t_hasRingEnd, XSDDatatype.XSDinteger);
        }
        // HasStereocode
        String t_hasStereocode = a_class.getHasStereocode();
        if ( t_hasStereocode != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_stereocode", t_hasStereocode, XSDDatatype.XSDstring);
        }
        // HasConfiguration
        List<Configuration> t_hasConfiguration = a_class.getHasConfiguration();
        for (Configuration t_var : t_hasConfiguration)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_configuration", t_var.getInstanceURI());
            this.addConfiguration(t_var, t_resource, false);
        }
        // HasRingType
        RingType t_hasRingType = a_class.getHasRingType();
        if ( t_hasRingType != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_ring_type", t_hasRingType.getInstanceURI());
            this.addRingType(t_hasRingType, t_resource, false);
        }
        // HasBasetypeId
        Integer t_hasBasetypeId = a_class.getHasBasetypeId();
        if ( t_hasBasetypeId != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_basetype_id", t_hasBasetypeId, XSDDatatype.XSDinteger);
        }
        // HasAnomer
        Anomer t_hasAnomer = a_class.getHasAnomer();
        if ( t_hasAnomer != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_anomer", t_hasAnomer.getInstanceURI());
            this.addAnomer(t_hasAnomer, t_resource, false);
        }
        // HasExtendedStereocode
        String t_hasExtendedStereocode = a_class.getHasExtendedStereocode();
        if ( t_hasExtendedStereocode != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_extended_stereocode", t_hasExtendedStereocode, XSDDatatype.XSDstring);
        }
        // HasSize
        Integer t_hasSize = a_class.getHasSize();
        if ( t_hasSize != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_size", t_hasSize, XSDDatatype.XSDinteger);
        }
    }

    public void addConfiguration(Configuration a_class)
    {
        this.addConfiguration(a_class, null,false);
    }

    protected void addConfiguration(Configuration a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#configuration");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#configuration");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
        // HasRelativeConfiguration
        RelativeConfiguration t_hasRelativeConfiguration = a_class.getHasRelativeConfiguration();
        if ( t_hasRelativeConfiguration != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_relative_configuration", t_hasRelativeConfiguration.getInstanceURI());
            this.addRelativeConfiguration(t_hasRelativeConfiguration, t_resource, false);
        }
        // HasAbsoluteConfiguration
        AbsoluteConfiguration t_hasAbsoluteConfiguration = a_class.getHasAbsoluteConfiguration();
        if ( t_hasAbsoluteConfiguration != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_absolute_configuration", t_hasAbsoluteConfiguration.getInstanceURI());
            this.addAbsoluteConfiguration(t_hasAbsoluteConfiguration, t_resource, false);
        }
    }

    public void addCoreModification(CoreModification a_class)
    {
        this.addCoreModification(a_class, null,false);
    }

    protected void addCoreModification(CoreModification a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#core_modification");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#core_modification");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
        // HasModificationPosition2
        Integer t_hasModificationPosition2 = a_class.getHasModificationPosition2();
        if ( t_hasModificationPosition2 != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_modification_position2", t_hasModificationPosition2, XSDDatatype.XSDinteger);
        }
        // HasModificationPosition
        Integer t_hasModificationPosition = a_class.getHasModificationPosition();
        if ( t_hasModificationPosition != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_modification_position", t_hasModificationPosition, XSDDatatype.XSDinteger);
        }
        // HasCoreModificationType
        CoreModificationType t_hasCoreModificationType = a_class.getHasCoreModificationType();
        if ( t_hasCoreModificationType != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_core_modification_type", t_hasCoreModificationType.getInstanceURI());
            this.addCoreModificationType(t_hasCoreModificationType, t_resource, false);
        }
    }

    public void addCoreModificationType(CoreModificationType a_class)
    {
        this.addCoreModificationType(a_class, null,false);
    }

    protected void addCoreModificationType(CoreModificationType a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#core_modification_type");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#core_modification_type");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
    }

    public void addLinkageType(LinkageType a_class)
    {
        this.addLinkageType(a_class, null,false);
    }

    protected void addLinkageType(LinkageType a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#linkage_type");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#linkage_type");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
    }

    public void addMonosaccharideAlias(MonosaccharideAlias a_class)
    {
        this.addMonosaccharideAlias(a_class, null,false);
    }

    protected void addMonosaccharideAlias(MonosaccharideAlias a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#monosaccharide_alias");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#monosaccharide_alias");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
        // IsTrivialName
        Boolean t_isTrivialName = a_class.getIsTrivialName();
        if ( t_isTrivialName != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#is_trivial_name", t_isTrivialName, XSDDatatype.XSDboolean);
        }
        // IsPrimaryName
        Boolean t_isPrimaryName = a_class.getIsPrimaryName();
        if ( t_isPrimaryName != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#is_primary_name", t_isPrimaryName, XSDDatatype.XSDboolean);
        }
        // HasAliasName
        List<String> t_hasAliasName = a_class.getHasAliasName();
        for (String t_var : t_hasAliasName)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_alias_name", t_var, XSDDatatype.XSDstring);
        }
        // HasMonosaccharideNotationScheme
        MonosaccharideNotationScheme t_hasMonosaccharideNotationScheme = a_class.getHasMonosaccharideNotationScheme();
        if ( t_hasMonosaccharideNotationScheme != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_monosaccharide_notation_scheme", t_hasMonosaccharideNotationScheme.getInstanceURI());
            this.addMonosaccharideNotationScheme(t_hasMonosaccharideNotationScheme, t_resource, false);
        }
        // HasExternalSubstituent
        List<Substituent> t_hasExternalSubstituent = a_class.getHasExternalSubstituent();
        for (Substituent t_var : t_hasExternalSubstituent)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_external_substituent", t_var.getInstanceURI());
            this.addSubstituent(t_var, t_resource, false);
        }
    }

    public void addMonosaccharideNotationScheme(MonosaccharideNotationScheme a_class)
    {
        this.addMonosaccharideNotationScheme(a_class, null,false);
    }

    protected void addMonosaccharideNotationScheme(MonosaccharideNotationScheme a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#monosaccharide_notation_scheme");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#monosaccharide_notation_scheme");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
    }

    public void addRelativeConfiguration(RelativeConfiguration a_class)
    {
        this.addRelativeConfiguration(a_class, null,false);
    }

    protected void addRelativeConfiguration(RelativeConfiguration a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#relative_configuration");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#relative_configuration");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
    }

    public void addRingType(RingType a_class)
    {
        this.addRingType(a_class, null,false);
    }

    protected void addRingType(RingType a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#ring_type");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#ring_type");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
    }

    public void addSubstituent(Substituent a_class)
    {
        this.addSubstituent(a_class, null,false);
    }

    protected void addSubstituent(Substituent a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#substituent");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#substituent");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
        // IsLinkable
        List<Boolean> t_isLinkable = a_class.getIsLinkable();
        for (Boolean t_var : t_isLinkable)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#is_linkable", t_var, XSDDatatype.XSDboolean);
        }
        // IsFuzzy
        List<Boolean> t_isFuzzy = a_class.getIsFuzzy();
        for (Boolean t_var : t_isFuzzy)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#is_fuzzy", t_var, XSDDatatype.XSDboolean);
        }
        // HasValence
        List<Integer> t_hasValence = a_class.getHasValence();
        for (Integer t_var : t_hasValence)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_valence", t_var, XSDDatatype.XSDinteger);
        }
        // HasSubstitutionName
        List<String> t_hasSubstitutionName = a_class.getHasSubstitutionName();
        for (String t_var : t_hasSubstitutionName)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_substitution_name", t_var, XSDDatatype.XSDstring);
        }
        // HasSubstituentType
        SubstituentType t_hasSubstituentType = a_class.getHasSubstituentType();
        if ( t_hasSubstituentType != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_substituent_type", t_hasSubstituentType.getInstanceURI());
            this.addSubstituentType(t_hasSubstituentType, t_resource, false);
        }
        // HasSubstituentLinkage
        List<SubstituentLinkage> t_hasSubstituentLinkage = a_class.getHasSubstituentLinkage();
        for (SubstituentLinkage t_var : t_hasSubstituentLinkage)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_substituent_linkage", t_var.getInstanceURI());
            this.addSubstituentLinkage(t_var, t_resource, false);
        }
    }

    public void addSubstituentLinkage(SubstituentLinkage a_class)
    {
        this.addSubstituentLinkage(a_class, null,false);
    }

    protected void addSubstituentLinkage(SubstituentLinkage a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#substituent_linkage");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#substituent_linkage");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
        // HasSubstituentLinkagePosition2
        Integer t_hasSubstituentLinkagePosition2 = a_class.getHasSubstituentLinkagePosition2();
        if ( t_hasSubstituentLinkagePosition2 != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_substituent_linkage_position2", t_hasSubstituentLinkagePosition2, XSDDatatype.XSDinteger);
        }
        // HasSubstituentLinkagePosition
        Integer t_hasSubstituentLinkagePosition = a_class.getHasSubstituentLinkagePosition();
        if ( t_hasSubstituentLinkagePosition != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_substituent_linkage_position", t_hasSubstituentLinkagePosition, XSDDatatype.XSDinteger);
        }
        // HasBasetypeLinkagePosition
        Integer t_hasBasetypeLinkagePosition = a_class.getHasBasetypeLinkagePosition();
        if ( t_hasBasetypeLinkagePosition != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_basetype_linkage_position", t_hasBasetypeLinkagePosition, XSDDatatype.XSDinteger);
        }
        // HasLinkageType
        LinkageType t_hasLinkageType = a_class.getHasLinkageType();
        if ( t_hasLinkageType != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_linkage_type", t_hasLinkageType.getInstanceURI());
            this.addLinkageType(t_hasLinkageType, t_resource, false);
        }
    }

    public void addSubstituentType(SubstituentType a_class)
    {
        this.addSubstituentType(a_class, null,false);
    }

    protected void addSubstituentType(SubstituentType a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#substituent_type");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#substituent_type");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMonosaccharideProperty(a_class,t_itself,true);
        // HasDefaultLinkageType
        List<LinkageType> t_hasDefaultLinkageType = a_class.getHasDefaultLinkageType();
        for (LinkageType t_var : t_hasDefaultLinkageType)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_default_linkage_type", t_var.getInstanceURI());
            this.addLinkageType(t_var, t_resource, false);
        }
        // HasDefaultLinkingPosition2
        List<Integer> t_hasDefaultLinkingPosition2 = a_class.getHasDefaultLinkingPosition2();
        for (Integer t_var : t_hasDefaultLinkingPosition2)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_default_linking_position2", t_var, XSDDatatype.XSDinteger);
        }
        // HasDefaultLinkageType2
        List<LinkageType> t_hasDefaultLinkageType2 = a_class.getHasDefaultLinkageType2();
        for (LinkageType t_var : t_hasDefaultLinkageType2)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_default_linkage_type2", t_var.getInstanceURI());
            this.addLinkageType(t_var, t_resource, false);
        }
        // HasDefaultLinkageBondorder
        List<URI> t_hasDefaultLinkageBondorder = a_class.getHasDefaultLinkageBondorder();
        for (URI t_var : t_hasDefaultLinkageBondorder)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_default_linkage_bondorder", t_var, XSDDatatype.XSDanyURI);
        }
        // HasDefaultLinkageBondorder2
        List<URI> t_hasDefaultLinkageBondorder2 = a_class.getHasDefaultLinkageBondorder2();
        for (URI t_var : t_hasDefaultLinkageBondorder2)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_default_linkage_bondorder2", t_var, XSDDatatype.XSDanyURI);
        }
        // HasDefaultLinkingPosition
        List<Integer> t_hasDefaultLinkingPosition = a_class.getHasDefaultLinkingPosition();
        for (Integer t_var : t_hasDefaultLinkingPosition)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_default_linking_position", t_var, XSDDatatype.XSDinteger);
        }
    }

    public void addMotif(Motif a_class)
    {
        this.addMotif(a_class, null,false);
    }

    protected void addMotif(Motif a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#motif");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#motif");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
        // HasGlycosequence
        List<Glycosequence> t_hasGlycosequence = a_class.getHasGlycosequence();
        for (Glycosequence t_var : t_hasGlycosequence)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_glycosequence", t_var.getInstanceURI());
            this.addGlycosequence(t_var, t_resource, false);
        }
        // HasGlycoconjugateSequence
        List<GlycoconjugateSequence> t_hasGlycoconjugateSequence = a_class.getHasGlycoconjugateSequence();
        for (GlycoconjugateSequence t_var : t_hasGlycoconjugateSequence)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_glycoconjugate_sequence", t_var.getInstanceURI());
            this.addGlycoconjugateSequence(t_var, t_resource, false);
        }
    }

    public void addGlycanMotif(GlycanMotif a_class)
    {
        this.addGlycanMotif(a_class, null,false);
    }

    protected void addGlycanMotif(GlycanMotif a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycan_motif");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycan_motif");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addMotif(a_class,t_itself,true);
        // ContainedIn
        List<Saccharide> t_containedIn = a_class.getContainedIn();
        for (Saccharide t_var : t_containedIn)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#contained_in", t_var.getInstanceURI());
            this.addSaccharide(t_var, t_resource, false);
        }
    }

    public void addGlycanEpitope(GlycanEpitope a_class)
    {
        this.addGlycanEpitope(a_class, null,false);
    }

    protected void addGlycanEpitope(GlycanEpitope a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycan_epitope");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycan_epitope");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addGlycanMotif(a_class,t_itself,true);
    }

    public void addReferencedCompound(ReferencedCompound a_class)
    {
        this.addReferencedCompound(a_class, null,false);
    }

    protected void addReferencedCompound(ReferencedCompound a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#referenced_compound");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#referenced_compound");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
        // HasStructureLocation
        List<String> t_hasStructureLocation = a_class.getHasStructureLocation();
        for (String t_var : t_hasStructureLocation)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_structure_location", t_var, XSDDatatype.XSDstring);
        }
        // Elucidated
        Boolean t_elucidated = a_class.getElucidated();
        if ( t_elucidated != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#elucidated", t_elucidated, XSDDatatype.XSDboolean);
        }
        // IsFromSource
        List<Source> t_isFromSource = a_class.getIsFromSource();
        for (Source t_var : t_isFromSource)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#is_from_source", t_var.getInstanceURI());
            this.addSource(t_var, t_resource, false);
        }
        // HasGlycan
        Saccharide t_hasGlycan = a_class.getHasGlycan();
        if ( t_hasGlycan != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_glycan", t_hasGlycan.getInstanceURI());
            this.addSaccharide(t_hasGlycan, t_resource, false);
        }
        // HasEvidence
        List<URI> t_hasEvidence = a_class.getHasEvidence();
        for (URI t_var : t_hasEvidence)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_evidence", t_var, XSDDatatype.XSDanyURI);
        }
        // PublishedIn
        URI t_publishedIn = a_class.getPublishedIn();
        if ( t_publishedIn != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#published_in", t_publishedIn, XSDDatatype.XSDanyURI);
        }
    }

    public void addRepeatAttribute(RepeatAttribute a_class)
    {
        this.addRepeatAttribute(a_class, null,false);
    }

    protected void addRepeatAttribute(RepeatAttribute a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#repeat_attribute");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#repeat_attribute");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
    }

    public void addResourceEntry(ResourceEntry a_class)
    {
        this.addResourceEntry(a_class, null,false);
    }

    protected void addResourceEntry(ResourceEntry a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#resource_entry");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#resource_entry");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
        // InGlycanDatabase
        GlycanDatabase t_inGlycanDatabase = a_class.getInGlycanDatabase();
        if ( t_inGlycanDatabase != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#in_glycan_database", t_inGlycanDatabase.getInstanceURI());
            this.addGlycanDatabase(t_inGlycanDatabase, t_resource, false);
        }
        // Identifier
        String t_identifier = a_class.getIdentifier();
        if ( t_identifier != null )
        {
            this.addLiteral(t_itself, "http://purl.org/dc/terms/identifier", t_identifier, XSDDatatype.XSDstring);
        }
    }

    public void addSequence(Sequence a_class)
    {
        this.addSequence(a_class, null,false);
    }

    protected void addSequence(Sequence a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#sequence");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#sequence");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
        // HasSequence
        String t_hasSequence = a_class.getHasSequence();
        if ( t_hasSequence != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_sequence", t_hasSequence, XSDDatatype.XSDstring);
        }
        // InCarbohydrateFormat
        CarbohydrateFormat t_inCarbohydrateFormat = a_class.getInCarbohydrateFormat();
        if ( t_inCarbohydrateFormat != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#in_carbohydrate_format", t_inCarbohydrateFormat.getInstanceURI());
            this.addCarbohydrateFormat(t_inCarbohydrateFormat, t_resource, false);
        }
    }

    public void addGlycoconjugateSequence(GlycoconjugateSequence a_class)
    {
        this.addGlycoconjugateSequence(a_class, null,false);
    }

    protected void addGlycoconjugateSequence(GlycoconjugateSequence a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycoconjugate_sequence");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycoconjugate_sequence");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addSequence(a_class,t_itself,true);
    }

    public void addGlycosequence(Glycosequence a_class)
    {
        this.addGlycosequence(a_class, null,false);
    }

    protected void addGlycosequence(Glycosequence a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycosequence");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#glycosequence");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addSequence(a_class,t_itself,true);
    }

    public void addSource(Source a_class)
    {
        this.addSource(a_class, null,false);
    }

    protected void addSource(Source a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
        }

        this.addThing(a_class,t_itself);
        // HasReference
        List<ReferencedCompound> t_hasReference = a_class.getHasReference();
        for (ReferencedCompound t_var : t_hasReference)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_reference", t_var.getInstanceURI());
            this.addReferencedCompound(t_var, t_resource, false);
        }
    }

    public void addSourceNatural(SourceNatural a_class)
    {
        this.addSourceNatural(a_class, null,false);
    }

    protected void addSourceNatural(SourceNatural a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#source_natural");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#source_natural");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }
        // add parent class properties
        this.addSource(a_class,t_itself,true);
        // HasTissue
        URI t_hasTissue = a_class.getHasTissue();
        if ( t_hasTissue != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_tissue", t_hasTissue, XSDDatatype.XSDanyURI);
        }
        // HasLifeStage
        URI t_hasLifeStage = a_class.getHasLifeStage();
        if ( t_hasLifeStage != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_life_stage", t_hasLifeStage, XSDDatatype.XSDanyURI);
        }
        // HostedBy
        SourceNatural t_hostedBy = a_class.getHostedBy();
        if ( t_hostedBy != null )
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#hosted_by", t_hostedBy.getInstanceURI());
            this.addSourceNatural(t_hostedBy, t_resource, false);
        }
        // HasFluid
        List<URI> t_hasFluid = a_class.getHasFluid();
        for (URI t_var : t_hasFluid)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_fluid", t_var, XSDDatatype.XSDanyURI);
        }
        // HasTaxon
        URI t_hasTaxon = a_class.getHasTaxon();
        if ( t_hasTaxon != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_taxon", t_hasTaxon, XSDDatatype.XSDanyURI);
        }
        // HasCellLine
        URI t_hasCellLine = a_class.getHasCellLine();
        if ( t_hasCellLine != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_cell_line", t_hasCellLine, XSDDatatype.XSDanyURI);
        }
        // HasSampleType
        List<URI> t_hasSampleType = a_class.getHasSampleType();
        for (URI t_var : t_hasSampleType)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_sample_type", t_var, XSDDatatype.XSDanyURI);
        }
        // HasCellType
        URI t_hasCellType = a_class.getHasCellType();
        if ( t_hasCellType != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_cell_type", t_hasCellType, XSDDatatype.XSDanyURI);
        }
        // HybridWith
        List<SourceNatural> t_hybridWith = a_class.getHybridWith();
        for (SourceNatural t_var : t_hybridWith)
        {
            Resource t_resource = this.addResource(t_itself, "http://purl.jp/bio/12/glyco/glycan#hybrid_with", t_var.getInstanceURI());
            this.addSourceNatural(t_var, t_resource, false);
        }
        // HasOrgan
        URI t_hasOrgan = a_class.getHasOrgan();
        if ( t_hasOrgan != null )
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_organ", t_hasOrgan, XSDDatatype.XSDanyURI);
        }
        // HasDisease
        List<URI> t_hasDisease = a_class.getHasDisease();
        for (URI t_var : t_hasDisease)
        {
            this.addLiteral(t_itself, "http://purl.jp/bio/12/glyco/glycan#has_disease", t_var, XSDDatatype.XSDanyURI);
        }
    }

    public void addSymbolFormat(SymbolFormat a_class)
    {
        this.addSymbolFormat(a_class, null,false);
    }

    protected void addSymbolFormat(SymbolFormat a_class, Resource a_resource, boolean a_callFromSubClass)
    {
        // create the resource itself, if not provided as argument
        Resource t_itself = a_resource;
        if ( t_itself == null )
        {
            t_itself = this.m_model.createResource(a_class.getInstanceURI());
            if ( !a_class.isOntologyInstance() )
            {
                this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#symbol_format");
                this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
            }
        }
        if ( this.m_alreadyStored.get(t_itself.getURI()) != null && !a_callFromSubClass)
        {
            return;
        }
        this.m_alreadyStored.put(t_itself.getURI(), Boolean.TRUE);
        if ( !a_class.isOntologyInstance() && !a_callFromSubClass)
        {
            this.addResource(t_itself, RDF.type, "http://purl.jp/bio/12/glyco/glycan#symbol_format");
            this.addResource(t_itself, RDF.type, OWL2.NamedIndividual);
        }

        this.addThing(a_class,t_itself);
    }


    protected void addThing(Thing a_class, Resource a_resource)
    {
        Resource t_itself = a_resource;
        for (URI a_uri : a_class.getSeeAlso())
        {
            this.addResource(t_itself, RDFS.seeAlso, a_uri.toString());
        }
        for (URI a_uri : a_class.getSameAs())
        {
            this.addResource(t_itself, OWL2.sameAs, a_uri.toString());
        }
        if ( a_class.getFoafName() != null )
        {
            this.addLiteral(t_itself, FOAF.name.getURI(), a_class.getFoafName(), XSDDatatype.XSDstring);
        }
    }

    public void write(OutputStream a_outputStream, String a_type) throws IOException
    {
        this.m_model.write(a_outputStream,a_type);
        a_outputStream.flush();
    }

    public void writeXML(OutputStream a_outputStream) throws IOException
    {
        this.m_model.write(a_outputStream);
        a_outputStream.flush();
    }

    public void write(Writer a_writer, String a_type) throws IOException
    {
        this.m_model.write(a_writer,a_type);
        a_writer.flush();
    }

    public void writeXML(Writer a_writer) throws IOException
    {
        this.m_model.write(a_writer);
        a_writer.flush();
    }

    public Integer getStatementCount()
    {
        StmtIterator t_iterator = this.m_model.listStatements();
        Integer t_counter = 0;
        while ( t_iterator.hasNext() )
        {
            t_iterator.next();
            t_counter++;
        }
        return t_counter;
    }

}