package uk.co.mtford.jalp.abduction.logic.instance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class SubstitutionTest {

    VariableInstance x, y, z;
    VariableInstance q, r, s;
    CharConstantInstance c, d, e;
    FalseInstance f;
    TrueInstance t;

    public SubstitutionTest() {

    }

    @Before
    public void noSetup() {
        HashMap<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>();
        x = new VariableInstance("X");
        y = new VariableInstance("Y");
        z = new VariableInstance("Z");
        q = new VariableInstance("q");
        r = new VariableInstance("r");
        s = new VariableInstance("s");
        c = new CharConstantInstance("c");
        d = new CharConstantInstance("d");
        e = new CharConstantInstance("e");
        f = new FalseInstance();
        t = new TrueInstance();
    }

    @After
    public void noTearDown() {

    }

    @Test
    public void constantTest() {
        HashMap<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>();
        subst.put(x, d);
        assertTrue(c == c.performSubstitutions(subst));
    }

    @Test
    public void variableTest() {
        HashMap<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>();
        subst.put(x, d);
        assertTrue(y.performSubstitutions(subst) == y);
        assertTrue(x.performSubstitutions(subst) == d);
    }

    @Test
    public void falseTest() {
        HashMap<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>();
        subst.put(x, d);
        assertTrue(f == f.performSubstitutions(subst));
    }

    @Test
    public void trueTest() {
        HashMap<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>();
        subst.put(x, d);
        assertTrue(t == t.performSubstitutions(subst));
    }

    @Test
    public void denialTest1() {
        PredicateInstance p = new PredicateInstance("p", x, y);
        PredicateInstance q = new PredicateInstance("q", z);
        DenialInstance denial = new DenialInstance(p, q);
        HashMap<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>();
        subst.put(x, d);
        assertTrue(denial.performSubstitutions(subst) == denial);
        assertTrue(denial.getBody().size() == 2);
        assertTrue(denial.getBody().get(0) == p);
        assertTrue(denial.getBody().get(1) == q);
        assertTrue(((PredicateInstance) denial.getBody().get(0)).getParameter(0) == d);  // p(d,Y)
        assertTrue(((PredicateInstance) denial.getBody().get(0)).getParameter(1) == y);  // p(d,Y)
        assertTrue(((PredicateInstance) denial.getBody().get(1)).getParameter(0) == z);  // p(Z)
    }

    @Test
    public void negationTest1() {
        PredicateInstance p = new PredicateInstance("p", x, y);
        PredicateInstance q = new PredicateInstance("q", z);
        DenialInstance denial = new DenialInstance(p, q);
        NegationInstance neg = new NegationInstance(denial);
        HashMap<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>();
        subst.put(x, d);
        assertTrue(neg.performSubstitutions(subst) == neg);
        assertTrue(neg.getSubFormula() == denial);
        assertTrue(neg.getSubFormula().equals(denial));

        assertTrue(denial.getBody().size() == 2);
        assertTrue(denial.getBody().get(0) == p);
        assertTrue(denial.getBody().get(1) == q);
        assertTrue(((PredicateInstance) denial.getBody().get(0)).getParameter(0) == d);  // p(d,Y)
        assertTrue(((PredicateInstance) denial.getBody().get(0)).getParameter(1) == y);  // p(d,Y)
        assertTrue(((PredicateInstance) denial.getBody().get(1)).getParameter(0) == z);  // p(Z)

    }

    @Test
    public void equalityTest1() {
        PredicateInstance p = new PredicateInstance("p", x, y);
        PredicateInstance q = new PredicateInstance("p", z, r);
        HashMap<VariableInstance, IUnifiableInstance> subst = new HashMap<VariableInstance, IUnifiableInstance>();
        subst.put(x, d);
        EqualityInstance e = new EqualityInstance(p, q);
        assert (e.performSubstitutions(subst) == e);
        assertTrue(((PredicateInstance) e.getLeft()).getParameter(0) == d);  // p(d,Y)
        assertTrue(((PredicateInstance) e.getLeft()).getParameter(1) == y);  // p(d,Y)
        assertTrue(((PredicateInstance) e.getRight()).getParameter(0) == z);  // p(Z)

    }


}
