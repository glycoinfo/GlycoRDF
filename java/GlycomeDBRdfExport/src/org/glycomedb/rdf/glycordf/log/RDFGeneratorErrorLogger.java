package org.glycomedb.rdf.glycordf.log;

import java.util.ArrayList;
import java.util.List;

public class RDFGeneratorErrorLogger
{
    private List<RDFGeneratorError> m_errors = new ArrayList<RDFGeneratorError>();

    public void addError(RDFGeneratorError a_error) 
    {
        this.m_errors.add(a_error);
    }
    
    public List<RDFGeneratorError> getErrors()
    {
        return m_errors;
    }

    public void setErrors(List<RDFGeneratorError> errors)
    {
        m_errors = errors;
    }
}