/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import java.util.List;
import java.util.Map;

/**
 *
 * @author mtford
 */
public interface ILogicInstance {
    @Override
    public boolean equals(Object obj);
    public boolean deepEquals(Object obj);
    public ILogicInstance clone(Map<String, VariableInstance> variablesSoFar);
    public List<VariableInstance> getVariables();

}
