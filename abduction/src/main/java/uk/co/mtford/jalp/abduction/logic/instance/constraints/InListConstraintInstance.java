package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 24/05/2012
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
public abstract class InListConstraintInstance extends ConstraintInstance {
    public InListConstraintInstance(ITermInstance left, ITermInstance right) {
        super(left, right);
    }

    @Override
    public String toString () {
        return left + " in "+ right;
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenials, List<IInferableInstance> goals) {
        return left.getNegativeRootRuleNode(this,abductiveFramework,nestedDenials,goals);
    }

}
