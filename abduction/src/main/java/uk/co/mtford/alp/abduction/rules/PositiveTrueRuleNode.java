package uk.co.mtford.alp.abduction.rules;

import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.Store;
import uk.co.mtford.alp.abduction.logic.instance.IASystemInferableInstance;
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
 * Time: 06:46
 * To change this template use File | Settings | File Templates.
 */
public class PositiveTrueRuleNode extends PositiveRuleNode {
    public PositiveTrueRuleNode(AbductiveFramework abductiveFramework, IASystemInferableInstance goal, List<IASystemInferableInstance> restOfGoals) {
        super(abductiveFramework, goal, restOfGoals);
    }

    public PositiveTrueRuleNode(AbductiveFramework abductiveFramework, IASystemInferableInstance goal, List<IASystemInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        super(abductiveFramework, goal, restOfGoals, store, assignments);
    }

    protected PositiveTrueRuleNode() {
    }

    @Override
    public RuleNode shallowClone() {
        PositiveTrueRuleNode newRuleNode = new PositiveTrueRuleNode();
        newRuleNode.children = new LinkedList<RuleNode>(children);
        newRuleNode.assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>(assignments);
        newRuleNode.currentGoal = currentGoal;
        newRuleNode.abductiveFramework = abductiveFramework;
        newRuleNode.store = store.shallowClone();
        newRuleNode.nextGoals = new LinkedList<IASystemInferableInstance>(nextGoals);
        return newRuleNode;
    }

    @Override
    public void acceptVisitor(RuleNodeVisitor v) {
        v.visit(this);
    }
}
