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
public class F2bTest {
    public F2bTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    @Test
    public void test1() throws Exception {
        F2bRuleNode ruleNode = new F2bRuleNode();
        List<IInferableInstance> goals = JALPQueryParser.readFromString("Y in [1,2,3], p(Y)");

        DenialInstance d = new DenialInstance(goals);
        d.getUniversalVariables().addAll(goals.get(0).getVariables());
        ruleNode.getGoals().add(d);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/F2b/Test1",ruleNode);
    }
}
