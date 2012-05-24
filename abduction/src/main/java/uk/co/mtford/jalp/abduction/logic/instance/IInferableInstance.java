/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.jalp.abduction.logic.instance;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.rules.RuleNode;

import java.util.List;

/**
 * @author mtford
 */
public interface IInferableInstance extends IFirstOrderLogicInstance {
    /**
     * Takes an abductive framework and a list of goals that doesn't include this inferable and produces
     * a root rule node for use in producing an ASystem derivation in a positive mode of reasoning.
     *
     * @param abductiveFramework,goals
     * @return A root rule node
     */
    public RuleNode getPositiveRootRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> goals);

    /**
     * Takes an abductive framework and a list of goals that includes this inferable and the nestedDenialsList that this
     * inferable came from which also includes this inferable and produces root rule node for use in
     * producing an ASystem derivation in a positive mode of reasoning.
     *
     * @param abductiveFramework,goals,nestedDenialsList
     *
     * @return A root rule node.
     */
    public RuleNode getNegativeRootRuleNode(AbductiveFramework abductiveFramework, List<DenialInstance> nestedDenials, List<IInferableInstance> goals);
}
