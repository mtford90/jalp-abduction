package uk.co.mtford.jalp.abduction.logic.instance;

import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.Map;
import java.util.Set;

/**
 * Root class for first order logic representations.
 */
public interface IFirstOrderLogicInstance {

    /**
     * Returns a deepClone of the formula, performing all substitutions specified by the mapping.
     *
     * @param substitutions
     * @return This object.
     */
    public IFirstOrderLogicInstance performSubstitutions(Map<VariableInstance, IUnifiableInstance> substitutions);

    /**
     * Returns a deepClone of the formula, performing all substitutions specified by the mapping, as well as creating
     * new instances of all variables. As such, new variable substitutions are added to the passed parameter.
     *
     * @param substitutions
     * @return The clone.
     */
    public IFirstOrderLogicInstance deepClone(Map<VariableInstance, IUnifiableInstance> substitutions);

    /** Returns a shallow clone of this object i.e. internal structure references are the same.
     *
     * @return The clone.
     */
    public IFirstOrderLogicInstance shallowClone();

    /**
     * Returns a list of all variables nested in this first order logic formula.
     *
     * @return A set of nested variables.
     */
    public Set<VariableInstance> getVariables();

}
