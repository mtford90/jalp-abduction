package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALP;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.InEqualityInstance;
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

    @Test
    public void test1() throws Exception {
        UniqueIdGenerator.reset();

        InE2RuleNode ruleNode = new InE2RuleNode();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");

        InEqualityInstance e = new InEqualityInstance(X,Y);

        goals.add(e);

        DenialInstance d = new DenialInstance(goals);
        ruleNode.getGoals().add(d);
        ruleNode.setQuery(new LinkedList<IInferableInstance>(ruleNode.getGoals()));


        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE2/Test1",ruleNode);
    }

    @Test
    public void test2() throws Exception {
        UniqueIdGenerator.reset();

        InE2RuleNode ruleNode = new InE2RuleNode();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");

        InEqualityInstance e = new InEqualityInstance(X,Y);

        goals.add(e);
        goals.add(new PredicateInstance("p",X));

        DenialInstance d = new DenialInstance(goals);
        ruleNode.getGoals().add(d);
        ruleNode.setQuery(new LinkedList<IInferableInstance>(ruleNode.getGoals()));


        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE2/Test2",ruleNode);
    }

    @Test
    public void test3() throws Exception {
        UniqueIdGenerator.reset();

        InE2RuleNode ruleNode = new InE2RuleNode();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");

        InEqualityInstance e = new InEqualityInstance(X,Y);

        goals.add(e);
        goals.add(new PredicateInstance("p",X));

        DenialInstance d = new DenialInstance(goals);
        ruleNode.getGoals().add(d);
        ruleNode.getGoals().add(new PredicateInstance("q",Y));
        ruleNode.setQuery(new LinkedList<IInferableInstance>(ruleNode.getGoals()));


        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE2/Test3",ruleNode);
    }
}
