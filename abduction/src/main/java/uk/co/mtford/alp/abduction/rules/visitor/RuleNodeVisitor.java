package uk.co.mtford.alp.abduction.rules.visitor;

import uk.co.mtford.alp.abduction.rules.*;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 06:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class RuleNodeVisitor {

    private RuleNode currentRuleNode;

    public RuleNodeVisitor(RuleNode ruleNode) {
        this.currentRuleNode = ruleNode;
    }

    public void visit(A1RuleNode ruleNode) {

    }
    public void visit(A2RuleNode ruleNode) {

    }
    public void visit(D1RuleNode ruleNode) {

    }
    public void visit(D2RuleNode ruleNode) {

    }
    public void visit(E1RuleNode ruleNode) {

    }
    public void visit(E2RuleNode ruleNode) {

    }
    public void visit(N1RuleNode ruleNode) {

    }
    public void visit(N2RuleNode ruleNode) {

    }
    public void visit(PositiveTrueRuleNode ruleNode) {

    }
    public void visit(NegativeTrueRuleNode ruleNode) {

    }
    public void visit(PositiveFalseRuleNode ruleNode) {

    }
    public void visit(NegativeFalseRuleNode ruleNode) {

    }

    /** Explores a new node based on exploration strategy defined in chooseNextNode. Then proceeds to expand that
     *  node to generate it's children using the rules defined above.
     * @return
     */
    public RuleNode stateRewrite() {
        currentRuleNode=chooseNextNode();
        currentRuleNode.acceptVisitor(this);
        return currentRuleNode;
    }

    protected abstract RuleNode chooseNextNode();
}
