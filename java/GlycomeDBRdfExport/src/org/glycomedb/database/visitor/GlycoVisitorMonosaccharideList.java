package org.glycomedb.database.visitor;

import java.util.HashMap;

import org.eurocarbdb.MolecularFramework.sugar.GlycoEdge;
import org.eurocarbdb.MolecularFramework.sugar.GlycoNode;
import org.eurocarbdb.MolecularFramework.sugar.Linkage;
import org.eurocarbdb.MolecularFramework.sugar.Monosaccharide;
import org.eurocarbdb.MolecularFramework.sugar.NonMonosaccharide;
import org.eurocarbdb.MolecularFramework.sugar.Substituent;
import org.eurocarbdb.MolecularFramework.sugar.SubstituentType;
import org.eurocarbdb.MolecularFramework.sugar.Sugar;
import org.eurocarbdb.MolecularFramework.sugar.SugarUnitAlternative;
import org.eurocarbdb.MolecularFramework.sugar.SugarUnitCyclic;
import org.eurocarbdb.MolecularFramework.sugar.SugarUnitRepeat;
import org.eurocarbdb.MolecularFramework.sugar.UnderdeterminedSubTree;
import org.eurocarbdb.MolecularFramework.sugar.UnvalidatedGlycoNode;
import org.eurocarbdb.MolecularFramework.util.traverser.GlycoTraverser;
import org.eurocarbdb.MolecularFramework.util.traverser.GlycoTraverserNodes;
import org.eurocarbdb.MolecularFramework.util.visitor.GlycoVisitor;
import org.eurocarbdb.MolecularFramework.util.visitor.GlycoVisitorException;
import org.eurocarbdb.MolecularFramework.util.visitor.GlycoVisitorNodeType;

public class GlycoVisitorMonosaccharideList implements GlycoVisitor
{
    private HashMap<String, MonosaccharideComponent> m_hComponents = new HashMap<String, MonosaccharideComponent>();
    private HashMap<Substituent, Boolean> m_hSubstituents = new HashMap<Substituent, Boolean>();
    private Integer m_multiplier = 1;
    
    public Integer getMultiplier()
    {
        return m_multiplier;
    }

    public void setMultiplier(Integer a_multiplier)
    {
        m_multiplier = a_multiplier;
    }

    @Override
    public void clear()
    {
        this.m_hComponents.clear();
        this.m_hSubstituents.clear();
        this.m_multiplier = 1;
    }

    @Override
    public GlycoTraverser getTraverser(GlycoVisitor a_visitor) throws GlycoVisitorException
    {
        return new GlycoTraverserNodes(a_visitor);
    }

    @Override
    public void start(Sugar a_sugar) throws GlycoVisitorException
    {
        this.clear();
        GlycoTraverser t_traverser = this.getTraverser(this);
        t_traverser.traverseGraph(a_sugar);
        for (UnderdeterminedSubTree t_subtree : a_sugar.getUndeterminedSubTrees())
        {
            if ( t_subtree.getProbabilityLower() < 100 )
            {
                this.m_multiplier = null;
            }
            t_traverser.traverseGraph(t_subtree);
        }
    }


    @Override
    public void visit(SugarUnitRepeat a_repeat) throws GlycoVisitorException
    {
        Integer t_save = this.m_multiplier;
        if ( a_repeat.getMinRepeatCount() != a_repeat.getMaxRepeatCount() || a_repeat.getMinRepeatCount() == SugarUnitRepeat.UNKNOWN )
        {
            this.m_multiplier = null;
        }
        GlycoTraverser t_traverser = this.getTraverser(this);
        t_traverser.traverseGraph(a_repeat);
        for (UnderdeterminedSubTree t_subtree : a_repeat.getUndeterminedSubTrees())
        {
            if ( t_subtree.getProbabilityLower() < 100 )
            {
                this.m_multiplier = null;
            }
            t_traverser.traverseGraph(t_subtree);
        }
        if ( this.m_multiplier != null )
        {
            this.m_multiplier = t_save;
        }
    }

    @Override
    public void visit(NonMonosaccharide a_arg0) throws GlycoVisitorException
    {
        throw new GlycoVisitorException("NonMonosaccharides are not supported.");
    }

    @Override
    public void visit(SugarUnitCyclic a_arg0) throws GlycoVisitorException
    {
        // nothing to do
    }

    @Override
    public void visit(SugarUnitAlternative a_arg0) throws GlycoVisitorException
    {
        throw new GlycoVisitorException("SugarUnitAlternative are not supported.");
    }

    @Override
    public void visit(UnvalidatedGlycoNode a_arg0) throws GlycoVisitorException
    {
        throw new GlycoVisitorException("UnvalidatedGlycoNode are not supported.");
    }

    @Override
    public void visit(GlycoEdge a_arg0) throws GlycoVisitorException
    {
        // nothing to do
    }

    public HashMap<String, MonosaccharideComponent> getComponents()
    {
        return m_hComponents;
    }

    public void visit(Monosaccharide a_ms) throws GlycoVisitorException
    {
        String t_name = this.getMonosaccharideName(a_ms);
        MonosaccharideComponent t_components = this.m_hComponents.get(t_name);
        if ( t_components == null )
        {
            t_components = new MonosaccharideComponent();
            t_components.setMsdbString(t_name);
            this.m_hComponents.put(t_name, t_components);
        }
        t_components.addNumber(this.m_multiplier);
    }

    /**
     * der MsDB-Name fuer b-D-3,6-anhydro-Galp dann b-dgal-HEX-1:5|3,6:anhydro.
     * Pyruvat ist ein Substituent, b-D-Galp3,6py ist also b-dgal-HEX-1:5||(3o:1,6o:1)(x)-pyruvate
     * 
     * @param a_ms
     * @return
     * @throws GlycoVisitorException
     */
    private String getMonosaccharideName(Monosaccharide a_ms) throws GlycoVisitorException
    {
        String t_name = a_ms.getGlycoCTName();
        GlycoVisitorNodeType t_visitor = new GlycoVisitorNodeType();
        String t_substName = "";
        String t_special = "";
        for (GlycoNode t_child : a_ms.getChildNodes())
        {
            Substituent t_subst = t_visitor.getSubstituent(t_child);
            if ( t_subst != null )
            {
                if ( t_subst.getSubstituentType().equals(SubstituentType.ANHYDRO) )
                {
                    t_special += "|" + this.writeAnhydroPostion(t_subst.getParentEdge()) + ":anhydro";
                    this.m_hSubstituents.put(t_subst, Boolean.TRUE);
                }
                else if ( t_subst.getSubstituentType().equals(SubstituentType.PYRUVATE) || t_subst.getSubstituentType().equals(SubstituentType.R_PYRUVATE) 
                        || t_subst.getSubstituentType().equals(SubstituentType.S_PYRUVATE) || t_subst.getSubstituentType().equals(SubstituentType.X_LACTATE) 
                        || t_subst.getSubstituentType().equals(SubstituentType.R_LACTATE) || t_subst.getSubstituentType().equals(SubstituentType.S_LACTATE) )
                {
                    t_substName = t_substName + "|(" + this.getSubstLinkageMulti(t_subst.getParentEdge()) + ")" + t_subst.getSubstituentType().getName();
                    this.m_hSubstituents.put(t_subst, Boolean.TRUE);
                }
                else if ( !(t_subst.getSubstituentType().equals(SubstituentType.PHOSPHATE) || t_subst.getSubstituentType().equals(SubstituentType.TRIPHOSPHATE)
                        || t_subst.getSubstituentType().equals(SubstituentType.PYROPHOSPHATE) || t_subst.getChildNodes().size() > 0 ) )
                {
                    t_substName = t_substName + "|(" + this.getSubstLinkage(t_subst.getParentEdge()) + ")" + t_subst.getSubstituentType().getName();
                    this.m_hSubstituents.put(t_subst, Boolean.TRUE);
                }
                
            }
        }
        if (t_special.length() != 0 )
        {
            t_name = t_name + t_special;
        }
        if (t_substName.length() != 0 )
        {
            t_name = t_name + "|" + t_substName;
        }
        return t_name;
    }

    private String writeAnhydroPostion(GlycoEdge a_parentEdge) throws GlycoVisitorException
    {
        String t_result = "";
        boolean t_firstPostion = true;
        for (Linkage t_linkage : a_parentEdge.getGlycosidicLinkages() )
        {
            if ( t_firstPostion )
            {
                t_firstPostion = false;
            }
            else
            {
                t_result += ",";
            }
            if ( t_linkage.getParentLinkages().size() != 1 )
            {
                throw new GlycoVisitorException("Alternative linkage positions for anhydro are not supported.");
            }
            for (Integer t_pos : t_linkage.getParentLinkages())
            {
                if ( t_pos.equals(Linkage.UNKNOWN_POSITION) )
                {
                    t_result += "0";
                }
                else 
                {
                    t_result += t_pos.toString();
                }
            }
        }
        return t_result;
    }

    private String getSubstLinkage(GlycoEdge a_parentEdge) throws GlycoVisitorException
    {
        if ( a_parentEdge.getGlycosidicLinkages().size() != 1 )
        {
            throw new GlycoVisitorException("Multi-linked substitutents are not supported.");
        }
        StringBuffer t_buffer = new StringBuffer("");
        for (Linkage t_linkage : a_parentEdge.getGlycosidicLinkages())
        {
            // 5d:1
            if ( t_linkage.getChildLinkages().size() != 1 || t_linkage.getParentLinkages().size() != 1 )
            {
                throw new GlycoVisitorException("Alternative linkage positions for substituents are not supported.");
            }
            for (Integer t_pos : t_linkage.getParentLinkages())
            {
                if ( t_pos.equals(Linkage.UNKNOWN_POSITION) )
                {
                    t_buffer.append("0");
                }
                else 
                {
                    t_buffer.append(t_pos.toString());
                }
            }
            t_buffer.append(t_linkage.getParentLinkageType().getType());
            t_buffer.append(":");
            for (Integer t_pos : t_linkage.getChildLinkages())
            {
                if ( t_pos.equals(Linkage.UNKNOWN_POSITION) )
                {
                    t_buffer.append("0");
                }
                else 
                {
                    t_buffer.append(t_pos.toString());
                }
            }
        }
        return t_buffer.toString();
    }

    private String getSubstLinkageMulti(GlycoEdge a_parentEdge) throws GlycoVisitorException
    {
        StringBuffer t_buffer = new StringBuffer("");
        boolean t_first = true;
        for (Linkage t_linkage : a_parentEdge.getGlycosidicLinkages())
        {
            if ( t_first )
            {
                t_first=false;
            }
            else 
            {
                t_buffer.append(",");
            }
            // 5d:1
            if ( t_linkage.getChildLinkages().size() != 1 || t_linkage.getParentLinkages().size() != 1 )
            {
                throw new GlycoVisitorException("Alternative linkage positions for substituents are not supported.");
            }
            for (Integer t_pos : t_linkage.getParentLinkages())
            {
                if ( t_pos.equals(Linkage.UNKNOWN_POSITION) )
                {
                    t_buffer.append("0");
                }
                else 
                {
                    t_buffer.append(t_pos.toString());
                }
            }
            t_buffer.append(t_linkage.getParentLinkageType().getType());
            t_buffer.append(":");
            for (Integer t_pos : t_linkage.getChildLinkages())
            {
                if ( t_pos.equals(Linkage.UNKNOWN_POSITION) )
                {
                    t_buffer.append("0");
                }
                else 
                {
                    t_buffer.append(t_pos.toString());
                }
            }
        }
        return t_buffer.toString();
    }

    @Override
    public void visit(Substituent a_subst) throws GlycoVisitorException
    {
        if ( this.m_hSubstituents.get(a_subst) == null )
        {
            this.m_hSubstituents.put(a_subst, Boolean.TRUE);
            String t_name = a_subst.getSubstituentType().getName();
            MonosaccharideComponent t_component = this.m_hComponents.get(t_name);
            if ( t_component == null )
            {
                t_component = new MonosaccharideComponent();
                t_component.setMsdbString(t_name);
                this.m_hComponents.put(t_name, t_component);
            }
            t_component.addNumber(this.m_multiplier);
            throw new GlycoVisitorException("Uncollapsed substituent found:" + t_name);
        }
    }
}
