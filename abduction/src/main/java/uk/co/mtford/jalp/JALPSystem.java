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
import uk.co.mtford.jalp.abduction.rules.visitor.AbstractRuleNodeVisitor;
import uk.co.mtford.jalp.abduction.rules.visitor.EfficientRuleNodeVisitor;
import uk.co.mtford.jalp.abduction.rules.visitor.SimpleRuleNodeVisitor;

import java.io.*;
import java.util.*;

/**
 * Responsible for executing queries on an abductive theory. Used by both the shell and interpreter as well
 * as with the Java API.
 * @author Michael Ford
 */
public class JALPSystem {

    private static final Logger LOGGER = Logger.getLogger(JALPSystem.class);

    private AbductiveFramework framework;
    private static final int MAX_SECONDS = 3;

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

    /** Combines the given framework with the current framework.
     *
     * @param newFramework
     */
    public void mergeFramework(AbductiveFramework newFramework) {
        if (framework == null) {
            framework = newFramework;
        } else {
            framework.getP().addAll(newFramework.getP());
            framework.getIC().addAll(newFramework.getIC());
            framework.getA().putAll(newFramework.getA());
        }
    }

    /** Combines the given framework from the file specified with the current framework.
     *
     * @param file
     */
    public void mergeFramework(File file) throws FileNotFoundException, ParseException {
        mergeFramework(JALPParser.readFromFile(file.getPath()));
    }

    /** Combines the given framework with the current framework.
     *
     * @param program
     */
    public void mergeFramework(String program) throws ParseException {
        mergeFramework(JALPParser.readFromString(program));
    }

    /** Clears the current abductive framework.
     *
     */
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

    /** For the given query, generates a log file and a visualizer at folderName.
     *
     * @param query
     * @param folderName
     * @return A list of results that represent abduction explanations.
     * @throws IOException
     * @throws uk.co.mtford.jalp.abduction.parse.query.ParseException
     */
    public List<Result> generateDebugFiles(String query, String folderName) throws IOException, uk.co.mtford.jalp.abduction.parse.query.ParseException, InterruptedException {
        return generateDebugFiles(new LinkedList<IInferableInstance>(JALPQueryParser.readFromString(query)),folderName);
    }

    /** For the given query, generates a log file and a visualizer at folderName.
     *
     * @param query
     * @param folderName
     * @return A list of results that represent abduction explanations.
     * @throws IOException
     * @throws uk.co.mtford.jalp.abduction.parse.query.ParseException
     */
    public List<Result> generateDebugFiles(List<IInferableInstance> query, String folderName) throws JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException, IOException, InterruptedException {
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

    /** Executes query represented by the string e.g. 'p(X)'
     *
     * @param query
     * @return A list of results that represent abduction explanations.
     * @throws uk.co.mtford.jalp.abduction.parse.query.ParseException
     */
    public List<Result> query(String query) throws uk.co.mtford.jalp.abduction.parse.query.ParseException, InterruptedException {
        List<Result> results = new LinkedList<Result>();
        query(query, results);
        return results;
    }

    /** Executes query e.g. 'p(X)'
     *
     * @param query
     * @return A list of results that represent abduction explanations.
     * @throws uk.co.mtford.jalp.abduction.parse.query.ParseException
     */
    public List<Result> query(List<IInferableInstance> query) throws InterruptedException {
        List<Result> results = new LinkedList<Result>();
        query(query, results);
        return results;
    }

    /** Executes the query represented by the string and generates result objects in the list.
     *
     * @param query
     * @param results
     * @return The root node of the derivation tree.
     * @throws uk.co.mtford.jalp.abduction.parse.query.ParseException
     */
    public RuleNode query(String query, List<Result> results) throws uk.co.mtford.jalp.abduction.parse.query.ParseException, InterruptedException {
        List<IInferableInstance> queryList = JALPQueryParser.readFromString(query);
        return query(queryList, results);
    }

    /** Executes the query and generates result objects in the list.
     *
     * @param query
     * @param results
     * @return The root node of the derivation tree.
     * @throws uk.co.mtford.jalp.abduction.parse.query.ParseException
     */
    public RuleNode query(List<IInferableInstance> query, List<Result> results) throws InterruptedException {

        try {
            AbstractRuleNodeVisitor visitor = new SimpleRuleNodeVisitor();
            Stack<RuleNode> nodeStack = new Stack<RuleNode>();

            List<IInferableInstance> goals = new LinkedList<IInferableInstance>(query);
            goals.addAll(framework.getIC());
            RuleNode rootNode = goals.get(0).getPositiveRootRuleNode(framework,new LinkedList<IInferableInstance>(query),goals);

            RuleNode currentNode;
            nodeStack.add(rootNode);


            int n = 0;

            while (!nodeStack.isEmpty()) {
                currentNode = nodeStack.pop();
                if (currentNode.getGoals().isEmpty()&&!currentNode.getNodeMark().equals(RuleNode.NodeMark.FAILED)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Found a leaf node!");
                    }
                    generateResults(rootNode, currentNode, query, results);
                }
                else if (currentNode.getNodeMark()==RuleNode.NodeMark.FAILED) {
                    LOGGER.debug("Found a failed node:\n" + currentNode);
                }
                else if (currentNode.getNodeMark()==RuleNode.NodeMark.UNEXPANDED) {
                    currentNode.acceptVisitor(visitor);
                    nodeStack.addAll(currentNode.getChildren());
                }
                else {
                    throw new JALPException("Expanded node on the node stack?\n"+currentNode); // Sanity check.
                }
            }

            return rootNode;

            }

        catch (StackOverflowError e) {
            throw new JALPException("Error: Stack overflow detected. Occurs problem?");
        }

    }

    /** Executes the query represented by the string and generates result objects in the list. Doesn't preserve the
     * derivation tree and ensures proper garbage collection.
     *
     * @param query
     * @return The root node of the derivation tree.
     * @throws uk.co.mtford.jalp.abduction.parse.query.ParseException
     */
    public List<Result> efficientQuery(List<IInferableInstance> query) throws InterruptedException {
        try {
            List<Result> results = new LinkedList<Result>();

            AbstractRuleNodeVisitor visitor = new EfficientRuleNodeVisitor();
            Stack<RuleNode> nodeStack = new Stack<RuleNode>();

            List<IInferableInstance> goals = new LinkedList<IInferableInstance>(query);
            goals.addAll(framework.getIC());
            RuleNode rootNode = goals.get(0).getPositiveRootRuleNode(framework,new LinkedList<IInferableInstance>(query),goals);

            RuleNode currentNode;
            nodeStack.add(rootNode);
            rootNode = null; // Encourage garbage collect.

            int n = 0;

            while (!nodeStack.isEmpty()) {
                currentNode = nodeStack.pop();
                if (currentNode.getGoals().isEmpty()&&!currentNode.getNodeMark().equals(RuleNode.NodeMark.FAILED)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Found a leaf node!");
                    }
                    generateResults(rootNode, currentNode, query, results);
                }
                else if (currentNode.getNodeMark()==RuleNode.NodeMark.FAILED) {
                    LOGGER.debug("Found a failed node:\n" + currentNode);
                }
                else if (currentNode.getNodeMark()==RuleNode.NodeMark.UNEXPANDED) {
                    currentNode.acceptVisitor(visitor);
                    nodeStack.addAll(currentNode.getChildren());
                }
                else {
                    throw new JALPException("Expanded node on the node stack?\n"+currentNode); // Sanity check.
                }
            }

            return results;
        }
        catch (StackOverflowError e) {
             throw new JALPException("Error: Stack overflow detected. Occurs problem?");
        }

    }

    /** Generates result objects. Each result object represents an abductive explanation.
     *
     * @param rootNode
     * @param successNode
     * @param originalQuery
     * @param results
     */
    private void generateResults(RuleNode rootNode, RuleNode successNode, List<IInferableInstance> originalQuery, List<Result> results) throws InterruptedException {
        List<RuleNode> newSuccessNodes = applyConstraintSolver(successNode);
        for (RuleNode node:newSuccessNodes) {
            generateResult(rootNode,node,originalQuery,results);
        }
    }

    /** Generates a single result object.
     *
     * @param rootNode
     * @param successNode
     * @param originalQuery
     * @param results
     */
    private void generateResult(RuleNode rootNode, RuleNode successNode, List<IInferableInstance> originalQuery, List<Result> results) {
        Result result = new Result(successNode.getStore(),successNode.getAssignments(),originalQuery,rootNode);
        results.add(result);
    }

    /**
     * Applys the constraint solver to the given node.
     *
     * @param node
     * @return A list of possible child nodes each one
     *         representing a possible assignment to the constrained variables in the given node.
     */
    private synchronized List<RuleNode> applyConstraintSolver(final RuleNode node) throws InterruptedException {
        final List<RuleNode> results = new LinkedList<RuleNode>();

        Runnable r = new Runnable() {
            public void run() {
                if (LOGGER.isDebugEnabled()) LOGGER.debug("Applying constraint solver to ruleNode:\n"+node);
                List<Map<VariableInstance,IUnifiableInstance>> possibleAssignments;
                if (node.getStore().constraints.isEmpty()) {
                    possibleAssignments
                            = new LinkedList<Map<VariableInstance, IUnifiableInstance>>();
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
                            = constraintSolver.execute(new HashMap<VariableInstance,IUnifiableInstance>(node.getAssignments()),constraints);
                }

                if (possibleAssignments.isEmpty()) {
                    if (LOGGER.isDebugEnabled()) LOGGER.debug("Constraint solver failed on rulenode:\n"+node);
                    node.setNodeMark(RuleNode.NodeMark.FAILED);
                }
                else { // Constraint solver succeeded. Generate possible children.
                    if (LOGGER.isDebugEnabled()) LOGGER.debug("Constraint solver returned "+possibleAssignments.size()+" results.");
                    node.setNodeMark(RuleNode.NodeMark.EXPANDED);
                    for (Map<VariableInstance,IUnifiableInstance> assignment:possibleAssignments) {
                        RuleNode newLeafNode =node.shallowClone();
                        newLeafNode.setAssignments(assignment);
                        newLeafNode.applySubstitutions();
                        newLeafNode.setNodeMark(RuleNode.NodeMark.SUCCEEDED);
                        results.add(newLeafNode);
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();

        int n = 0;

        while (n<MAX_SECONDS) {
            t.join(1000);
            n++;
            if (!t.isAlive()) break;
            if (n==MAX_SECONDS) {
                t.stop();
                throw new JALPException("Floundering detected.");
            }
        }

        return results;

    }

}
