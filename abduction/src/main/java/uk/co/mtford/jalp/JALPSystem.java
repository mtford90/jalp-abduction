package uk.co.mtford.jalp;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.*;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.ChocoConstraintSolverFacade;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.IConstraintInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.parse.program.JALPParser;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.parse.query.JALPQueryParser;
import uk.co.mtford.jalp.abduction.rules.RuleNode;
import uk.co.mtford.jalp.abduction.rules.visitor.RuleNodeVisitor;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 27/05/2012
 * Time: 08:33
 * To change this template use File | Settings | File Templates.
 */
public class JALPSystem {

    private static final Logger LOGGER = Logger.getLogger(JALPSystem.class);

    private static int MAX_EXPANSIONS = Integer.MAX_VALUE;

    private AbductiveFramework framework;

    public JALPSystem(AbductiveFramework framework) {
        this.framework = framework;
    }

    public JALPSystem(String fileName) throws FileNotFoundException, ParseException {
        framework = JALPParser.readFromFile(fileName);
    }

    public JALPSystem(String[] fileNames) throws FileNotFoundException, ParseException {
        setFramework(fileNames);
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

    public void mergeFramework(File file) throws FileNotFoundException, ParseException {
        mergeFramework(JALPParser.readFromFile(file.getPath()));
    }

    public void mergeFramework(String query) throws ParseException {
        mergeFramework(JALPParser.readFromString(query));
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

    public List<Result> generateDebugFiles(String query, String folderName) throws IOException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        return generateDebugFiles(new LinkedList<IInferableInstance>(JALPQueryParser.readFromString(query)),folderName);
    }

    public List<Result> generateDebugFiles(List<IInferableInstance> query, String folderName) throws Exception, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        File folder = new File(folderName);
        FileUtils.touch(folder);
        FileUtils.forceDelete(folder);
        folder = new File(folderName);
        Appender R = Logger.getRootLogger().getAppender("R");
        Level previousLevel = Logger.getRootLogger().getLevel();
        Logger.getRootLogger().removeAppender("R");
        Logger.getRootLogger().setLevel(Level.DEBUG);
        FileAppender newAppender = new DailyRollingFileAppender(new PatternLayout("%d{dd-MM-yyyy HH:mm:ss} %C %L %-5p: %m%n"), folderName+"/log.txt", "'.'dd-MM-yyyy");
        newAppender.setName("R");
        Logger.getRootLogger().addAppender(newAppender);
        LOGGER.info("Abductive framework is:\n"+framework);
        LOGGER.info("Query is:" + query);
        List<Result> results = new LinkedList<Result>();
        RuleNode root = query(query, results);

        JALP.getVisualizer(folderName + "/visualizer", root);
        int rNum = 1;
        LOGGER.info("Found "+results.size()+" results");
        for (Result r:results) {
            LOGGER.info("Result "+rNum+" is\n"+r);
            rNum++;
        }
        Logger.getRootLogger().removeAppender("R");
        Logger.getRootLogger().addAppender(R);
        Logger.getRootLogger().setLevel(previousLevel);
        return results;
    }

    public List<Result> query(String query) throws uk.co.mtford.jalp.abduction.parse.query.ParseException {
        List<Result> results = new LinkedList<Result>();
        query(query, results);
        return results;
    }

    public List<Result> query(List<IInferableInstance> query) {
        List<Result> results = new LinkedList<Result>();
        query(query, results);
        return results;
    }

    public RuleNode query(String query, List<Result> results) throws uk.co.mtford.jalp.abduction.parse.query.ParseException {
        List<IInferableInstance> queryList = JALPQueryParser.readFromString(query);
        return query(queryList, results);
    }

    public RuleNode query(List<IInferableInstance> query, List<Result> results) {

        RuleNodeVisitor visitor = new RuleNodeVisitor();
        Stack<RuleNode> nodeStack = new Stack<RuleNode>();

        List<IInferableInstance> goals = new LinkedList<IInferableInstance>(query);
        goals.addAll(framework.getIC());
        RuleNode rootNode = goals.get(0).getPositiveRootRuleNode(framework,new LinkedList<IInferableInstance>(query),goals);

        RuleNode currentNode;
        nodeStack.add(rootNode);

        int n = 0;

        while (!nodeStack.isEmpty()) {
            if (n==MAX_EXPANSIONS) {
                LOGGER.error("Hit max expansions of "+MAX_EXPANSIONS);
                return rootNode;
            }
            n++;
            currentNode = nodeStack.pop();
            if (currentNode.getGoals().isEmpty()) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Found a leaf node!");
                }
                generateResults(rootNode, currentNode, query, results);
            }
            else if (currentNode.getNodeMark()==RuleNode.NodeMark.FAILED) {
                LOGGER.debug("Found a failed node:\n"+currentNode);
            }
            else if (currentNode.getNodeMark()==RuleNode.NodeMark.UNEXPANDED) {
                try {
                    currentNode.acceptVisitor(visitor);
                }
                catch (Exception e) {
                    LOGGER.fatal("Encountered an error whilst state rewriting. Returning what have so far.",e);
                    return rootNode;
                }
                nodeStack.addAll(currentNode.getChildren());
            }
            else {
                throw new JALPException("Expanded node on the node stack?\n"+currentNode); // Sanity check.
            }
        }

        return rootNode;

    }

    private void generateResults(RuleNode rootNode, RuleNode successNode, List<IInferableInstance> originalQuery, List<Result> results) {
        List<RuleNode> newSuccessNodes = applyConstraintSolver(successNode);
        for (RuleNode node:newSuccessNodes) {
            generateResult(rootNode,node,originalQuery,results);
        }
    }

    private void generateResult(RuleNode rootNode, RuleNode successNode, List<IInferableInstance> originalQuery, List<Result> results) {
        HashMap<VariableInstance,IUnifiableAtomInstance> resultAssignments = new HashMap<VariableInstance,IUnifiableAtomInstance>();
        for (IInferableInstance queryInferable:originalQuery) {
            for (IInferableInstance finalQuery:successNode.getQuery()) { // TODO: Should be IUnifiable, not Inferable.
                IUnifiableAtomInstance atom1 = (IUnifiableAtomInstance) queryInferable;
                IUnifiableAtomInstance atom2 = (IUnifiableAtomInstance) finalQuery;
                atom1.unify(atom2,resultAssignments);
            }
        }
        Result result = new Result(successNode.getStore(),resultAssignments,originalQuery,rootNode);
        results.add(result);
    }

    private List<RuleNode> applyConstraintSolver(RuleNode node) {
        if (LOGGER.isDebugEnabled()) LOGGER.debug("Applying constraint solver to ruleNode:\n"+node);
        List<Map<VariableInstance,IUnifiableAtomInstance>> possibleAssignments;
        if (node.getStore().constraints.isEmpty()) {
            possibleAssignments
                    = new LinkedList<Map<VariableInstance, IUnifiableAtomInstance>>();
            possibleAssignments.add(node.getAssignments());
            if (LOGGER.isDebugEnabled()) LOGGER.debug("No need to apply constraint solver. Returning unmodified node.");
        }
        else {
            LinkedList<IConstraintInstance> constraints = new LinkedList<IConstraintInstance>();
            for (IConstraintInstance d:node.getStore().constraints) {
                IConstraintInstance newConstraint = (IConstraintInstance) d.shallowClone();
                newConstraint = (IConstraintInstance) newConstraint.performSubstitutions(node.getAssignments()); // TODO Substitute elsewhere i.e. at each state rewrite.
                constraints.add(newConstraint);
            }
            ChocoConstraintSolverFacade constraintSolver = new ChocoConstraintSolverFacade();
            possibleAssignments
                    = constraintSolver.execute(new HashMap<VariableInstance,IUnifiableAtomInstance>(node.getAssignments()),constraints);
        }

        if (possibleAssignments.isEmpty()) {
            if (LOGGER.isDebugEnabled()) LOGGER.debug("Constraint solver failed on rulenode:\n"+node);
            node.setNodeMark(RuleNode.NodeMark.FAILED);
            return new LinkedList<RuleNode>();
        }
        else { // Constraint solver succeeded. Generate possible children.
            List<RuleNode> results = new LinkedList<RuleNode>();
            if (LOGGER.isDebugEnabled()) LOGGER.debug("Constraint solver returned "+possibleAssignments.size()+" results.");
            node.setNodeMark(RuleNode.NodeMark.EXPANDED);
            for (Map<VariableInstance,IUnifiableAtomInstance> assignment:possibleAssignments) {
                RuleNode newLeafNode =node.shallowClone();
                newLeafNode.setAssignments(assignment);
                newLeafNode.applySubstitutions();
                newLeafNode.setNodeMark(RuleNode.NodeMark.SUCCEEDED);
                results.add(newLeafNode);
            }
            return results;
        }

    }

}
