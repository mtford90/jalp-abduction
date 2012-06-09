package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Definition;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 07/06/2012
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class D1Test {
    public D1Test() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    G = {p(t,s)}
    P = {p(X,Y) :- g(X), d(Y), P(X,Y) :- e(X,Y).}
     */
    @Test
    public void test1() { // TODO D1.alp

        /*// Constants.
        CharConstantInstance t = new CharConstantInstance("t");
        CharConstantInstance s = new CharConstantInstance("s");

        // Variables.
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        VariableInstance X2 = new VariableInstance("X");
        VariableInstance Y2 = new VariableInstance("Y");

        PredicateInstance p3 = new PredicateInstance("p",t,s);


        // Rules
        PredicateInstance p1 = new PredicateInstance("p",X,Y);
        PredicateInstance g = new PredicateInstance("g",X);
        PredicateInstance d = new PredicateInstance("d",Y);

        PredicateInstance p2 = new PredicateInstance("p",X2,Y2);
        PredicateInstance e = new PredicateInstance("e",X2,Y2);

        // P
        Definition d1 = new Definition(p1);
        d1.getBody().add(g);
        d1.getBody().add(d);

        Definition d2 = new Definition(p2);
        d2.getBody().add(e);

        AbductiveFramework f = new AbductiveFramework();
        f.getP().add(d1);
        f.getP().add(d2);

        D1RuleNode r = new D1RuleNode();
        r.setAbductiveFramework(f);
        r.  */

    }
}
