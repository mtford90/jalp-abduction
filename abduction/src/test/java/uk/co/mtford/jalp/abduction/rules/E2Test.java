package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALP;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.parse.query.JALPQueryParser;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 07/06/2012
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class E2Test {
    public E2Test() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    @Test
    public void test1() throws Exception {
        E2RuleNode ruleNode = new E2RuleNode();
        List<IInferableInstance> goals = JALPQueryParser.readFromString("X=c, p(X)");

        DenialInstance d = new DenialInstance(goals);
        d.getUniversalVariables().addAll(goals.get(0).getVariables());
        ruleNode.getGoals().add(d);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/E2/Test1",ruleNode);
    }

    @Test
    public void test2() throws Exception {
        E2RuleNode ruleNode = new E2RuleNode();
        List<IInferableInstance> goals = JALPQueryParser.readFromString("c=X, p(X)");

        DenialInstance d = new DenialInstance(goals);
        d.getUniversalVariables().addAll(goals.get(0).getVariables());
        ruleNode.getGoals().add(d);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/E2/Test2",ruleNode);
    }

    @Test
    public void test3() throws Exception {
        E2RuleNode ruleNode = new E2RuleNode();
        List<IInferableInstance> goals = JALPQueryParser.readFromString("p(X)=q(Y), e(D)");

        DenialInstance d = new DenialInstance(goals);
        d.getUniversalVariables().addAll(goals.get(0).getVariables());
        ruleNode.getGoals().add(d);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/E2/Test3",ruleNode);
    }

    @Test
    public void test4() throws Exception {
        E2RuleNode ruleNode = new E2RuleNode();
        List<IInferableInstance> goals = JALPQueryParser.readFromString("X=p(Y), q(X)");

        DenialInstance d = new DenialInstance(goals);
        d.getUniversalVariables().addAll(goals.get(0).getVariables());
        ruleNode.getGoals().add(d);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/E2/Test4",ruleNode);
    }

    @Test
    public void test5() throws Exception {
        E2RuleNode ruleNode = new E2RuleNode();
        List<IInferableInstance> goals = JALPQueryParser.readFromString("p(Y)=X, q(X)");

        DenialInstance d = new DenialInstance(goals);
        d.getUniversalVariables().addAll(goals.get(0).getVariables());
        ruleNode.getGoals().add(d);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/E2/Test5",ruleNode);
    }
}
