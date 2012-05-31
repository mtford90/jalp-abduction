package uk.co.mtford.jalp.abduction.logic.instance;

import com.sun.org.apache.bcel.internal.classfile.ConstantNameAndType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALPException;
import uk.co.mtford.jalp.JALPSystem;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.InEqualityInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 27/05/2012
 * Time: 08:12
 * To change this template use File | Settings | File Templates.
 */
public class ExampleTest {

    JALPSystem system;

    public ExampleTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    @Test
    public void factTest1() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/basic/fact-example-a.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance likes = new PredicateInstance("likes",X,Y);
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result onlyResult = result.remove(0);
        JALPSystem.reduceResult(onlyResult);
        assertTrue(onlyResult.getAssignments().get(Y).equals(new CharConstantInstance("jane")));
        assertTrue(onlyResult.getAssignments().get(X).equals(new CharConstantInstance("john")));
    }

    @Test
    public void factTest2() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/basic/fact-example-b.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance likes = new PredicateInstance("likes",X,Y);
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==2);
        Result resultOne = result.remove(0);
        Result resultTwo = result.remove(0);
        JALPSystem.reduceResult(resultOne);
        JALPSystem.reduceResult(resultTwo);
        assertTrue(resultOne.getAssignments().get(X).equals(new CharConstantInstance("bob")));
        assertTrue(resultOne.getAssignments().get(Y).equals(new CharConstantInstance("jane")));
        assertTrue(resultTwo.getAssignments().get(X).equals(new CharConstantInstance("john")));
        assertTrue(resultTwo.getAssignments().get(Y).equals(new CharConstantInstance("jane")));
    }

    @Test
    public void ruleTest1() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/basic/rule-example-a.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance likes = new PredicateInstance("likes",new CharConstantInstance("john"),Y);
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==2);
        Result resultOne = result.remove(0);
        Result resultTwo = result.remove(0);
        JALPSystem.reduceResult(resultOne);
        JALPSystem.reduceResult(resultTwo);
        assertTrue(resultOne.getAssignments().get(Y).equals(new CharConstantInstance("mary")));
        assertTrue(resultTwo.getAssignments().get(Y).equals(new CharConstantInstance("jane")));
    }

    @Test
    public void abducibleTest1() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/basic/abducible-example-a.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance likes = new PredicateInstance("likes",new CharConstantInstance("john"),Y);
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.remove(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).isSameFunction(new PredicateInstance("girl",Y)));
    }

    @Test
    public void abducibleTest2() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/basic/abducible-example-a.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance john = new CharConstantInstance(("john"));
        CharConstantInstance jane = new CharConstantInstance("jane");
        PredicateInstance likes = new PredicateInstance("likes",john,jane);
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.remove(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("girl", jane)));
    }

    @Test
      public void notTest1() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/basic/not-example-a.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance john = new CharConstantInstance(("john"));
        CharConstantInstance jane = new CharConstantInstance("jane");
        PredicateInstance likes = new PredicateInstance("likes",john,jane);
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.remove(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().denials.size()==1);
        assertTrue(resultOne.getStore().denials.get(0).getBody().size()==1);
        assertTrue(resultOne.getStore().denials.get(0).getBody().get(0).equals(new PredicateInstance("girl",jane)));
    }

    @Test
    public void simpleExample() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/basic/simple-example.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance(("X"));
        VariableInstance Y = new VariableInstance(("Y"));
        PredicateInstance likes = new PredicateInstance("likes",X,Y);
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==9);
    }

    @Test
    public void fliesX() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance(("X"));
        PredicateInstance likes = new PredicateInstance("flies",X);
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getAssignments().get(X).equals(new CharConstantInstance("sam")));
    }

    @Test
    public void fliesTweety() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        PredicateInstance likes = new PredicateInstance("flies",new CharConstantInstance("tweety"));
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==0);
    }

    @Test
     public void fliesSam() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance(("X"));
        PredicateInstance likes = new PredicateInstance("flies",new CharConstantInstance("sam"));
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
    }

    @Test
    public void fliesAbnormalX() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance(("X"));
        PredicateInstance abnormal = new PredicateInstance("abnormal",X);
        query.add(abnormal);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getAssignments().get(X).equals(new CharConstantInstance("tweety")));
    }

    @Test
    public void medicalHeadacheJohn() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/medical.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance john = new CharConstantInstance("john");
        PredicateInstance headache = new PredicateInstance("headache",john);
        query.add(headache);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==2);
        Result resultOne = result.get(0);
        Result resultTwo = result.get(1);
        JALPSystem.reduceResult(resultOne);
        JALPSystem.reduceResult(resultTwo);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("migraine",john)));
        assertTrue(resultTwo.getStore().abducibles.size()==2);
        assertTrue(resultTwo.getStore().abducibles.get(0).equals(new PredicateInstance("jaundice",john)));
        assertTrue(resultTwo.getStore().abducibles.get(1).equals(new PredicateInstance("stomachBug",john)));
    }

    @Test
    public void medicalDizzinessJohn() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/medical.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance john = new CharConstantInstance("john");
        PredicateInstance dizziness = new PredicateInstance("dizziness",john);
        query.add(dizziness);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("migraine",john)));
    }

    @Test
    public void medicalSicknessJohn() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/medical.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance john = new CharConstantInstance("john");
        PredicateInstance sickness = new PredicateInstance("sickness",john);
        query.add(sickness);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("stomachBug",john)));
    }

    @Test
    public void carDoesntStart() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/jiefei/cars.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance mycar = new CharConstantInstance("mycar");
        PredicateInstance car_doesnt_start = new PredicateInstance("car_doesnt_start",mycar);
        query.add(car_doesnt_start);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size() == 1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("has_no_fuel",mycar)));
    }

    @Test
    public void ungroundAbducible() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/basic/unground-abducible.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        PredicateInstance a = new PredicateInstance("p",X);
        query.add(a);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        IUnifiableAtomInstance XAssignment = resultOne.getAssignments().get(X);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("a",XAssignment)));
        assertTrue(resultOne.getStore().equalities.contains(new InEqualityInstance(XAssignment,new IntegerConstantInstance(1))));
    }

    @Test
    public void graphNode1Red() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/basic/graph.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        CharConstantInstance node1 = new CharConstantInstance("node1");
        CharConstantInstance node2 = new CharConstantInstance("node2");
        CharConstantInstance red = new CharConstantInstance("red");
        PredicateInstance has_colour = new PredicateInstance("has_colour",node1,red);
        query.add(has_colour);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(has_colour));
        DenialInstance denial = new DenialInstance(new PredicateInstance("has_colour",node2,red));
        assertTrue(resultOne.getStore().denials.contains(denial));
    }

   @Test
    public void graphNode1Node2() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/basic/graph.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance C = new VariableInstance("C");
        VariableInstance D = new VariableInstance("D");
        CharConstantInstance node1 = new CharConstantInstance("node1");
        CharConstantInstance node2 = new CharConstantInstance("node2");
        CharConstantInstance red = new CharConstantInstance("red");
        PredicateInstance has_colour1 = new PredicateInstance("has_colour",node1,C);
        PredicateInstance has_colour2 = new PredicateInstance("has_colour",node2,D);
        query.add(has_colour1);
        query.add(has_colour2);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==9); // Matches jiefeis ASystem implementation.
    }

    @Test
    public void graph2Node1Red() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/basic/graph2.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        CharConstantInstance node1 = new CharConstantInstance("node1");
        CharConstantInstance node2 = new CharConstantInstance("node2");
        CharConstantInstance node3 = new CharConstantInstance("node3");
        CharConstantInstance red = new CharConstantInstance("red");
        PredicateInstance has_colour = new PredicateInstance("has_colour",node1,red);
        query.add(has_colour);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(has_colour));
        DenialInstance denial1 = new DenialInstance(new PredicateInstance("has_colour",node2,red));
        DenialInstance denial2 = new DenialInstance(new PredicateInstance("has_colour",node3,red));
        assertTrue(resultOne.getStore().denials.contains(denial1));
        assertTrue(resultOne.getStore().denials.contains(denial2));
    }

    @Test
    public void graphConstraintNode1Red() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/graph.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        CharConstantInstance node1 = new CharConstantInstance("node1");
        CharConstantInstance node2 = new CharConstantInstance("node2");
        CharConstantInstance red = new CharConstantInstance("red");
        PredicateInstance has_colour = new PredicateInstance("has_colour",node1,red);
        query.add(has_colour);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(has_colour));
        DenialInstance denial = new DenialInstance(new PredicateInstance("has_colour",node2,red));
        assertTrue(resultOne.getStore().denials.contains(denial));
    }

    @Test
    public void graphConstraintNode1Node2() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/graph.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance C = new VariableInstance("C");
        VariableInstance D = new VariableInstance("D");
        CharConstantInstance node1 = new CharConstantInstance("node1");
        CharConstantInstance node2 = new CharConstantInstance("node2");
        CharConstantInstance red = new CharConstantInstance("red");
        PredicateInstance has_colour1 = new PredicateInstance("has_colour",node1,C);
        PredicateInstance has_colour2 = new PredicateInstance("has_colour",node2,D);
        query.add(has_colour1);
        query.add(has_colour2);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==9); // Matches jiefeis ASystem implementation.
    }

    @Test
    public void verySimpleConstantConstraintTest1() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/very-simple.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        PredicateInstance q = new PredicateInstance("q",X);
        query.add(q);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==3);
        Result resultOne = result.get(0);
        Result resultTwo = result.get(1);
        Result resultThree = result.get(2);
        JALPSystem.reduceResult(resultOne);
        JALPSystem.reduceResult(resultTwo);
        JALPSystem.reduceResult(resultThree);
        assertTrue(resultOne.getAssignments().get(X).equals(new CharConstantInstance("mary")));
        assertTrue(resultTwo.getAssignments().get(X).equals(new CharConstantInstance("bob")));
        assertTrue(resultThree.getAssignments().get(X).equals(new CharConstantInstance("john")));
    }

    @Test
    public void verySimpleConstantConstraintTest2() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/very-simple.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance mike = new CharConstantInstance("mike");
        PredicateInstance q = new PredicateInstance("q",mike);
        query.add(q);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==0);
    }

    @Test
    public void verySimpleConstantConstraintTest3() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/very-simple.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance bob = new CharConstantInstance("bob");
        PredicateInstance q = new PredicateInstance("q",bob);
        query.add(q);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
    }

    @Test
    public void verySimpleConstantNegativeConstraintTest1() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/very-simple-negative.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance john = new CharConstantInstance("john");
        PredicateInstance q = new PredicateInstance("q",john);
        query.add(q);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==0);
    }

    @Test
    public void verySimpleConstantNegativeConstraintTest2() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/very-simple-negative.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance will = new CharConstantInstance("will");
        PredicateInstance q = new PredicateInstance("q",will);
        query.add(q);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
    }

    @Test
    public void verySimpleIntegerNegativeConstraintTest1() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/very-simple-negative-integer.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance one = new IntegerConstantInstance(1);
        PredicateInstance q = new PredicateInstance("q",one);
        query.add(q);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
    }

    @Test
      public void verySimpleIntegerNegativeConstraintTest2() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/very-simple-negative-integer.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        IntegerConstantInstance four = new IntegerConstantInstance(4);
        PredicateInstance q = new PredicateInstance("q",four);
        query.add(q);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==0);
    }

    @Test
    public void verySimpleIntegerNegativeConstraintTest3() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/constraint/very-simple-negative-integer.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        PredicateInstance q = new PredicateInstance("q",X);
        query.add(q);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==3);
    }


}
