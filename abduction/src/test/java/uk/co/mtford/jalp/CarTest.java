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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 02/06/2012
 * Time: 07:23
 * To change this template use File | Settings | File Templates.
 */
public class CarTest {

    JALPSystem system;

    public CarTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    abducible(battery_flat(X)).
    abducible(has_no_fuel(X)).
    abducible(broken_indicator(X)).

    car_doesnt_start(X) :- battery_flat(X).
    car_doesnt_start(X) :- has_no_fuel(X).

    lights_go_on(mycar).
    fuel_indicator_empty(mycar).

    ic :- battery_flat(X), lights_go_on(X).
    ic :- has_no_fuel(X), not fuel_indicator_empty(X), not broken_indicator(X).

    Q = car_doesnt_start(mycar)

    Expected result is an abducible hypothesizing that the car has no fuel.
     */
    @Test
    public void carDoesntStartTest() throws IOException, ParseException, Exception, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/full/jiefei/cars.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        CharConstantInstance mycar = new CharConstantInstance("mycar");
        PredicateInstance car_doesnt_start = new PredicateInstance("car_doesnt_start",mycar);
        query.add(car_doesnt_start);
        List<Result> result = system.generateDebugFiles(query, "debug/full/jiefei/cars");
        assertTrue(result.size()==1);
        Result resultOne = result.get(0);
        assertTrue(resultOne.getStore().abducibles.size() == 1);
        assertTrue(resultOne.getStore().abducibles.get(0).equals(new PredicateInstance("has_no_fuel",mycar)));
    }

}
