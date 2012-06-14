package uk.co.mtford.jalp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 02/06/2012
 * Time: 07:30
 * To change this template use File | Settings | File Templates.
 */
public class MedicalTest {

    JALPSystem system;

    public MedicalTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    headache(X) :- jaundice(X).
    headache(X) :- migraine(X).
    yellowEyes(X) :- jaundice(X).
    dizziness(X) :- migraine(X).

    sickness(X) :- stomachBug(X).

    ic :- migraine(X), jaundice(X).
    ic :- migraine(X), young(X).
    ic :- jaundice(X), not yellowEyes(X).
    ic :- jaundice(X), not sickness(X).

    abducible(jaundice(X)).
    abducible(migraine(X)).
    abducible(stomachBug(X)).

    Q = headache(john)

    Expect two results, either that john has a migraine, or jaundice and a stomach bug.
     */
    @Test
    public void medicalHeadacheTest() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/full/jiefei/medical.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance john = new CharConstantInstance("john");
        PredicateInstance headache = new PredicateInstance("headache",john);
        query.add(headache);
        List<Result> result = system.query(query);
        assertTrue(result.size()==2);
        Result resultOne = result.get(0);
        Result resultTwo = result.get(1);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("migraine",john)));
        assertTrue(resultTwo.getStore().abducibles.size()==2);
        assertTrue(resultTwo.getStore().abducibles.get(0).equals(new PredicateInstance("jaundice",john)));
        assertTrue(resultTwo.getStore().abducibles.get(1).equals(new PredicateInstance("stomachBug",john)));
    }

    /*
   headache(X) :- jaundice(X).
   headache(X) :- migraine(X).
   yellowEyes(X) :- jaundice(X).
   dizziness(X) :- migraine(X).

   sickness(X) :- stomachBug(X).

   ic :- migraine(X), jaundice(X).
   ic :- migraine(X), young(X).
   ic :- jaundice(X), not yellowEyes(X).
   ic :- jaundice(X), not sickness(X).

   abducible(jaundice(X)).
   abducible(migraine(X)).
   abducible(stomachBug(X)).

   Q = dizziness(john)

   Expect one result: that john has a migraine.
    */
    @Test
    public void medicalDizzinessTest() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/full/jiefei/medical.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance john = new CharConstantInstance("john");
        PredicateInstance dizziness = new PredicateInstance("dizziness",john);
        query.add(dizziness);
        List<Result> result = system.query(query);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("migraine",john)));
    }

    /*
   headache(X) :- jaundice(X).
   headache(X) :- migraine(X).
   yellowEyes(X) :- jaundice(X).
   dizziness(X) :- migraine(X).

   sickness(X) :- stomachBug(X).

   ic :- migraine(X), jaundice(X).
   ic :- migraine(X), young(X).
   ic :- jaundice(X), not yellowEyes(X).
   ic :- jaundice(X), not sickness(X).

   abducible(jaundice(X)).
   abducible(migraine(X)).
   abducible(stomachBug(X)).

   Q = sickness(john)

   Expect one result: that john has a stomach bug.
    */
    @Test
    public void medicalSicknessTest() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/full/jiefei/medical.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance john = new CharConstantInstance("john");
        PredicateInstance sickness = new PredicateInstance("sickness",john);
        query.add(sickness);
        List<Result> result = system.query(query);
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        assertTrue(resultOne.getStore().abducibles.size()==1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("stomachBug",john)));
    }
}
