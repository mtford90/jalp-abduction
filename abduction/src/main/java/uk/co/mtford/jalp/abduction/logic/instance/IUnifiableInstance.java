package uk.co.mtford.jalp.abduction.logic.instance;

import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
public interface IUnifiableInstance extends IAtomInstance {

    public List<EqualityInstance> reduce(VariableInstance other);
    public List<EqualityInstance> reduce(ConstantInstance other);
    public List<EqualityInstance> reduce(PredicateInstance other);
    public List<EqualityInstance> reduce(IUnifiableInstance other);
    public List<EqualityInstance> acceptReduceVisitor (IUnifiableInstance unifiable);

    public boolean unify(VariableInstance other,
                                  Map<VariableInstance, IUnifiableInstance> assignment);
    public boolean unify(ConstantInstance other,
                                  Map<VariableInstance, IUnifiableInstance> assignment);
    public boolean  unify(PredicateInstance other,
                                   Map<VariableInstance, IUnifiableInstance> assignment);
    public boolean  unify(IUnifiableInstance other,
                          Map<VariableInstance, IUnifiableInstance> assignment);
    public boolean  acceptUnifyVisitor(IUnifiableInstance unifiable,
                                       Map<VariableInstance, IUnifiableInstance> assignment);

}
