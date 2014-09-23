package edu.uga.ccrc.ontology.doc.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.uga.ccrc.ontology.doc.data.OntologyPredicate;
import edu.uga.ccrc.ontology.doc.data.OntologyThing;

public class ExcelHandler
{
    private Workbook m_workbook = null; 

    private Workbook openFile(String a_filename) throws IOException
    {
        Workbook t_workbook = null;
        try
        {
            t_workbook = new XSSFWorkbook(new FileInputStream(a_filename));
            return t_workbook;
        } 
        catch (FileNotFoundException e)
        {
            throw new IOException(e.getMessage(),e);
        }
        catch (Exception e) 
        {
        }
        try
        {
            t_workbook = new HSSFWorkbook(new FileInputStream(a_filename));
        } 
        catch (FileNotFoundException e)
        {
            throw new IOException(e.getMessage(),e);
        }
        return t_workbook;
    }

    public HashMap<String, List<OntologyPredicate>> readFile(String a_filename) throws IOException, InterruptedException
    {
        HashMap<String, List<OntologyPredicate>> t_information = new HashMap<String, List<OntologyPredicate>>();
        Model t_model = ModelFactory.createDefaultModel();
        this.m_workbook = this.openFile(a_filename);
        Sheet t_sheet = this.m_workbook.getSheetAt(0);
        // read all rows
        for (int t_i = 0; t_i <= t_sheet.getLastRowNum(); t_i++)
        {
            Row t_row = t_sheet.getRow(t_i);
            if ( t_row != null )
            {
                // class
                Cell t_cell = t_row.getCell(0);
                String t_class = t_cell.getStringCellValue();
                // URI
                t_cell = t_row.getCell(1);
                Resource t_resource = t_model.createResource(t_cell.getStringCellValue().trim());
                // range
                t_cell = t_row.getCell(2);
                Resource t_resourceRange = t_model.createResource(t_cell.getStringCellValue().trim());
                List<OntologyThing> t_range = new ArrayList<OntologyThing>();
                OntologyThing t_thing = new OntologyThing();
                t_thing.setResource(t_resourceRange);
                t_thing.setName(t_resourceRange.getLocalName());
                t_range.add(t_thing);
                // Description
                t_cell = t_row.getCell(3);
                String t_description = null;
                if ( t_cell != null )
                {
                    t_description = t_cell.getStringCellValue();
                }
                // functional
                t_cell = t_row.getCell(4);
                boolean t_functional = false;
                if ( t_cell != null )
                {
                    if ( t_cell.getStringCellValue().equals("yes") )
                    {
                        t_functional = true;
                    }
                }
                List<OntologyPredicate> t_list = t_information.get(t_class);
                if ( t_list == null )
                {
                    t_list = new ArrayList<OntologyPredicate>();
                    t_information.put(t_class, t_list);
                }
                OntologyPredicate t_predicate = new OntologyPredicate();
                t_predicate.setComment(t_description);
                t_predicate.setResource(t_resource);
                t_predicate.setRangeResource(t_range);
                t_predicate.setFunctional(t_functional);
                t_list.add(t_predicate);
            }
        }
        return t_information;
    }
}
