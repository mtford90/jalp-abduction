package uk.co.mtford.alp.abduction.logic.instance;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 19/05/2012
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
public interface IFirstOrderLogicInstance {

    /**
     * Returns a deepClone of the formula, performing all substitutions specified by the mapping.
     *
     * @param substitutions
     * @return
     */
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableAtomInstance> substitutions);

    /**
     * Returns a deepClone of the formula, performing all substitutions specified by the mapping, as well as creating
     * new instances of all variables. As such, new variable substitutions are added to the passed parameter.
     *
     * @param substitutions
     * @return
     */
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableAtomInstance> substitutions);

    public IFirstOrderLogicInstance shallowClone();

    /**
     * Returns a list of all variables nested in this first order logic formula.
     *
     * @return
     */
    public Set<VariableInstance> getVariables();
}
