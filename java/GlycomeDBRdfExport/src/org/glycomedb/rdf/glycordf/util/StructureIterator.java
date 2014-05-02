package org.glycomedb.rdf.glycordf.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.glycomedb.database.data.Structure;

public class StructureIterator
{
    private List<Integer> m_list = new ArrayList<Integer>();
    private Iterator<Integer> m_iterator = null;
    private StructureProvider m_structureProvider = null;
    private Integer m_lastId = null;
    
    public StructureIterator(StructureProvider a_provider) throws IOException
    {
        this.m_structureProvider = a_provider;
        this.m_list = this.m_structureProvider.getAllStructureId();
        this.m_iterator = this.m_list.iterator();
    }
    
    public StructureIterator(Integer a_structureId, StructureProvider a_provider)
    {
        this.m_structureProvider = a_provider;
        this.m_list.add(a_structureId);
        this.m_iterator = this.m_list.iterator();
    }

    public StructureIterator(List<Integer> a_structureId, StructureProvider a_provider)
    {
        this.m_structureProvider = a_provider;
        for (Integer t_integer : a_structureId)
        {
            this.m_list.add(t_integer);
        }
        this.m_iterator = this.m_list.iterator();
    }
    
    public boolean hasNext()
    {
        return this.m_iterator.hasNext();
    }

    public Structure next() throws IOException
    {
        this.m_lastId = this.m_iterator.next();
        return this.m_structureProvider.getStructure(this.m_lastId);
    }

    public Integer getLastId()
    {
        return m_lastId;
    }

    public void remove()
    {
        throw new UnsupportedOperationException("This operation is not valid for StructureIterator");
    }
    
}
