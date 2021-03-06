package uk.co.mtford.jalp.abduction.logic.instance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 23/05/2012
 * Time: 10:21
 * To change this template use File | Settings | File Templates.
 */
public class UnificationTest { // TODO

    VariableInstance X, Y, Z;
    PredicateInstance p, p1, p2, q;
    CharConstantInstance c, d, e;
    FalseInstance f;
    TrueInstance t;

    public UnificationTest() {

    }

    @Before
    public void noSetup() {
        HashMap<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>();
        X = new VariableInstance("X");
        Y = new VariableInstance("Y");
        Z = new VariableInstance("Z");
        c = new CharConstantInstance("c");
        d = new CharConstantInstance("d");
        e = new CharConstantInstance("e");
        f = new FalseInstance();
        t = new TrueInstance();
        p = new PredicateInstance("p", Y);
        p1 = new PredicateInstance("p", X, Z);
        p2 = new PredicateInstance("p", Y,e);
        q = new PredicateInstance("q", Y, Z);
    }

    @After
    public void noTearDown() {

    }

    @Test
    public void reduceTest1() {
        List<EqualityInstance> results = c.reduce(c);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest2() {
        List<EqualityInstance> results = c.reduce(d);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest3() {
        List<EqualityInstance> results = c.reduce(X);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest4() {
        List<EqualityInstance> results = c.reduce(p);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest5() {
        List<EqualityInstance> results = p.reduce(q);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest6() {
        List<EqualityInstance> results = p1.reduce(p);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest7() {
        List<EqualityInstance> results = X.reduce(p);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest8() {
        List<EqualityInstance> results = p1.reduce(p2);
        assertTrue(!results.isEmpty());
        assertTrue(results.contains(new EqualityInstance(X, Y)));
        assertTrue(results.contains(new EqualityInstance(Z,e)));
    }

    @Test
    public void reduceTest1a() {
        List<EqualityInstance> results = c.reduce((IUnifiableInstance)c);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest2a() {
        List<EqualityInstance> results = c.reduce((IUnifiableInstance)d);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest3a() {
        List<EqualityInstance> results = c.reduce((IUnifiableInstance) X);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest4a() {
        List<EqualityInstance> results = c.reduce((IUnifiableInstance)p);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest5a() {
        List<EqualityInstance> results = p.reduce((IUnifiableInstance)q);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest6a() {
        List<EqualityInstance> results = p1.reduce((IUnifiableInstance)p);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest7a() {
        List<EqualityInstance> results = X.reduce((IUnifiableInstance)p);
        assertTrue(results.isEmpty());
    }

    @Test
    public void reduceTest8a() {
        List<EqualityInstance> results = p1.reduce((IUnifiableInstance)p2);
        assertTrue(!results.isEmpty());
        assertTrue(results.contains(new EqualityInstance(X, Y)));
        assertTrue(results.contains(new EqualityInstance(Z,e)));
    }

    @Test
    public void unifyTest1() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = c.unify(c,assignments);
        assertTrue(success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest2() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = c.unify(d,assignments);
        assertTrue(!success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest3() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = c.unify(X,assignments);
        assertTrue(success);
        assertTrue(assignments.get(X).equals(c));
    }

    @Test
    public void unifyTest4() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = X.unify(c,assignments);
        assertTrue(success);
        assertTrue(assignments.get(X).equals(c));
    }

    @Test
    public void unifyTest5() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = X.unify(X,assignments);
        assertTrue(success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest6() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = X.unify(p,assignments);
        assertTrue(success);
        assertTrue(assignments.get(X).equals(p));
    }

    @Test
    public void unifyTest7() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = p.unify(X,assignments);
        assertTrue(success);
        assertTrue(assignments.get(X).equals(p));
    }

    @Test
    public void unifyTest8() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = p.unify(q,assignments);
        assertTrue(!success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest9() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = p1.unify(q,assignments);
        assertTrue(!success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest10() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = q.unify(p,assignments);
        assertTrue(!success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest11() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = q.unify(p1,assignments);
        assertTrue(!success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest12() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = p1.unify(p2,assignments);
        assertTrue(success);
        assertTrue(assignments.get(X).equals(Y));
        assertTrue(assignments.get(Z).equals(e));
    }

    @Test
    public void unifyTest13() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = p2.unify(p1,assignments);
        assertTrue(success);
        assertTrue(assignments.get(Y).equals(X));
        assertTrue(assignments.get(Z).equals(e));
    }

    @Test
    public void unifyTest1a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = c.unify((IUnifiableInstance)c,assignments);
        assertTrue(success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest2a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = c.unify((IUnifiableInstance)d,assignments);
        assertTrue(!success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest3a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = c.unify((IUnifiableInstance)X,assignments);
        assertTrue(success);
        assertTrue(assignments.get(X).equals(c));
    }

    @Test
    public void unifyTest4a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = X.unify((IUnifiableInstance)c,assignments);
        assertTrue(success);
        assertTrue(assignments.get(X).equals(c));
    }

    @Test
    public void unifyTest5a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = X.unify((IUnifiableInstance)X,assignments);
        assertTrue(success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest6a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = X.unify((IUnifiableInstance)p,assignments);
        assertTrue(success);
        assertTrue(assignments.get(X).equals(p));
    }

    @Test
    public void unifyTest7a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = p.unify((IUnifiableInstance)X,assignments);
        assertTrue(success);
        assertTrue(assignments.get(X).equals(p));
    }

    @Test
    public void unifyTest8a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = p.unify((IUnifiableInstance)q,assignments);
        assertTrue(!success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest9a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = p1.unify((IUnifiableInstance)q,assignments);
        assertTrue(!success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest10a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = q.unify((IUnifiableInstance)p,assignments);
        assertTrue(!success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest11a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = q.unify((IUnifiableInstance)p1,assignments);
        assertTrue(!success);
        assertTrue(assignments.isEmpty());
    }

    @Test
    public void unifyTest12a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = p1.unify((IUnifiableInstance)p2,assignments);
        assertTrue(success);
        assertTrue(assignments.get(X).equals(Y));
        assertTrue(assignments.get(Z).equals(e));
    }

    @Test
    public void unifyTest13a() {
        HashMap<VariableInstance,IUnifiableInstance> assignments = new HashMap<VariableInstance, IUnifiableInstance>();
        boolean success = p2.unify((IUnifiableInstance)p1,assignments);
        assertTrue(success);
        assertTrue(assignments.get(Y).equals(X));
        assertTrue(assignments.get(Z).equals(e));
    }






}
