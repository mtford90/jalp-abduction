/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.unification;

import uk.co.mtford.abduction.logic.instance.ConstantInstance;
import uk.co.mtford.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.abduction.logic.instance.IAtomInstance;
import uk.co.mtford.abduction.logic.instance.VariableInstance;
import java.util.*;
import org.apache.log4j.Logger;
import uk.co.mtford.abduction.asystem.EqualityInstance;

/**
 *
 * @author mtford
 */
public class Unifier {
    
    private static final Logger LOGGER = Logger.getLogger(Unifier.class);
    
    public static boolean occurs(VariableInstance x, IAtomInstance t, Set<VariableInstance> sub) {
        
        String logHead = "occurs("+x+", "+t+"): ";
        LOGGER.info(logHead+"starting.");
        
        Stack<IAtomInstance> stack = new Stack<IAtomInstance>();
        stack.push(t);
        
        while (!stack.empty()) {
            t = stack.pop();
            if (t instanceof PredicateInstance) {
                PredicateInstance p = (PredicateInstance) t;
                stack.addAll((Arrays.asList(p.getParameters())));
            }
            else if (t instanceof VariableInstance) {
                VariableInstance y = (VariableInstance) t;
                if (x.equals(y)) {
                    LOGGER.info(logHead+"Finished. Yes it occurs.");
                    return true;
                }
                if (sub.contains(y)) {
                    stack.push(y.getValue());
                }
            }
            
	}
	LOGGER.info(logHead+"Finished. No it doesn't occur.");
	return false;
    }
    
    public static Set<VariableInstance> unifyReplace(IAtomInstance left, IAtomInstance right) throws CouldNotUnifyException {
        Set<VariableInstance> subst = new HashSet<VariableInstance>();
        unify(left,right,subst,new HashSet<EqualityInstance>());
        return subst; 
    }
    
    public static Set<EqualityInstance> unify(IAtomInstance left, IAtomInstance right) throws CouldNotUnifyException {
        // Clone in order to prevent altering.
        left = (IAtomInstance) left.clone();
        right = (IAtomInstance) right.clone();
        Set<EqualityInstance> equalities = new HashSet<EqualityInstance>();
        unify(left,right,new HashSet<VariableInstance>(),equalities);
        return equalities;
    }
    
    // TODO: Consider using a table instead....
    // TODO: Consider returning null rather than throwing exception. 
    //       Is it really an 'exceptional' event??
    
    /** Returns the substitutions that unify s and t. 
     * @param left
     * @param right
     * @return 
     */
    private static void unify(IAtomInstance left, IAtomInstance right, Set<VariableInstance> subst, Set<EqualityInstance> equalities) throws CouldNotUnifyException {
        
        
        
        String logHead = "unify("+left+", "+right+"): ";
        LOGGER.info(logHead+"starting.");
        
        // Pairs of formulae waiting to unified.
        Stack<IAtomInstance[]> stack = new Stack<IAtomInstance[]>();
        
        // The current pair we are unifying.
        IAtomInstance[] currentPair = new IAtomInstance[2];
        currentPair[0]=left;currentPair[1]=right;
        stack.push(currentPair);
        
        // Whilst we still have formulae to unify.
        while (!stack.empty()) {
            
            currentPair = stack.pop();
            left = currentPair[0];
            right = currentPair[1];
            
            if (left instanceof ConstantInstance && 
                right instanceof ConstantInstance) {
               if (left.equals(right)) {
                    return;
               } 
               else {
                   LOGGER.info("Couldn't unify. Different constants.");
                   throw new CouldNotUnifyException("Incompatible constants.");
               }
            }
            
            // Replace variables with their substitutions.
            if (left instanceof VariableInstance) {
                while (left instanceof VariableInstance) {
                    VariableInstance var = (VariableInstance)left;
                    if (!var.isAssigned()) {
                        break;
                    }
                    left = var.getValue();    
                }
            }
            if (right instanceof VariableInstance) {
                while (right instanceof VariableInstance) {
                    VariableInstance var = (VariableInstance)right;
                    if (!var.isAssigned()) {
                        break;
                    }
                    right = var.getValue();    
                }
            }
            
            // Perform unification
            if (!left.deepEquals(right)) {  
                
                // One formula is a variable.
                if (left instanceof VariableInstance) {
                    VariableInstance var = (VariableInstance) left;
                    if (!occurs(var,right,subst)) {
                        var.setValue(right);
                        subst.add(var);
                        equalities.add(new EqualityInstance(var,right));
                    }
                    else {
                        LOGGER.info("Couldn't unify. Occurs check failed.");
                        throw new CouldNotUnifyException();
                    }
                }
                else if (right instanceof VariableInstance) {
                    VariableInstance var = (VariableInstance) right;
                    if (!occurs(var,left,subst)) {
                        var.setValue(left);
                        subst.add(var);
                        equalities.add(new EqualityInstance(var,left));
                    }
                    else {
                        LOGGER.info("Couldn't unify. Occurs check failed.");
                        throw new CouldNotUnifyException();
                    }
                }
                
                // Both predicates
                else if (left instanceof PredicateInstance &&
                    right instanceof PredicateInstance) {
                    PredicateInstance leftPredicate = (PredicateInstance)left;
                    PredicateInstance rightPredicate = (PredicateInstance)right;
                    boolean sameName = leftPredicate.getName().equals(rightPredicate.getName());
                    boolean sameNumParams = leftPredicate.getNumParams()==rightPredicate.getNumParams();
                    if (sameName&&sameNumParams) {
                        IAtomInstance[] leftParamArray = leftPredicate.getParameters();
                        IAtomInstance[] rightParamArray = rightPredicate.getParameters();
                        int length = leftParamArray.length; // Both same length.
                        for (int i=0;i<length;i++) {
                            IAtomInstance[] unifyPair = new IAtomInstance[2];
                            unifyPair[0]=leftParamArray[i];
                            unifyPair[1]=rightParamArray[i];
                            stack.add(unifyPair);
                        }
                        
                    }
                    else {
                        LOGGER.info(logHead+"Could not unify. Predicates dont match.");
                        throw new CouldNotUnifyException("Incompatible predicates");
                    }
                }          
                
            }
            
        }
        
        LOGGER.info(logHead+"Successful.");
        
    }
       
 
}