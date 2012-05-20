package uk.co.mtford.alp.abduction.rules.visitor;

import uk.co.mtford.alp.abduction.Store;
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

    private RuleNode constructPositiveChildNode(IASystemInferableInstance newGoal, List<IASystemInferableInstance> newRestOfGoals,
                                                RuleNode previousNode) {
        RuleNode newRuleNode = newGoal.getPositiveRootRuleNode(previousNode.getAbductiveFramework(), newRestOfGoals);
        Map<VariableInstance, IUnifiableAtomInstance> assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>
                (previousNode.getAssignments());
        newRuleNode.setAssignments(assignments);
        newRuleNode.setStore(previousNode.getStore().shallowClone());
        return newRuleNode;
    }

    private RuleNode constructNegativeChildNode(IASystemInferableInstance newGoal, List<DenialInstance> nestedDenialList,
                                                List<IASystemInferableInstance> newRestOfGoals, RuleNode previousNode) {
        newRestOfGoals = new LinkedList<IASystemInferableInstance>(newRestOfGoals);
        RuleNode newRuleNode = newGoal.getNegativeRootRuleNode(previousNode.getAbductiveFramework(), nestedDenialList, newRestOfGoals);
        Map<VariableInstance, IUnifiableAtomInstance> assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>
                (previousNode.getAssignments());
        newRuleNode.setAssignments(assignments);
        newRuleNode.setStore(previousNode.getStore().shallowClone());
        return newRuleNode;
    }

    public void visit(A1RuleNode ruleNode) {

        // Generate child nodes where we attempt to unify with all matching abducibles.
        RuleNode childNode;
        LinkedList<RuleNode> childNodes = new LinkedList<RuleNode>();
        Store store = ruleNode.getStore();
        PredicateInstance goalAbducible = (PredicateInstance) ruleNode.getCurrentGoal();
        for (PredicateInstance storeAbducible : store.abducibles) {
            if (goalAbducible.isSameFunction(storeAbducible)) {
                List<IASystemInferableInstance> equalitySolved
                        = new LinkedList<IASystemInferableInstance>
                        (storeAbducible.equalitySolve(goalAbducible, ruleNode.getAssignments()));
                List<IASystemInferableInstance> newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
                IASystemInferableInstance newGoal = equalitySolved.remove(0);
                newRestOfGoals.addAll(equalitySolved);
                childNode = constructPositiveChildNode(newGoal, newRestOfGoals, ruleNode);
                childNodes.add(childNode);
            }
        }
        // Generate a single node where we check against constraints, check no possible unifiable already collected
        // abducibles and then collect the abducible.
        List<IASystemInferableInstance> newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        for (DenialInstance collectedDenial : store.denials) {
            PredicateInstance collectedDenialHead = (PredicateInstance) collectedDenial.getBody().get(0);
            if (collectedDenialHead.isSameFunction(goalAbducible)) {
                DenialInstance newDenial = (DenialInstance)
                        collectedDenial.deepClone(new HashMap<VariableInstance, IUnifiableAtomInstance>(ruleNode.getAssignments()));
                collectedDenialHead = (PredicateInstance) newDenial.getBody().remove(0);
                newDenial.getBody().addAll(goalAbducible.equalitySolve(collectedDenialHead, ruleNode.getAssignments()));
                newRestOfGoals.add(0, newDenial);
            }
        }
        for (PredicateInstance storeAbducible : store.abducibles) {
            if (goalAbducible.isSameFunction(storeAbducible)) {
                List<IEqualitySolverResultInstance> equalitySolved = goalAbducible.equalitySolve(storeAbducible, ruleNode.getAssignments());
                for (IEqualitySolverResultInstance result : equalitySolved) {
                    newRestOfGoals.add(new NegationInstance(result));
                }
            }
        }

        childNode = constructPositiveChildNode(newRestOfGoals.remove(0), newRestOfGoals, ruleNode);
        childNode.getStore().abducibles.add(goalAbducible);
        childNodes.add(childNode);

        ruleNode.getChildren().addAll(childNodes);

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
        List<IASystemInferableInstance> newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        IASystemInferableInstance newGoal = newRestOfGoals.remove(0);
        RuleNode newRuleNode = constructPositiveChildNode(newGoal, newRestOfGoals, ruleNode);
        ruleNode.getChildren().add(newRuleNode);
    }

    /**
     * Produces one child node where the true instance is removed from the denial.
     *
     * @param ruleNode
     */
    public void visit(NegativeTrueRuleNode ruleNode) {
        List<IASystemInferableInstance> newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        List<DenialInstance> newNestedDenialList = new LinkedList<DenialInstance>(ruleNode.getDenials());
        DenialInstance currentDenialInstance = newNestedDenialList.get(0);
        IASystemInferableInstance newGoal;
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
        List<IASystemInferableInstance> newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        List<DenialInstance> newNestedDenialList = new LinkedList<DenialInstance>(ruleNode.getDenials());
        RuleNode newChildNode;
        newNestedDenialList.remove(0);
        if (newNestedDenialList.isEmpty()) {
            IASystemInferableInstance newGoal = newRestOfGoals.remove(0);
            newChildNode = constructPositiveChildNode(newGoal, newRestOfGoals, ruleNode);
        } else {
            DenialInstance newDenial = newNestedDenialList.remove(0).shallowClone();
            IASystemInferableInstance newGoal = newDenial.getBody().remove(0);
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
