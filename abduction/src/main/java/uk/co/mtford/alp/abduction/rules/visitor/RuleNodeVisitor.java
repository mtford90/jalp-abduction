package uk.co.mtford.alp.abduction.rules.visitor;

import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.DefinitionException;
import uk.co.mtford.alp.abduction.Store;
import uk.co.mtford.alp.abduction.logic.instance.*;
import uk.co.mtford.alp.abduction.logic.instance.equality.EqualityInstance;
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

    private static final Logger LOGGER = Logger.getLogger(RuleNodeVisitor.class);

    protected RuleNode currentRuleNode;
    protected LinkedList<SuccessNode> successNodes;

    public RuleNodeVisitor(RuleNode ruleNode) throws DefinitionException {
        currentRuleNode = ruleNode;
        successNodes = new LinkedList<SuccessNode>();;
    }

    public LinkedList<SuccessNode> getSuccessNodes() {
        return successNodes;
    }

    private RuleNode constructPositiveChildNode(IASystemInferableInstance newGoal, List<IASystemInferableInstance> newRestOfGoals,
                                                RuleNode previousNode) {
        RuleNode newRuleNode;
        if (!(newGoal==null)) {
            newRuleNode = newGoal.getPositiveRootRuleNode(previousNode.getAbductiveFramework(), newRestOfGoals);
        }
        else {
            newRuleNode = new SuccessNode(previousNode.getAbductiveFramework(),newGoal,newRestOfGoals);
        }

        Map<VariableInstance, IUnifiableAtomInstance> assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>(previousNode.getAssignments());
        newRuleNode.setAssignments(assignments);
        newRuleNode.setStore(previousNode.getStore().shallowClone());
        return newRuleNode;
    }

    private RuleNode constructNegativeChildNode(IASystemInferableInstance newGoal, List<DenialInstance> nestedDenialList,
                                                List<IASystemInferableInstance> newRestOfGoals, RuleNode previousNode) {
        newRestOfGoals = new LinkedList<IASystemInferableInstance>(newRestOfGoals);
        RuleNode newRuleNode = newGoal.getNegativeRootRuleNode(previousNode.getAbductiveFramework(), nestedDenialList, newRestOfGoals);
        Map<VariableInstance, IUnifiableAtomInstance> assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>(previousNode.getAssignments());
        newRuleNode.setAssignments(assignments);
        newRuleNode.setStore(previousNode.getStore().shallowClone());
        return newRuleNode;
    }

    public void visit(A1RuleNode ruleNode) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying A1 to node.");
        // 1st Branch: Unify with an already collected abducible.
        LinkedList<RuleNode> childNodes = new LinkedList<RuleNode>();
        Store store = ruleNode.getStore();
        PredicateInstance goalAbducible = (PredicateInstance) ruleNode.getCurrentGoal();
        LinkedList<RuleNode> firstBranchChildNodes = getA1FirstBranch(ruleNode, store, goalAbducible);
        childNodes.addAll(0,firstBranchChildNodes);

        // Second branch: Add a new abducible. Check satisfies collected denials. Check not possible to unify with any existing.
        RuleNode secondBranchChildNode = getA1SecondBranch(ruleNode, store, goalAbducible);
        childNodes.add(secondBranchChildNode);
        if (LOGGER.isInfoEnabled()) LOGGER.info("A1 generated "+childNodes.size()+" new states.");
        ruleNode.getChildren().addAll(0,childNodes);
        ruleNode.setNodeMark(RuleNode.NodeMark.EXPANDED);
    }

    private LinkedList<RuleNode> getA1FirstBranch(A1RuleNode ruleNode, Store store, PredicateInstance goalAbducible) {
        LinkedList<RuleNode> childNodes = new LinkedList<RuleNode>();
        for (PredicateInstance storeAbducible : store.abducibles) {
            if (goalAbducible.isSameFunction(storeAbducible)) {
                List<IASystemInferableInstance> equalitySolved = new LinkedList<IASystemInferableInstance>(storeAbducible.equalitySolve(goalAbducible, ruleNode.getAssignments()));
                List<IASystemInferableInstance> newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
                IASystemInferableInstance newGoal = equalitySolved.remove(0);
                newRestOfGoals.addAll(0,equalitySolved);
                RuleNode childNode = constructPositiveChildNode(newGoal, newRestOfGoals, ruleNode);
                childNodes.add(childNode);
            }
        }
        return childNodes;
    }

    private RuleNode getA1SecondBranch(A1RuleNode ruleNode, Store store, PredicateInstance goalAbducible) {
        // Set up new child node and it's data structures.
        RuleNode childNode;
        List<IASystemInferableInstance> newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());



        // Check our new collected abducible doesn't violate any collected constraints.
        for (DenialInstance collectedDenial : store.denials) {
            PredicateInstance collectedDenialHead = (PredicateInstance) collectedDenial.getBody().get(0);
            if (collectedDenialHead.isSameFunction(goalAbducible)) {
                DenialInstance newDenial = (DenialInstance) collectedDenial.deepClone(new HashMap<VariableInstance, IUnifiableAtomInstance>(ruleNode.getAssignments()));
                collectedDenialHead = (PredicateInstance) newDenial.getBody().remove(0);
                newDenial.getBody().addAll(0,goalAbducible.equalitySolve(collectedDenialHead, ruleNode.getAssignments()));
                newRestOfGoals.add(0, newDenial);
            }
        }

        // Check our new collected abducible won't unify with any already collected abducibles.
        for (PredicateInstance storeAbducible : store.abducibles) {
            if (goalAbducible.isSameFunction(storeAbducible)) {
                List<IEqualitySolverResultInstance> equalitySolved = goalAbducible.equalitySolve(storeAbducible, ruleNode.getAssignments());
                for (IEqualitySolverResultInstance result : equalitySolved) {
                    newRestOfGoals.add(0,new NegationInstance(result));
                }
            }
        }

        IASystemInferableInstance newGoal = null;
        if (!newRestOfGoals.isEmpty()) newGoal = newRestOfGoals.remove(0);

        childNode = constructPositiveChildNode(newGoal, newRestOfGoals, ruleNode);
        childNode.getStore().abducibles.add(goalAbducible);

        return childNode;
    }

    public void visit(A2RuleNode ruleNode) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying A2 to node.");
        List<IASystemInferableInstance> restOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        List<DenialInstance> nestedDenialList = new LinkedList<DenialInstance>(ruleNode.getDenials());
        DenialInstance currentDenial = nestedDenialList.remove(0);
        PredicateInstance goalAbducible = (PredicateInstance) ruleNode.getCurrentGoal();
        currentDenial.getBody().add(0, goalAbducible);
        Store store = ruleNode.getStore();
        for (PredicateInstance storeAbducible : store.abducibles) {
            if (storeAbducible.isSameFunction(goalAbducible)) {
                DenialInstance newDenial = (DenialInstance) currentDenial.deepClone(new HashMap<VariableInstance, IUnifiableAtomInstance>(ruleNode.getAssignments()));
                PredicateInstance newDenialHead = (PredicateInstance) newDenial.getBody().remove(0);
                List<IEqualitySolverResultInstance> equalitySolved = newDenialHead.equalitySolve(storeAbducible, ruleNode.getAssignments());
                newDenial.getBody().addAll(0,equalitySolved);
                if (nestedDenialList.isEmpty()) {
                    restOfGoals.add(0, newDenial);
                } else {
                    nestedDenialList.get(0).getBody().add(0, newDenial);
                }
            }
        }
        RuleNode childNode;
        if (nestedDenialList.isEmpty()) {
            IASystemInferableInstance newGoal = null;
            if (!restOfGoals.isEmpty()) newGoal = restOfGoals.remove(0);
            childNode = constructPositiveChildNode(newGoal, restOfGoals, ruleNode);
        } else {
            IASystemInferableInstance newGoal = nestedDenialList.get(0).getBody().remove(0);
            childNode = constructNegativeChildNode(newGoal, nestedDenialList, restOfGoals, ruleNode);
        }
        currentDenial = currentDenial.shallowClone();
        childNode.getStore().denials.add(currentDenial);
        ruleNode.getChildren().add(childNode);
        ruleNode.setNodeMark(RuleNode.NodeMark.EXPANDED);
    }

    public void visit(D1RuleNode ruleNode) throws DefinitionException {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying D1 to node.");
        PredicateInstance definedPredicate = (PredicateInstance) ruleNode.getCurrentGoal();
        List<List<IASystemInferableInstance>> possibleUnfolds = ruleNode.getAbductiveFramework().unfoldDefinitions(definedPredicate);
        LinkedList<RuleNode> childNodes = new LinkedList<RuleNode>();
        for (List<IASystemInferableInstance> possibleUnfold : possibleUnfolds) {
            List<IASystemInferableInstance> restOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
            restOfGoals.addAll(0,possibleUnfold);
            IASystemInferableInstance newGoal = restOfGoals.remove(0);
            RuleNode childNode = constructPositiveChildNode(newGoal, restOfGoals, ruleNode);
            childNodes.add(childNode);
        }
        ruleNode.getChildren().addAll(0,childNodes);
        if (LOGGER.isInfoEnabled()) LOGGER.info("D1 generated "+childNodes.size()+" new states.");
        ruleNode.setNodeMark(RuleNode.NodeMark.EXPANDED);
    }

    public void visit(D2RuleNode ruleNode) throws DefinitionException {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying D2 to node.");
        PredicateInstance definedPredicate = (PredicateInstance) ruleNode.getCurrentGoal();
        List<List<IASystemInferableInstance>> possibleUnfolds = ruleNode.getAbductiveFramework().unfoldDefinitions(definedPredicate);
        LinkedList<RuleNode> childNodes = new LinkedList<RuleNode>();
        List<IASystemInferableInstance> currentRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        PredicateInstance currentGoal = (PredicateInstance) ruleNode.getCurrentGoal();
        List<DenialInstance> nestedDenialInstances = new LinkedList<DenialInstance>(ruleNode.getDenials());
        DenialInstance currentDenial = nestedDenialInstances.remove(0);
        List<IASystemInferableInstance> newGoals = new LinkedList<IASystemInferableInstance>();
        for (List<IASystemInferableInstance> possibleUnfold : possibleUnfolds) {
            for (IASystemInferableInstance inferable:possibleUnfold) {
                currentDenial.getBody().add(inferable);
            }
            DenialInstance newDenial = (DenialInstance) currentDenial.deepClone(new HashMap<VariableInstance, IUnifiableAtomInstance>(ruleNode.getAssignments()));
            newGoals.add(newDenial);
            for (int i=0;i<possibleUnfold.size();i++) {
                currentDenial.getBody().remove(0);
            }
        }
        RuleNode childNode;
        if (nestedDenialInstances.isEmpty()) {
            currentRestOfGoals.addAll(0,newGoals);
            IASystemInferableInstance newGoal = currentRestOfGoals.remove(0);
            childNode = constructPositiveChildNode(newGoal,currentRestOfGoals,ruleNode);
        }
        else {
            IASystemInferableInstance newGoal = newGoals.remove(0);
            nestedDenialInstances.get(0).getBody().addAll(0,newGoals);
            childNode = constructNegativeChildNode(newGoal,nestedDenialInstances,currentRestOfGoals,ruleNode);
        }
        ruleNode.getChildren().add(childNode);
        ruleNode.setNodeMark(RuleNode.NodeMark.EXPANDED);
    }

    public void visit(E1RuleNode ruleNode) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying E1 to node.");
        List<IASystemInferableInstance> restOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        Map<VariableInstance, IUnifiableAtomInstance> newAssignments = new HashMap<VariableInstance, IUnifiableAtomInstance>(ruleNode.getAssignments());
        EqualityInstance currentGoal = (EqualityInstance) ruleNode.getCurrentGoal();
        List<IEqualitySolverResultInstance> equalitySolved = currentGoal.getRight().equalitySolve(currentGoal.getLeft(), newAssignments);
        restOfGoals.addAll(0,equalitySolved);
        IASystemInferableInstance newGoalNode = null;
        if (!restOfGoals.isEmpty()) newGoalNode = restOfGoals.remove(0);
        RuleNode childNode = constructPositiveChildNode(newGoalNode, restOfGoals, ruleNode);
        childNode.setAssignments(newAssignments);
        childNode.getStore().equalities.add(currentGoal);
        ruleNode.getChildren().add(childNode);
        ruleNode.setNodeMark(RuleNode.NodeMark.EXPANDED);
    }

    public void visit(E2RuleNode ruleNode) { // TODO instanceof's are gross...
        // Set up for collection of child nodes.
        RuleNode childNode = null;
        List<RuleNode> childNodes = new LinkedList<RuleNode>();
        IASystemInferableInstance newGoal;

        // Set up new data structures for the new node.
        List<IASystemInferableInstance> newRestOfGoals
                = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        Map<VariableInstance, IUnifiableAtomInstance> newAssignments
                = new HashMap<VariableInstance, IUnifiableAtomInstance>(ruleNode.getAssignments());
        List<DenialInstance> newNestedDenialList
                = new LinkedList<DenialInstance>(ruleNode.getDenials());
        DenialInstance newCurrentDenial
                = newNestedDenialList.remove(0).shallowClone();

        // Obtain current goal.
        EqualityInstance currentGoal = (EqualityInstance) ruleNode.getCurrentGoal();
        IUnifiableAtomInstance left = currentGoal.getLeft();
        IUnifiableAtomInstance right = currentGoal.getRight();

        // If the left unifiable is a variable we apply one of the two base cases.
        if (left instanceof VariableInstance) {
            // If both are variables, then we use special case E2c.
            if (right instanceof VariableInstance) {
                LOGGER.info("Applying E2c to node.");
                left.equalitySolve(right,newAssignments); // Y=Z
                if (newCurrentDenial.getBody().isEmpty()) {
                    if (newNestedDenialList.isEmpty()) {
                        childNode = constructPositiveChildNode(new FalseInstance(),newRestOfGoals,ruleNode);
                    }
                    else {
                        childNode = constructNegativeChildNode(new FalseInstance(),newNestedDenialList,newRestOfGoals,ruleNode);
                    }
                }
                else {
                    newNestedDenialList.add(0,newCurrentDenial);
                    newGoal = newCurrentDenial.getBody().remove(0);
                    childNode = constructNegativeChildNode(newGoal,newNestedDenialList,newRestOfGoals,ruleNode);
                }
                childNode.setAssignments(newAssignments);
                childNodes.add(0,childNode);
            }
            // Otherwise we apply E2b
            else {
                LOGGER.info("Applying E2b to node.");
             /*   // 1st Branch: Produce an inequality that succeeds the denial. Add to inequality store and remove denial.
                // TODO Don't really understand this branch.
                InequalityInstance inEqualityInstance = new InequalityInstance(currentGoal);
                if (newNestedDenialList.isEmpty()) {
                    newGoal = null;
                    if (!newRestOfGoals.isEmpty()) newGoal = newRestOfGoals.remove(0);
                    //childNode = constructPositiveChildNode(newGoal, newRestOfGoals,ruleNode);
                }
                else {
                    newCurrentDenial = newNestedDenialList.remove(0);
                    if (newCurrentDenial.getBody().isEmpty()) {
                        newGoal = new FalseInstance();
                        if (newNestedDenialList.isEmpty()) {
                            childNode = constructPositiveChildNode(newGoal,newRestOfGoals,ruleNode);
                        }
                        else {
                            childNode = constructNegativeChildNode(newGoal,newNestedDenialList,newRestOfGoals,ruleNode);
                        }
                    }
                }
                childNode.setAssignments( new HashMap<VariableInstance, IUnifiableAtomInstance>(newAssignments));
                childNode.getStore().equalities.add(inEqualityInstance);
                childNodes.add(0,childNode); */
                // 2nd Branch: Apply the assignment and find another way to disprove the denial.
                right.equalitySolve(left,newAssignments);
                if (newCurrentDenial.getBody().isEmpty()) {
                    if (newNestedDenialList.isEmpty()) {
                        childNode = constructPositiveChildNode(new FalseInstance(),newRestOfGoals, ruleNode);
                    }
                    else {
                        childNode = constructNegativeChildNode(new FalseInstance(),newNestedDenialList,newRestOfGoals,ruleNode);
                    }
                }
                else {
                    newGoal = newCurrentDenial.getBody().remove(0);
                    newNestedDenialList.add(0,newCurrentDenial);
                    childNode = constructNegativeChildNode(newGoal,newNestedDenialList,newRestOfGoals,ruleNode);
                }
                childNode.setAssignments(newAssignments);
                childNodes.add(0,childNode);
            }
        }

        // Otherwise we apply the general case to reduce to equational solved form.
        else {
            LOGGER.info("Applying E2 general case to node.");
            List<IEqualitySolverResultInstance> equalitySolvedResults = right.equalitySolve(left,newAssignments);
            newCurrentDenial.getBody().addAll(0,equalitySolvedResults);
            newNestedDenialList.add(newCurrentDenial);
            newGoal = newCurrentDenial.getBody().remove(0);
            childNode = constructNegativeChildNode(newGoal,newNestedDenialList,newRestOfGoals,ruleNode);
            childNode.setAssignments(newAssignments);
            childNodes.add(childNode);
        }

        ruleNode.getChildren().addAll(childNodes);
        ruleNode.setNodeMark(RuleNode.NodeMark.EXPANDED);
    }

    public void visit(N1RuleNode ruleNode) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying N1 to node.");
        List<IASystemInferableInstance> newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        NegationInstance goal = (NegationInstance) ruleNode.getCurrentGoal();
        DenialInstance denial = new DenialInstance(goal.getSubFormula());
        RuleNode childNode = constructPositiveChildNode(denial, newRestOfGoals, ruleNode);
        ruleNode.getChildren().add(childNode);
        ruleNode.setNodeMark(RuleNode.NodeMark.EXPANDED);
    }

    public void visit(N2RuleNode ruleNode) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying N2 to node.");
        RuleNode childNode;
        LinkedList<RuleNode> childNodes = new LinkedList<RuleNode>();
        List<IASystemInferableInstance> newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        NegationInstance goal = (NegationInstance) ruleNode.getCurrentGoal();
        List<DenialInstance> newNestedDenialList = new LinkedList<DenialInstance>(ruleNode.getDenials());
        DenialInstance currentDenial = newNestedDenialList.remove(0).shallowClone();
        if (newNestedDenialList.isEmpty()) {
            childNode = constructPositiveChildNode(goal.getSubFormula(), newRestOfGoals, ruleNode);

        } else {
            childNode = constructNegativeChildNode(goal.getSubFormula(), newNestedDenialList, newRestOfGoals, ruleNode);
        }
        childNodes.add(childNode);
        // OR
        newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        if (newNestedDenialList.isEmpty()) {
            newRestOfGoals.add(0, currentDenial);
            childNode = constructPositiveChildNode(goal, newRestOfGoals, ruleNode);
        } else {
            DenialInstance nestedDenial = newNestedDenialList.remove(0).shallowClone();
            nestedDenial.getBody().add(0, currentDenial);
            nestedDenial.getBody().add(0, goal);
            newNestedDenialList.add(0, nestedDenial);
            childNode = constructNegativeChildNode(goal, newNestedDenialList, newRestOfGoals, ruleNode);
        }
        childNodes.add(childNode);
        ruleNode.getChildren().addAll(0,childNodes);
        ruleNode.setNodeMark(RuleNode.NodeMark.EXPANDED);
    }

    /**
     * Produces one child node where the true instance is removed from the goal stack.
     *
     * @param ruleNode
     */
    public void visit(PositiveTrueRuleNode ruleNode)
    {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying truth conjunction rule to node.");
        List<IASystemInferableInstance> newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        IASystemInferableInstance newGoal = null;
        if (!newRestOfGoals.isEmpty()) newGoal = newRestOfGoals.remove(0);
        RuleNode newRuleNode = constructPositiveChildNode(newGoal, newRestOfGoals, ruleNode);
        ruleNode.getChildren().add(newRuleNode);
        ruleNode.setNodeMark(RuleNode.NodeMark.EXPANDED);
    }

    /**
     * Produces one child node where the true instance is removed from the denial.
     *
     * @param ruleNode
     */
    public void visit(NegativeTrueRuleNode ruleNode) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying truth denial conjunction rule to node.");
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
        RuleNode newChildNode = constructPositiveChildNode(newGoal, newRestOfGoals, ruleNode);
        ruleNode.getChildren().add(newChildNode);
        ruleNode.setNodeMark(RuleNode.NodeMark.EXPANDED);
    }

    /**
     * Fails the node.
     *
     * @param ruleNode
     */
    public void visit(PositiveFalseRuleNode ruleNode) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying false conjunction rule to node.");
        ruleNode.setNodeMark(RuleNode.NodeMark.FAILED);
    }

    /**
     * Produces one child node whereby the denials succeeds.
     *
     * @param ruleNode
     */
    public void visit(NegativeFalseRuleNode ruleNode) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying false denial conjunction rule to node.");
        List<IASystemInferableInstance> newRestOfGoals = new LinkedList<IASystemInferableInstance>(ruleNode.getNextGoals());
        List<DenialInstance> newNestedDenialList = new LinkedList<DenialInstance>(ruleNode.getDenials());
        RuleNode newChildNode;
        newNestedDenialList.remove(0);
        if (newNestedDenialList.isEmpty()) {
            IASystemInferableInstance newGoal = new TrueInstance();
            newChildNode = constructPositiveChildNode(newGoal, newRestOfGoals, ruleNode);
        } else {
            DenialInstance newDenial = newNestedDenialList.remove(0).shallowClone();
            IASystemInferableInstance newGoal = newDenial.getBody().remove(0);
            newChildNode = constructNegativeChildNode(newGoal, newNestedDenialList, newRestOfGoals, ruleNode);
        }
        ruleNode.getChildren().add(newChildNode);
        ruleNode.setNodeMark(RuleNode.NodeMark.EXPANDED);
    }

    public void visit(SuccessNode ruleNode) {
        successNodes.add(ruleNode);
        ruleNode.setNodeMark(RuleNode.NodeMark.SUCCEEDED);
    }

    public RuleNode stateRewrite() throws DefinitionException {
        currentRuleNode.acceptVisitor(this);
        if (currentRuleNode == null) {  // Finished.
            return null;
        }
        currentRuleNode = chooseNextNode();
        return currentRuleNode;
    }

    public RuleNode getCurrentRuleNode() {
        return currentRuleNode;
    }

    protected abstract RuleNode chooseNextNode();
}
