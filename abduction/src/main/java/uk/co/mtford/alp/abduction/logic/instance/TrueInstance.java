/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.rules.NegativeTrueRuleNode;
import uk.co.mtford.alp.abduction.rules.PositiveTrueRuleNode;
import uk.co.mtford.alp.abduction.rules.RuleNode;

import java.util.List;

/**
 *
 * @author mtford
 */
public class TrueInstance implements IASystemInferable {

    @Override
    public String toString() {
        return "TRUE";
    }

    @Override
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        return new PositiveTrueRuleNode(abductiveFramework,goals);
    }

    @Override
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IASystemInferable> goals) {
        return new NegativeTrueRuleNode(abductiveFramework,goals);
    }
}
