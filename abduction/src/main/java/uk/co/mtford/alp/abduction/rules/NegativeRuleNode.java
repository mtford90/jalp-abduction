package uk.co.mtford.alp.abduction.rules;

import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.alp.abduction.logic.instance.IASystemInferable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 06:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class NegativeRuleNode extends RuleNode {
    protected DenialInstance denial;

    public NegativeRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        super(abductiveFramework, goals);
        denial=(DenialInstance) goals.remove(0);
        goals.add(denial.getBody().remove(0));
    }
}
