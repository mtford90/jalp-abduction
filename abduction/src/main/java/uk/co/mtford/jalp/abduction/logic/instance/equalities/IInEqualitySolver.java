package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import org.javatuples.Pair;
import uk.co.mtford.jalp.JALPException;

import java.util.List;

/**
 * Interface representing methods that an inequality solver should have.
 */
public interface IInEqualitySolver {
    /**
     * Takes the inequality and returns a list of pairs of equality and inequalities that represent a possible
     * combination that must be true to make the inequality true.
     *
     * @param inEquality
     * @return
     * @throws JALPException
     */
    public List<Pair<List<EqualityInstance>,List<InEqualityInstance>>> execute(InEqualityInstance inEquality) throws JALPException;
}
