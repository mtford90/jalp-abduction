package uk.co.mtford.alp.abduction.rules;

import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.DefinitionException;
import uk.co.mtford.alp.abduction.Store;
import uk.co.mtford.alp.abduction.logic.instance.IASystemInferableInstance;
import uk.co.mtford.alp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.alp.abduction.logic.instance.VariableInstance;
import uk.co.mtford.alp.abduction.rules.visitor.RuleNodeVisitor;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 20/05/2012
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class SuccessNode extends RuleNode {

    public SuccessNode(AbductiveFramework abductiveFramework, IASystemInferableInstance goal, List<IASystemInferableInstance> restOfGoals) {
        super(abductiveFramework, goal, restOfGoals);
    }

    public SuccessNode(AbductiveFramework abductiveFramework, IASystemInferableInstance goal, List<IASystemInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        super(abductiveFramework, goal, restOfGoals, store, assignments);
    }

    protected SuccessNode() {
    }

    @Override
    public RuleNode shallowClone() {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public void acceptVisitor(RuleNodeVisitor v) throws DefinitionException {
        v.visit(this);
    }
}
