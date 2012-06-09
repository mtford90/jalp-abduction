package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALP;
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
public class F1Test {
    public F1Test() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    @Test
    public void test1() throws Exception {
        F1RuleNode ruleNode = new F1RuleNode();
        List<IInferableInstance> goals = JALPQueryParser.readFromString("X<Y, p(X)");

        ruleNode.setGoals(goals);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/F1/Test1",ruleNode);
    }
}
