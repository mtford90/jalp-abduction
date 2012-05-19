package uk.co.mtford.alp.abduction.rules;

import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.Store;
import uk.co.mtford.alp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.alp.abduction.logic.instance.IASystemInferable;
import uk.co.mtford.alp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.alp.abduction.logic.instance.VariableInstance;
import uk.co.mtford.alp.abduction.rules.visitor.RuleNodeVisitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 06:59
 * To change this template use File | Settings | File Templates.
 */
public class NegativeTrueRuleNode extends NegativeRuleNode {
    public NegativeTrueRuleNode(AbductiveFramework abductiveFramework, IASystemInferable goal, List<IASystemInferable> restOfGoals, List<DenialInstance> denial) {
        super(abductiveFramework, goal, restOfGoals, denial);
    }

    public NegativeTrueRuleNode(AbductiveFramework abductiveFramework, IASystemInferable goal, List<IASystemInferable> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments, List<DenialInstance> denial) {
        super(abductiveFramework, goal, restOfGoals, store, assignments, denial);
    }

    protected NegativeTrueRuleNode() {
    }

    @Override
    public RuleNode shallowClone() {
        NegativeTrueRuleNode newRuleNode = new NegativeTrueRuleNode();
        newRuleNode.children = new LinkedList<RuleNode>(children);
        newRuleNode.assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>(assignments);
        newRuleNode.currentGoal = currentGoal;
        newRuleNode.abductiveFramework = abductiveFramework;
        newRuleNode.store = store.shallowClone();
        newRuleNode.nextGoals = new LinkedList<IASystemInferable>(nextGoals);
        newRuleNode.denials = denials;
        return newRuleNode;
    }

    @Override
    public void acceptVisitor(RuleNodeVisitor v) {
        v.visit(this);
    }
}
