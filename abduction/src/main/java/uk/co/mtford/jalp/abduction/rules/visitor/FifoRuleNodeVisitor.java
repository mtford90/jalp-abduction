package uk.co.mtford.jalp.abduction.rules.visitor;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.JALPException;
import uk.co.mtford.jalp.abduction.DefinitionException;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

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

    public FifoRuleNodeVisitor(RuleNode ruleNode) throws Exception {
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
        while (chosenNode.getNodeMark()==RuleNode.NodeMark.FAILED) {
            if (nodeStack.isEmpty()) return null;
            chosenNode = nodeStack.pop();
        }
        return chosenNode;
    }

    @Override
    public boolean hasNextNode() {
        if (currentRuleNode==null) return false;
        else {
            return !currentRuleNode.getChildren().isEmpty() || !nodeStack.isEmpty() || !(currentRuleNode.getNodeMark()==RuleNode.NodeMark.UNEXPANDED);
        }
    }


}
