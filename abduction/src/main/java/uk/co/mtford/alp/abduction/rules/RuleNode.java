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
 * Time: 06:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class RuleNode {

    public static enum NodeMark {
        FAILED_MARK,
        SUCCEEDED_MARK,
        UNEXPLORED_MARK
    }

    ;

    protected List<RuleNode> children; // Next states.
    protected NodeMark nodeMark; // Defines whether or not leaf node or search node.

    protected Map<VariableInstance, IUnifiableAtomInstance> assignments;  // Theta
    protected AbductiveFramework abductiveFramework; // (P,A,IC)
    protected Store store; // ST
    protected List<IASystemInferableInstance> nextGoals; // G - {currentGoal}
    protected IASystemInferableInstance currentGoal;

    public RuleNode(AbductiveFramework abductiveFramework, IASystemInferableInstance goal, List<IASystemInferableInstance> restOfGoals) {
        children = new LinkedList<RuleNode>();
        assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        nodeMark = nodeMark.UNEXPLORED_MARK;
        this.abductiveFramework = abductiveFramework;
        this.currentGoal = goal;
        this.nextGoals = restOfGoals;
        store = new Store();
    }

    public RuleNode(AbductiveFramework abductiveFramework, IASystemInferableInstance goal, List<IASystemInferableInstance> restOfGoals,
                    Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        children = new LinkedList<RuleNode>();
        this.assignments = assignments;
        this.store = store;
        this.abductiveFramework = abductiveFramework;
        this.currentGoal = goal;
        this.nextGoals = restOfGoals;
    }

    protected RuleNode() {
    } // For use whilst cloning.

    public List<RuleNode> getChildren() {
        return children;
    }

    public void setChildren(List<RuleNode> children) {
        this.children = children;
    }

    public Map<VariableInstance, IUnifiableAtomInstance> getAssignments() {
        return assignments;
    }

    public void setAssignments(Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        this.assignments = assignments;
    }

    public NodeMark getNodeMark() {
        return nodeMark;
    }

    public void setNodeMark(NodeMark nodeMark) {
        this.nodeMark = nodeMark;
    }

    public AbductiveFramework getAbductiveFramework() {
        return abductiveFramework;
    }

    public void setAbductiveFramework(AbductiveFramework abductiveFramework) {
        this.abductiveFramework = abductiveFramework;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<IASystemInferableInstance> getNextGoals() {
        return nextGoals;
    }

    public void setNextGoals(List<IASystemInferableInstance> nextGoals) {
        this.nextGoals = nextGoals;
    }

    public IASystemInferableInstance getCurrentGoal() {
        return currentGoal;
    }

    public void setCurrentGoal(IASystemInferableInstance currentGoal) {
        this.currentGoal = currentGoal;
    }

    public abstract RuleNode shallowClone(); // TODO: Use reflection instead?

    public abstract void acceptVisitor(RuleNodeVisitor v);

}
