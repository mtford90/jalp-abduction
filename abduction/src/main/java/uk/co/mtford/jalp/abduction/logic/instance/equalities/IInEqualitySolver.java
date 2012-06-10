package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import org.javatuples.Pair;
import uk.co.mtford.jalp.JALPException;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 08/06/2012
 * Time: 16:43
 * To change this template use File | Settings | File Templates.
 */
public interface IInEqualitySolver {
    public List<Pair<List<EqualityInstance>,List<InEqualityInstance>>> execute(InEqualityInstance inEquality) throws JALPException;
}
