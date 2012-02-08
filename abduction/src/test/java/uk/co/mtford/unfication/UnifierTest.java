/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.unfication;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import uk.co.mtford.abduction.logic.*;
import uk.co.mtford.unfication.CouldNotUnifyException;

/**
 *
 * @author mtford
 */
public class UnifierTest {
    
    private static Unifier unifier;
    
    public UnifierTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        unifier = new Unifier();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /** Returns whether or not a variable exists within a substitution. */
    private boolean subContainsVariable(Variable X, Set<Variable> subst) {
        LinkedList<Variable> subList = new LinkedList<Variable>(subst);
        for (int i=0;i<subList.size();i++) {
            if (subList.get(i).equals(X)) {
                return true;
            }
        }
        return false;
    }
    
    /** If available returns version of the variable that was substituted. */
    private Variable getSubstitutedVariable(Variable X, Set<Variable> subst) {
        LinkedList<Variable> subList = new LinkedList<Variable>(subst);
        for (int i=0;i<subList.size();i++) {
            if (subList.get(i).equals(X)) {
                return subList.get(i);
            }
        }
        return null;
    }
    
    /** unify(a,a) = {} */
    @Test
    public void simpleConstantTestA() throws CouldNotUnifyException {
        Constant a = new Constant("a");
        Set<Variable> sub = unifier.unify(a,a);
        assertTrue(sub.isEmpty());
    }
    
    /** unify(a,b) = FAIL */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleConstantTestB1() throws CouldNotUnifyException {
        Constant a = new Constant("a");
        Constant b = new Constant("b");
        Set<Variable> sub = unifier.unify(a,b);
    }
    
    /** unify(b,a) = FAIL */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleConstantTestB2() throws CouldNotUnifyException {
        Constant a = new Constant("a");
        Constant b = new Constant("b");
        Set<Variable> sub = unifier.unify(b,a);
    }
    
    /** unify(X,X) = {} */
    @Test
    public void simpleVariableTestA() throws CouldNotUnifyException {
       Variable X = new Variable("X");
       Set<Variable> sub = unifier.unify(X,X);
       assertTrue(sub.isEmpty());
    }
    
    /** unify(X,Y) = {X/Y} */
    @Test
    public void simpleVariableTestB1() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("Y");
       
        Set<Variable> sub = unifier.unify(X, Y);
        
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub));
    }
    
    /** unify(Y,X) = {Y/X} */
    @Test
    public void simpleVariableTestB2() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("Y");
       
        Set<Variable> sub = unifier.unify(Y, X);
        
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(Y, sub));
    }
    
    
    
    /** unify(X,a) = {X/a} */
    @Test
    public void simpleVariableConstantTest1() throws CouldNotUnifyException {
        final Constant a = new Constant("a");
        final Variable X = new Variable("X");
       
        Set<Variable> sub = unifier.unify(X, a);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(a));
    }
    
    /** unify(a,X) = {X/a} */
    @Test
    public void simpleVariableConstantTest2() throws CouldNotUnifyException {
        final Constant a = new Constant("a");
        final Variable X = new Variable("X");
       
        Set<Variable> sub = unifier.unify(a,X);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(a));
    }
    
    /** unify(f(a,X),f(a,b)) = {X/b} */
    @Test
    public void simpleFunctionTestA1() throws CouldNotUnifyException {
        final Constant a = new Constant("a");
        final Constant b = new Constant("b");
        final Variable X = new Variable("X");
        final Function f1 = new Function("f",a,X);
        final Function f2 = new Function("f",a,b);
       
        Set<Variable> sub = unifier.unify(f1, f2);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(b));
    }
    
    /** unify(f(a,b),f(a,X) = {X/b} */
    @Test
    public void simpleFunctionTestA2() throws CouldNotUnifyException {
        final Constant a = new Constant("a");
        final Constant b = new Constant("b");
        final Variable X = new Variable("X");
        final Function f1 = new Function("f",a,X);
        final Function f2 = new Function("f",a,b);
       
        Set<Variable> sub = unifier.unify(f2, f1);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(b));
    }
    
    /** unify(f(a),g(a)) = FAIL */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionTestB1() throws CouldNotUnifyException {
        final Constant a = new Constant("a");
        final Function f = new Function("f",a);
        final Function g = new Function("g",a);
       
        Set<Variable> sub = unifier.unify(f,g);
    }
    
    /** unify(f(a),g(a)) = FAIL */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionTestB2() throws CouldNotUnifyException {
        final Constant a = new Constant("a");
        final Function f = new Function("f",a);
        final Function g = new Function("g",a);
       
        Set<Variable> sub = unifier.unify(g,f);
    }
    
    /** unify(f(X),f(Y)) = {X/Y} */
    @Test
    public void simpleFunctionTestC1() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("Y");
        final Function f1 = new Function("f",X);
        final Function f2 = new Function("f",Y); 
        Set<Variable> sub = unifier.unify(f1,f2);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(Y));
    }
    
    /** unify(f(Y),f(X)) = {Y/X} */
    @Test
    public void simpleFunctionTestC2() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("Y");
        final Function f1 = new Function("f",X);
        final Function f2 = new Function("f",Y); 
        Set<Variable> sub = unifier.unify(f2,f1);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(Y, sub)&&getSubstitutedVariable(Y, sub).getValue().equals(X));
    }
    
    /** unify(f(X),g(Y)) = FAIL */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionTestD1() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("X");
        final Function f = new Function("f",X);
        final Function g = new Function("g",Y); 
        Set<Variable> sub = unifier.unify(f,g);
    }
    
    /** unify(g(Y),f(X)) = FAIL */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionTestD2() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("X");
        final Function f = new Function("f",X);
        final Function g = new Function("g",Y); 
        Set<Variable> sub = unifier.unify(g,f);
    }
    
    /** unify(f(X),g(Y)) = FAIL */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionTestE1() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("X");
        final Variable Z = new Variable("X");
        final Function f1 = new Function("f",X);
        final Function f2 = new Function("f",Y,Z); 
        Set<Variable> sub = unifier.unify(f1,f2);
    }
    
    /** unify(g(Y),f(X)) = FAIL */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionTestE2() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("X");
        final Variable Z = new Variable("X");
        final Function f1 = new Function("f",X);
        final Function f2 = new Function("f",Y,Z); 
        Set<Variable> sub = unifier.unify(f2,f1);
    }
    
    /** unify(f(g(X)),f(Y)) = {Y/g(X)} */
    @Test
    public void simpleFunctionUnificationTest1() throws CouldNotUnifyException {
        Variable X = new Variable("X");
        Function g = new Function("g",X);
        Variable Y = new Variable("Y");
        Set<Variable> sub1 = unifier.unify(new Function("f",g),
                                                      new Function("f",Y));
        assertTrue(sub1.size()==1);
        assertTrue(subContainsVariable(Y, sub1));
        assertTrue(getSubstitutedVariable(Y, sub1).getValue().equals(g));
    }
    
    /** unify(f(X),X) = FAIL (due to occurs check) */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionAndVariableUnificationTest1() throws CouldNotUnifyException {
        Variable X = new Variable("X");
        Function f = new Function("f",X);
        Set<Variable> sub1 = unifier.unify(f,X);
        sub1 = unifier.unify(f,X);
    }
    
    /** unify(X,f(X)) = FAIL (due to occurs check) */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionAndVariableUnificationTest2() throws CouldNotUnifyException {
        Variable X = new Variable("X");
        Function f = new Function("f",X);
        Set<Variable> sub1 = unifier.unify(X,f);
        sub1 = unifier.unify(f,X);
    }
    
    /** unify(f(g(X),X),f(Y,a)) = {X/a,Y/g(a)} */
    @Test
    public void complexFunctionUnificationTest1() throws CouldNotUnifyException {
        Variable X = new Variable("X");
        Constant a = new Constant("a");
        Function gOfX = new Function("g",X);
        Function gOfa = new Function("g",a);
        Variable Y = new Variable("Y");
        
        Set<Variable> sub1 = unifier.unify(new Function("f",gOfX, X),
                                                      new Function("f",Y,a));
        assertTrue(sub1.size()==2);
        assertTrue(subContainsVariable(X, sub1)&&subContainsVariable(Y, sub1));
        assertTrue(getSubstitutedVariable(X, sub1).getValue().equals(a));
        assertTrue(getSubstitutedVariable(Y, sub1).getValue().equals(gOfa)); 
    }
    
     /** unify(f(Y,a),f(g(X),X)) = {X/a,Y/g(a)} */
    @Test
    public void complexFunctionUnificationTest2() throws CouldNotUnifyException {
        Variable X = new Variable("X");
        Constant a = new Constant("a");
        Function gOfX = new Function("g",X);
        Function gOfa = new Function("g",a);
        Variable Y = new Variable("Y");

        Set<Variable> sub1 = unifier.unify(new Function("f",Y, a),
                                                      new Function("f",gOfX, X));
        assertTrue(sub1.size()==2);
        assertTrue(subContainsVariable(X, sub1)&&subContainsVariable(Y, sub1));
        assertTrue(getSubstitutedVariable(X, sub1).getValue().equals(a));
        assertTrue(getSubstitutedVariable(Y, sub1).getValue().equals(gOfa)); 
    }
    
    /** unify(Knows(John,x),Knows(John,Jane)) = {x/Jane} */
    @Test
    public void simplePredicateTest1() throws CouldNotUnifyException {
        Constant John = new Constant("John");
        Constant Jane = new Constant("Jane");
        
        Variable x = new Variable("x");
        
        Set<Variable> sub = unifier.unify(new Predicate("Knows", John, x), new Predicate("Knows", John, Jane));
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(x, sub)&&getSubstitutedVariable(x, sub).getValue().equals(Jane));
    }
    
    /** unify(Knows(John,Jane),Knows(John,x)) = {x/Jane} */
    @Test
    public void simplePredicateTest2() throws CouldNotUnifyException {
        Constant John = new Constant("John");
        Constant Jane = new Constant("Jane");
        Variable x = new Variable("x");
        Set<Variable> sub = unifier.unify(new Predicate("Knows", John, Jane),new Predicate("Knows", John, x));
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(x, sub)&&getSubstitutedVariable(x, sub).getValue().equals(Jane));
    }
    
    /** occurs(A,P(A,B)) should return true */
    @Test
    public void predicateOccursTestA() {
                Variable A = new Variable("A");
        Variable B = new Variable("B");
                Predicate p = new Predicate("p", A, B);
        assertTrue(unifier.occurs(A, p, new HashSet<Variable>()));
    }
    
     /** occurs(B,P(A,B)) should return true */
    @Test
    public void predicateOccursTestB() {
                Variable A = new Variable("A");
        Variable B = new Variable("B");
        Predicate p = new Predicate("p", A, B);
        assertTrue(unifier.occurs(B, p, new HashSet<Variable>()));
    }
    
     /** occurs(C,P(A,B)) should return false */
    @Test
    public void predicateOccursTestC() {
                Variable A = new Variable("A");
        Variable B = new Variable("B");
        Variable C = new Variable("C");
        Predicate p = new Predicate("p", A, B);
        assertFalse(unifier.occurs(C, p, new HashSet<Variable>()));
    }
    
}
