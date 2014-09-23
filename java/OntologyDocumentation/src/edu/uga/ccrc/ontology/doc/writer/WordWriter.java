package edu.uga.ccrc.ontology.doc.writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;

import com.hp.hpl.jena.rdf.model.Resource;

import edu.uga.ccrc.ontology.doc.data.OntologyClass;
import edu.uga.ccrc.ontology.doc.data.OntologyClassProperties;
import edu.uga.ccrc.ontology.doc.data.OntologyInstance;
import edu.uga.ccrc.ontology.doc.data.OntologyPredicate;
import edu.uga.ccrc.ontology.doc.data.OntologyThing;
import edu.uga.ccrc.ontology.doc.util.ComparatorPredicates;
import edu.uga.ccrc.ontology.doc.util.ComperatorOntologyClasses;
import edu.uga.ccrc.ontology.doc.util.PrefixGenerator;

public class WordWriter implements IWriter 
{
    private XWPFDocument m_document = null;
    private String m_outputFileName = null;

    public WordWriter(String a_fileName, String a_styleTemplate, String a_outputFileName) throws FileNotFoundException, IOException, XmlException
    {
        if ( a_fileName != null )
        {
            this.m_document = new XWPFDocument(new FileInputStream(new File(a_fileName)));
            XWPFDocument t_document = new XWPFDocument(new FileInputStream(new File(a_styleTemplate)));

            XWPFStyles t_styles = this.m_document.createStyles();
            t_styles.setStyles(t_document.getStyle());
        }
        else
        {
            this.m_document = new XWPFDocument();
        }
        this.m_outputFileName = a_outputFileName;
    }

    public void close() throws IOException
    {
        FileOutputStream t_outStream = new FileOutputStream(this.m_outputFileName);
        this.m_document.write(t_outStream);
        t_outStream.close();
    }

    public void writeNameSpaces(String a_text, HashMap<String, String> a_namespaces)
    {
        this.writeHeadlineLevel("Namespaces", 1);
        XWPFParagraph t_paragraphOne = this.m_document.createParagraph();
        t_paragraphOne.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun t_paragraphOneRunOne = t_paragraphOne.createRun();
        t_paragraphOneRunOne.setText(a_text);

        XWPFTable t_table = this.createTable();
        XWPFTableRow t_row = t_table.getRow(0);
        this.tableHeadlineCell(t_row.getCell(0),"Prefix");
        this.tableHeadlineCell(t_row.addNewTableCell(),"URI");

        for (String t_uri : a_namespaces.keySet())
        {
            t_row = t_table.createRow();
            this.tableCell(t_row.getCell(0),a_namespaces.get(t_uri));
            this.tableCell(t_row.getCell(1),t_uri);
        }
    }

    private void tableCell(XWPFTableCell a_cell, String a_text)
    {
        List<XWPFParagraph> t_paragraphCell = a_cell.getParagraphs();
        for (XWPFParagraph t_xwpfParagraph : t_paragraphCell)
        {
            t_xwpfParagraph.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun runCell = t_xwpfParagraph.createRun();
            runCell.setText(a_text);
        }
    }

    private void tableHeadlineCell(XWPFTableCell a_cell, String a_text)
    {
        a_cell.setColor("C8C8C8");
        List<XWPFParagraph> t_paragraphCell = a_cell.getParagraphs();
        for (XWPFParagraph t_xwpfParagraph : t_paragraphCell)
        {
            t_xwpfParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun runCell = t_xwpfParagraph.createRun();
            runCell.setBold(true);
            runCell.setText(a_text);
        }
    }

    private XWPFTable createTable()
    {
        return this.m_document.createTable();
    }

    public void writeHeadline(String a_text)
    {
        XWPFParagraph t_paragraphOne = this.m_document.getLastParagraph();
        t_paragraphOne.setAlignment(ParagraphAlignment.CENTER);
        t_paragraphOne.setStyle("Title");
        XWPFRun paragraphOneRunOne = t_paragraphOne.createRun();
        paragraphOneRunOne.setText(a_text);
    }

    private void writeClass(OntologyClass a_ontologyClass, PrefixGenerator a_prefixGen)
    {
        OntologyClassProperties t_properties = a_ontologyClass.getProperties();
        Collections.sort(t_properties.getPredicates(),new ComparatorPredicates(a_prefixGen));
        this.writeHeadlineLevel(a_ontologyClass.getResource(),a_ontologyClass.getLevel(), a_prefixGen, t_properties.getLabel());
        // Description
        if ( t_properties.getDescription() != null && t_properties.getDescription().trim().length() != 0 )
        {
            XWPFParagraph t_paragraphOne = this.m_document.createParagraph();
            t_paragraphOne.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun t_paragraphOneRunOne = t_paragraphOne.createRun();
            t_paragraphOneRunOne.setText(t_properties.getDescription());
        }
        // URI
        this.writeURI(a_ontologyClass.getResource().getURI());
        // parentClass
        if ( t_properties.getParentClass().size() != 0 )
        {
            this.writeParentClass(t_properties,a_prefixGen);
        }
        // predicates
        if ( t_properties.getPredicates().size() != 0 )
        {
            this.writePredicates(t_properties,a_prefixGen);
        }
        // instances
        if ( t_properties.getInstances().size() != 0 )
        {
            this.writeInstances(t_properties,a_prefixGen);
        }
        List<OntologyClass> t_classes = a_ontologyClass.getChildClasses();
        this.writeClasses(t_classes, a_prefixGen);
    }

    private void writeInstances(OntologyClassProperties a_properties, PrefixGenerator a_prefixGen)
    {
        XWPFParagraph t_paragraphOne = this.m_document.createParagraph();
        t_paragraphOne.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun t_paragraphOneRunOne = t_paragraphOne.createRun();
        t_paragraphOneRunOne.setBold(true);
        t_paragraphOneRunOne.setText("Instances:");
        
        XWPFTable t_table = this.createTable();
        XWPFTableRow t_row = t_table.getRow(0);
        this.tableHeadlineCell(t_row.getCell(0),"URI");
        this.tableHeadlineCell(t_row.addNewTableCell(),"Label");
        this.tableHeadlineCell(t_row.addNewTableCell(),"Description");

        for (OntologyInstance t_instance : a_properties.getInstances())
        {
            t_row = t_table.createRow();
            this.tableCell(t_row.getCell(0), a_prefixGen.prefixName(t_instance.getResource()));
            this.tableCell(t_row.getCell(1),t_instance.getLabel());
            this.tableCell(t_row.getCell(2),t_instance.getComment());
        }
    }

    private void writeParentClass(OntologyClassProperties a_properties, PrefixGenerator a_prefixGen)
    {
        XWPFParagraph t_paragraphOne = this.m_document.createParagraph();
        t_paragraphOne.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun t_paragraphOneRunOne = t_paragraphOne.createRun();
        t_paragraphOneRunOne.setBold(true);
        t_paragraphOneRunOne.setText("Superclass: ");
        t_paragraphOneRunOne = t_paragraphOne.createRun();
        boolean t_first = true;
        StringBuffer t_buffer = new StringBuffer("");
        for (OntologyThing t_resource : a_properties.getParentClass())
        {
            if ( t_first )
            {
                t_first = false;
                t_buffer.append(a_prefixGen.prefixName(t_resource.getResource()));
            }
            else
            {
                t_buffer.append(", " + a_prefixGen.prefixName(t_resource.getResource()));
            }
        }
        t_paragraphOneRunOne.setText(t_buffer.toString());
    }

    private void writeURI(String a_uri)
    {
        XWPFParagraph t_paragraphOne = this.m_document.createParagraph();
        t_paragraphOne.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun t_paragraphOneRunOne = t_paragraphOne.createRun();
        t_paragraphOneRunOne.setBold(true);
        t_paragraphOneRunOne.setText("URI: ");
        t_paragraphOneRunOne = t_paragraphOne.createRun();
        t_paragraphOneRunOne.setText(a_uri);
    }

    private void writePredicates(OntologyClassProperties a_properties, PrefixGenerator a_prefixGen)
    {
        XWPFParagraph t_paragraphOne = this.m_document.createParagraph();
        t_paragraphOne.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun t_paragraphOneRunOne = t_paragraphOne.createRun();
        t_paragraphOneRunOne.setBold(true);
        t_paragraphOneRunOne.setText("Predicates that have this class as domain:");
        
        XWPFTable t_table = this.createTable();
        XWPFTableRow t_row = t_table.getRow(0);
        this.tableHeadlineCell(t_row.getCell(0),"Predicate");
        this.tableHeadlineCell(t_row.addNewTableCell(),"Range");
        this.tableHeadlineCell(t_row.addNewTableCell(),"Functional");
        this.tableHeadlineCell(t_row.addNewTableCell(),"Description");

        for (OntologyPredicate t_predicate : a_properties.getPredicates())
        {
            t_row = t_table.createRow();
            this.tableCell(t_row.getCell(0), a_prefixGen.prefixName(t_predicate.getResource()));
            this.writeRangeCell(t_row.getCell(1),t_predicate.getRangeResource(),a_prefixGen);
            if ( t_predicate.isFunctional() )
            {
                this.tableCell(t_row.getCell(2), "yes");
            }
            this.tableCell(t_row.getCell(3),t_predicate.getComment());
        }
    }

    private void writeHeadlineLevel(Resource a_class, Integer a_level, PrefixGenerator a_prefixGen, String a_label)
    {
        if ( a_label == null )
        {
            this.writeHeadlineLevel(a_prefixGen.prefixName(a_class), a_level);
        }
        else
        {
            this.writeHeadlineLevel(a_label, a_level);
        }
    }

    private void writeHeadlineLevel(String a_class, Integer a_level)
    {
        this.m_document.createParagraph().createRun().addBreak();
        XWPFParagraph t_paragraphOne = this.m_document.createParagraph();
        t_paragraphOne.setAlignment(ParagraphAlignment.LEFT);
        t_paragraphOne.setStyle("Heading" + a_level.toString());
        XWPFRun paragraphOneRunOne = t_paragraphOne.createRun();
        paragraphOneRunOne.setText(a_class);
    }
    
    private void writeRangeCell(XWPFTableCell a_cell, List<OntologyThing> a_ranges, PrefixGenerator a_prefixGen)
    {
        List<XWPFParagraph> t_paragraphCell = a_cell.getParagraphs();
        for (XWPFParagraph t_xwpfParagraph : t_paragraphCell)
        {
            t_xwpfParagraph.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun t_runCell = t_xwpfParagraph.createRun();
            for (OntologyThing t_range : a_ranges)
            {
                t_runCell.setText(a_prefixGen.prefixName(t_range.getResource()));
            }
        }
    }

    public void writeClasses(List<OntologyClass> a_classes, PrefixGenerator a_prefixGen)
    {
        Collections.sort(a_classes,new ComperatorOntologyClasses());
        for (OntologyClass t_ontologyClass : a_classes)
        {
            this.writeClass(t_ontologyClass, a_prefixGen);
        }
    }

}
