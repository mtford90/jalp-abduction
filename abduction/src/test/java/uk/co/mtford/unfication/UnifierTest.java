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
    
    private static Predicate Knows;
    private static Constant John;
    private static Constant Jane;
    private static Variable x;
    
    private static Variable A;
    private static Variable B;
    private static Variable C;
    private static Predicate p;
    private static Predicate q;
    
    public UnifierTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        unifier = new Unifier();
        Knows = new Predicate("Knows", John, x);
        John = new Constant("John");
        Jane = new Constant("Jane");
        x = new Variable("x");
        
        A = new Variable("A");
        B = new Variable("B");
        C = new Variable("C");
        p = new Predicate("p", A, B);
        q = new Predicate("q", C);
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
    public void simpleConstantTestB() throws CouldNotUnifyException {
        Constant a = new Constant("a");
        Constant b = new Constant("b");
        Set<Variable> sub = unifier.unify(a,b);
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
    public void simpleVariableTestB() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("Y");
       
        Set<Variable> sub = unifier.unify(X, Y);
        
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub));
    }
    
    
    
    /** unify(X,a) = {X/a} */
    @Test
    public void simpleVariableConstantTest() throws CouldNotUnifyException {
        final Constant a = new Constant("a");
        final Variable X = new Variable("X");
       
        Set<Variable> sub = unifier.unify(X, a);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(a));
    }
    
    /** unify(f(a,X),f(a,b) = {X/b} */
    @Test
    public void simpleFunctionTestA() throws CouldNotUnifyException {
        final Constant a = new Constant("a");
        final Constant b = new Constant("b");
        final Variable X = new Variable("X");
        final Function f1 = new Function("f",a,X);
        final Function f2 = new Function("f",a,b);
       
        Set<Variable> sub = unifier.unify(f1, f2);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(b));
    }
    
    /** unify(f(a),g(a)) = FAIL */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionTestB() throws CouldNotUnifyException {
        final Constant a = new Constant("a");
        final Function f = new Function("f",a);
        final Function g = new Function("g",a);
       
        Set<Variable> sub = unifier.unify(f,g);
    }
    
    /** unify(f(X),f(Y)) = {X/Y} */
    @Test
    public void simpleFunctionTestC() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("Y");
        final Function f1 = new Function("f",X);
        final Function f2 = new Function("f",Y); 
        Set<Variable> sub = unifier.unify(f1,f2);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(Y));
    }
    
    /** unify(f(X),g(Y)) = FAIL */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionTestD() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("X");
        final Function f = new Function("f",X);
        final Function g = new Function("g",Y); 
        Set<Variable> sub = unifier.unify(f,g);
    }
    
    /** unify(f(X),g(Y)) = FAIL */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionTestE() throws CouldNotUnifyException {
        final Variable X = new Variable("X");
        final Variable Y = new Variable("X");
        final Variable Z = new Variable("X");
        final Function f1 = new Function("f",X);
        final Function f2 = new Function("f",Y,Z); 
        Set<Variable> sub = unifier.unify(f1,f2);
    }
    
    /** unify(f(g(X)),f(Y)) = {Y/g(X)} */
    @Test
    public void simpleFunctionUnificationTest() throws CouldNotUnifyException {
        Variable X = new Variable("X");
        Function g = new Function("g",X);
        Variable Y = new Variable("Y");
        Set<Variable> sub1 = unifier.unify(new Function("f",g),
                                                      new Function("f",Y));
        Set<Variable> sub2 = unifier.unify(new Function("f",Y),
                                                      new Function("f",g));
        assertTrue(sub1.size()==1&&sub2.size()==1);
        assertTrue(subContainsVariable(Y, sub1)&&subContainsVariable(Y, sub2));
        assertTrue(getSubstitutedVariable(Y, sub1).getValue().equals(g)&&getSubstitutedVariable(Y, sub2).getValue().equals(g));
    }
    
    /** unify(X,f(X)) = FAIL (due to occurs check) */
    @Test(expected=CouldNotUnifyException.class)
    public void simpleFunctionAndVariableUnificationTest() throws CouldNotUnifyException {
        Variable X = new Variable("X");
        Function f = new Function("f",X);
        Set<Variable> sub1 = unifier.unify(X,f);
        sub1 = unifier.unify(f,X);
    }
    
    /** unify(f(g(X),X),f(Y,a)) = {X/a,Y/g(a)} */
    @Test
    public void complexFunctionUnificationTest() throws CouldNotUnifyException {
        Variable X = new Variable("X");
        Constant a = new Constant("a");
        Function gOfX = new Function("g",X);
        Function gOfa = new Function("g",a);
        Variable Y = new Variable("Y");
        
        Set<Variable> sub1 = unifier.unify(new Function("f",gOfX, X),
                                                      new Function("f",Y,a));
        Set<Variable> sub2 = unifier.unify(new Function("f",Y, a),
                                                      new Function("f",gOfX, X));
        assertTrue(sub1.size()==2);
        assertTrue(sub2.size()==2);
        assertTrue(subContainsVariable(X, sub1)&&subContainsVariable(Y, sub1));
        assertTrue(subContainsVariable(X, sub2)&&subContainsVariable(Y, sub2));
        assertTrue(getSubstitutedVariable(X, sub1).getValue().equals(a));
        assertTrue(getSubstitutedVariable(Y, sub1).getValue().equals(gOfa)); 
        assertTrue(getSubstitutedVariable(X, sub2).getValue().equals(a));
        assertTrue(getSubstitutedVariable(Y, sub2).getValue().equals(gOfa)); 
    }
    
    /** unify(Knows(John,x),Knows(John,Jane)) = {x/Jane} */
    @Test
    public void simplePredicateTest() throws CouldNotUnifyException {
        Set<Variable> sub = unifier.unify(new Predicate("Knows", John, x), new Predicate("Knows", John, Jane));
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(x, sub)&&getSubstitutedVariable(x, sub).getValue().equals(Jane));
    }
    
    /** occurs(A,P(A,B)) should return true */
    @Test
    public void predicateOccursTestA() {
        assertTrue(unifier.occurs(A, p, new HashSet<Variable>()));
    }
    
     /** occurs(B,P(A,B)) should return true */
    @Test
    public void predicateOccursTestB() {
        assertTrue(unifier.occurs(B, p, new HashSet<Variable>()));
    }
    
     /** occurs(C,P(A,B)) should return false */
    @Test
    public void predicateOccursTestC() {
        assertFalse(unifier.occurs(C, p, new HashSet<Variable>()));
    }
    
}
