package uk.co.mtford.jalp.abduction.rules;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.DefinitionException;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
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
 * Date: 24/05/2012
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */
public class F1RuleNode extends PositiveRuleNode {

    public F1RuleNode(AbductiveFramework abductiveFramework, IInferableInstance goal, List<IInferableInstance> restOfGoals) {
        super(abductiveFramework, goal, restOfGoals);
    }

    public F1RuleNode(AbductiveFramework abductiveFramework, IInferableInstance goal, List<IInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        super(abductiveFramework, goal, restOfGoals, store, assignments);
    }

    protected  F1RuleNode() {
        super();
    }

    @Override
    public RuleNode shallowClone() {
        A2RuleNode newRuleNode = new A2RuleNode();
        newRuleNode.children = new LinkedList<RuleNode>(children);
        newRuleNode.assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>(assignments);
        newRuleNode.currentGoal = currentGoal;
        newRuleNode.abductiveFramework = abductiveFramework;
        newRuleNode.store = store.shallowClone();
        newRuleNode.nextGoals = new LinkedList<IInferableInstance>(nextGoals);
        return newRuleNode;
    }
    @Override
    public void acceptVisitor(RuleNodeVisitor v) throws DefinitionException {
        v.visit(this);
    }
}
