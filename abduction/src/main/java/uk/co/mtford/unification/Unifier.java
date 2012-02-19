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
    
    public boolean occurs(Variable x, IUnifiable t, Set<Variable> sub) {
        
        Stack<IUnifiable> stack = new Stack<IUnifiable>();
        stack.push(t);
        
        while (!stack.empty()) {
            t = stack.pop();
            if (t instanceof AbstractPredicate) {
                AbstractPredicate p = (AbstractPredicate) t;
                stack.addAll(Arrays.asList(p.getParameters()));
            }
            else if (t instanceof Variable) {
                Variable y = (Variable) t;
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
    
    /** Returns the substitutions that unify s and t. 
     * @param left
     * @param right
     * @return 
     */
    public Set<Variable> unify(IUnifiable left, IUnifiable right) throws CouldNotUnifyException {
        
        Set<Variable> subst = new HashSet<Variable>();
        
        // Pairs of formulae waiting to unified.
        Stack<IUnifiable[]> stack = new Stack<IUnifiable[]>();
        
        // The current pair we are unifying.
        IUnifiable[] currentPair = new IUnifiable[2];
        currentPair[0]=left;currentPair[1]=right;
        stack.push(currentPair);
        
        // Whilst we still have formulae to unify.
        while (!stack.empty()) {
            
            currentPair = stack.pop();
            left = currentPair[0];
            right = currentPair[1];
            
            if (left instanceof Constant && 
                right instanceof Constant) {
               if (left.equals(right)) {
                   return subst;
               } 
               else {
                   throw new CouldNotUnifyException("Incompatible constants.");
               }
            }
            
            // Replace variables with their substitutions.
            if (left instanceof Variable) {
                while (left instanceof Variable) {
                    Variable var = (Variable)left;
                    if (!var.isAssigned()) {
                        break;
                    }
                    left = var.getValue();    
                }
            }
            if (right instanceof Variable) {
                while (right instanceof Variable) {
                    Variable var = (Variable)right;
                    if (!var.isAssigned()) {
                        break;
                    }
                    right = var.getValue();    
                }
            }
            
            // Perform unification
            if (!left.equals(right)) {  
                
                // One formula is a variable.
                if (left instanceof Variable) {
                    Variable var = (Variable) left;
                    if (!occurs(var,right,subst)) {
                        var.setValue(right);
                        subst.add(var);
                    }
                    else {
                        throw new CouldNotUnifyException();
                    }
                }
                else if (right instanceof Variable) {
                    Variable var = (Variable) right;
                    if (!occurs(var,left,subst)) {
                        var.setValue(left);
                        subst.add(var);
                    }
                    else {
                        throw new CouldNotUnifyException();
                    }
                }
                
                // Both predicates
                else if (left instanceof AbstractPredicate &&
                    right instanceof AbstractPredicate) {
                    AbstractPredicate leftPredicate = (AbstractPredicate)left;
                    AbstractPredicate rightPredicate = (AbstractPredicate)right;
                    boolean sameName = leftPredicate.getName().equals(rightPredicate.getName());
                    boolean sameNumParams = leftPredicate.getNumParams()==rightPredicate.getNumParams();
                    if (sameName&&sameNumParams) {
                        IUnifiable[] leftParamArray = leftPredicate.getParameters();
                        IUnifiable[] rightParamArray = rightPredicate.getParameters();
                        int length = leftParamArray.length; // Both same length.
                        for (int i=0;i<length;i++) {
                            IUnifiable[] unifyPair = new IUnifiable[2];
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private class Substitution {
        private Variable v;
        private IUnifiable i;

        public Substitution(Variable v, IUnifiable i) {
            this.v = v;
            this.i = i;
        }

        public IUnifiable getI() {
            return i;
        }

        public void setI(IUnifiable i) {
            this.i = i;
        }

        public Variable getV() {
            return v;
        }

        public void setV(Variable v) {
            this.v = v;
        }
        
    }
              
   
   
    
 
    

 
}