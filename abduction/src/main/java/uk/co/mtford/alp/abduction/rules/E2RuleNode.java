package uk.co.mtford.alp.abduction.rules;

import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.logic.instance.IASystemInferable;
import uk.co.mtford.alp.abduction.rules.visitor.RuleNodeVisitor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 06:47
 * To change this template use File | Settings | File Templates.
 */
public class E2RuleNode extends NegativeRuleNode {
    public E2RuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        super(abductiveFramework, goals);
    }

    @Override
    public void acceptVisitor(RuleNodeVisitor v) {
        v.visit(this);
    }
}
