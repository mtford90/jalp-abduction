package uk.co.mtford.jalp.abduction.rules;

import com.sun.tools.corba.se.idl.constExpr.Equal;
import uk.co.mtford.jalp.JALPException;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
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
public class E2RuleNode extends NegativeRuleNode {
    public E2RuleNode(AbductiveFramework abductiveFramework, RuleNode parent,List<IInferableInstance> query,List<IInferableInstance> restOfGoals) {
        super(abductiveFramework, parent,query, restOfGoals);
        if (!(restOfGoals.get(0) instanceof DenialInstance)) { // Sanity check.
            throw new JALPException("Was expecting a denial at goal head of E2 rule node, but there isnt. Instead there is "+restOfGoals.get(0));
        }
        DenialInstance d = (DenialInstance) restOfGoals.get(0); // Sanity check.
        if (!(d.getBody().get(0) instanceof EqualityInstance)) {
            throw new JALPException("Was expecting an equality at head of denial but instead there is "+d.getBody().get(0));
        }
    }

    public E2RuleNode(AbductiveFramework abductiveFramework, RuleNode parent,List<IInferableInstance> query,List<IInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        super(abductiveFramework,parent,query,restOfGoals, store, assignments);
        if (!(restOfGoals.get(0) instanceof DenialInstance)) { // Sanity check.
            throw new JALPException("Was expecting a denial at goal head of E2 rule node, but there isnt. Instead there is "+restOfGoals.get(0));
        }
        DenialInstance d = (DenialInstance) restOfGoals.get(0);
        if (!(d.getBody().get(0) instanceof EqualityInstance)) {  // Sanity check.
            throw new JALPException("Was expecting an equality at head of denial but instead there is "+d.getBody().get(0));
        }
    }

    public E2RuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> restOfGoals) {
        super(abductiveFramework, query, restOfGoals);
    }

    public E2RuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        super(abductiveFramework, query, restOfGoals, store, assignments);
    }

    public E2RuleNode() {
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
        newRuleNode.query = query;
        newRuleNode.nodeMark = nodeMark;
        newRuleNode.parent = parent;

        return newRuleNode;
    }

    @Override
    public void acceptVisitor(RuleNodeVisitor v)  {
        v.visit(this);
    }
}
