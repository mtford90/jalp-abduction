package uk.co.mtford.alp.abduction.rules.visitor;

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
    private Stack<RuleNode> nodeStack;

    public FifoRuleNodeVisitor(RuleNode ruleNode) {
        super(ruleNode);
        nodeStack = new Stack<RuleNode>();
    }

    // Simple FIFO strategy.
    @Override
    protected RuleNode chooseNextNode() {
        return nodeStack.pop();
    }
}
