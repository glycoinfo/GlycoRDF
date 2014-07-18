package org.glycomedb.rdf.glycordf.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.eurocarbdb.MolecularFramework.io.SugarImporterException;
import org.eurocarbdb.MolecularFramework.io.GlycoCT.SugarImporterGlycoCTCondensed;
import org.eurocarbdb.MolecularFramework.io.Glyde.SugarExporterGlydeIIC;
import org.eurocarbdb.MolecularFramework.io.Linucs.SugarExporterLinucs;
import org.eurocarbdb.MolecularFramework.io.carbbank.SugarExporterCarbBank;
import org.eurocarbdb.MolecularFramework.io.kcf.GlycoVisitorFromGlycoCTAnomer;
import org.eurocarbdb.MolecularFramework.io.kcf.SugarExporterKcf;
import org.eurocarbdb.MolecularFramework.io.namespace.GlycoVisitorFromGlycoCT;
import org.eurocarbdb.MolecularFramework.sugar.Sugar;
import org.eurocarbdb.MolecularFramework.util.visitor.GlycoVisitorException;
import org.eurocarbdb.resourcesdb.GlycanNamescheme;
import org.glycomedb.database.data.RemoteEntry;
import org.glycomedb.database.data.Structure;
import org.glycomedb.database.visitor.GlycoVisitorGlycanType;
import org.glycomedb.database.visitor.GlycoVisitorMonosaccharideList;
import org.glycomedb.database.visitor.MonosaccharideComponent;
import org.glycomedb.rdf.glycordf.data.CarbohydrateFormat;
import org.glycomedb.rdf.glycordf.data.Component;
import org.glycomedb.rdf.glycordf.data.CyclicGlycan;
import org.glycomedb.rdf.glycordf.data.GlycanDatabase;
import org.glycomedb.rdf.glycordf.data.Glycosequence;
import org.glycomedb.rdf.glycordf.data.Image;
import org.glycomedb.rdf.glycordf.data.Monosaccharide;
import org.glycomedb.rdf.glycordf.data.Nglycan;
import org.glycomedb.rdf.glycordf.data.Oglycan;
import org.glycomedb.rdf.glycordf.data.Polysaccharide;
import org.glycomedb.rdf.glycordf.data.ReferencedCompound;
import org.glycomedb.rdf.glycordf.data.ResourceEntry;
import org.glycomedb.rdf.glycordf.data.Saccharide;
import org.glycomedb.rdf.glycordf.data.SourceNatural;
import org.glycomedb.rdf.glycordf.data.SymbolFormat;
import org.glycomedb.rdf.glycordf.jena.RDFGenerator;
import org.glycomedb.rdf.glycordf.log.RDFGeneratorError;
import org.glycomedb.rdf.glycordf.log.RDFGeneratorErrorLogger;
import org.glycomedb.residuetranslator.ResidueTranslator;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;


public class GlycoRDFWriter
{
    private StructureProvider m_provider = null;
    private RDFGeneratorGlycanConfig m_config = null;
    private RDFGenerator m_generator = null;
    private RDFGeneratorErrorLogger m_logger = new RDFGeneratorErrorLogger();
    private ResidueTranslator m_residueTransTranslation = null;
    
    private String namespace = "glycome-db";

    public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public RDFGeneratorErrorLogger getLogger()
    {
        return m_logger;
    }

    public void setLogger(RDFGeneratorErrorLogger a_logger)
    {
        m_logger = a_logger;
    }

    public GlycoRDFWriter(StructureProvider a_provider, RDFGeneratorGlycanConfig a_config, ResidueTranslator a_translator)
    {
        this.m_provider = a_provider;
        this.m_config = a_config;
        Model t_model = ModelFactory.createDefaultModel();
        this.m_generator = new RDFGenerator(t_model);
        this.m_residueTransTranslation = a_translator;
    }

    public void write(Integer a_structureId) throws IOException
    {
        StructureIterator t_iterator = new StructureIterator(a_structureId, this.m_provider);
        this.write(t_iterator);
    }

    public void write(List<Integer> a_structureId) throws IOException
    {
        StructureIterator t_iterator = new StructureIterator(a_structureId, this.m_provider);
        this.write(t_iterator);
    }

    public void writeAll() throws IOException
    {
        StructureIterator t_iterator = new StructureIterator(this.m_provider);
        this.write(t_iterator);
    }

    private void write(StructureIterator a_iterator) throws IOException
    {
        while ( a_iterator.hasNext() )
        {
            Structure t_structure = a_iterator.next();
            if ( t_structure == null )
            {
                this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.INVALID_ID, "structure id", a_iterator.getLastId().toString(), null));
            }
            else
            {
                try
                {
                    SugarImporterGlycoCTCondensed t_importer = new SugarImporterGlycoCTCondensed();
                    Sugar t_sugar = t_importer.parse(t_structure.getSequence());
                    GlycoVisitorGlycanType t_visitorType = new GlycoVisitorGlycanType();
                    t_visitorType.start(t_sugar);
                    if ( t_visitorType.getType() == null )
                    {
                    	String uri;
                    	if (namespace.equalsIgnoreCase("glyspace")) {
                    		uri = URIPattern.getURI(URIPattern.glyspace_glycan,t_structure.getAccessionNumber());
                    	} else {
                    		uri = URIPattern.getURI(URIPattern.glycomedb_glycan,t_structure.getId().toString());
                    	}
                    	
                        Saccharide t_glycan = new Saccharide(uri);
                        this.addProperties(t_glycan,t_structure,t_sugar);
                        this.m_generator.addSaccharide(t_glycan);
                    }
                    else
                    {
                        if ( t_visitorType.getType().equals(GlycoVisitorGlycanType.N_glycan) )
                        {
                        	String uri;
                        	if (namespace.equalsIgnoreCase("glyspace")) {
                        		uri = URIPattern.getURI(URIPattern.glyspace_glycan,t_structure.getAccessionNumber());
                        	} else {
                        		uri = URIPattern.getURI(URIPattern.glycomedb_glycan,t_structure.getId().toString());
                        	}
                            Nglycan t_glycan = new Nglycan(uri);
                            this.addProperties(t_glycan,t_structure,t_sugar);
                            this.m_generator.addNglycan(t_glycan);
                        }
                        else if (t_visitorType.getType().equals(GlycoVisitorGlycanType.O_glycan) )
                        {
                        	String uri;
                        	if (namespace.equalsIgnoreCase("glyspace")) {
                        		uri = URIPattern.getURI(URIPattern.glyspace_glycan,t_structure.getAccessionNumber());
                        	} else {
                        		uri = URIPattern.getURI(URIPattern.glycomedb_glycan,t_structure.getId().toString());
                        	}
                            Oglycan t_glycan = new Oglycan(uri);
                            this.addProperties(t_glycan,t_structure,t_sugar);
                            this.m_generator.addOglycan(t_glycan);
                        }
                        else if (t_visitorType.getType().equals(GlycoVisitorGlycanType.Cyclic_Glycan) )
                        {
                        	String uri;
                        	if (namespace.equalsIgnoreCase("glyspace")) {
                        		uri = URIPattern.getURI(URIPattern.glyspace_glycan,t_structure.getAccessionNumber());
                        	} else {
                        		uri = URIPattern.getURI(URIPattern.glycomedb_glycan,t_structure.getId().toString());
                        	}
                            CyclicGlycan t_glycan = new CyclicGlycan(uri);
                            this.addProperties(t_glycan,t_structure,t_sugar);
                            this.m_generator.addCyclicGlycan(t_glycan);
                        }
                        else if (t_visitorType.getType().equals(GlycoVisitorGlycanType.Polysaccharide) )
                        {
                        	String uri;
                        	if (namespace.equalsIgnoreCase("glyspace")) {
                        		uri = URIPattern.getURI(URIPattern.glyspace_glycan,t_structure.getAccessionNumber());
                        	} else {
                        		uri = URIPattern.getURI(URIPattern.glycomedb_glycan,t_structure.getId().toString());
                        	}
                            Polysaccharide t_glycan = new Polysaccharide(uri);
                            this.addProperties(t_glycan,t_structure,t_sugar);
                            this.m_generator.addPolysaccharide(t_glycan);
                        }
                        else
                        {
                        	String uri;
                        	if (namespace.equalsIgnoreCase("glyspace")) {
                        		uri = URIPattern.getURI(URIPattern.glyspace_glycan,t_structure.getAccessionNumber());
                        	} else {
                        		uri = URIPattern.getURI(URIPattern.glycomedb_glycan,t_structure.getId().toString());
                        	}
                            Saccharide t_glycan = new Saccharide(uri);
                            this.addProperties(t_glycan,t_structure,t_sugar);
                            this.m_generator.addSaccharide(t_glycan);
                        }
                    }
                }
                catch (SugarImporterException e)
                {
                    this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.PARSING_GLYCOCT, e.getErrorText(), t_structure.getId().toString(), e));
                }
                catch (GlycoVisitorException e) 
                {
                    this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.GLYCAN_TYPE, e.getMessage(), t_structure.getId().toString(), e));
                }
            }
        }
    }

    private void addProperties(Saccharide a_glycan, Structure a_structure, Sugar a_sugar)
    {
        // GlycoCT
        this.addSequences(a_glycan, a_structure,a_sugar);
        // composition
        this.addComposition(a_glycan, a_structure,a_sugar);
        // image
        this.addImages(a_glycan, a_structure);
        // add resource entries
        this.addResourceEntries(a_glycan, a_structure);
        // referenced compound 
        this.addReferencedCompounds(a_glycan, a_structure);
    }

    private void addReferencedCompounds(Saccharide a_glycan, Structure a_structure)
    {
        if ( this.m_config.isReferencedCompound() )
        {
            for (Integer t_taxonId : a_structure.getTaxonUniprot())
            {
                try
                {
                    if ( this.m_config.isFlatReferencedCompound() )
                    {
                        ReferencedCompound t_refCompound = new ReferencedCompound(
                                URIPattern.getURI(URIPattern.glycomedb_ref_compound,a_structure.getId().toString() + "-" + t_taxonId.toString()), 
                                true);
                        a_glycan.addHasReference(t_refCompound);
                    }
                    else
                    {
                        ReferencedCompound t_refCompound = new ReferencedCompound(
                                URIPattern.getURI(URIPattern.glycomedb_ref_compound,a_structure.getId().toString() + "-" + t_taxonId.toString())
                                );
                        SourceNatural t_sourceNatural = new SourceNatural(
                                URIPattern.getURI(URIPattern.glycomedb_source,t_taxonId.toString())
                                );
                        t_sourceNatural.setHasTaxon(new URI(URIPattern.getURI(URIPattern.uniprot_taxon,t_taxonId.toString())));
                        t_refCompound.addIsFromSource(t_sourceNatural);
                        t_refCompound.setHasGlycan(a_glycan);
                        a_glycan.addHasReference(t_refCompound);
                        t_sourceNatural.addHasReference(t_refCompound);
                        this.m_generator.addSourceNatural(t_sourceNatural);
                    }
                }
                catch (Exception e)
                {
                    this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.SOURCE, e.getMessage(), a_structure.getId().toString(), e));
                }
            }
        }
    }

    private void addResourceEntries(Saccharide a_glycan, Structure a_structure)
    {
        if ( this.m_config.isRemoteEntries() )
        {
            for (RemoteEntry t_entry : a_structure.getRemoteEntries())
            {
                try
                {
                    ResourceEntry t_resEntry = new ResourceEntry(this.createResourceURI(t_entry));
                    t_resEntry.setIdentifier(t_entry.getResourceId());
                    t_resEntry.setInGlycanDatabase(this.createGlycanDatabase(t_entry.getResource()));
                    a_glycan.addHasResourceEntry(t_resEntry);
                }
                catch ( Exception e )
                {
                    this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.REMOTE_ENTRY, e.getMessage(), a_structure.getId().toString(), e));
                }
            }
        }
    }

    private String createResourceURI(RemoteEntry a_entry) throws Exception
    {
        String t_databaseString = null;
        if ( a_entry.getResource().equals("kegg") )
        {
            t_databaseString = URIPattern.database_kegg;
        }
        if ( a_entry.getResource().equals("bcsdb") )
        {
            t_databaseString = URIPattern.database_bcsdb;
        }
        if ( a_entry.getResource().equals("pdb") )
        {
            t_databaseString = URIPattern.database_pdb;
        }
        if ( a_entry.getResource().equals("cfg") )
        {
            t_databaseString = URIPattern.database_cfg;
        }
        if ( a_entry.getResource().equals("glyco") )
        {
            t_databaseString = URIPattern.database_glyco;
        }
        if ( a_entry.getResource().equals("jcggdb") )
        {
            t_databaseString = URIPattern.database_jcggdb;
        }
        if ( a_entry.getResource().equals("glycobase(lille)") )
        {
            t_databaseString = URIPattern.database_glycobase_lille;
        }
        if ( a_entry.getResource().equals("glycosciences.de") )
        {
            t_databaseString = URIPattern.database_glycosciences_de;
        }
        if ( a_entry.getResource().equals("carbbank") )
        {
            t_databaseString = URIPattern.database_carbbank;
        }
        if ( a_entry.getResource().equals("glyaffinity") )
        {
            t_databaseString =  URIPattern.database_glyaffinity;
        }
        if ( t_databaseString == null )
        {
            throw new Exception("Unknown database type:" + a_entry.getResource());
        }
        return URIPattern.getURI(t_databaseString,a_entry.getResourceId());
    }

    private GlycanDatabase createGlycanDatabase(String a_resource) throws Exception
    {
        if ( a_resource.equals("kegg") )
        {
            return GlycanDatabase.database_kegg;
        }
        if ( a_resource.equals("bcsdb") )
        {
            return GlycanDatabase.database_bcsdb;
        }
        if ( a_resource.equals("pdb") )
        {
            return GlycanDatabase.database_pdb;
        }
        if ( a_resource.equals("cfg") )
        {
            return GlycanDatabase.database_cfg;
        }
        if ( a_resource.equals("glyco") )
        {
            return GlycanDatabase.database_glyco;
        }
        if ( a_resource.equals("jcggdb") )
        {
            return GlycanDatabase.database_jcggdb;
        }
        if ( a_resource.equals("glycobase(lille)") )
        {
            return GlycanDatabase.database_glycobase_lille;
        }
        if ( a_resource.equals("glycosciences.de") )
        {
            return GlycanDatabase.database_glycosciences_de;
        }
        if ( a_resource.equals("carbbank") )
        {
            return GlycanDatabase.database_carbbank;
        }
        if ( a_resource.equals("glyaffinity") )
        {
            return GlycanDatabase.database_glyaffinity;
        }
        throw new Exception("Unknown database type:" + a_resource);
    }

    private void addComposition(Saccharide a_glycan, Structure a_structure, Sugar a_sugar) 
    {
        if ( this.m_config.isComposition() )
        {
            try
            {
                GlycoVisitorMonosaccharideList t_visitorMs = new GlycoVisitorMonosaccharideList();
                t_visitorMs.start(a_sugar);
                if ( t_visitorMs.getMultiplier() != null )
                {
                    List<Component> t_list = new ArrayList<Component>();
                    for (MonosaccharideComponent t_compomentElement : t_visitorMs.getComponents().values())
                    {
                        Component t_component = new Component(this.createComponentURI(t_compomentElement));
                        t_component.setHasCardinality(t_compomentElement.getNumber());
                        t_component.setHasMonosaccharide(new Monosaccharide(this.createMonosaccharideURI(t_compomentElement),true));
                        t_list.add(t_component);
                    }
                    a_glycan.setHasComponent(t_list);
                }
            }
            catch (GlycoVisitorException e)
            {
                this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.COMPOSITION, e.getMessage(), a_structure.getId().toString(), e));
            }
            catch (UnsupportedEncodingException e)
            {
                this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.COMPOSITION, e.getMessage(), a_structure.getId().toString(), e));
            }
        }
    }

    private String createMonosaccharideURI(MonosaccharideComponent a_compomentElement) throws UnsupportedEncodingException
    {
        return URIPattern.getURI(URIPattern.msdb_monosaccharide, URLEncoder.encode(a_compomentElement.getMsdbString(),"UTF-8"));
    }

    private String createComponentURI(MonosaccharideComponent a_compomentElement) throws UnsupportedEncodingException
    {
    	String uri;
    	if (namespace.equalsIgnoreCase("glyspace")) {
    		uri = URIPattern.getURI(URIPattern.glyspace_component,a_compomentElement.getNumber() + "_" + URLEncoder.encode(a_compomentElement.getMsdbString(),"UTF-8"));
    	} else {
    		uri = URIPattern.getURI(URIPattern.glycomedb_component, a_compomentElement.getNumber() + "_" + URLEncoder.encode(a_compomentElement.getMsdbString(),"UTF-8"));
    	}
        return uri;
    }

    private void addSequences(Saccharide a_glycan, Structure a_structure, Sugar a_sugar)
    {
        if ( this.m_config.isSequenceGlycoCt() )
        {
        	String uri;
        	if (namespace.equalsIgnoreCase("glyspace")) {
        		uri = URIPattern.getURI(URIPattern.glyspace_sequence,a_structure.getAccessionNumber()) + "/ct";
        	} else {
        		uri = URIPattern.getURI(URIPattern.glycomedb_sequence,a_structure.getId().toString()) + "/ct";
        	}
        	
            this.addGlycoSequence(uri,
                    CarbohydrateFormat.carbohydrate_format_glycoct, a_structure.getSequence(), a_glycan);
        }
        if ( this.m_config.isSequenceGlydeII() )
        {
            try
            {
                SugarExporterGlydeIIC t_exporter = new SugarExporterGlydeIIC();
                t_exporter.setGlycanId(a_structure.getId().toString());
                t_exporter.start(a_sugar);
                String t_sequenceXML = t_exporter.getXMLCode();
                String uri;
            	if (namespace.equalsIgnoreCase("glyspace")) {
            		uri = URIPattern.getURI(URIPattern.glyspace_sequence,a_structure.getId().toString()) + "/glyde";
            	} else {
            		uri = URIPattern.getURI(URIPattern.glycomedb_sequence,a_structure.getId().toString()) + "/glyde";
            	}
                this.addGlycoSequence(uri,
                        CarbohydrateFormat.carbohydrate_format_glyde2, t_sequenceXML, a_glycan);
            }
            catch (Exception e)
            {}
        }
        if ( this.m_config.isSequenceCarbBank() )
        {
            try
            {
                SugarImporterGlycoCTCondensed t_importerCT = new SugarImporterGlycoCTCondensed();
                GlycoVisitorFromGlycoCT t_visitorFromCT = new GlycoVisitorFromGlycoCT(this.m_residueTransTranslation, GlycanNamescheme.CARBBANK);
                Sugar t_sugarNew = null;
                Sugar t_sugar = t_importerCT.parse(a_structure.getSequence());
                t_visitorFromCT.start(t_sugar);
                t_sugarNew = t_visitorFromCT.getNormalizedSugar();
                SugarExporterCarbBank t_exporterTarget = new SugarExporterCarbBank();
                String t_newSequence = t_exporterTarget.export(t_sugarNew);
                String uri;
            	if (namespace.equalsIgnoreCase("glyspace")) {
            		uri = URIPattern.getURI(URIPattern.glyspace_sequence,a_structure.getId().toString()) + "/carbbank";
            	} else {
            		uri = URIPattern.getURI(URIPattern.glycomedb_sequence,a_structure.getId().toString()) + "/carbbank";
            	}
                this.addGlycoSequence(uri,
                        CarbohydrateFormat.carbohydrate_format_carbbank, t_newSequence, a_glycan);
            }
            catch (Exception e)
            {}
        }
        if ( this.m_config.isSequenceLinucs() )
        {
            try
            {
                SugarImporterGlycoCTCondensed t_importerCT = new SugarImporterGlycoCTCondensed();
                GlycoVisitorFromGlycoCT t_visitorFromCT = new GlycoVisitorFromGlycoCT(this.m_residueTransTranslation, GlycanNamescheme.CARBBANK);
                Sugar t_sugarNew = null;
                Sugar t_sugar = t_importerCT.parse(a_structure.getSequence());
                t_visitorFromCT.start(t_sugar);
                t_sugarNew = t_visitorFromCT.getNormalizedSugar();
                SugarExporterLinucs t_exporterTarget = new SugarExporterLinucs();
                String t_newSequence = t_exporterTarget.export(t_sugarNew);
                String uri;
            	if (namespace.equalsIgnoreCase("glyspace")) {
            		uri = URIPattern.getURI(URIPattern.glyspace_sequence,a_structure.getId().toString()) + "/linucs";
            	} else {
            		uri = URIPattern.getURI(URIPattern.glycomedb_sequence,a_structure.getId().toString()) + "/linucs";
            	}
                this.addGlycoSequence(uri,
                        CarbohydrateFormat.carbohydrate_format_linucs, t_newSequence, a_glycan);
            }
            catch (Exception e)
            {}
        }
        if ( this.m_config.isSequenceKCF() )
        {
            try
            {
                SugarImporterGlycoCTCondensed t_importerCT = new SugarImporterGlycoCTCondensed();
                GlycoVisitorFromGlycoCTAnomer t_visitorFromCT = new GlycoVisitorFromGlycoCTAnomer(this.m_residueTransTranslation,GlycanNamescheme.KEGG);
                Sugar t_sugarNew = null;
                Sugar t_sugar = t_importerCT.parse(a_structure.getSequence());
                t_visitorFromCT.start(t_sugar);
                t_sugarNew = t_visitorFromCT.getNormalizedSugar();
                SugarExporterKcf t_exporterTarget = new SugarExporterKcf();
                t_exporterTarget.setId(a_structure.getId().toString());
                t_exporterTarget.setHashAnomer(t_visitorFromCT.getHashAnomer());
                String t_newSequence = t_exporterTarget.export(t_sugarNew);
                String uri;
            	if (namespace.equalsIgnoreCase("glyspace")) {
            		uri = URIPattern.getURI(URIPattern.glyspace_sequence,a_structure.getId().toString()) + "/kcf";
            	} else {
            		uri = URIPattern.getURI(URIPattern.glycomedb_sequence,a_structure.getId().toString()) + "/kcf";
            	}
                this.addGlycoSequence(uri,
                        CarbohydrateFormat.carbohydrate_format_linucs, t_newSequence, a_glycan);
            }
            catch (Exception e)
            {}
        }
    }

    private void addGlycoSequence(String a_url, CarbohydrateFormat a_carbohydrateFormat, String a_sequence, Saccharide a_glycan)
    {
        if ( this.m_config.isFlatSequence() )
        {
            Glycosequence t_sequence = new Glycosequence(a_url,true);
            a_glycan.addHasGlycosequence(t_sequence);
        }
        else
        {
            Glycosequence t_sequence = new Glycosequence(a_url);
            t_sequence.setInCarbohydrateFormat(a_carbohydrateFormat);
            t_sequence.setHasSequence(a_sequence);
            a_glycan.addHasGlycosequence(t_sequence);
        }
    }

    private void addGlycoSequence(String a_url, CarbohydrateFormat a_carbohydrateFormat, String a_sequence)
    {
        Glycosequence t_sequence = new Glycosequence(a_url);
        t_sequence.setInCarbohydrateFormat(a_carbohydrateFormat);
        t_sequence.setHasSequence(a_sequence);
        this.m_generator.addGlycosequence(t_sequence);
    }

    private void addImages(Saccharide a_glycan, Structure a_structure)
    {
        if ( this.m_config.isImages() )
        {
            if ( a_structure.isHasCfgImage() )
            {
            	String uri;
            	if (namespace.equalsIgnoreCase("glyspace")) {
            		uri = URIPattern.getURI(URIPattern.glyspace_image,a_structure.getAccessionNumber()) + "?style=extended&format=png&notation=cfg";
            	} else {
            		uri = URIPattern.getURI(URIPattern.glycomedb_image,a_structure.getId().toString()) + "&type=cfg&filetype=png";
            	}	
                Image t_image = new Image(uri);
                t_image.setHasSymbolFormat(SymbolFormat.symbol_format_cfg);
                t_image.setFormat("image/jpeg");
                a_glycan.addHasImage(t_image);
                
                if (namespace.equalsIgnoreCase("glyspace")) {
            		uri = URIPattern.getURI(URIPattern.glyspace_image,a_structure.getAccessionNumber()) + "?style=extended&format=svg&notation=cfg";
            	} else {
            		uri = URIPattern.getURI(URIPattern.glycomedb_image,a_structure.getId().toString()) + "&type=cfg&filetype=svg";
            	}
                t_image = new Image(uri);
                t_image.setHasSymbolFormat(SymbolFormat.symbol_format_cfg);
                t_image.setFormat("image/svg+xml");
                a_glycan.addHasImage(t_image);
            }
            if ( a_structure.isHasOxImag() )
            {
            	String uri;
            	if (namespace.equalsIgnoreCase("glyspace")) {
            		uri = URIPattern.getURI(URIPattern.glyspace_image,a_structure.getAccessionNumber()) + "?style=extended&format=png&notation=uoxf";
            	} else {
            		uri = URIPattern.getURI(URIPattern.glycomedb_image,a_structure.getId().toString()) + "&type=oxford&filetype=png";
            	}
                Image t_image = new Image(uri);
                t_image.setHasSymbolFormat(SymbolFormat.symbol_format_uoxf);
                t_image.setFormat("image/jpeg");
                a_glycan.addHasImage(t_image);
                if (namespace.equalsIgnoreCase("glyspace")) {
            		uri = URIPattern.getURI(URIPattern.glyspace_image,a_structure.getAccessionNumber()) + "?style=extended&format=svg&notation=uoxf";
            	} else {
            		uri = URIPattern.getURI(URIPattern.glycomedb_image,a_structure.getId().toString()) + "&type=oxford&filetype=svg";
            	}
                t_image = new Image(uri);
                t_image.setHasSymbolFormat(SymbolFormat.symbol_format_uoxf);
                t_image.setFormat("image/svg+xml");
                a_glycan.addHasImage(t_image);
            }
            if ( a_structure.isHasIupacImage() )
            {
            	String uri;
            	if (namespace.equalsIgnoreCase("glyspace")) {
            		uri = URIPattern.getURI(URIPattern.glyspace_image,a_structure.getAccessionNumber()) + "?style=extended&format=png&notation=iupac";
            	} else {
            		uri = URIPattern.getURI(URIPattern.glycomedb_image,a_structure.getId().toString()) + "&type=iupac&filetype=png";
            	}
                Image t_image = new Image(uri);
                t_image.setHasSymbolFormat(SymbolFormat.symbol_format_text);
                t_image.setFormat("image/jpeg");
                a_glycan.addHasImage(t_image);
                if (namespace.equalsIgnoreCase("glyspace")) {
            		uri = URIPattern.getURI(URIPattern.glyspace_image,a_structure.getAccessionNumber()) + "?style=extended&format=svg&notation=iupac";
            	} else {
            		uri = URIPattern.getURI(URIPattern.glycomedb_image,a_structure.getId().toString()) + "&type=iupac&filetype=svg";
            	}
                t_image = new Image();
                t_image.setHasSymbolFormat(SymbolFormat.symbol_format_text);
                t_image.setFormat("image/svg+xml");
                a_glycan.addHasImage(t_image);
            }
        }
    }

    public void serialze(Writer a_writer, String a_format) throws IOException
    {
        if (a_format.equals("XML"))
        {
            this.m_generator.writeXML(a_writer);
        }
        else
        {
            this.m_generator.write(a_writer, a_format);
        }
    }

    public Integer getTripleCount()
    {
        return this.m_generator.getStatementCount();
    }

    public boolean writeSequenceOnly(Integer a_structureId, SequenceFromat a_format) throws IOException
    {
        StructureIterator t_iterator = new StructureIterator(a_structureId, this.m_provider);
        if ( t_iterator.hasNext() )
        {
            Structure t_structure = t_iterator.next();

            if ( a_format.equals(SequenceFromat.GlycoCT) )
            {
                this.addGlycoSequence(URIPattern.getURI(URIPattern.glycomedb_sequence, a_structureId.toString()) + "/ct",
                        CarbohydrateFormat.carbohydrate_format_glycoct, t_structure.getSequence());
                return true;
            }
            else if ( a_format.equals(SequenceFromat.Glyde_II) )
            {
                try
                {
                    SugarImporterGlycoCTCondensed t_importer = new SugarImporterGlycoCTCondensed();
                    Sugar t_sugar = t_importer.parse(t_structure.getSequence());
                    SugarExporterGlydeIIC t_exporter = new SugarExporterGlydeIIC();
                    t_exporter.setGlycanId(t_structure.getId().toString());
                    t_exporter.start(t_sugar);
                    String t_sequenceXML = t_exporter.getXMLCode();
                    this.addGlycoSequence(URIPattern.getURI(URIPattern.glycomedb_sequence,t_structure.getId().toString()) + "/glyde",
                            CarbohydrateFormat.carbohydrate_format_glyde2, t_sequenceXML);
                    return true;
                }
                catch (Exception e)
                {
                    this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.FORMAT, "Error creating Glyde-II sequence: " + e.getMessage(),a_structureId.toString(), e));
                    return false;
                }
            }
            else if ( a_format.equals(SequenceFromat.CARBBANK) )
            {
                try
                {
                    SugarImporterGlycoCTCondensed t_importerCT = new SugarImporterGlycoCTCondensed();
                    GlycoVisitorFromGlycoCT t_visitorFromCT = new GlycoVisitorFromGlycoCT(this.m_residueTransTranslation, GlycanNamescheme.CARBBANK);
                    Sugar t_sugarNew = null;
                    Sugar t_sugar = t_importerCT.parse(t_structure.getSequence());
                    t_visitorFromCT.start(t_sugar);
                    t_sugarNew = t_visitorFromCT.getNormalizedSugar();
                    SugarExporterCarbBank t_exporterTarget = new SugarExporterCarbBank();
                    String t_newSequence = t_exporterTarget.export(t_sugarNew);
                    this.addGlycoSequence(URIPattern.getURI(URIPattern.glycomedb_sequence,t_structure.getId().toString()) + "/carbbank",
                            CarbohydrateFormat.carbohydrate_format_carbbank, t_newSequence);
                    return true;
                }
                catch (Exception e)
                {
                    this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.FORMAT, "Error creating CarbBank sequence: " + e.getMessage(), a_structureId.toString(), e));
                    return false;
                }
            }
            else if ( a_format.equals(SequenceFromat.LINUCS) )
            {
                try
                {
                    SugarImporterGlycoCTCondensed t_importerCT = new SugarImporterGlycoCTCondensed();
                    GlycoVisitorFromGlycoCT t_visitorFromCT = new GlycoVisitorFromGlycoCT(this.m_residueTransTranslation, GlycanNamescheme.CARBBANK);
                    Sugar t_sugarNew = null;
                    Sugar t_sugar = t_importerCT.parse(t_structure.getSequence());
                    t_visitorFromCT.start(t_sugar);
                    t_sugarNew = t_visitorFromCT.getNormalizedSugar();
                    SugarExporterLinucs t_exporterTarget = new SugarExporterLinucs();
                    String t_newSequence = t_exporterTarget.export(t_sugarNew);
                    this.addGlycoSequence(URIPattern.getURI(URIPattern.glycomedb_sequence,t_structure.getId().toString()) + "/linucs",
                            CarbohydrateFormat.carbohydrate_format_linucs, t_newSequence);
                    return true;
                }
                catch (Exception e)
                {
                    this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.FORMAT, "Error creating LINUCS sequence: " + e.getMessage(), a_structureId.toString(), e));
                    return false;
                }
            }
            else if ( a_format.equals(SequenceFromat.KCF) )
            {
                try
                {
                    SugarImporterGlycoCTCondensed t_importerCT = new SugarImporterGlycoCTCondensed();
                    Sugar t_sugarNew = null;
                    Sugar t_sugar = t_importerCT.parse(t_structure.getSequence());
                    GlycoVisitorFromGlycoCTAnomer t_visitorFromCt = new GlycoVisitorFromGlycoCTAnomer(this.m_residueTransTranslation,GlycanNamescheme.KEGG);
                    t_visitorFromCt.start(t_sugar);
                    t_sugarNew = t_visitorFromCt.getNormalizedSugar();
                    SugarExporterKcf t_exporterTarget = new SugarExporterKcf();
                    t_exporterTarget.setHashAnomer(t_visitorFromCt.getHashAnomer());
                    t_exporterTarget.setId(t_structure.getId().toString());
                    String t_newSequence = t_exporterTarget.export(t_sugarNew);
                    this.addGlycoSequence(URIPattern.getURI(URIPattern.glycomedb_sequence,t_structure.getId().toString()) + "/kcf",
                            CarbohydrateFormat.carbohydrate_format_kcf, t_newSequence);
                    return true;
                }
                catch (Exception e)
                {
                    this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.FORMAT, "Error creating LINUCS sequence: " + e.getMessage(), a_structureId.toString(), e));
                    return false;
                }
            }
            else
            {
                this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.FORMAT, "Unknown sequence format: " + a_format, a_structureId.toString(), null));
                return false;
            }
        }
        else
        {
            this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.FORMAT, "No sequence found: " + a_structureId.toString(), a_structureId.toString(), null));
            return false;
        }
    }

    public void writeReferencedCompoundOnly(Integer a_structureId, Integer a_sourceID)
    {
        try
        {
            ReferencedCompound t_refCompound = new ReferencedCompound(
                    URIPattern.getURI(URIPattern.glycomedb_ref_compound,a_structureId.toString() + "-" + a_sourceID.toString())
                    );
            SourceNatural t_sourceNatural = new SourceNatural(
                    URIPattern.getURI(URIPattern.glycomedb_source,a_sourceID.toString())
                    ,true);
            Saccharide t_glycan = new Saccharide(URIPattern.getURI(URIPattern.glycomedb_glycan,a_structureId.toString()), true);
            t_refCompound.addIsFromSource(t_sourceNatural);
            t_refCompound.setHasGlycan(t_glycan);
            this.m_generator.addReferencedCompound(t_refCompound);
        }
        catch (Exception e)
        {
            this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.REFERENCE_COMPOUND, e.getMessage(), a_structureId.toString() + "-" + a_sourceID.toString(), e));
        }
    }

    public void writeSourceOnly(Integer a_taxonId, ArrayList<Integer> a_structureIDs)
    {
        try
        {
            SourceNatural t_sourceNatural = new SourceNatural(
                    URIPattern.getURI(URIPattern.glycomedb_source,a_taxonId.toString())
                    );
            t_sourceNatural.setHasTaxon(
                    new URI(URIPattern.getURI(URIPattern.uniprot_taxon,a_taxonId.toString()))
                    );
            for (Integer t_id : a_structureIDs)
            {
                ReferencedCompound t_refCompound = new ReferencedCompound(
                        URIPattern.getURI(URIPattern.glycomedb_ref_compound,t_id.toString() + "-" + a_taxonId.toString())
                        ,true);
                t_sourceNatural.addHasReference(t_refCompound);
            }
            this.m_generator.addSourceNatural(t_sourceNatural);
        }
        catch (Exception e)
        {
            this.m_logger.addError(new RDFGeneratorError(RDFGeneratorError.SOURCE, e.getMessage(), a_taxonId.toString(), e));
        }
    }
}
