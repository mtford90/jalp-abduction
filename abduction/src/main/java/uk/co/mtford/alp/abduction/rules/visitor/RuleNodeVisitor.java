package uk.co.mtford.alp.abduction.rules.visitor;

import uk.co.mtford.alp.abduction.logic.instance.*;
import uk.co.mtford.alp.abduction.rules.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 06:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class RuleNodeVisitor {

    RuleNode currentRuleNode;

    public RuleNodeVisitor(RuleNode ruleNode) {
        currentRuleNode = ruleNode;
        ruleNode.acceptVisitor(this);
    }

    private RuleNode constructPositiveChildNode(IASystemInferable newGoal, List<IASystemInferable> newRestOfGoals,
                                                RuleNode previousNode) {
        RuleNode newRuleNode = newGoal.getPositiveRootRuleNode(previousNode.getAbductiveFramework(), newRestOfGoals);
        Map<VariableInstance, IUnifiableAtomInstance> assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>
                (previousNode.getAssignments());
        newRuleNode.setAssignments(assignments);
        newRuleNode.setStore(previousNode.getStore().shallowClone());
        return newRuleNode;
    }

    private RuleNode constructNegativeChildNode(IASystemInferable newGoal, List<DenialInstance> nestedDenialList,
                                                List<IASystemInferable> newRestOfGoals, RuleNode previousNode) {
        newRestOfGoals = new LinkedList<IASystemInferable>(newRestOfGoals);
        RuleNode newRuleNode = newGoal.getNegativeRootRuleNode(previousNode.getAbductiveFramework(), nestedDenialList, newRestOfGoals);
        Map<VariableInstance, IUnifiableAtomInstance> assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>
                (previousNode.getAssignments());
        newRuleNode.setAssignments(assignments);
        newRuleNode.setStore(previousNode.getStore().shallowClone());
        return newRuleNode;
    }

    public void visit(A1RuleNode ruleNode) {
        // Generate child nodes where we attempt to unify with all matching abducibles.
        // Generate a single node where we check against constraints, check no possible unifiable already collected
        // abducibles and then collect the abducible.

    }

    public void visit(A2RuleNode ruleNode) {

    }

    public void visit(D1RuleNode ruleNode) {

    }

    public void visit(D2RuleNode ruleNode) {

    }

    public void visit(E1RuleNode ruleNode) {

    }

    public void visit(E2RuleNode ruleNode) {

    }

    public void visit(N1RuleNode ruleNode) {

    }

    public void visit(N2RuleNode ruleNode) {

    }

    /**
     * Produces one child node where the true instance is removed from the goal stack.
     *
     * @param ruleNode
     */
    public void visit(PositiveTrueRuleNode ruleNode) {
        List<IASystemInferable> newRestOfGoals = new LinkedList<IASystemInferable>(ruleNode.getNextGoals());
        IASystemInferable newGoal = newRestOfGoals.remove(0);
        RuleNode newRuleNode = constructPositiveChildNode(newGoal, newRestOfGoals, ruleNode);
        ruleNode.getChildren().add(newRuleNode);
    }

    /**
     * Produces one child node where the true instance is removed from the denial.
     *
     * @param ruleNode
     */
    public void visit(NegativeTrueRuleNode ruleNode) {
        List<IASystemInferable> newRestOfGoals = new LinkedList<IASystemInferable>(ruleNode.getNextGoals());
        List<DenialInstance> newNestedDenialList = new LinkedList<DenialInstance>(ruleNode.getDenials());
        DenialInstance currentDenialInstance = newNestedDenialList.get(0);
        IASystemInferable newGoal;
        if (!currentDenialInstance.getBody().isEmpty()) {
            newGoal = currentDenialInstance.getBody().remove(0);
        } else {
            newNestedDenialList.remove(0);
            newGoal = new FalseInstance();
        }
        RuleNode newChildNode = constructNegativeChildNode(newGoal, newNestedDenialList, newRestOfGoals, ruleNode);
        ruleNode.getChildren().add(newChildNode);
    }

    /**
     * Fails the node.
     *
     * @param ruleNode
     */
    public void visit(PositiveFalseRuleNode ruleNode) {
        ruleNode.setNodeMark(RuleNode.NodeMark.FAILED_MARK);
    }

    /**
     * Produces one child node whereby the denials succeeds.
     *
     * @param ruleNode
     */
    public void visit(NegativeFalseRuleNode ruleNode) {
        List<IASystemInferable> newRestOfGoals = new LinkedList<IASystemInferable>(ruleNode.getNextGoals());
        List<DenialInstance> newNestedDenialList = new LinkedList<DenialInstance>(ruleNode.getDenials());
        RuleNode newChildNode;
        newNestedDenialList.remove(0);
        if (newNestedDenialList.isEmpty()) {
            IASystemInferable newGoal = newRestOfGoals.remove(0);
            newChildNode = constructPositiveChildNode(newGoal, newRestOfGoals, ruleNode);
        } else {
            DenialInstance newDenial = newNestedDenialList.remove(0).shallowClone();
            IASystemInferable newGoal = newDenial.getBody().remove(0);
            newChildNode = constructNegativeChildNode(newGoal, newNestedDenialList, newRestOfGoals, ruleNode);
        }
        ruleNode.getChildren().add(ruleNode);
    }

    /**
     * Explores a new node based on exploration strategy defined in chooseNextNode. Then proceeds to expand that
     * node to generate it's children using the rules defined above.
     *
     * @return
     */
    public RuleNode stateRewrite() {
        RuleNode nextNode = chooseNextNode();
        if (nextNode == null) {  // Finished.
            return null;
        }
        if (nextNode.getNextGoals().isEmpty()) { // Success.
            nextNode.setNodeMark(RuleNode.NodeMark.SUCCEEDED_MARK);
            nextNode = chooseNextNode();
        }
        currentRuleNode = nextNode;
        nextNode.acceptVisitor(this); // Choose the correct rule to apply.
        return nextNode;
    }

    protected abstract RuleNode chooseNextNode();
}
