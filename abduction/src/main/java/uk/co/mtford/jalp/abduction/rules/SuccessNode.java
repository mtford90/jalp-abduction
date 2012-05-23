package uk.co.mtford.jalp.abduction.rules;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.DefinitionException;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.rules.visitor.RuleNodeVisitor;

import java.util.HashMap;
import java.util.LinkedList;
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



    public SuccessNode(AbductiveFramework abductiveFramework, IASystemInferableInstance goal,
                       List<IASystemInferableInstance> restOfGoals, Store store, Map<VariableInstance,
                       IUnifiableAtomInstance> assignments) {
        super(abductiveFramework, goal, restOfGoals, store, assignments);
    }

    protected SuccessNode() {
    }

    @Override
    public RuleNode shallowClone() {
        throw new UnsupportedOperationException(); // TODO
    }

    public boolean equalitySolve()  {

        Map<VariableInstance, IUnifiableAtomInstance> newAssignments
                = new HashMap<VariableInstance, IUnifiableAtomInstance>(assignments);

        List<IEqualityInstance> equalities = new LinkedList<IEqualityInstance>(this.getStore().equalities);






          return false;


    }

    @Override
    public void acceptVisitor(RuleNodeVisitor v) throws DefinitionException {
        v.visit(this);
    }
}
