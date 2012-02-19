/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.logic;

/**
 *
 * @author mtford
 */
public class Negation {
    IUnifiable formula;

    public Negation(IUnifiable formula) {
        this.formula = formula;
    }

    public IUnifiable getFormula() {
        return formula;
    }

    public void setFormula(IUnifiable formula) {
        this.formula = formula;
    }
    
    
}
