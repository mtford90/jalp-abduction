/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.List;

/**
 * An 'inferable' is a first order logic instance that we can apply an ASystem inference rule to.
 *
 * @author mtford
 */
public interface IInferableInstance extends IFirstOrderLogicInstance {
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals);
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> goals);
}
