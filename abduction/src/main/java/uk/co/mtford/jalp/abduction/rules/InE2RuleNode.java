package uk.co.mtford.jalp.abduction.rules;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
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
 * Date: 26/05/2012
 * Time: 20:13
 * To change this template use File | Settings | File Templates.
 */
public class InE2RuleNode extends NegativeRuleNode {

    public InE2RuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query,List<IInferableInstance> restOfGoals) {
        super(abductiveFramework,query, restOfGoals);
    }

    public InE2RuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query,List<IInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        super(abductiveFramework, query, restOfGoals, store, assignments);
    }

    private InE2RuleNode() {
        super();
    }

    @Override
    public RuleNode shallowClone() {
        E2RuleNode newRuleNode = new E2RuleNode();
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
