package uk.co.mtford.jalp.abduction.rules;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.visitor.RuleNodeVisitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 06:47
 * To change this template use File | Settings | File Templates.
 */
public class N1RuleNode extends PositiveRuleNode {
    public N1RuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> restOfGoals) {
        super(abductiveFramework, restOfGoals);
    }

    public N1RuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        super(abductiveFramework, restOfGoals, store, assignments);
    }

    protected N1RuleNode() {
        super();
    }

    @Override
    public RuleNode shallowClone() {
        N1RuleNode newRuleNode = new N1RuleNode();
        newRuleNode.children = new LinkedList<RuleNode>(children);
        newRuleNode.assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>(assignments);
        newRuleNode.abductiveFramework = abductiveFramework;
        newRuleNode.store = store.shallowClone();
        newRuleNode.goals = new LinkedList<IInferableInstance>(goals);
        return newRuleNode;
    }

    @Override
    public void acceptVisitor(RuleNodeVisitor v) {
        v.visit(this);
    }
}
