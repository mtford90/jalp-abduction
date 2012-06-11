package uk.co.mtford.jalp.abduction.logic.instance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.E2RuleNode;
import uk.co.mtford.jalp.abduction.rules.E2bRuleNode;
import uk.co.mtford.jalp.abduction.rules.E2cRuleNode;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

/** Checks that the correct types of E rule nodes are produced depending on quantification etc.
 *
 */
public class ETest {
    public ETest() {

    }
    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    // ForAll X . <- X = u should produce an E2 node.
    @Test
    public void test1() {

        AbductiveFramework f = new AbductiveFramework();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        EqualityInstance E = new EqualityInstance(X,Y);
        DenialInstance d = new DenialInstance(E);
        d.getUniversalVariables().add(X);


        goals.add(d);


        assertTrue(d.getPositiveRootRuleNode(f,null,goals) instanceof E2RuleNode);

    }

    /*

    // ForAll X . <- p(X)=p(Y) should produce an E2 node with X=Y as head.
    @Test
    public void test2() {

        AbductiveFramework f = new AbductiveFramework();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p1 = new PredicateInstance("p",X);
        PredicateInstance p2 = new PredicateInstance("p",Y);
        EqualityInstance E = new EqualityInstance(p1,p2);

        DenialInstance d = new DenialInstance(E);
        d.getUniversalVariables().add(X);

        goals.add(d);

        assertTrue(d.getPositiveRootRuleNode(f,null,goals) instanceof E2RuleNode);
        assertTrue(goals.get(0).equals(new EqualityInstance(X,Y)));

    }

    */

    // <- Z = X should produce an E2b node.
    @Test
    public void test3() {

        AbductiveFramework f = new AbductiveFramework();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        VariableInstance Z = new VariableInstance("Z");
        VariableInstance X = new VariableInstance("X");
        EqualityInstance E = new EqualityInstance(Z,X);

        DenialInstance d = new DenialInstance(E);

        goals.add(d);

        assertTrue(d.getPositiveRootRuleNode(f,null,goals) instanceof E2bRuleNode);

    }

    //  <- Z = p(X) should produce an E2b node.
    @Test
    public void test4() {

        AbductiveFramework f = new AbductiveFramework();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        VariableInstance Z = new VariableInstance("Z");
        VariableInstance X = new VariableInstance("X");
        PredicateInstance p1 = new PredicateInstance("p",X);

        EqualityInstance E = new EqualityInstance(Z,p1);

        DenialInstance d = new DenialInstance(E);

        goals.add(d);

        assertTrue(d.getPositiveRootRuleNode(f,null,goals) instanceof E2bRuleNode);

    }

    // for all Y <- Z = Y should produce an E2c node.
    @Test
    public void test5() {

        AbductiveFramework f = new AbductiveFramework();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        VariableInstance Z = new VariableInstance("Z");
        VariableInstance Y = new VariableInstance("Y");

        EqualityInstance E = new EqualityInstance(Z,Y);

        DenialInstance d = new DenialInstance(E);
        d.getUniversalVariables().add(Y);

        goals.add(d);

        assertTrue(d.getPositiveRootRuleNode(f,null,goals) instanceof E2cRuleNode);

    }

    // for all Y <- c = Y should produce an E2 node.
    @Test
    public void test6() {

        AbductiveFramework f = new AbductiveFramework();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        CharConstantInstance c = new CharConstantInstance("c");
        VariableInstance Y = new VariableInstance("Y");

        EqualityInstance E = new EqualityInstance(c,Y);

        DenialInstance d = new DenialInstance(E);
        d.getUniversalVariables().add(Y);

        goals.add(d);

        assertTrue(d.getPositiveRootRuleNode(f,null,goals) instanceof E2RuleNode);

    }

    // for all Y <- Y = c should produce an E2 node.
    @Test
    public void test7() {

        AbductiveFramework f = new AbductiveFramework();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        CharConstantInstance c = new CharConstantInstance("c");
        VariableInstance Y = new VariableInstance("Y");

        EqualityInstance E = new EqualityInstance(Y,c);

        DenialInstance d = new DenialInstance(E);
        d.getUniversalVariables().add(Y);

        goals.add(d);

        assertTrue(d.getPositiveRootRuleNode(f,null,goals) instanceof E2RuleNode);

    }

    //  <- c = c should produce an E2 node.
    @Test
    public void test8() {

        AbductiveFramework f = new AbductiveFramework();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        CharConstantInstance c = new CharConstantInstance("c");

        EqualityInstance E = new EqualityInstance(c,c);

        DenialInstance d = new DenialInstance(E);

        goals.add(d);

        assertTrue(d.getPositiveRootRuleNode(f,null,goals) instanceof E2RuleNode);

    }

    // <- c = d should produce an E2 node.
    @Test
    public void test9() {

        AbductiveFramework f = new AbductiveFramework();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        CharConstantInstance c = new CharConstantInstance("c");
        CharConstantInstance d = new CharConstantInstance("d");


        EqualityInstance E = new EqualityInstance(c,d);

        DenialInstance de = new DenialInstance(E);

        goals.add(de);

        assertTrue(de.getPositiveRootRuleNode(f,null,goals) instanceof E2RuleNode);

    }
}
