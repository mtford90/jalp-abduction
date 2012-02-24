/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

/**
 *
 * @author mtford
 */
public class NegationInstance {
    IUnifiableInstance formula;

    public NegationInstance(IUnifiableInstance formula) {
        this.formula = formula;
    }

    public IUnifiableInstance getFormula() {
        return formula;
    }

    public void setFormula(IUnifiableInstance formula) {
        this.formula = formula;
    }
    
    
}
