package uk.co.mtford.jalp.abduction.logic.instance.equalities;

import org.apache.log4j.Logger;
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

    // c =/= c |-> ic :- TRUE.
    @Test
    public void equalConstantTest() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        CharConstantInstance c = new CharConstantInstance("c");
        inequalities.add(new InEqualityInstance(c,c));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        DenialInstance d = new DenialInstance();
        d.getBody().add(new TrueInstance());
        assertTrue(n.remove(0).equals(d));
    }

    // c =/= d  |-> TRUE.
    @Test
    public void unequalConstantTest1() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        CharConstantInstance c = new CharConstantInstance("c");
        CharConstantInstance d = new CharConstantInstance("d");

        inequalities.add(new InEqualityInstance(c,d));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        assertTrue(n.remove(0).equals(new TrueInstance()));
    }

    // d =/= c  |-> TRUE.
    @Test
    public void unequalConstantTest2() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        CharConstantInstance c = new CharConstantInstance("c");
        CharConstantInstance d = new CharConstantInstance("d");

        inequalities.add(new InEqualityInstance(d,c));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        assertTrue(n.remove(0).equals(new TrueInstance()));
    }

    // X =/= a |-> ic :- X=a.
    @Test
    public void variableTest1() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        VariableInstance X = new VariableInstance("X");
        CharConstantInstance a = new CharConstantInstance("a");
        inequalities.add(new InEqualityInstance(X,a));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        DenialInstance d = new DenialInstance();
        d.getBody().add(new EqualityInstance(X,a));
        assertTrue(n.remove(0).equals(d));
    }

    // X =/= Y |-> ic :- X=Y.
    @Test
    public void variableTest2() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y= new VariableInstance("Y");
        inequalities.add(new InEqualityInstance(X,Y));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        DenialInstance d = new DenialInstance();
        d.getBody().add(new EqualityInstance(X,Y));
        assertTrue(n.remove(0).equals(d));
    }

    // X =/= p(Z,Y) |-> ic :- X = p(Z,Y)
    @Test
    public void variableTest3() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y= new VariableInstance("Y");
        VariableInstance Z = new VariableInstance("Z");

        PredicateInstance p = new PredicateInstance("p",Z,Y);
        inequalities.add(new InEqualityInstance(X,p));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        DenialInstance d = new DenialInstance();
        d.getBody().add(new EqualityInstance(X,p));
        assertTrue(n.remove(0).equals(d));
    }

    // p(X,Y) =/= p(a,b) :-> ic:- X=a, Y=b
    @Test
    public void predicateTest1() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y= new VariableInstance("Y");
        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");

        PredicateInstance p1 = new PredicateInstance("p",X,Y);
        PredicateInstance p2 = new PredicateInstance("p",a,b);

        inequalities.add(new InEqualityInstance(p1,p2));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        DenialInstance d = new DenialInstance();
        d.getBody().add(new EqualityInstance(X,a));
        d.getBody().add(new EqualityInstance(Y,b));
        assertTrue(n.remove(0).equals(d));
    }


    // p(a,b) =/= p(a,b) |-> ic :- TRUE, TRUE.
    @Test
    public void predicateTest2() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");

        PredicateInstance p1 = new PredicateInstance("p",a,b);
        PredicateInstance p2 = new PredicateInstance("p",a,b);

        inequalities.add(new InEqualityInstance(p1,p2));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        DenialInstance d = new DenialInstance();
        d.getBody().add(new TrueInstance());
        d.getBody().add(new TrueInstance());
        assertTrue(n.remove(0).equals(d));
    }

    // p(a,c) =/= p(a,b) |-> TRUE.
    @Test
    public void predicateTest3() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");
        CharConstantInstance c = new CharConstantInstance("c");


        PredicateInstance p1 = new PredicateInstance("p",a,c);
        PredicateInstance p2 = new PredicateInstance("p",a,b);

        inequalities.add(new InEqualityInstance(p1,p2));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        assertTrue(n.remove(0).equals(new TrueInstance()));
    }

    // p(c,b) =/= p(a,b)
    @Test
    public void predicateTest4() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");
        CharConstantInstance c = new CharConstantInstance("c");


        PredicateInstance p1 = new PredicateInstance("p",c,b);
        PredicateInstance p2 = new PredicateInstance("p",a,b);

        inequalities.add(new InEqualityInstance(p1,p2));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        assertTrue(n.remove(0).equals(new TrueInstance()));
    }

    // p(X,Y) =/= q(a,b)
    @Test
    public void differentNamePredicateTest() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y= new VariableInstance("Y");

        PredicateInstance p = new PredicateInstance("p",X,Y);
        PredicateInstance q = new PredicateInstance("q",a,b);

        inequalities.add(new InEqualityInstance(p,q));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        assertTrue(n.remove(0).equals(new TrueInstance()));
    }

    // p(X,Y) =/= p(a,q(b))
    @Test
    public void nestedPredicateTest() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y= new VariableInstance("Y");
        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");

        PredicateInstance p1 = new PredicateInstance("p",X,Y);
        PredicateInstance q = new PredicateInstance("q",b);
        PredicateInstance p2 = new PredicateInstance("p",a,q);

        inequalities.add(new InEqualityInstance(p1,p2));

        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==1);
        DenialInstance d = new DenialInstance();
        d.getBody().add(new EqualityInstance(X,a));
        d.getBody().add(new EqualityInstance(Y,q));
        assertTrue(n.remove(0).equals(d));
    }

    // p(X,Y) =/= p(a,q(b)), Z =/= b
    @Test
    public void multipleTest1()
        throws JALPException {
            List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
            VariableInstance X = new VariableInstance("X");
            VariableInstance Y= new VariableInstance("Y");
            VariableInstance Z= new VariableInstance("Z");
            CharConstantInstance a = new CharConstantInstance("a");
            CharConstantInstance b = new CharConstantInstance("b");

            PredicateInstance p1 = new PredicateInstance("p",X,Y);
            PredicateInstance q = new PredicateInstance("q",b);
            PredicateInstance p2 = new PredicateInstance("p",a,q);

            inequalities.add(new InEqualityInstance(p1,p2));
            inequalities.add(new InEqualityInstance(Z,b));

            List<IInferableInstance> n = solver.execute(inequalities);
            assertTrue(n.size()==2);
            DenialInstance d1 = new DenialInstance();
            d1.getBody().add(new EqualityInstance(X,a));
            d1.getBody().add(new EqualityInstance(Y,q));
            assertTrue(n.remove(0).equals(d1));
            DenialInstance d2 = new DenialInstance();
            d2.getBody().add(new EqualityInstance(Z,b));
            assertTrue(n.remove(0).equals(d2));
        }



    // p(X,Y) =/= p(a,q(b)), Z =/= b, q(A) =/= D
    @Test
    public void multipleTest2() throws JALPException {
        List<InEqualityInstance> inequalities = new LinkedList<InEqualityInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y= new VariableInstance("Y");
        VariableInstance Z= new VariableInstance("Z");
        VariableInstance A= new VariableInstance("A");
        VariableInstance D= new VariableInstance("D");

        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");

        PredicateInstance p1 = new PredicateInstance("p",X,Y);
        PredicateInstance q1 = new PredicateInstance("q",b);
        PredicateInstance p2 = new PredicateInstance("p",a,q1);
        PredicateInstance q2 = new PredicateInstance("q",A);

        inequalities.add(new InEqualityInstance(p1,p2));
        inequalities.add(new InEqualityInstance(Z,b));
        inequalities.add(new InEqualityInstance(q2,D));


        List<IInferableInstance> n = solver.execute(inequalities);
        assertTrue(n.size()==3);
        DenialInstance d1 = new DenialInstance();
        d1.getBody().add(new EqualityInstance(X,a));
        d1.getBody().add(new EqualityInstance(Y,q1));
        assertTrue(n.remove(0).equals(d1));
        DenialInstance d2 = new DenialInstance();
        d2.getBody().add(new EqualityInstance(Z,b));
        assertTrue(n.remove(0).equals(d2));
        DenialInstance d3 = new DenialInstance();
        d3.getBody().add(new EqualityInstance(q2,D));
        assertTrue(n.remove(0).equals(d3));
    }




}
