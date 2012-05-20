package uk.co.mtford.alp.abduction.logic.instance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class CloningTest {

    VariableInstance x, y, z;
    VariableInstance q, r, s;
    ConstantInstance c, d, e;
    FalseInstance f;
    TrueInstance t;

    public CloningTest() {

    }

    @Before
    public void noSetup() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        x = new VariableInstance("x");
        y = new VariableInstance("y");
        z = new VariableInstance("z");
        q = new VariableInstance("q");
        r = new VariableInstance("r");
        s = new VariableInstance("s");
        c = new ConstantInstance("c");
        d = new ConstantInstance("d");
        e = new ConstantInstance("e");
        f = new FalseInstance();
        t = new TrueInstance();
    }

    @After
    public void noTearDown() {

    }

    @Test
    public void constantTest() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        subst.put(x, d);
        ConstantInstance c2 = (ConstantInstance) c.deepClone(subst);
        assertTrue(c != c2);
        assertTrue(c.equals(c2));
    }

    @Test
    public void variableTest() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        subst.put(x, d);
        assertTrue(y.deepClone(subst) != y);
        assertTrue(((VariableInstance) y.deepClone(subst)).getName().equals(y.getName()));
        assertTrue(x.deepClone(subst) == d);
    }

    @Test
    public void falseTest() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        subst.put(x, d);
        assertTrue(f != f.deepClone(subst));
        assertTrue(f.deepClone(subst) instanceof FalseInstance);
    }

    @Test
    public void trueTest() {
        HashMap<VariableInstance, IUnifiableAtomInstance> subst = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        subst.put(x, d);
        assertTrue(t != t.deepClone(subst));
        assertTrue(t.deepClone(subst) instanceof TrueInstance);
    }

    @Test
    public void denialTest1() {
        PredicateInstance p = new PredicateInstance("p", x, y);
        PredicateInstance q = new PredicateInstance("q", z);
        DenialInstance denial = new DenialInstance(p, q);
        HashMap<VariableInstance, IUnifiableAtomInstance> subst = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        subst.put(x, d);
        DenialInstance clonedDenial = (DenialInstance) denial.deepClone(subst);
        assertTrue(clonedDenial != denial);
        assertTrue(clonedDenial.getBody().size() == 2);
        assertTrue(clonedDenial.getBody().get(0) != p);
        assertTrue(((PredicateInstance) clonedDenial.getBody().get(0)).getName().equals(p.getName()));
        assertTrue(clonedDenial.getBody().get(1) != q);
        assertTrue(((PredicateInstance) clonedDenial.getBody().get(1)).getName().equals(q.getName()));
        assertTrue(((PredicateInstance) clonedDenial.getBody().get(0)).getParameter(0) == d);
        assertTrue(((PredicateInstance) clonedDenial.getBody().get(0)).getParameter(1) != y);
        assertTrue(((PredicateInstance) clonedDenial.getBody().get(1)).getParameter(0) != z);
        assertTrue(((VariableInstance) ((PredicateInstance) clonedDenial.getBody().get(0)).getParameter(1)).getName().equals(y.getName()));
        assertTrue(((VariableInstance) ((PredicateInstance) clonedDenial.getBody().get(1)).getParameter(0)).getName().equals(z.getName()));
    }

    @Test
    public void negationTest1() {
        PredicateInstance p = new PredicateInstance("p", x, y);
        PredicateInstance q = new PredicateInstance("q", z);
        DenialInstance denial = new DenialInstance(p, q);
        HashMap<VariableInstance, IUnifiableAtomInstance> subst = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        subst.put(x, d);
        NegationInstance neg = new NegationInstance(denial);
        NegationInstance clonedNegation = (NegationInstance) neg.deepClone(subst);
        assertTrue(neg != clonedNegation);
        assertTrue(clonedNegation.getSubFormula() != denial);
        assertTrue(((DenialInstance) clonedNegation.getSubFormula()).getBody().size() == 2);
        assertTrue(((DenialInstance) clonedNegation.getSubFormula()).getBody().get(0) != p);
        assertTrue(((PredicateInstance) ((DenialInstance) clonedNegation.getSubFormula()).getBody().get(0)).getName().equals(p.getName()));
        assertTrue(((DenialInstance) clonedNegation.getSubFormula()).getBody().get(1) != q);
        assertTrue(((PredicateInstance) ((DenialInstance) clonedNegation.getSubFormula()).getBody().get(1)).getName().equals(q.getName()));
        assertTrue(((PredicateInstance) ((DenialInstance) clonedNegation.getSubFormula()).getBody().get(0)).getParameter(0) == d);
        assertTrue(((PredicateInstance) ((DenialInstance) clonedNegation.getSubFormula()).getBody().get(0)).getParameter(1) != y);
        assertTrue(((PredicateInstance) ((DenialInstance) clonedNegation.getSubFormula()).getBody().get(1)).getParameter(0) != z);
        assertTrue(((VariableInstance) ((PredicateInstance) ((DenialInstance) clonedNegation.getSubFormula()).getBody().get(0)).getParameter(1)).getName().equals(y.getName()));
        assertTrue(((VariableInstance) ((PredicateInstance) ((DenialInstance) clonedNegation.getSubFormula()).getBody().get(1)).getParameter(0)).getName().equals(z.getName()));
    }

    @Test
    public void equalityTest1() {
        PredicateInstance p = new PredicateInstance("p", x, y);
        PredicateInstance q = new PredicateInstance("q", z);
        DenialInstance denial = new DenialInstance(p, q);
        HashMap<VariableInstance, IUnifiableAtomInstance> subst = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        subst.put(x, d);
        EqualityInstance e = new EqualityInstance(p, q);
        EqualityInstance clonedE = (EqualityInstance) e.deepClone(subst);
        assertTrue(e != clonedE);
        assertTrue(clonedE.getLeft() != p);
        assertTrue(((PredicateInstance) clonedE.getLeft()).getName().equals(p.getName()));
        assertTrue(clonedE.getRight() != q);
        assertTrue(((PredicateInstance) clonedE.getRight()).getName().equals(q.getName()));
        assertTrue(((VariableInstance) ((PredicateInstance) clonedE.getRight()).getParameter(0)).getName().equals(z.getName()));
        assertTrue((((PredicateInstance) clonedE.getLeft()).getParameter(0)) == d);
        assertTrue(((VariableInstance) ((PredicateInstance) clonedE.getLeft()).getParameter(1)).getName().equals(y.getName()));


    }


}
