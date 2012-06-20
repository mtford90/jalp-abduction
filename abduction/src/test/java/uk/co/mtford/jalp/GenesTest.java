package uk.co.mtford.jalp;

import org.junit.*;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 14/06/2012
 * Time: 19:12
 * To change this template use File | Settings | File Templates.
 */
public class GenesTest {
    JALPSystem system;

    public GenesTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    @org.junit.Test
    public void genesTest() throws ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException, FileNotFoundException {
        UniqueIdGenerator.reset();

        system = new JALPSystem("examples/full/jiefei/genes.alp");
        List<IInferableInstance> query = new LinkedList<IInferableInstance>();
        List<Result> result = system.query("produce(permease)");
        assertTrue(result.size()==3);

        boolean one = false;
        boolean two = false;
        boolean three = false;

        for (Result r:result) {
            r.reduce(new LinkedList<VariableInstance>());
            List<PredicateInstance> abducibles = r.getStore().abducibles;
            if (abducibles.contains(new PredicateInstance("absent",new CharConstantInstance("glucose"))) &&
                abducibles.contains(new PredicateInstance("absent",new CharConstantInstance("sugar"))) &&
                abducibles.contains(new PredicateInstance("present",new CharConstantInstance("lactose")))) {
                one = true;
            }
            else if (abducibles.contains(new PredicateInstance("present",new CharConstantInstance("allosugar"))) &&
                    abducibles.contains(new PredicateInstance("absent",new CharConstantInstance("sugar"))) &&
                    abducibles.contains(new PredicateInstance("present",new CharConstantInstance("lactose")))) {
                two = true;
            }
            else if (
                    abducibles.contains(new PredicateInstance("absent",new CharConstantInstance("sugar"))) &&
                    abducibles.contains(new PredicateInstance("present",new CharConstantInstance("lactose")))) {
                three = true;
            }
        }

        assertTrue(one && two && three);

    }
}
