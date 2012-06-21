package uk.co.mtford.jalp.abduction.logic.instance.term;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import choco.kernel.model.variables.Variable;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.IAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

/**
 * Interface for terms i.e. variables, constants and functions.
 */
public interface ITermInstance extends IAtomInstance {
    /** Returns the choco representation of this term.
     *
     * @param possSubst
     * @param termToVarMap
     * @return
     */
    boolean reduceToChoco(List<Map<VariableInstance,IUnifiableInstance>> possSubst, HashMap<ITermInstance,Variable> termToVarMap);

    /** Returns true if this term is in the specified list.
     *
     * @param constantList
     * @param possSubst
     * @return
     */
    boolean inList(CharConstantListInstance constantList, List<Map<VariableInstance,IUnifiableInstance>> possSubst);

    /**
     * Returns the ruleNode for which this term is at the head of the current denial goal.
     * @param abductiveFramework
     * @param query
     * @param goals
     * @return
     */
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals);
}
