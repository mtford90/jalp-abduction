package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALP;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.InIntegerListConstraintInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.IntegerConstantListInstance;
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
        UniqueIdGenerator.reset();

        F2bRuleNode ruleNode = new F2bRuleNode();
        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        VariableInstance Y = new VariableInstance("Y");

        PredicateInstance p = new PredicateInstance("p",Y);

        IntegerConstantListInstance list = new IntegerConstantListInstance(1,2,3);
        InIntegerListConstraintInstance i = new InIntegerListConstraintInstance(Y,list);

        goals.add(i);
        goals.add(p);

        DenialInstance d = new DenialInstance(goals);
        d.getUniversalVariables().add(Y);
        ruleNode.getGoals().add(d);
        ruleNode.setQuery(new LinkedList<IInferableInstance>(ruleNode.getGoals()));


        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/F2b/Test1",ruleNode);
    }
}
