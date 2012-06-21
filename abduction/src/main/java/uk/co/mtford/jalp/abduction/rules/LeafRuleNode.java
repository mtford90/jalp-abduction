package uk.co.mtford.jalp.abduction.rules;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.DefinitionException;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.visitor.AbstractRuleNodeVisitor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Rule node for which there are no more goals.
 */
public class LeafRuleNode extends PositiveRuleNode {

    private RuleNode parentNode;

    private static Logger LOGGER = Logger.getLogger(LeafRuleNode.class);

    public LeafRuleNode(AbductiveFramework framework, RuleNode parent,List<IInferableInstance> query,Store store, Map<VariableInstance,
            IUnifiableInstance> assignments) {
        super(framework, parent, query, new LinkedList<IInferableInstance>(), store, assignments);
        this.parentNode = parent;
    }

    protected LeafRuleNode() {
        super();
    }

    @Override
    public RuleNode shallowClone() {
        LeafRuleNode newRuleNode = new LeafRuleNode();
        newRuleNode.children = new LinkedList<RuleNode>(children);
        newRuleNode.query = query;
        newRuleNode.assignments = assignments;
        newRuleNode.abductiveFramework = abductiveFramework;
        newRuleNode.store = store.shallowClone();
        newRuleNode.goals = new LinkedList<IInferableInstance>(goals);
        newRuleNode.parentNode = parentNode;
        newRuleNode.nodeMark=nodeMark;
        newRuleNode.nodeMark = nodeMark;
        return newRuleNode;
    }

    @Override
    public void acceptVisitor(AbstractRuleNodeVisitor v) throws DefinitionException {
        v.visit(this);
    }

    public RuleNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(RuleNode parentNode) {
        this.parentNode = parentNode;
    }

    @Override
    public String toString() {
        String message =
                "query = " + query + "\n" +
                        "assignments = " + assignments + "\n\n"+
                        "delta = " + store.abducibles + "\n" +
                        "delta* = " + store.denials + "\n" +
                                "epsilon = " + store.equalities + " "+store.inequalities+"\n" +
                                "fd = " + store.constraints + "\n\n" +
                        "nodeType = " + this.getClass() + "\n" +
                        "nodeMark = " + this.getNodeMark() + "\n";
        return message;
    }

}
