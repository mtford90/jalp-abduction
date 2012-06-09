package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALP;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IUnifiableAtomInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.parse.query.JALPQueryParser;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 07/06/2012
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class E1Test {
    public E1Test() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {



    }

    @Test
    public void test1() throws Exception {
        E1RuleNode ruleNode = new E1RuleNode();
        List<IInferableInstance> goals = JALPQueryParser.readFromString("X=Y");

        ruleNode.setGoals(goals);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/E1/Test1",ruleNode);
    }
}
