package uk.co.mtford.jalp.abduction.logic.instance;

import java.util.List;
import java.util.Map;
import choco.kernel.model.variables.Variable;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.list.ConstantListInstance;
import uk.co.mtford.jalp.abduction.rules.F2RuleNode;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 23/05/2012
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public interface ITermInstance extends IAtomInstance {
    boolean reduceToChoco(List<Map<VariableInstance,IUnifiableAtomInstance>> possSubst, List<Variable> chocoVariables);
    boolean inList(ConstantListInstance constantList, List<Map<VariableInstance,IUnifiableAtomInstance>> possSubst);
    public RuleNode getNegativeRootRuleNode(IInferableInstance newGoal, AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenials, List<IInferableInstance> goals);
}
