package org.glycomedb.rdf.glycordf.util;

import java.io.IOException;
import java.util.List;

import org.glycomedb.database.data.Structure;

public interface StructureProvider
{

    public List<Integer> getAllStructureId() throws IOException;

    public Structure getStructure(Integer a_integer) throws IOException;

}
