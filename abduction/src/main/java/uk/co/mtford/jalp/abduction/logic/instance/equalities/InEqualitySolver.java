package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import org.apache.log4j.Logger;
import org.javatuples.Pair;
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

    public List<Pair<List<EqualityInstance>,List<InEqualityInstance>>> execute(InEqualityInstance inEquality) throws JALPException {

            List<Pair<List<EqualityInstance>,List<InEqualityInstance>>> result = new LinkedList<Pair<List<EqualityInstance>, List<InEqualityInstance>>>();

            IUnifiableAtomInstance left;
            IUnifiableAtomInstance right;
            List<EqualityInstance> reducedEqualities = new LinkedList<EqualityInstance>();
            reducedEqualities.add(inEquality.getEqualityInstance());

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

            int numSolved = 0;
            LinkedList<EqualityInstance> equalities = new LinkedList<EqualityInstance>();
            LinkedList<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
            for (EqualityInstance reducedEquality:reducedEqualities) {
                HashMap<VariableInstance, IUnifiableAtomInstance> subst = new HashMap<VariableInstance,IUnifiableAtomInstance>();
                boolean unificationSuccess = reducedEquality.equalitySolve(subst);

                if (subst.size()>1) throw new JALPException("Reduce not working properly."); // Sanity check: Should be in reduced form, so only one substitution could occur.

                if (subst.size()==1) { // X=u or u=X
                    inequalities.add(new InEqualityInstance(reducedEquality));
                }
                else {
                    if (unificationSuccess) { // c=c and hence must carry on.
                    }
                    else { // d=c and hence this equality succeeds.
                        inequalities = new LinkedList<InEqualityInstance>();
                        break;
                    }
                }
                numSolved++;
            }

            if (numSolved == reducedEqualities.size()) {
                if (inequalities.isEmpty()) return null; // Failed.
            }

            for (int i=0;i<inequalities.size();i++) {
                LinkedList<EqualityInstance> newEqualities = new LinkedList<EqualityInstance>();
                LinkedList<InEqualityInstance> newInequalities = new LinkedList<InEqualityInstance>();
                for (int j=0;j<i;j++) {
                    newEqualities.add(inequalities.get(j).getEqualityInstance());
                }
                for(int j=i;j<inequalities.size();j++) {
                    newInequalities.add(inequalities.get(j));
                }
                result.add(new Pair<List<EqualityInstance>, List<InEqualityInstance>>(newEqualities,newInequalities));
            }

         return result;

    }
}
