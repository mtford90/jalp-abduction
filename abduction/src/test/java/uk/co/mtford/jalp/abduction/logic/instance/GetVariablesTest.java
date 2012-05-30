package uk.co.mtford.jalp.abduction.logic.instance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class GetVariablesTest {

    public GetVariablesTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    @Test
    public void constantTest() {
        ConstantInstance c = new CharConstantInstance("a");
        assertTrue(c.getVariables().isEmpty());
    }

    @Test
    public void variableTest() {
        VariableInstance v = new VariableInstance("v");
        assertTrue(v.getVariables().size() == 1);
        assertTrue(v.getVariables().contains(v));
    }

    @Test
    public void falseTest() {
        FalseInstance f = new FalseInstance();
        assertTrue(f.getVariables().isEmpty());
    }

    @Test
    public void trueTest() {
        TrueInstance f = new TrueInstance();
        assertTrue(f.getVariables().isEmpty());
    }

    @Test
    public void predicateTest1() {
        VariableInstance x = new VariableInstance("X");
        VariableInstance y = new VariableInstance("Y");
        VariableInstance z = new VariableInstance("Z");
        PredicateInstance p = new PredicateInstance("p", x, y, z);
        Set<VariableInstance> variables = p.getVariables();
        assertTrue(variables.contains(x));
        assertTrue(variables.contains(y));
        assertTrue(variables.contains(z));
        assertTrue(variables.size() == 3);
    }

    @Test
    public void denialTest1() {
        VariableInstance x = new VariableInstance("X");
        VariableInstance y = new VariableInstance("Y");
        VariableInstance z = new VariableInstance("Z");
        PredicateInstance p = new PredicateInstance("p", x, y);
        PredicateInstance q = new PredicateInstance("q", z);
        DenialInstance d = new DenialInstance(p, q);
        Set<VariableInstance> variables = d.getVariables();
        assertTrue(variables.contains(x));
        assertTrue(variables.contains(y));
        assertTrue(variables.contains(z));
        assertTrue(variables.size() == 3);
    }

    @Test
    public void equalityTest1() {
        VariableInstance x = new VariableInstance("X");
        VariableInstance y = new VariableInstance("Y");
        VariableInstance z = new VariableInstance("Z");
        VariableInstance m = new VariableInstance("m");
        PredicateInstance p = new PredicateInstance("p", x, y);
        PredicateInstance q = new PredicateInstance("q", z, m);
        EqualityInstance e = new EqualityInstance(p, q);
        Set<VariableInstance> variables = e.getVariables();
        assertTrue(variables.contains(x));
        assertTrue(variables.contains(y));
        assertTrue(variables.contains(z));
        assertTrue(variables.contains(m));
        assertTrue(variables.size() == 4);
    }

    @Test
    public void negationTest1() {
        VariableInstance x = new VariableInstance("X");
        VariableInstance y = new VariableInstance("Y");
        VariableInstance z = new VariableInstance("Z");
        PredicateInstance p = new PredicateInstance("p", x, y);
        PredicateInstance q = new PredicateInstance("q", z);
        DenialInstance d = new DenialInstance(p, q);
        NegationInstance n = new NegationInstance(d);
        Set<VariableInstance> variables = d.getVariables();
        assertTrue(variables.contains(x));
        assertTrue(variables.contains(y));
        assertTrue(variables.contains(z));
        assertTrue(variables.size() == 3);
    }

}
