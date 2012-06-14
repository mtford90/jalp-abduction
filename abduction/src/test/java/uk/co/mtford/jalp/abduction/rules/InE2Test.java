package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALP;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.InEqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
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
public class InE2Test {
    public InE2Test() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
   G = {'<-X!=Y,p(X)',q(Y) }
    */
    @Test
    public void test1() throws Exception {
        UniqueIdGenerator.reset();

        InE2RuleNode ruleNode = new InE2RuleNode();
        LinkedList<IInferableInstance> denialBody = new LinkedList<IInferableInstance>();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");

        InEqualityInstance e = new InEqualityInstance(X,Y);

        denialBody.add(e);
        denialBody.add(new PredicateInstance("p", X));

        DenialInstance d = new DenialInstance(denialBody);
        ruleNode.getGoals().add(d);
        ruleNode.getGoals().add(new PredicateInstance("q",Y));

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE2/Test1",ruleNode);
    }

    /*
   G = {'<-X!=c',q(Y) }
    */
    @Test
    public void test2() throws Exception {
        UniqueIdGenerator.reset();

        InE2RuleNode ruleNode = new InE2RuleNode();
        LinkedList<IInferableInstance> denialBody = new LinkedList<IInferableInstance>();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        CharConstantInstance c = new CharConstantInstance("c");

        InEqualityInstance e = new InEqualityInstance(X,c);

        denialBody.add(e);

        DenialInstance d = new DenialInstance(denialBody);
        ruleNode.getGoals().add(d);
        ruleNode.getGoals().add(new PredicateInstance("q",Y));

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE2/Test2",ruleNode);
    }

    /*
   G = {'<-p(X)!=p(Y)' }
    */
    @Test
    public void test3() throws Exception {
        UniqueIdGenerator.reset();

        InE2RuleNode ruleNode = new InE2RuleNode();
        LinkedList<IInferableInstance> denialBody = new LinkedList<IInferableInstance>();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        PredicateInstance p1 = new PredicateInstance("p",X);
        PredicateInstance p2 = new PredicateInstance("p",Y);

        InEqualityInstance e = new InEqualityInstance(p1,p2);

        denialBody.add(e);

        DenialInstance d = new DenialInstance(denialBody);
        ruleNode.getGoals().add(d);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE2/Test3",ruleNode);
    }


}
