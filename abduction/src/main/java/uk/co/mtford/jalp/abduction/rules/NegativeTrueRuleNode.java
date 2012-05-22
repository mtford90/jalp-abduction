package uk.co.mtford.jalp.abduction.rules;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IASystemInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.visitor.RuleNodeVisitor;

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
    public NegativeTrueRuleNode(AbductiveFramework abductiveFramework, IASystemInferableInstance goal, List<IASystemInferableInstance> restOfGoals, List<DenialInstance> denial) {
        super(abductiveFramework, goal, restOfGoals, denial);
    }

    public NegativeTrueRuleNode(AbductiveFramework abductiveFramework, IASystemInferableInstance goal, List<IASystemInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments, List<DenialInstance> denial) {
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
        newRuleNode.nextGoals = new LinkedList<IASystemInferableInstance>(nextGoals);
        newRuleNode.denials = denials;
        return newRuleNode;
    }

    @Override
    public void acceptVisitor(RuleNodeVisitor v) {
        v.visit(this);
    }
}
