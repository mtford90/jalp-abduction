package uk.co.mtford.jalp.abduction.rules.visitor;

import org.apache.log4j.Logger;
import org.javatuples.Pair;
import uk.co.mtford.jalp.JALPException;
import uk.co.mtford.jalp.abduction.DefinitionException;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * An efficient implementation of rule node visitor. Doesn't preserve the derivation tree. Don't use this if need to
 * visualize or interact with derivation tree afterwards as it will be incorrect.
 */
public class EfficientRuleNodeVisitor extends AbstractRuleNodeVisitor {

    private static Logger LOGGER = Logger.getLogger(EfficientRuleNodeVisitor.class);


    public EfficientRuleNodeVisitor() {
    }

    @Override
    protected RuleNode constructChildNode(List<IInferableInstance> newGoals, RuleNode previousNode) {
        RuleNode newRuleNode;
        if (!(newGoals.isEmpty())) {
            newRuleNode = newGoals.get(0).getPositiveRootRuleNode(previousNode.getAbductiveFramework(), new LinkedList<IInferableInstance>(previousNode.getQuery()), newGoals);
            newRuleNode.setStore(previousNode.getStore().shallowClone());
            newRuleNode.setAssignments(new HashMap<VariableInstance, IUnifiableInstance>(previousNode.getAssignments()));
        }
        else {
            newRuleNode = new LeafRuleNode(previousNode.getAbductiveFramework(),previousNode,new LinkedList<IInferableInstance>(previousNode.getQuery()),previousNode.getStore().shallowClone(),new HashMap<VariableInstance, IUnifiableInstance>(previousNode.getAssignments()));
        }

        return newRuleNode;
    }

    protected RuleNode constructChildNodeWithoutClone(List<IInferableInstance> newGoals, RuleNode previousNode) {
        RuleNode newRuleNode;
        if (!(newGoals.isEmpty())) {
            newRuleNode = newGoals.get(0).getPositiveRootRuleNode(previousNode.getAbductiveFramework(), previousNode.getQuery(), newGoals);
            newRuleNode.setStore(previousNode.getStore());
            newRuleNode.setAssignments(previousNode.getAssignments());
        }
        else {
            newRuleNode = new LeafRuleNode(previousNode.getAbductiveFramework(),previousNode,new LinkedList<IInferableInstance>(previousNode.getQuery()),previousNode.getStore().shallowClone(),new HashMap<VariableInstance, IUnifiableInstance>(previousNode.getAssignments()));
        }

        return newRuleNode;
    }

    @Override
    public void visit(A2RuleNode ruleNode) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying A2 to node.");

        // Set up new child nodes data structures.
        RuleNode childNode;
        List<IInferableInstance> newGoals = ruleNode.getGoals();
        DenialInstance currentGoal = (DenialInstance) newGoals.remove(0);
        PredicateInstance abducibleDenialHead = (PredicateInstance) currentGoal.getBody().remove(0);

        // Check that the new constraint we are creating isn't instantly violated by an already collected abducible.
        Store store = ruleNode.getStore();
        List<DenialInstance> newDenials = new LinkedList<DenialInstance>();
        for (PredicateInstance storeAbducible : store.abducibles) {
            if (storeAbducible.isSameFunction(abducibleDenialHead)) {
                HashMap<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>(ruleNode.getAssignments());
                DenialInstance newDenial = (DenialInstance) currentGoal.shallowClone();
                List<EqualityInstance> equalitySolved = storeAbducible.reduce(abducibleDenialHead);
                newDenial.getBody().addAll(0,equalitySolved);
                newDenials.add(newDenial);
            }
        }

        newGoals.addAll(0,newDenials);
        childNode = constructChildNodeWithoutClone(newGoals, ruleNode);

        currentGoal.getBody().add(0,abducibleDenialHead);
        childNode.getStore().denials.add(currentGoal);

        expandNode(ruleNode,childNode);
    }

    @Override
    public void visit(D1RuleNode ruleNode) throws DefinitionException {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying D1 to node.");
        List<IInferableInstance> newGoals = new LinkedList<IInferableInstance>(ruleNode.getGoals());
        PredicateInstance definedPredicate = (PredicateInstance) newGoals.remove(0);
        List<List<IInferableInstance>> possibleUnfolds = ruleNode.getAbductiveFramework().unfoldDefinitions(definedPredicate);
        LinkedList<RuleNode> childNodes = new LinkedList<RuleNode>();
        boolean first = true;
        for (List<IInferableInstance> possibleUnfold : possibleUnfolds) {
            List<IInferableInstance> goals = new LinkedList<IInferableInstance>(newGoals);
            goals.addAll(0, possibleUnfold);
            RuleNode childNode;
            if (first) {
                childNode  = constructChildNodeWithoutClone(goals, ruleNode);
            }
            else {
                childNode = constructChildNode(goals,ruleNode);
                first = false;
            }
            childNodes.add(childNode);
        }
        if (LOGGER.isInfoEnabled()) LOGGER.info("D1 generated "+childNodes.size()+" new states.");
        expandNode(ruleNode,childNodes);
    }

    @Override
    public void visit(N1RuleNode ruleNode) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Applying N1 to node.");

        List<IInferableInstance> newGoals = new LinkedList<IInferableInstance>(ruleNode.getGoals());
        NegationInstance goal = (NegationInstance) newGoals.remove(0);

        DenialInstance denial = new DenialInstance(goal.getSubFormula());
        newGoals.add(0,denial);

        RuleNode childNode = constructChildNodeWithoutClone(newGoals, ruleNode);
        expandNode(ruleNode,childNode);
    }

    @Override
    protected List<RuleNode> applyInEqualitySolver(RuleNode node) throws JALPException {
        List<InEqualityInstance> inequalities = node.getStore().inequalities;
        LinkedList<RuleNode> ruleNodes = new LinkedList<RuleNode>();

        boolean first = true;

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Applying inequality solver to rulenode:\n"+node);
        for (InEqualityInstance e:inequalities){
            e.performSubstitutions(node.getAssignments()); // TODO: Do this somewhere else i.e. when applying to whole state.
        }
        InEqualitySolver solver = new InEqualitySolver();

        List<Pair<List<EqualityInstance>,List<InEqualityInstance>>> possibleEDECombinations = new LinkedList<Pair<List<EqualityInstance>,List<InEqualityInstance>>>();

        for (InEqualityInstance inEquality:inequalities) {
            List<Pair<List<EqualityInstance>,List<InEqualityInstance>>> inequalitySolved = solver.execute(inEquality);
            if (inequalitySolved==null) {
                if (LOGGER.isDebugEnabled()) LOGGER.debug("Inequality solver failed to find any solutions");
                return null;
            }
            if (possibleEDECombinations.isEmpty()) {
                possibleEDECombinations.addAll(inequalitySolved);
            }
            else {
                List<Pair<List<EqualityInstance>,List<InEqualityInstance>>> newPossibleEDECombinations = new LinkedList<Pair<List<EqualityInstance>,List<InEqualityInstance>>>();
                for (Pair<List<EqualityInstance>, List<InEqualityInstance>> newPair:inequalitySolved) {
                    for (Pair<List<EqualityInstance>, List<InEqualityInstance>> oldPair:possibleEDECombinations) {
                        List<EqualityInstance> newEqualities = new LinkedList<EqualityInstance>();
                        List<InEqualityInstance> newInEqualities = new LinkedList<InEqualityInstance>();
                        newEqualities.addAll(newPair.getValue0());
                        newEqualities.addAll(newPair.getValue0());
                        newInEqualities.addAll(newPair.getValue1());
                        newInEqualities.addAll(newPair.getValue1());
                        newPossibleEDECombinations.add(new Pair<List<EqualityInstance>, List<InEqualityInstance>>(newEqualities,newInEqualities));
                    }
                }
                possibleEDECombinations = newPossibleEDECombinations;
            }
        }

        if (possibleEDECombinations.isEmpty()) {
            if (LOGGER.isDebugEnabled()) LOGGER.debug("Inequality solver found one solution");
            node.getStore().inequalities=new LinkedList<InEqualityInstance>();
            node.getStore().equalities=new LinkedList<EqualityInstance>();
            ruleNodes.add(node);
        }

        else {
            if (LOGGER.isDebugEnabled()) LOGGER.debug("Inequality solver found "+possibleEDECombinations.size()+" solutions.");
            for (Pair<List<EqualityInstance>, List<InEqualityInstance>> combinations:possibleEDECombinations) {
                RuleNode newNode;
                if (first) {
                    newNode = node;
                }
                else {
                    newNode = node.shallowClone();
                    first=false;
                }
                newNode.getStore().equalities=combinations.getValue0();
                newNode.getStore().inequalities=combinations.getValue1();
                ruleNodes.add(newNode);
            }
        }

        return ruleNodes;
    }

    @Override
    protected boolean applyEqualitySolver(RuleNode node) {
        Map<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>(node.getAssignments());
        List<EqualityInstance> equalities = new LinkedList<EqualityInstance>(node.getStore().equalities);

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Applying equality solver to rule node:\n"+node);

        IEqualitySolver solver = new EqualitySolver();
        boolean equalitySolveSuccess = solver.execute(assignments,equalities);

        if (equalitySolveSuccess) {
            if (LOGGER.isDebugEnabled()) LOGGER.debug("Equality solver succeeded.");
            node.setAssignments(assignments);
            node.getStore().equalities=new LinkedList<EqualityInstance>();
        }

        else {
            if (LOGGER.isDebugEnabled()) LOGGER.debug("Equality solver failed");
            node.setNodeMark(RuleNode.NodeMark.FAILED);
        }

        return equalitySolveSuccess;

    }

    @Override
    protected void expandNode(RuleNode parent, RuleNode child) {
        List<RuleNode> newChildren = new LinkedList<RuleNode>();
        boolean equalitySolveSuccess = true;
        if (!child.getStore().equalities.isEmpty()) {
            equalitySolveSuccess = applyEqualitySolver(child);
            if (equalitySolveSuccess) {
                child.applySubstitutions();
            }
        }
        if (equalitySolveSuccess) {
            if (!child.getStore().inequalities.isEmpty()) {
                List<RuleNode> newConstraintChildren = applyInEqualitySolver(child);
                if (newConstraintChildren==null) {
                    child.setNodeMark(RuleNode.NodeMark.FAILED);
                    newChildren.add(child);
                }
                else {
                    newChildren.addAll(newConstraintChildren);
                    parent.setNodeMark(RuleNode.NodeMark.EXPANDED);
                }
            }
            else {
                newChildren.add(child);
            }

        }
        else {
            child.setNodeMark(RuleNode.NodeMark.FAILED);
            newChildren.add(child);
        }
        parent.getChildren().addAll(newChildren);
    }

    @Override
    protected void expandNode(RuleNode parent, List<RuleNode> children) {
        for (RuleNode child:children) {
            expandNode(parent, child);
        }
    }
}
