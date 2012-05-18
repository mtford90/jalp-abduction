package uk.co.mtford.alp.abduction.rules;

import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.logic.instance.IASystemInferable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 06:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class PositiveRuleNode extends RuleNode {

    public PositiveRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        super(abductiveFramework, goals);
    }
}
