package uk.co.mtford.jalp;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.DefinitionException;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.parse.program.JALPParser;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.parse.program.TokenMgrError;
import uk.co.mtford.jalp.abduction.rules.RuleNode;
import uk.co.mtford.jalp.abduction.rules.visitor.FifoRuleNodeVisitor;
import uk.co.mtford.jalp.abduction.rules.visitor.RuleNodeVisitor;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 27/05/2012
 * Time: 08:33
 * To change this template use File | Settings | File Templates.
 */
public class JALPSystem {

    public enum Heuristic {
        NONE
    }

    private static final Logger LOGGER = Logger.getLogger(JALPSystem.class);
    private AbductiveFramework framework;

    public JALPSystem(AbductiveFramework framework) {
        this.framework = framework;
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

    private void loadFrameworks(String[] fileNames) throws FileNotFoundException, ParseException, TokenMgrError {  // todo
        if (LOGGER.isInfoEnabled()) LOGGER.info("Loading files:"+fileNames);
        if (fileNames.length == 0) {
            throw new FileNotFoundException("Need to specify a file.");
        } else {
            for (int i = 1; i < fileNames.length; i++) {
                AbductiveFramework newF = null;
                if (newF != null) mergeFramework(newF);
            }
        }
    }

    public List<Result> processQuery(List<IInferableInstance> query, Heuristic heuristic) throws JALPException {
        RuleNodeIterator iterator = new RuleNodeIterator(query,heuristic);
        return performDerivation(iterator);
    }

    private List<Result> performDerivation(RuleNodeIterator iterator) {
        LinkedList<Result> resultList = new LinkedList<Result>();
        RuleNode currentNode = iterator.getCurrentNode();

        while (iterator.hasNext()) {
            if (currentNode.getNodeMark()==RuleNode.NodeMark.SUCCEEDED) {
                Result result = new Result(currentNode.getStore(),currentNode.getAssignments());
                resultList.add(result);
            }
            currentNode = iterator.next();
        }

        if (currentNode.getNodeMark()==RuleNode.NodeMark.SUCCEEDED) {
            Result result = new Result(currentNode.getStore(),currentNode.getAssignments());
            resultList.add(result);
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
