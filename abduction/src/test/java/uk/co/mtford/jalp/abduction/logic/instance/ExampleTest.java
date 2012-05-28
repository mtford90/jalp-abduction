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
        system = new JALPSystem("examples/fact-example-a.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance likes = new PredicateInstance("likes",X,Y);
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result onlyResult = result.remove(0);
        JALPSystem.reduceResult(onlyResult);
        assertTrue(onlyResult.getAssignments().get(Y).equals(new ConstantInstance("jane")));
        assertTrue(onlyResult.getAssignments().get(X).equals(new ConstantInstance("john")));
    }

    @Test
    public void factTest2() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/fact-example-b.alp");
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
        assertTrue(resultOne.getAssignments().get(X).equals(new ConstantInstance("bob")));
        assertTrue(resultOne.getAssignments().get(Y).equals(new ConstantInstance("jane")));
        assertTrue(resultTwo.getAssignments().get(X).equals(new ConstantInstance("john")));
        assertTrue(resultTwo.getAssignments().get(Y).equals(new ConstantInstance("jane")));
    }

    @Test
    public void ruleTest1() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/rule-example-a.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance likes = new PredicateInstance("likes",new ConstantInstance("john"),Y);
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==2);
        Result resultOne = result.remove(0);
        Result resultTwo = result.remove(0);
        JALPSystem.reduceResult(resultOne);
        JALPSystem.reduceResult(resultTwo);
        assertTrue(resultOne.getAssignments().get(Y).equals(new ConstantInstance("mary")));
        assertTrue(resultTwo.getAssignments().get(Y).equals(new ConstantInstance("jane")));
    }

    @Test
    public void abducibleTest1() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/abducible-example-a.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance likes = new PredicateInstance("likes",new ConstantInstance("john"),Y);
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
        system = new JALPSystem("examples/abducible-example-a.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        ConstantInstance john = new ConstantInstance(("john"));
        ConstantInstance jane = new ConstantInstance("jane");
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
        system = new JALPSystem("examples/not-example-a.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        ConstantInstance john = new ConstantInstance(("john"));
        ConstantInstance jane = new ConstantInstance("jane");
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
        system = new JALPSystem("examples/simple-example.alp");
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
        system = new JALPSystem("examples/alp_exp/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance(("X"));
        PredicateInstance likes = new PredicateInstance("flies",X);
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(result.size()==1);
        assertTrue(resultOne.getAssignments().get(X).equals(new ConstantInstance("sam")));
    }

    @Test
    public void fliesTweety() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/alp_exp/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        PredicateInstance likes = new PredicateInstance("flies",new ConstantInstance("tweety"));
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==0);
    }

    @Test
     public void fliesSam() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/alp_exp/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance(("X"));
        PredicateInstance likes = new PredicateInstance("flies",new ConstantInstance("sam"));
        query.add(likes);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
    }

    @Test
    public void fliesAbnormalX() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/alp_exp/flies.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        VariableInstance X = new VariableInstance(("X"));
        PredicateInstance abnormal = new PredicateInstance("abnormal",X);
        query.add(abnormal);
        List<Result> result = system.processQuery(query, JALPSystem.Heuristic.NONE);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        JALPSystem.reduceResult(resultOne);
        assertTrue(resultOne.getAssignments().get(X).equals(new ConstantInstance("tweety")));
    }

    @Test
    public void medicalHeadacheJohn() throws FileNotFoundException, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        system = new JALPSystem("examples/alp_exp/medical.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        ConstantInstance john = new ConstantInstance("john");
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
        system = new JALPSystem("examples/alp_exp/medical.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        ConstantInstance john = new ConstantInstance("john");
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
        system = new JALPSystem("examples/alp_exp/medical.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        ConstantInstance john = new ConstantInstance("john");
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
        system = new JALPSystem("examples/alp_exp/cars.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        ConstantInstance mycar = new ConstantInstance("mycar");
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
        system = new JALPSystem("examples/unground-abducible.alp");
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
        assertTrue(resultOne.getStore().equalities.contains(new InEqualityInstance(XAssignment,new ConstantInstance("1"))));
    }


}
