package uk.co.mtford.alp.abduction.logic.instance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class EqualitySolverTest {

    PredicateInstance p, p2, p3, p4, r;
    VariableInstance u, v, w, x, y, z;
    ConstantInstance c, d, e;
    FalseInstance f;
    TrueInstance t;

    public EqualitySolverTest() {

    }

    @Before
    public void noSetup() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        u = new VariableInstance("u");
        v = new VariableInstance("v");
        w = new VariableInstance("w");
        x = new VariableInstance("x");
        y = new VariableInstance("y");
        z = new VariableInstance("z");
        c = new ConstantInstance("c");
        d = new ConstantInstance("d");
        e = new ConstantInstance("e");
        f = new FalseInstance();
        t = new TrueInstance();
        p = new PredicateInstance("p", u, v);
        p2 = new PredicateInstance("p", x, y);
        p3 = new PredicateInstance("p", x);
        r = new PredicateInstance("r", x, y);
        p4 = new PredicateInstance("p", r, z);
    }

    @After
    public void noTearDown() {

    }

    @Test
    public void constantTest1() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = c.equalitySolve(c, subst);
        assertTrue(result.size() == 1);
        assertTrue(result.remove(0).equals(t));
    }

    @Test
    public void constantTest2() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = c.equalitySolve(d, subst);
        assertTrue(result.size() == 1);
        assertTrue(result.remove(0).equals(f));
    }

    @Test
    public void variableTest1() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = x.equalitySolve(c, subst);
        assertTrue(result.size() == 0);
        assertTrue(subst.get(x) == c);
    }

    @Test
    public void variableTest2() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = c.equalitySolve(x, subst);
        assertTrue(result.size() == 0);
        assertTrue(subst.get(x) == c);
    }

    @Test
    public void variableTest3() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = x.equalitySolve(y, subst);
        assertTrue(result.size() == 0);
        assertTrue(subst.get(x) == y);
    }

    @Test
    public void variableTest4() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = y.equalitySolve(x, subst);
        assertTrue(result.size() == 0);
        assertTrue(subst.get(y) == x);
    }

    @Test
    public void variableTest5() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = x.equalitySolve(p, subst);
        assertTrue(result.size() == 0);
        assertTrue(subst.get(x) == p);
    }

    @Test
    public void variableTest6() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = p.equalitySolve(x, subst);
        assertTrue(result.size() == 0);
        assertTrue(subst.get(x) == p);
    }

    @Test
    public void predicateTest1() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = p.equalitySolve(p2, subst);
        assertTrue(result.size() == 2);
        assertTrue(subst.size() == 0);
        assertTrue(result.contains(new EqualityInstance(u, x)));
        assertTrue(result.contains(new EqualityInstance(v, y)));
    }

    @Test
    public void predicateTest2() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = p.equalitySolve(p3, subst);
        assertTrue(result.size() == 1);
        assertTrue(result.contains(f));
    }

    @Test
    public void predicateTest3() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = p.equalitySolve(r, subst);
        assertTrue(result.size() == 1);
        assertTrue(result.contains(f));
    }

    @Test
    public void predicateTest4() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst
                = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        List<IEqualitySolverResultInstance> result = p.equalitySolve(p4, subst);
        assertTrue(result.size() == 2);
        assertTrue(result.contains(new EqualityInstance(u, r)));
        assertTrue(result.contains(new EqualityInstance(v, z)));
    }


}
