package uk.co.mtford.alp.abduction.rules;

import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.Store;
import uk.co.mtford.alp.abduction.logic.instance.IASystemInferable;
import uk.co.mtford.alp.abduction.logic.instance.ILiteralInstance;
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
 * Time: 06:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class RuleNode {

    public static enum NodeMark {
        FAILED_MARK,
        SUCCEEDED_MARK,
        UNEXPLORED_MARK
    };

    protected List<RuleNode> children; // Next states.
    protected NodeMark nodeMark; // Defines whether or not leaf node or search node.

    protected Map<VariableInstance, ILiteralInstance> assignments;  // Theta
    protected AbductiveFramework abductiveFramework; // (P,A,IC)
    protected Store store; // ST
    protected List<IASystemInferable> nextGoals; // G - {currentGoal}
    protected IASystemInferable currentGoal;

    public RuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        children = new LinkedList<RuleNode>();
        assignments = new HashMap<VariableInstance, ILiteralInstance>();
        nodeMark = nodeMark.UNEXPLORED_MARK;
        this.abductiveFramework = new AbductiveFramework();
        if (!goals.isEmpty()) currentGoal = goals.remove(0);
        this.nextGoals=goals;
        store = new Store();
    }

    public List<RuleNode> getChildren() {
        return children;
    }

    public void setChildren(List<RuleNode> children) {
        this.children = children;
    }

    public Map<VariableInstance, ILiteralInstance> getAssignments() {
        return assignments;
    }

    public void setAssignments(Map<VariableInstance, ILiteralInstance> assignments) {
        this.assignments = assignments;
    }

    public NodeMark getNodeMark() {
        return nodeMark;
    }

    public void setNodeMark(NodeMark nodeMark) {
        this.nodeMark = nodeMark;
    }

    public abstract void acceptVisitor(RuleNodeVisitor v);

}
