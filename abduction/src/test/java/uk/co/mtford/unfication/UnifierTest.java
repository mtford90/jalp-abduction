/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.unfication;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.PropertyConfigurator;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import uk.co.mtford.alp.abduction.logic.instance.ConstantInstance;
import uk.co.mtford.alp.abduction.logic.instance.IAtomInstance;
import uk.co.mtford.alp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.alp.abduction.logic.instance.VariableInstance;
import uk.co.mtford.alp.abduction.tools.NameGenerator;
import uk.co.mtford.alp.unification.Unifier;

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
        PropertyConfigurator.configure("log4j.properties");
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
    private boolean subContainsVariable(VariableInstance X, List<VariableInstance> subList) {
        for (int i=0;i<subList.size();i++) {
            if (subList.get(i).equals(X)) {
                return true;
            }
        }
        return false;
    }
    
    /** If available returns version of the variable that was substituted. */
    private VariableInstance getSubstitutedVariable(VariableInstance X, List<VariableInstance> subList) {
        for (int i=0;i<subList.size();i++) {
            if (subList.get(i).equals(X)) {
                return subList.get(i);
            }
        }
        return null;
    }
    
    /** unify(a,a) = {} */
    @Test
    public void simpleConstantTestA() {
        ConstantInstance a = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        LinkedList<VariableInstance> sub = unifier.unifyReplace(a,a);
        assertTrue(sub.isEmpty());
    }
    
    /** unify(a,b) = FAIL */
    public void simpleConstantTestB1() {
        ConstantInstance a = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        ConstantInstance b = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        LinkedList<VariableInstance> sub = unifier.unifyReplace(a,b);
        assertTrue(sub==null);
    }
    
    /** unify(b,a) = FAIL */
    public void simpleConstantTestB2() {
        ConstantInstance a = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        ConstantInstance b = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        LinkedList<VariableInstance> sub = unifier.unifyReplace(b,a);
        assertTrue(sub==null);
    }
    
    /** unify(X,X) = {} */
    @Test
    public void simpleVariableTestA() {
       VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
       LinkedList<VariableInstance> sub = unifier.unifyReplace(X,X);
       assertTrue(sub.isEmpty());
    }
    
    /** unify(X,Y) = {X/Y} */
    @Test
    public void simpleVariableTestB1() {
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final VariableInstance Y = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
       
        LinkedList<VariableInstance> sub = unifier.unifyReplace(X, Y);
        
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub));
    }
    
    /** unify(Y,X) = {Y/X} */
    @Test
    public void simpleVariableTestB2() {
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final VariableInstance Y = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
       
        LinkedList<VariableInstance> sub = unifier.unifyReplace(Y, X);
        
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(Y, sub));
    }
    
    
    
    /** unify(X,a) = {X/a} */
    @Test
    public void simpleVariableConstantTest1() {
        final ConstantInstance a = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
       
        LinkedList<VariableInstance> sub = unifier.unifyReplace(X, a);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(a));
    }
    
    /** unify(a,X) = {X/a} */
    @Test
    public void simpleVariableConstantTest2() {
        final ConstantInstance a = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
       
        LinkedList<VariableInstance> sub = unifier.unifyReplace(a,X);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(a));
    }
    
    /** unify(f(a,X),f(a,b)) = {X/b} */
    @Test
    public void simplePredicateTestA1() {
        final ConstantInstance a = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        final ConstantInstance b = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final VariableInstance Y = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        String fName = NameGenerator.lowerCaseNameGen.getNextName();
        final PredicateInstance f1 = new PredicateInstance(fName,a,X);
        final PredicateInstance f2 = new PredicateInstance(fName,a,b);
       
        LinkedList<VariableInstance> sub = unifier.unifyReplace(f1, f2);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(b));
    }
    
    /** unify(f(a,b),f(a,X) = {X/b} */
    @Test
    public void simplePredicateTestA2() {
        final ConstantInstance a = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        final ConstantInstance b = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        String fName = NameGenerator.lowerCaseNameGen.getNextName();
        final PredicateInstance f1 = new PredicateInstance(fName,a,X);
        final PredicateInstance f2 = new PredicateInstance(fName,a,b);
       
        LinkedList<VariableInstance> sub = Unifier.unifyReplace(f2, f1);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(b));
    }
    
    /** unify(f(a),g(a)) = FAIL */
    public void simplePredicateTestB1() {
        final ConstantInstance a = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        final PredicateInstance f = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(),a);
        final PredicateInstance g = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(),a);
       
        LinkedList<VariableInstance> sub = unifier.unifyReplace(f,g);
        assertTrue(sub==null);
    }
    
    /** unify(f(a),g(a)) = FAIL */
    public void simplePredicateTestB2() {
        final ConstantInstance a = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        final PredicateInstance f = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(),a);
        final PredicateInstance g = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(),a);
       
        LinkedList<VariableInstance> sub = unifier.unifyReplace(g,f);
        assertTrue(sub==null);
    }
    
    /** unify(f(X),f(Y)) = {X/Y} */
    @Test
    public void simplePredicateTestC1() {
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final VariableInstance Y = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final String fName = NameGenerator.lowerCaseNameGen.getNextName();
        final PredicateInstance f1 = new PredicateInstance(fName,X);
        final PredicateInstance f2 = new PredicateInstance(fName,Y); 
        LinkedList<VariableInstance> sub = unifier.unifyReplace(f1,f2);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(X, sub)&&getSubstitutedVariable(X, sub).getValue().equals(Y));
    }
    
    /** unify(f(Y),f(X)) = {Y/X} */
    @Test
    public void simplePredicateTestC2() {
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final VariableInstance Y = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final String fName = NameGenerator.lowerCaseNameGen.getNextName();
        final PredicateInstance f1 = new PredicateInstance(fName,X);
        final PredicateInstance f2 = new PredicateInstance(fName,Y); 
        LinkedList<VariableInstance> sub = unifier.unifyReplace(f2,f1);
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(Y, sub)&&getSubstitutedVariable(Y, sub).getValue().equals(X));
    }
    
    /** unify(f(X),g(Y)) = FAIL */
    public void simplePredicateTestD1() {
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final VariableInstance Y = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final PredicateInstance f = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(),X);
        final PredicateInstance g = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(),Y); 
        LinkedList<VariableInstance> sub = unifier.unifyReplace(f,g);
        assertTrue(sub==null);
    }
    
    /** unify(g(Y),f(X)) = FAIL */
    public void simplePredicateTestD2() {
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final VariableInstance Y = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final PredicateInstance f = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(),X);
        final PredicateInstance g = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(),Y); 
        LinkedList<VariableInstance> sub = unifier.unifyReplace(g,f);
        assertTrue(sub==null);
    }
    
    /** unify(f(X),g(Y,Z)) = FAIL */
    public void simplePredicateTestE1() {
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final VariableInstance Y = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final VariableInstance Z = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final PredicateInstance f1 = new PredicateInstance(NameGenerator.upperCaseNameGen.getNextName(),X);
        final PredicateInstance f2 = new PredicateInstance(NameGenerator.upperCaseNameGen.getNextName(),Y,Z); 
        LinkedList<VariableInstance> sub = unifier.unifyReplace(f1,f2);
        assertTrue(sub==null);
    }
    
    /** unify(g(Y),f(X,Z)) = FAIL */
    public void simplePredicateTestE2() {
        final VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final VariableInstance Y = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final VariableInstance Z = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        final PredicateInstance f1 = new PredicateInstance(NameGenerator.upperCaseNameGen.getNextName(),X);
        final PredicateInstance f2 = new PredicateInstance(NameGenerator.upperCaseNameGen.getNextName(),Y,Z); 
        LinkedList<VariableInstance> sub = unifier.unifyReplace(f2,f1);
        assertTrue(sub==null);
    }
    
    /** unify(f(g(X)),f(Y)) = {Y/g(X)} */
    @Test
    public void simplePredicateUnificationTest1() {
        VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        PredicateInstance g = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(),X);
        VariableInstance Y = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        String fName = NameGenerator.lowerCaseNameGen.getNextName();
        LinkedList<VariableInstance> sub1 = unifier.unifyReplace(new PredicateInstance(fName,g),
                                                      new PredicateInstance(fName,Y));
        assertTrue(sub1.size()==1);
        assertTrue(subContainsVariable(Y, sub1));
        assertTrue(getSubstitutedVariable(Y, sub1).getValue().equals(g));
    }
    
    /** unify(f(X),X) = FAIL (due to occurs check) */
    public void simplePredicateAndVariableUnificationTest1() {
        VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        PredicateInstance f = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(),X);
        LinkedList<VariableInstance> sub1 = unifier.unifyReplace(f,X);
        sub1 = unifier.unifyReplace(f,X);
        assertTrue(sub1==null);
    }
    
    /** unify(X,f(X)) = FAIL (due to occurs check) */
    public void simplePredicateAndVariableUnificationTest2() {
        VariableInstance X = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        PredicateInstance f = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(),X);
        LinkedList<VariableInstance> sub1 = unifier.unifyReplace(X,f);
        sub1 = unifier.unifyReplace(f,X);
        assertTrue(sub1==null);
    }
    
    /** unify(f(g(X),X),f(Y,a)) = {X/a,Y/g(a)} */
    @Test
    public void complexPredicateUnificationTest1() {
        VariableInstance X = new VariableInstance("X");
        ConstantInstance a = new ConstantInstance("a");
        PredicateInstance gOfX = new PredicateInstance("g",X);
        PredicateInstance gOfa = new PredicateInstance("g",a);
        VariableInstance Y = new VariableInstance("Y");
        String fName = "f";
        
        LinkedList<VariableInstance> sub1 = unifier.unifyReplace(new PredicateInstance(fName,gOfX, X),
                                                      new PredicateInstance(fName,Y,a));
        assertTrue(sub1.size()==2);
        assertTrue(subContainsVariable(X, sub1)&&subContainsVariable(Y, sub1));
        assertTrue(getSubstitutedVariable(X, sub1).getValue().equals(a));
        VariableInstance YSub = getSubstitutedVariable(Y, sub1);
        IAtomInstance YValue = YSub.getValue();
        assertTrue(YValue.equals(gOfa)); 
    }
    
    /** unify(f(g(X),X),f(Y,a)) = {X/a,Y/g(a)} */
    @Test
    public void complexPredicateUnificationTest2() {
        VariableInstance X = new VariableInstance("X");
        ConstantInstance a = new ConstantInstance("a");
        PredicateInstance gOfX = new PredicateInstance("g",X);
        PredicateInstance gOfa = new PredicateInstance("g",a);
        VariableInstance Y = new VariableInstance("Y");
        String fName = "f";
        
        LinkedList<VariableInstance> sub1 = unifier.unifyReplace(new PredicateInstance(fName,Y,a),
                new PredicateInstance(fName,gOfX, X)
                                                      );
        assertTrue(sub1.size()==2);
        assertTrue(subContainsVariable(X, sub1)&&subContainsVariable(Y, sub1));
        assertTrue(getSubstitutedVariable(X, sub1).getValue().equals(a));
        VariableInstance YSub = getSubstitutedVariable(Y, sub1);
        IAtomInstance YValue = YSub.getValue();
        assertTrue(YValue.equals(gOfa)); 
    }
    
    /** unify(P(f(P(X)),P(X) = FAIL */
    public void complexPredicateUnificationTest3() {
        VariableInstance X = new VariableInstance("X");
        PredicateInstance POfX = new PredicateInstance("P",X);
        PredicateInstance f = new PredicateInstance("f",POfX);
        PredicateInstance P2 = new PredicateInstance("P",f);
        
        LinkedList<VariableInstance> sub = unifier.unifyReplace(P2,POfX);   
        assertTrue(sub==null);
    }
    
    /** unify(Knows(John,x),Knows(John,Jane)) = {x/Jane} */
    @Test
    public void simplePredicateTest1()  {
        ConstantInstance John = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        ConstantInstance Jane = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        
        VariableInstance x = new VariableInstance(NameGenerator.lowerCaseNameGen.getNextName());
        
        String Knows = NameGenerator.upperCaseNameGen.getNextName();
        
        LinkedList<VariableInstance> sub = unifier.unifyReplace(new PredicateInstance(Knows, John, x), new PredicateInstance(Knows, John, Jane));
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(x, sub)&&getSubstitutedVariable(x, sub).getValue().equals(Jane));
    }
    
    /** unify(Knows(John,Jane),Knows(John,x)) = {x/Jane} */
    @Test
    public void simplePredicateTest2()  {
        ConstantInstance John = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        ConstantInstance Jane = new ConstantInstance(NameGenerator.lowerCaseNameGen.getNextName());
        VariableInstance x = new VariableInstance(NameGenerator.lowerCaseNameGen.getNextName());
        String Knows = NameGenerator.upperCaseNameGen.getNextName();
        LinkedList<VariableInstance> sub = unifier.unifyReplace(new PredicateInstance(Knows, John, Jane),new PredicateInstance(Knows, John, x));
        assertTrue(sub.size()==1);
        assertTrue(subContainsVariable(x, sub)&&getSubstitutedVariable(x, sub).getValue().equals(Jane));
    }
    
    /** occurs(A,P(A,B)) should return true */
    @Test
    public void predicateOccursTestA() {
                VariableInstance A = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        VariableInstance B = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
                PredicateInstance p = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(), A, B);
        assertTrue(unifier.occurs(A, p, new LinkedList<VariableInstance>()));
    }
    
     /** occurs(B,P(A,B)) should return true */
    @Test
    public void predicateOccursTestB() {
                VariableInstance A = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        VariableInstance B = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        PredicateInstance p = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(), A, B);
        assertTrue(unifier.occurs(B, p, new LinkedList<VariableInstance>()));
    }
    
     /** occurs(C,P(A,B)) should return false */
    @Test
    public void predicateOccursTestC() {
                VariableInstance A = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        VariableInstance B = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        VariableInstance C = new VariableInstance(NameGenerator.upperCaseNameGen.getNextName());
        PredicateInstance p = new PredicateInstance(NameGenerator.lowerCaseNameGen.getNextName(), A, B);
        assertFalse(unifier.occurs(C, p, new LinkedList<VariableInstance>()));
    }
    
}
