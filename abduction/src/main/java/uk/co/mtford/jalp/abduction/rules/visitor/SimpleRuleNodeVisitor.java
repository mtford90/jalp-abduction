package uk.co.mtford.jalp.abduction.rules.visitor;

import org.apache.log4j.Logger;
import org.javatuples.Pair;
import uk.co.mtford.jalp.JALPException;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.LeafRuleNode;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A less efficient implementation of rule node visitor. Preserves the structure of the derivation tree.
 */
public class SimpleRuleNodeVisitor extends AbstractRuleNodeVisitor {

    private static Logger LOGGER = Logger.getLogger(SimpleRuleNodeVisitor.class);

    public SimpleRuleNodeVisitor() {
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

    @Override
    protected List<RuleNode> applyInEqualitySolver(RuleNode node) throws JALPException {
        List<InEqualityInstance> inequalities = node.getStore().inequalities;
        LinkedList<RuleNode> ruleNodes = new LinkedList<RuleNode>();

        if (!inequalities.isEmpty()) {
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
                    RuleNode newNode = node.shallowClone();
                    newNode.getStore().equalities=combinations.getValue0();
                    newNode.getStore().inequalities=combinations.getValue1();
                    ruleNodes.add(newNode);
                }
            }

        }

        else {
            ruleNodes.add(node);
        }
        return ruleNodes;
    }

    @Override
    protected boolean applyEqualitySolver(RuleNode node) {
        Map<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>(node.getAssignments());
        List<EqualityInstance> equalities = new LinkedList<EqualityInstance>(node.getStore().equalities);

        if (!equalities.isEmpty()) {
            if (LOGGER.isDebugEnabled()) LOGGER.debug("Applying equality solver to rule node:\n"+node);

            IEqualitySolver solver = new EqualitySolver();
            boolean equalitySolveSuccess = solver.execute(assignments,equalities);

            if (equalitySolveSuccess) {
                if (LOGGER.isDebugEnabled()) LOGGER.debug("Equality solver succeeded.");
                node.setAssignments(assignments);
                node.applySubstitutions();
                node.getStore().equalities=new LinkedList<EqualityInstance>();
                return true;
            }

            else {
                if (LOGGER.isDebugEnabled()) LOGGER.debug("Equality solver failed");
                node.setNodeMark(RuleNode.NodeMark.FAILED);
                return false;
            }

        }

        return true;

    }

    @Override
    protected void expandNode(RuleNode parent, RuleNode child) {
        List<RuleNode> newChildren = new LinkedList<RuleNode>();
        if (applyEqualitySolver(child)) {
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
