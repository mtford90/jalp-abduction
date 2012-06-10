package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.rmi.log.LogInputStream;
import uk.co.mtford.jalp.JALP;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Definition;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.parse.program.JALPParser;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.parse.query.JALPQueryParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 07/06/2012
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class D1Test {
    public D1Test() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    G = {p(t,s)}
    P = {p(X,Y) :- g(X), d(Y), P(X,Y) :- e(X,Y).}
     */
    @Test
    public void test1() throws Exception, ParseException, uk.co.mtford.jalp.abduction.parse.query.ParseException { // TODO D1.alp

        AbductiveFramework framework = JALPParser.readFromFile("examples/inference-rules/D1.alp");
        List<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        CharConstantInstance t = new CharConstantInstance("t");
        CharConstantInstance s = new CharConstantInstance("s");
        PredicateInstance p = new PredicateInstance("p",t,s);
        goals.add(p);

        D1RuleNode ruleNode = new D1RuleNode(framework,new LinkedList<IInferableInstance>(goals),goals);
        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/D1/Test1",ruleNode);


    }
}
