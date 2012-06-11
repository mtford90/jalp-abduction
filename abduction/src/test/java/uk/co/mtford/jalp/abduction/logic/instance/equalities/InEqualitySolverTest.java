package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import org.apache.log4j.Logger;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALPException;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.LeafRuleNode;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 23/05/2012
 * Time: 10:21
 * To change this template use File | Settings | File Templates.
 */
public class InEqualitySolverTest { // TODO fix up. i.e. equality solver now seperated...

    private static Logger LOGGER = Logger.getLogger(InEqualitySolverTest.class);

    InEqualitySolver solver = new InEqualitySolver();

    public InEqualitySolverTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    // c =/= c |-> fail
    @Test
    public void equalConstantTest() throws JALPException {
        CharConstantInstance c = new CharConstantInstance("c");

        List<Pair<List<EqualityInstance>,List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(c,c));
        assertTrue(n==null);
    }

    // c =/= d  |-> {}
    @Test
    public void unequalConstantTest1() throws JALPException {
        UniqueIdGenerator.reset();

        CharConstantInstance c = new CharConstantInstance("c");
        CharConstantInstance d = new CharConstantInstance("d");

        List<Pair<List<EqualityInstance>, List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(c,d));
        assertTrue(n.size()==0);
    }

    // d =/= c  |-> {}
    @Test
    public void unequalConstantTest2() throws JALPException {
        UniqueIdGenerator.reset();

        CharConstantInstance c = new CharConstantInstance("c");
        CharConstantInstance d = new CharConstantInstance("d");

        List<Pair<List<EqualityInstance>, List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(d,c));
        assertTrue(n.size()==0);

    }

    // X =/= a |-> { {{},{X=/=a}} }
    @Test
    public void variableTest1() throws JALPException {
        UniqueIdGenerator.reset();

        VariableInstance X = new VariableInstance("X");
        CharConstantInstance a = new CharConstantInstance("a");

        List<Pair<List<EqualityInstance>, List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(X,a));

        assertTrue(n.size()==1);
        assertTrue(n.get(0).getValue0().isEmpty());
        assertTrue(n.get(0).getValue1().contains(new InEqualityInstance(X,a)));
    }

    // X =/= Y |-> { {{},{X=/=Y}} }
    @Test
    public void variableTest2() throws JALPException {
        UniqueIdGenerator.reset();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y= new VariableInstance("Y");

        List<Pair<List<EqualityInstance>, List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(X,Y));
        assertTrue(n.size()==1);
        assertTrue(n.get(0).getValue0().isEmpty());
        assertTrue(n.get(0).getValue1().contains(new InEqualityInstance(X, Y)));
    }

    // X =/= p(Z,Y) |-> { {{},{X=/=P(Z/Y)}} }
    @Test
    public void variableTest3() throws JALPException {
        UniqueIdGenerator.reset();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y= new VariableInstance("Y");
        VariableInstance Z = new VariableInstance("Z");

        PredicateInstance p = new PredicateInstance("p",Z,Y);

        List<Pair<List<EqualityInstance>, List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(X,p));
        assertTrue(n.size()==1);
        assertTrue(n.get(0).getValue0().isEmpty());
        assertTrue(n.get(0).getValue1().contains(new InEqualityInstance(X, p)));
    }

    // p(X,Y) =/= p(a,b) :-> { {{},{X=/=a}}, {{X==a},{Y=/=b}} }
    @Test
    public void predicateTest1() throws JALPException {
        UniqueIdGenerator.reset();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y= new VariableInstance("Y");
        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");

        PredicateInstance p1 = new PredicateInstance("p",X,Y);
        PredicateInstance p2 = new PredicateInstance("p",a,b);

        List<Pair<List<EqualityInstance>, List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(p1,p2));
        assertTrue(n.size()==2);
        assertTrue(n.get(0).getValue0().isEmpty());
        assertTrue(n.get(0).getValue1().contains(new InEqualityInstance(X, a)));
        assertTrue(n.get(1).getValue0().contains(new EqualityInstance(X,a)));
        assertTrue(n.get(1).getValue1().contains(new InEqualityInstance(Y,b)));
    }


    // p(a,b) =/= p(a,b) |-> fail
    @Test
    public void predicateTest2() throws JALPException {
        UniqueIdGenerator.reset();

        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");

        PredicateInstance p1 = new PredicateInstance("p",a,b);
        PredicateInstance p2 = new PredicateInstance("p",a,b);

        List<Pair<List<EqualityInstance>, List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(p1,p2));
        assertTrue(n==null);

    }

    // p(a,c) =/= p(a,b) |-> { }
    @Test
    public void predicateTest3() throws JALPException {
        UniqueIdGenerator.reset();

        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");
        CharConstantInstance c = new CharConstantInstance("c");

        PredicateInstance p1 = new PredicateInstance("p",a,c);
        PredicateInstance p2 = new PredicateInstance("p",a,b);

        List<Pair<List<EqualityInstance>, List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(p1,p2));
        assertTrue(n.size()==0);
    }

    // p(c,b) =/= p(a,b) |-> {  }
    @Test
    public void predicateTest4() throws JALPException {
        UniqueIdGenerator.reset();

        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");
        CharConstantInstance c = new CharConstantInstance("c");


        PredicateInstance p1 = new PredicateInstance("p",c,b);
        PredicateInstance p2 = new PredicateInstance("p",a,b);

        List<Pair<List<EqualityInstance>, List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(p1,p2));
        assertTrue(n.size()==0);
    }

    // p(X,Y) =/= q(a,b)  |-> { }
    @Test
    public void differentNamePredicateTest() throws JALPException {
        UniqueIdGenerator.reset();

        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y= new VariableInstance("Y");

        PredicateInstance p = new PredicateInstance("p",X,Y);
        PredicateInstance q = new PredicateInstance("q",a,b);

        List<Pair<List<EqualityInstance>, List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(p,q));
        assertTrue(n.size()==0);
    }

    // p(X,Y) =/= p(a,q(b)) |-> { {{},{X=/=a}}, {{X==a},{Y=/=q(b)}}  }
    @Test
    public void nestedPredicateTest() throws JALPException {
        UniqueIdGenerator.reset();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y= new VariableInstance("Y");
        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");

        PredicateInstance p1 = new PredicateInstance("p",X,Y);
        PredicateInstance q = new PredicateInstance("q",b);
        PredicateInstance p2 = new PredicateInstance("p",a,q);

        List<Pair<List<EqualityInstance>, List<InEqualityInstance>>> n = solver.execute(new InEqualityInstance(p1,p2));
        assertTrue(n.size()==2);
        assertTrue(n.get(0).getValue0().isEmpty());
        assertTrue(n.get(0).getValue1().contains(new InEqualityInstance(X, a)));
        assertTrue(n.get(1).getValue0().contains(new EqualityInstance(X,a)));
        assertTrue(n.get(1).getValue1().contains(new InEqualityInstance(Y,q)));
    }

}
