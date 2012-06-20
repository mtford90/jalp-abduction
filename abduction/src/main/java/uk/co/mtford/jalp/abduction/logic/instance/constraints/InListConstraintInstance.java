package uk.co.mtford.jalp.abduction.logic.instance.constraints;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.ITermInstance;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.List;

/**
 * e.g. 1 in [1,2,3]
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
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals) {
        return left.getNegativeRootRuleNode(abductiveFramework, query,goals);
    }

}
