package uk.co.mtford.jalp.abduction.rules;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.visitor.AbstractRuleNodeVisitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Rule node for which inference rule E2c will next be applied. Equality as the next goal at head of a denial. One of the
 * variables is existentially quantified. The other is a universally quantified variable.
 */
public class E2cRuleNode extends NegativeRuleNode {
    public E2cRuleNode(AbductiveFramework abductiveFramework, RuleNode parent,List<IInferableInstance> query, List<IInferableInstance> restOfGoals) {
        super(abductiveFramework, parent,query, restOfGoals);
    }

    public E2cRuleNode(AbductiveFramework abductiveFramework, RuleNode parent,List<IInferableInstance> query, List<IInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableInstance> assignments) {
        super(abductiveFramework,parent,query,restOfGoals, store, assignments);
    }

    public E2cRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> restOfGoals) {
        super(abductiveFramework, query, restOfGoals);
    }

    public E2cRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableInstance> assignments) {
        super(abductiveFramework, query, restOfGoals, store, assignments);
    }

    protected E2cRuleNode() {
        super();
    }

    @Override
    public RuleNode shallowClone() {
        E2cRuleNode newRuleNode = new E2cRuleNode();
        newRuleNode.children = new LinkedList<RuleNode>(children);
        newRuleNode.assignments = new HashMap<VariableInstance, IUnifiableInstance>(assignments);
        newRuleNode.abductiveFramework = abductiveFramework;
        newRuleNode.store = store.shallowClone();
        newRuleNode.goals = new LinkedList<IInferableInstance>(goals);
        newRuleNode.query = query;
        newRuleNode.nodeMark = nodeMark;
        return newRuleNode;
    }

    @Override
    public void acceptVisitor(AbstractRuleNodeVisitor v)  {
        v.visit(this);
    }
}
