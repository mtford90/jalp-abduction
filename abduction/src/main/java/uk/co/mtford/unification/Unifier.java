/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.unification;

import java.util.*;
import uk.co.mtford.abduction.logic.*;

/**
 *
 * @author mtford
 */
public class Unifier {
    
    public static boolean occurs(VariableInstance x, IUnifiableInstance t, Set<VariableInstance> sub) {
        
        Stack<IUnifiableInstance> stack = new Stack<IUnifiableInstance>();
        stack.push(t);
        
        while (!stack.empty()) {
            t = stack.pop();
            if (t instanceof AbstractPredicateInstance) {
                AbstractPredicateInstance p = (AbstractPredicateInstance) t;
                stack.addAll(Arrays.asList(p.getParameters()));
            }
            else if (t instanceof VariableInstance) {
                VariableInstance y = (VariableInstance) t;
                if (x.equals(y)) {
                    return true;
                }
                if (sub.contains(y)) {
                    stack.push(y.getValue());
                }
            }
            
	}
	
	return false;
    }
    
    // TODO: Consider using a table instead....
    // TODO: Consider returning null rather than throwing exception. 
    //       Is it really an 'exceptional' event??
    
    /** Returns the substitutions that unify s and t. 
     * @param left
     * @param right
     * @return 
     */
    public static Set<VariableInstance> unify(IUnifiableInstance left, IUnifiableInstance right) throws CouldNotUnifyException {
        
        Set<VariableInstance> subst = new HashSet<VariableInstance>();
        
        // Pairs of formulae waiting to unified.
        Stack<IUnifiableInstance[]> stack = new Stack<IUnifiableInstance[]>();
        
        // The current pair we are unifying.
        IUnifiableInstance[] currentPair = new IUnifiableInstance[2];
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
                   return subst;
               } 
               else {
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
                    }
                    else {
                        throw new CouldNotUnifyException();
                    }
                }
                else if (right instanceof VariableInstance) {
                    VariableInstance var = (VariableInstance) right;
                    if (!occurs(var,left,subst)) {
                        var.setValue(left);
                        subst.add(var);
                    }
                    else {
                        throw new CouldNotUnifyException();
                    }
                }
                
                // Both predicates
                else if (left instanceof AbstractPredicateInstance &&
                    right instanceof AbstractPredicateInstance) {
                    AbstractPredicateInstance leftPredicate = (AbstractPredicateInstance)left;
                    AbstractPredicateInstance rightPredicate = (AbstractPredicateInstance)right;
                    boolean sameName = leftPredicate.getName().equals(rightPredicate.getName());
                    boolean sameNumParams = leftPredicate.getNumParams()==rightPredicate.getNumParams();
                    if (sameName&&sameNumParams) {
                        IUnifiableInstance[] leftParamArray = leftPredicate.getParameters();
                        IUnifiableInstance[] rightParamArray = rightPredicate.getParameters();
                        int length = leftParamArray.length; // Both same length.
                        for (int i=0;i<length;i++) {
                            IUnifiableInstance[] unifyPair = new IUnifiableInstance[2];
                            unifyPair[0]=leftParamArray[i];
                            unifyPair[1]=rightParamArray[i];
                            stack.add(unifyPair);
                        }
                        
                    }
                    else {
                        throw new CouldNotUnifyException("Incompatible predicates");
                    }
                }          
                
            }
            
        }
          
        return subst;
        
    }
       
 
}