package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALP;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.parse.query.JALPQueryParser;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 07/06/2012
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class E2cTest {
    public E2cTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }


    /*
   G = {ic(Y) :- X=Y, q(Y).}

   Expect two nodes, G = {ic :- q(Y).}
    */
    @Test
    public void test1() throws Exception {
        UniqueIdGenerator.reset();

        E2cRuleNode ruleNode = new E2cRuleNode();
        List<IInferableInstance> denialBody = new LinkedList<IInferableInstance>();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        EqualityInstance e = new EqualityInstance(X,Y);
        PredicateInstance q = new PredicateInstance("q",Y);
        denialBody.add(e);
        denialBody.add(q);

        DenialInstance d = new DenialInstance(denialBody);
        d.getUniversalVariables().add(Y);
        ruleNode.getGoals().add(d);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/E2c/Test1",ruleNode);
    }

}
