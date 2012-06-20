package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 08/06/2012
 * Time: 16:13
 * To change this template use File | Settings | File Templates.
 */
public interface IEqualitySolver {
    public boolean execute(Map<VariableInstance, IUnifiableInstance> subst, List<EqualityInstance> equalities);
}
