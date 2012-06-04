package uk.co.mtford.jalp.abduction.rules;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.DefinitionException;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.visitor.RuleNodeVisitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 20/05/2012
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class LeafRuleNode extends RuleNode {

    private static Logger LOGGER = Logger.getLogger(LeafRuleNode.class);

    public LeafRuleNode(AbductiveFramework framework, Store store, Map<VariableInstance,
            IUnifiableAtomInstance> assignments) {
        super(framework, null, new LinkedList<IInferableInstance>(), store, assignments);
    }

    protected LeafRuleNode() {
        super();
    }

    @Override
    public RuleNode shallowClone() {
        LeafRuleNode newRuleNode = new LeafRuleNode();
        newRuleNode.children = new LinkedList<RuleNode>(children);
        newRuleNode.assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>(assignments);
        newRuleNode.abductiveFramework = abductiveFramework;
        newRuleNode.store = store.shallowClone();
        newRuleNode.nextGoals = new LinkedList<IInferableInstance>(nextGoals);
        newRuleNode.constraintSolver = constraintSolver.shallowClone();

        return newRuleNode;
    }

    @Override
    public void acceptVisitor(RuleNodeVisitor v) throws DefinitionException {
        v.visit(this);
    }

    @Override
    public String toString() {
        String message =
                        "assignments = " + assignments + "\n\n"+
                        "delta = " + store.abducibles + "\n" +
                        "delta* = " + store.denials + "\n" +
                        "epsilon = " + store.equalities + "\n" +
                        "fd = " + store.constraints + "\n\n" +
                        "chocoFd = " + constraintSolver.getChocoConstraints() + "\n\n" +
                        "nodeType = " + this.getClass() + "\n" +
                        "nodeMark = " + this.getNodeMark() + "\n";
        return message;
    }

}
