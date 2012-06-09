package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALP;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.parse.program.JALPParser;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.parse.query.JALPQueryParser;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 07/06/2012
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class D2Test {
    public D2Test() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
   G = {ic :- p(t,s)}
   P = {p(X,Y) :- g(X), d(Y), P(X,Y) :- e(X,Y).}
    */
    @Test
    public void test1() throws Exception, ParseException, uk.co.mtford.jalp.abduction.parse.query.ParseException { // TODO D1.alp

        AbductiveFramework framework = JALPParser.readFromFile("examples/inference-rules/D1.alp");



        D2RuleNode ruleNode = new D2RuleNode();
        ruleNode.setAbductiveFramework(framework);

        DenialInstance denial = new DenialInstance();
        CharConstantInstance t = new CharConstantInstance("t");
        CharConstantInstance s = new CharConstantInstance("s");
        PredicateInstance p = new PredicateInstance("p",t,s);
        denial.getBody().add(p);

        ruleNode.getGoals().add(denial);


        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/D2/Test1",ruleNode);


    }
}
