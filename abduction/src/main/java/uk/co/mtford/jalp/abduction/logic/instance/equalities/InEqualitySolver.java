package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import org.apache.log4j.Logger;
import sun.tools.tree.ReturnStatement;
import uk.co.mtford.jalp.JALPException;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.TrueInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 08/06/2012
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class InEqualitySolver implements IInEqualitySolver {

    private static Logger LOGGER = Logger.getLogger(InEqualitySolver.class);


    @Override
    public List<IInferableInstance> execute(List<InEqualityInstance> inEqualities) throws JALPException {

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Executing inequality solver on "+inEqualities);

        List<IInferableInstance> result = new LinkedList<IInferableInstance>();

        for (InEqualityInstance inequality:inEqualities) {

            IUnifiableAtomInstance left = inequality.getEqualityInstance().getLeft();
            IUnifiableAtomInstance right = inequality.getEqualityInstance().getRight();
            List<EqualityInstance> reducedEqualities = new LinkedList<EqualityInstance>();
            reducedEqualities.add(inequality.getEqualityInstance());

            int previousNumReducedEqualities = 0;
            while (reducedEqualities.size()>previousNumReducedEqualities) {
                previousNumReducedEqualities = reducedEqualities.size();
                List<EqualityInstance> newEqualities = new LinkedList<EqualityInstance>();
                for (EqualityInstance equality:reducedEqualities) {
                    left = equality.getLeft();
                    right = equality.getRight();
                    List<EqualityInstance> reductionResult = left.reduce(right);
                    if (reductionResult.isEmpty()) newEqualities.add(equality);
                    else newEqualities.addAll(reductionResult);
                }
                reducedEqualities = newEqualities;
            }

            IInferableInstance inferable = new DenialInstance();
            for (EqualityInstance reducedEquality:reducedEqualities) {
                DenialInstance denial = (DenialInstance) inferable;
                HashMap<VariableInstance, IUnifiableAtomInstance> subst = new HashMap<VariableInstance,IUnifiableAtomInstance>();
                boolean unificationSuccess = reducedEquality.equalitySolve(subst);

                if (subst.size()>1) throw new JALPException("Reduce not working properly."); // Sanity check: Should be in reduced form, so only one substitution could occur.

                if (subst.size()==1) { // X=u or u=X
                    denial.getBody().add(reducedEquality);
                }
                else {
                    if (unificationSuccess) { // c=c and hence must carry on.
                        denial.getBody().add(new TrueInstance());
                    }
                    else { // d=c and hence this equality succeeds.
                        inferable = new TrueInstance();
                        break;
                    }
                }
            }
            result.add(inferable);

        }

        if (LOGGER.isDebugEnabled()) LOGGER.debug("Inequality solver computed: "+result);

        return result;

    }
}
