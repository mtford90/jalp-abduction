package uk.co.mtford.alp.abduction.logic.instance;

/** Thrown when the goals passed to the getRootRuleNode methods is not headed by the inferable.
 *
 */
public class FaultyGoalsRuleNodeException extends Exception {

    public FaultyGoalsRuleNodeException() {
    }

    public FaultyGoalsRuleNodeException(String s) {
        super(s);
    }

    public FaultyGoalsRuleNodeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public FaultyGoalsRuleNodeException(Throwable throwable) {
        super(throwable);
    }

}
