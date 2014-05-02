package org.glycomedb.rdf.glycordf.data;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SubstituentType  extends MonosaccharideProperty 
{
    public static final SubstituentType substituent_type_fluoro = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_fluoro" , true);
    public static final SubstituentType substituent_type_thio = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_thio" , true);
    public static final SubstituentType substituent_type_s_methyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_s_methyl" , true);
    public static final SubstituentType substituent_type_glycolyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_glycolyl" , true);
    public static final SubstituentType substituent_type_trifluoroacetyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_trifluoroacetyl" , true);
    public static final SubstituentType substituent_type_n_trimethyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_n_trimethyl" , true);
    public static final SubstituentType substituent_type_n_dimethyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_n_dimethyl" , true);
    public static final SubstituentType substituent_type_n_alanine = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_n_alanine" , true);
    public static final SubstituentType substituent_type_iodo = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_iodo" , true);
    public static final SubstituentType substituent_type_chloro = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_chloro" , true);
    public static final SubstituentType substituent_type_s_pyruvate = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_s_pyruvate" , true);
    public static final SubstituentType substituent_type_imino = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_imino" , true);
    public static final SubstituentType substituent_type_n_trifluoroacetyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_n_trifluoroacetyl" , true);
    public static final SubstituentType substituent_type_r_lactate = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_r_lactate" , true);
    public static final SubstituentType substituent_type_sulfate = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_sulfate" , true);
    public static final SubstituentType substituent_type_n_formyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_n_formyl" , true);
    public static final SubstituentType substituent_type_n_sulfate = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_n_sulfate" , true);
    public static final SubstituentType substituent_type_ethanolamine = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_ethanolamine" , true);
    public static final SubstituentType substituent_type_x_lactate = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_x_lactate" , true);
    public static final SubstituentType substituent_type_n_succinate = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_n_succinate" , true);
    public static final SubstituentType substituent_type_ethyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_ethyl" , true);
    public static final SubstituentType substituent_type_n_methyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_n_methyl" , true);
    public static final SubstituentType substituent_type_telluro = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_telluro" , true);
    public static final SubstituentType substituent_type_nitrat = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_nitrat" , true);
    public static final SubstituentType substituent_type_amino = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_amino" , true);
    public static final SubstituentType substituent_type_s_lactate = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_s_lactate" , true);
    public static final SubstituentType substituent_type_n_glycolyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_n_glycolyl" , true);
    public static final SubstituentType substituent_type_acetyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_acetyl" , true);
    public static final SubstituentType substituent_type_hydroxymethyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_hydroxymethyl" , true);
    public static final SubstituentType substituent_type_seleno = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_seleno" , true);
    public static final SubstituentType substituent_type_formyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_formyl" , true);
    public static final SubstituentType substituent_type_x_pyruvate = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_x_pyruvate" , true);
    public static final SubstituentType substituent_type_n_ethyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_n_ethyl" , true);
    public static final SubstituentType substituent_type_n_acetyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_n_acetyl" , true);
    public static final SubstituentType substituent_type_r_pyruvate = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_r_pyruvate" , true);
    public static final SubstituentType substituent_type_methyl = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_methyl" , true);
    public static final SubstituentType substituent_type_bromo = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_bromo" , true);
    public static final SubstituentType substituent_type_phosphate = new SubstituentType( "http://purl.jp/bio/12/glyco/glycan#substituent_type_phosphate" , true);

    protected List<LinkageType> m_hasDefaultLinkageType = new ArrayList<LinkageType>();
    protected List<Integer> m_hasDefaultLinkingPosition2 = new ArrayList<Integer>();
    protected List<LinkageType> m_hasDefaultLinkageType2 = new ArrayList<LinkageType>();
    protected List<URI> m_hasDefaultLinkageBondorder = new ArrayList<URI>();
    protected List<URI> m_hasDefaultLinkageBondorder2 = new ArrayList<URI>();
    protected List<Integer> m_hasDefaultLinkingPosition = new ArrayList<Integer>();

    public SubstituentType(){}
    
    public SubstituentType(String a_uri)
    {
        this.setInstanceURI(a_uri);
    }

    public SubstituentType(String a_uri, boolean a_isInstance)
    {
        this.setInstanceURI(a_uri);
        this.setOntologyInstance(a_isInstance);
    }

    public String getInstanceURI()
    {
        return this.m_instanceURI;
    }

    public void setOntologyInstance(boolean a_isInstance)
    {
        this.m_ontologyInstance = a_isInstance;
    }

    public boolean isOntologyInstance()
    {
        return this.m_ontologyInstance;
    }

    public void setInstanceURI(String instanceURI)
    {
        this.m_instanceURI = instanceURI;
    }

    public List<LinkageType> getHasDefaultLinkageType()
    {
        return this.m_hasDefaultLinkageType;
    }

    public void setHasDefaultLinkageType(List<LinkageType> a_hasDefaultLinkageType)
    {
        this.m_hasDefaultLinkageType = a_hasDefaultLinkageType;
    }

    public void addHasDefaultLinkageType(LinkageType a_hasDefaultLinkageType)
    {
        this.m_hasDefaultLinkageType.add(a_hasDefaultLinkageType);
    }
    public List<Integer> getHasDefaultLinkingPosition2()
    {
        return this.m_hasDefaultLinkingPosition2;
    }

    public void setHasDefaultLinkingPosition2(List<Integer> a_hasDefaultLinkingPosition2)
    {
        this.m_hasDefaultLinkingPosition2 = a_hasDefaultLinkingPosition2;
    }

    public void addHasDefaultLinkingPosition2(Integer a_hasDefaultLinkingPosition2)
    {
        this.m_hasDefaultLinkingPosition2.add(a_hasDefaultLinkingPosition2);
    }
    public List<LinkageType> getHasDefaultLinkageType2()
    {
        return this.m_hasDefaultLinkageType2;
    }

    public void setHasDefaultLinkageType2(List<LinkageType> a_hasDefaultLinkageType2)
    {
        this.m_hasDefaultLinkageType2 = a_hasDefaultLinkageType2;
    }

    public void addHasDefaultLinkageType2(LinkageType a_hasDefaultLinkageType2)
    {
        this.m_hasDefaultLinkageType2.add(a_hasDefaultLinkageType2);
    }
    public List<URI> getHasDefaultLinkageBondorder()
    {
        return this.m_hasDefaultLinkageBondorder;
    }

    public void setHasDefaultLinkageBondorder(List<URI> a_hasDefaultLinkageBondorder)
    {
        this.m_hasDefaultLinkageBondorder = a_hasDefaultLinkageBondorder;
    }

    public void addHasDefaultLinkageBondorder(URI a_hasDefaultLinkageBondorder)
    {
        this.m_hasDefaultLinkageBondorder.add(a_hasDefaultLinkageBondorder);
    }
    public List<URI> getHasDefaultLinkageBondorder2()
    {
        return this.m_hasDefaultLinkageBondorder2;
    }

    public void setHasDefaultLinkageBondorder2(List<URI> a_hasDefaultLinkageBondorder2)
    {
        this.m_hasDefaultLinkageBondorder2 = a_hasDefaultLinkageBondorder2;
    }

    public void addHasDefaultLinkageBondorder2(URI a_hasDefaultLinkageBondorder2)
    {
        this.m_hasDefaultLinkageBondorder2.add(a_hasDefaultLinkageBondorder2);
    }
    public List<Integer> getHasDefaultLinkingPosition()
    {
        return this.m_hasDefaultLinkingPosition;
    }

    public void setHasDefaultLinkingPosition(List<Integer> a_hasDefaultLinkingPosition)
    {
        this.m_hasDefaultLinkingPosition = a_hasDefaultLinkingPosition;
    }

    public void addHasDefaultLinkingPosition(Integer a_hasDefaultLinkingPosition)
    {
        this.m_hasDefaultLinkingPosition.add(a_hasDefaultLinkingPosition);
    }
}