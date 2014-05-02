package org.glycomedb.database.visitor;

import org.eurocarbdb.MolecularFramework.sugar.GlycoEdge;
import org.eurocarbdb.MolecularFramework.sugar.GlycoNode;
import org.eurocarbdb.MolecularFramework.sugar.Monosaccharide;
import org.eurocarbdb.MolecularFramework.sugar.NonMonosaccharide;
import org.eurocarbdb.MolecularFramework.sugar.Substituent;
import org.eurocarbdb.MolecularFramework.sugar.Sugar;
import org.eurocarbdb.MolecularFramework.sugar.SugarUnitAlternative;
import org.eurocarbdb.MolecularFramework.sugar.SugarUnitCyclic;
import org.eurocarbdb.MolecularFramework.sugar.SugarUnitRepeat;
import org.eurocarbdb.MolecularFramework.sugar.UnvalidatedGlycoNode;
import org.eurocarbdb.MolecularFramework.util.traverser.GlycoTraverser;
import org.eurocarbdb.MolecularFramework.util.traverser.GlycoTraverserNodes;
import org.eurocarbdb.MolecularFramework.util.visitor.GlycoVisitor;
import org.eurocarbdb.MolecularFramework.util.visitor.GlycoVisitorException;

public class GlycoVisitorGlycanType implements GlycoVisitor
{
    public static String Cyclic_Glycan = "Cyclic Glycan";
    public static String N_glycan = "N-Glycan";
    public static String O_glycan = "O-Glycan";
    public static String Polysaccharide = "Polysaccharide";

    private String m_type = null;
    private Sugar m_sugar = null;

    public void clear()
    {
        this.setType(null);
    }

    public GlycoTraverser getTraverser(GlycoVisitor a_visitor) throws GlycoVisitorException
    {
        return new GlycoTraverserNodes(a_visitor);
    }

    public void start(Sugar a_sugar) throws GlycoVisitorException
    {
        this.m_sugar = a_sugar;
        GlycoTraverser t_traverser = this.getTraverser(this);
        t_traverser.traverseGraph(a_sugar);
    }

    public void visit(Monosaccharide a_arg0) throws GlycoVisitorException
    {
        // nothing to do
    }

    public void visit(NonMonosaccharide a_arg0) throws GlycoVisitorException
    {
        // nothing to do
    }

    public void visit(Substituent a_arg0) throws GlycoVisitorException
    {
        // nothing to do
    }

    public void visit(SugarUnitCyclic a_cyclic) throws GlycoVisitorException
    {
        this.setType(GlycoVisitorGlycanType.Cyclic_Glycan);
    }

    public void visit(SugarUnitAlternative a_arg0) throws GlycoVisitorException
    {
        // nothing to do
    }

    public void visit(UnvalidatedGlycoNode a_arg0) throws GlycoVisitorException
    {
        // nothing to do
    }

    public void visit(GlycoEdge a_arg0) throws GlycoVisitorException
    {
        // nothing to do
    }

    public void visit(SugarUnitRepeat a_repeat) throws GlycoVisitorException
    {
        try
        {
            if ( this.m_sugar.getNodes().size() == 1 )
            {
                for (GlycoNode t_node : this.m_sugar.getRootNodes())
                {
                    if ( t_node.equals(a_repeat) )
                    {
                        this.setType(GlycoVisitorGlycanType.Polysaccharide);
                    }
                }
            }
        } 
        catch (Exception t_e)
        {
            throw new GlycoVisitorException(t_e.getMessage(),t_e);
        }
    }

    public String getType()
    {
        return m_type;
    }

    public void setType(String type)
    {
        m_type = type;
    }

}
