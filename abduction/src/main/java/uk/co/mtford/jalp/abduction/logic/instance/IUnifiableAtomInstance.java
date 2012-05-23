package uk.co.mtford.jalp.abduction.logic.instance;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
public interface IUnifiableAtomInstance extends IAtomInstance {

    public List<IEqualitySolverResultInstance> reduce(VariableInstance other);

    public List<IEqualitySolverResultInstance> reduce(ConstantInstance other);

    public List<IEqualitySolverResultInstance> reduce(PredicateInstance other);

    public List<IEqualitySolverResultInstance> reduce(IUnifiableAtomInstance other);

    public List<IEqualitySolverResultInstance> acceptReduceVisitor (IUnifiableAtomInstance unifiableAtom);


    public boolean unify(VariableInstance other,
                                  Map<VariableInstance, IUnifiableAtomInstance> assignment);

    public boolean unify(ConstantInstance other,
                                  Map<VariableInstance, IUnifiableAtomInstance> assignment);

    public boolean  unify(PredicateInstance other,
                                   Map<VariableInstance, IUnifiableAtomInstance> assignment);

    public boolean  unify(IUnifiableAtomInstance other,
                          Map<VariableInstance, IUnifiableAtomInstance> assignment);

    public boolean  acceptUnifyVisitor(IUnifiableAtomInstance unifiableAtom,
                                       Map<VariableInstance, IUnifiableAtomInstance> assignment);


}
