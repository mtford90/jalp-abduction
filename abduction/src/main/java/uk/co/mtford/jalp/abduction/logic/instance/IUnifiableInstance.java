package uk.co.mtford.jalp.abduction.logic.instance;

import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * A unifiable is an instance that can be unified with another unifiable.
 *
 * e.g. p(X) unified with Z would generate a substitution Z/p(X)
 *
 */
public interface IUnifiableInstance extends IFirstOrderLogicInstance {

    /** Performs one level of unification but doesn't perform any substitutions.
     *  e.g. X reduce Y would return X == Y
     *  e.g. p(X,a) reduce p(b,a) would return X==b.
     *
     * @param other
     * @return
     */
    public List<EqualityInstance> reduce(VariableInstance other);

    /** Performs one level of unification but doesn't perform any substitutions.
     *  e.g. X reduce Y would return X == Y
     *  e.g. p(X,a) reduce p(b,a) would return X==b.
     *
     * @param other
     * @return
     */
    public List<EqualityInstance> reduce(ConstantInstance other);

    /** Performs one level of unification but doesn't perform any substitutions.
     *  e.g. X reduce Y would return X == Y
     *  e.g. p(X,a) reduce p(b,a) would return X==b.
     *
     * @param other
     * @return
     */
    public List<EqualityInstance> reduce(PredicateInstance other);

    /** Performs one level of unification but doesn't perform any substitutions.
     *  e.g. X reduce Y would return X == Y
     *  e.g. p(X,a) reduce p(b,a) would return X==b.
     *
     * @param other
     * @return
     */
    public List<EqualityInstance> reduce(IUnifiableInstance other);

    /** Calls back the other unifiable instance
     *
     * @param unifiable
     * @return
     */
    public List<EqualityInstance> acceptReduceVisitor (IUnifiableInstance unifiable);

    /** Unifies this unifiable with an other unifiable.
     *
     *  e.g. X unify Y would return true and place a substitution X/Y
     *
     * @param other
     * @param assignment
     * @return whether unification was successful
     */
    public boolean unify(VariableInstance other,
                                  Map<VariableInstance, IUnifiableInstance> assignment);

    /** Unifies this unifiable with an other unifiable.
     *
     *  e.g. X unify Y would return true and place a substitution X/Y
     *
     * @param other
     * @param assignment
     * @return whether unification was successful
     */
    public boolean unify(ConstantInstance other,
                                  Map<VariableInstance, IUnifiableInstance> assignment);

    /** Unifies this unifiable with an other unifiable.
     *
     *  e.g. X unify Y would return true and place a substitution X/Y
     *
     * @param other
     * @param assignment
     * @return whether unification was successful
     */
    public boolean  unify(PredicateInstance other,
                                   Map<VariableInstance, IUnifiableInstance> assignment);

    /** Unifies this unifiable with an other unifiable.
     *
     *  e.g. X unify Y would return true and place a substitution X/Y
     *
     * @param other
     * @param assignment
     * @return whether unification was successful
     */
    public boolean  unify(IUnifiableInstance other,
                          Map<VariableInstance, IUnifiableInstance> assignment);

    /** Calls back the other unifiable instance
     *
     * @param unifiable
     * @param assignment
     * @return
     */
    public boolean  acceptUnifyVisitor(IUnifiableInstance unifiable,
                                       Map<VariableInstance, IUnifiableInstance> assignment);

}
