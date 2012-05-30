package uk.co.mtford.jalp;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.DefinitionException;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.IConstraintInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.IEqualityInstance;
import uk.co.mtford.jalp.abduction.parse.program.JALPParser;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.parse.program.TokenMgrError;
import uk.co.mtford.jalp.abduction.parse.query.JALPQueryParser;
import uk.co.mtford.jalp.abduction.rules.RuleNode;
import uk.co.mtford.jalp.abduction.rules.visitor.FifoRuleNodeVisitor;
import uk.co.mtford.jalp.abduction.rules.visitor.RuleNodeVisitor;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 27/05/2012
 * Time: 08:33
 * To change this template use File | Settings | File Templates.
 */
public class JALPSystem {

    public static void reduceResult(Result result) {
        List<PredicateInstance> substAbducibles = new LinkedList<PredicateInstance>();
        List<DenialInstance> substDenials = new LinkedList<DenialInstance>();
        List<IEqualityInstance> substEqualities = new LinkedList<IEqualityInstance>();
        List<IConstraintInstance> substConstraints = new LinkedList<IConstraintInstance>();
        List<VariableInstance> queryVariables = new LinkedList<VariableInstance>();

        for (IInferableInstance inferable:result.getQuery()) queryVariables.addAll(inferable.getVariables());

        Set<VariableInstance> relevantVariables = new HashSet<VariableInstance>(queryVariables);
        HashMap<VariableInstance,IUnifiableAtomInstance> relevantAssignments = new HashMap<VariableInstance, IUnifiableAtomInstance>();


        for (PredicateInstance a:result.getStore().abducibles) {
            substAbducibles.add((PredicateInstance)a.performSubstitutions(result.getAssignments()));
        }

        for (DenialInstance d:result.getStore().denials) {
            substDenials.add((DenialInstance) d.performSubstitutions(result.getAssignments()));
        }

        for (IEqualityInstance e:result.getStore().equalities) {
            substEqualities.add((IEqualityInstance) e.performSubstitutions(result.getAssignments()));
        }

        for (IConstraintInstance c:result.getStore().constraints) {
            substConstraints.add((IConstraintInstance)c.performSubstitutions(result.getAssignments()));
        }

        Set<IUnifiableAtomInstance> keySet = new HashSet<IUnifiableAtomInstance>(result.getAssignments().keySet());

        for (IUnifiableAtomInstance key:keySet) {


            if (queryVariables.contains(key)) {
                IUnifiableAtomInstance value = result.getAssignments().get(key);

                while (keySet.contains(value)) value = result.getAssignments().get(value);
                relevantAssignments.put((VariableInstance) key,value);
            }
        }

        result.setAssignments(relevantAssignments);
        result.getStore().abducibles=substAbducibles;
        result.getStore().denials = substDenials;
        result.getStore().equalities=substEqualities;
        result.getStore().constraints=substConstraints;

    }

    public enum Heuristic {
        NONE
    }

    private static final Logger LOGGER = Logger.getLogger(JALPSystem.class);
    private AbductiveFramework framework;

    public JALPSystem(AbductiveFramework framework) {
        this.framework = framework;
    }

    public JALPSystem(String fileName) throws FileNotFoundException, ParseException {
        framework = JALPParser.readFromFile(fileName);
    }

    public JALPSystem(String[] fileNames) {
        try {
            setFramework(fileNames);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public JALPSystem() {
        framework = new AbductiveFramework();
    }

    public void mergeFramework(AbductiveFramework newFramework) {
        if (framework == null) {
            framework = newFramework;
        } else {
            framework.getP().addAll(newFramework.getP());
            framework.getIC().addAll(newFramework.getIC());
            framework.getA().putAll(newFramework.getA());
        }
    }

    private void reset() {
        framework = new AbductiveFramework();
    }

    public AbductiveFramework getFramework() {
        return framework;
    }

    public void setFramework(AbductiveFramework framework) {
        this.framework = framework;
    }

    public void setFramework(String fileName) throws FileNotFoundException, ParseException {
        framework = JALPParser.readFromFile(fileName);
    }

    public void setFramework(String[] fileName) throws FileNotFoundException, ParseException {
        for (String f:fileName) {
            mergeFramework(JALPParser.readFromFile(f));
        }
    }

    public List<Result> processQuery(List<IInferableInstance> query, Heuristic heuristic) throws JALPException {
        RuleNodeIterator iterator = new RuleNodeIterator(query,heuristic);
        return performDerivation(iterator);
    }

    public List<Result> processQuery(String query, Heuristic heuristic) throws JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        RuleNodeIterator iterator = new RuleNodeIterator(new
                LinkedList<IInferableInstance>(JALPQueryParser.readFromString(query)),heuristic);
        return performDerivation(iterator);
    }

    private List<Result> performDerivation(RuleNodeIterator iterator) {
        LinkedList<Result> resultList = new LinkedList<Result>();
        RuleNode currentNode = iterator.getCurrentNode();
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        query.add(currentNode.getCurrentGoal());
        query.addAll(currentNode.getNextGoals());

        while (iterator.hasNext()) {
            if (currentNode.getNodeMark()==RuleNode.NodeMark.SUCCEEDED) {
                Result result = new Result(currentNode.getStore(),currentNode.getAssignments(),query);
                resultList.add(result);
            }
            currentNode = iterator.next();
        }

        return resultList;
    }

    public RuleNode getDerivationTree(List<IInferableInstance> query, Heuristic heuristic) throws JALPException {
        RuleNodeIterator iterator = new RuleNodeIterator(query,heuristic);
        RuleNode root = iterator.getCurrentNode();
        performDerivation(iterator);
        return root;
    }

    public RuleNodeIterator getRuleNodeIterator(List<IInferableInstance> query, Heuristic heuristic) throws JALPException {
        return new RuleNodeIterator(query,heuristic);
    }

    public class RuleNodeIterator implements Iterator<RuleNode> {

        private RuleNode currentNode;
        private RuleNodeVisitor nodeVisitor;

        public RuleNode getCurrentNode() {
            return currentNode;
        }

        public void setCurrentNode(RuleNode currentNode) {
            this.currentNode = currentNode;
        }

        private RuleNodeIterator(List<IInferableInstance> goals, Heuristic heuristic) throws JALPException {
            switch (heuristic) {
                case NONE:
                    currentNode = goals.remove(0).getPositiveRootRuleNode(framework,goals);
                    nodeVisitor = new FifoRuleNodeVisitor(currentNode);
                    break;
                default: throw new JALPException("No such heuristic exists.");
            }
        }

        @Override
        public boolean hasNext() {
            return nodeVisitor.hasNextNode();
        }

        @Override
        public RuleNode next() {
            try {
                currentNode=nodeVisitor.stateRewrite();
            } catch (DefinitionException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return currentNode;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(); // TODO
        }
    }
}
