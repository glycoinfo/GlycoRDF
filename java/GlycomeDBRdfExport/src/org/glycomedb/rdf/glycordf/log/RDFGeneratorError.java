package org.glycomedb.rdf.glycordf.log;

public class RDFGeneratorError
{
    public static String PARSING_GLYCOCT = "parsing GlycoCT";
    public static String GLYCAN_TYPE = "glycan type";
    public static String COMPOSITION = "compostion";
    public static String FORMAT = "format";
    public static String REMOTE_ENTRY = "remote_entry";
    public static String SOURCE = "source";
    public static String INVALID_ID = "invalid id";
    public static String REFERENCE_COMPOUND = "reference compound";

    private String m_errorType = null;
    private String m_errorMessage = null;
    private String m_id = null;
    private Exception m_exception = null;
    
    public RDFGeneratorError()
    {
        super();
    }
    public RDFGeneratorError(String a_type, String a_message, String a_id, Exception a_exception)
    {
        super();
        this.m_errorType = a_type;
        this.m_errorMessage = a_message;
        this.m_id = a_id;
        this.m_exception = a_exception;
    }
    public String getErrorType()
    {
        return m_errorType;
    }
    public void setErrorType(String a_errorType)
    {
        m_errorType = a_errorType;
    }
    public String getErrorMessage()
    {
        return m_errorMessage;
    }
    public void setErrorMessage(String a_errorMessage)
    {
        m_errorMessage = a_errorMessage;
    }
    public String getId()
    {
        return m_id;
    }
    public void setId(String a_id)
    {
        m_id = a_id;
    }
    public Exception getException()
    {
        return m_exception;
    }
    public void setException(Exception exception)
    {
        m_exception = exception;
    }
}
