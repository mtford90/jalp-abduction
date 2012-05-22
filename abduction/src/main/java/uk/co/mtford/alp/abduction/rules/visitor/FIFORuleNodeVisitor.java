package uk.co.mtford.alp.abduction.rules.visitor;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.DefinitionException;
import uk.co.mtford.alp.abduction.rules.RuleNode;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 07:08
 * To change this template use File | Settings | File Templates.
 */
public class FifoRuleNodeVisitor extends RuleNodeVisitor {

    private static final Logger LOGGER = Logger.getLogger(FifoRuleNodeVisitor.class);

    private Stack<RuleNode> nodeStack;

    public FifoRuleNodeVisitor(RuleNode ruleNode) throws DefinitionException {
        super(ruleNode);
        nodeStack = new Stack<RuleNode>();
    }

    // Simple FIFO strategy.
    @Override
    protected RuleNode chooseNextNode() {
        nodeStack.addAll(currentRuleNode.getChildren());
        if (nodeStack.isEmpty()) {
            if (LOGGER.isInfoEnabled()) LOGGER.info("No nodes left to choose.");
            return null;
        }
        RuleNode chosenNode = nodeStack.pop();
        return chosenNode;
    }
}
