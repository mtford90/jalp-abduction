package uk.co.mtford.jalp.abduction.rules;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 06:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class PositiveRuleNode extends RuleNode {

    public PositiveRuleNode(AbductiveFramework abductiveFramework, IInferableInstance goal, List<IInferableInstance> restOfGoals) {
        super(abductiveFramework, goal, restOfGoals);
    }

    public PositiveRuleNode(AbductiveFramework abductiveFramework, IInferableInstance goal, List<IInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        super(abductiveFramework, goal, restOfGoals, store, assignments);
    }

    protected PositiveRuleNode() {
        super();
    }



}
