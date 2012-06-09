package uk.co.mtford.jalp.abduction.logic.instance.term;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import choco.kernel.model.variables.Variable;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 23/05/2012
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public interface ITermInstance extends IAtomInstance {
    boolean reduceToChoco(List<Map<VariableInstance,IUnifiableAtomInstance>> possSubst, HashMap<ITermInstance,Variable> termToVarMap);
    boolean inList(CharConstantListInstance constantList, List<Map<VariableInstance,IUnifiableAtomInstance>> possSubst);
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> goals);
}
