/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.alp.abduction.logic.instance;

import org.apache.log4j.Logger;

/**
 *
 * @author mtford
 */
public class ConstantInstance implements ITermInstance {
    
    private static final Logger LOGGER = Logger.getLogger(ConstantInstance.class);
    
    private String value;

    public ConstantInstance(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
