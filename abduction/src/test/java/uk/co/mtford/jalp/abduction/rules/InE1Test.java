package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALP;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.LessThanConstraintInstance;
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
public class InE1Test {
    public InE1Test() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    G = {X!=Y}

    Expected result is collection to the inequality store.
     */
    @Test
    public void test1() throws Exception {
        UniqueIdGenerator.reset();

        InE1RuleNode ruleNode = new InE1RuleNode();

        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");

        InEqualityInstance e = new InEqualityInstance(X,Y);

        goals.add(e);

        ruleNode.setGoals(goals);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE1/Test1",ruleNode);
    }

    /*
   G = {p(X)!=p(Y)}

   Expected result is collection of X!=Y to the inequality store.
    */
    @Test
     public void test2() throws Exception {
        UniqueIdGenerator.reset();

        InE1RuleNode ruleNode = new InE1RuleNode();

        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");

        PredicateInstance p1 = new PredicateInstance("p",X);
        PredicateInstance p2 = new PredicateInstance("p",Y);

        InEqualityInstance e = new InEqualityInstance(p1,p2);

        goals.add(e);

        ruleNode.setGoals(goals);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE1/Test2",ruleNode);
    }

    /*
   G = {p(a)!=p(b)}

   Expected leaf node ready to be evaluated by constraint solver.
    */
    @Test
    public void test3() throws Exception {
        UniqueIdGenerator.reset();

        InE1RuleNode ruleNode = new InE1RuleNode();

        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");

        PredicateInstance p1 = new PredicateInstance("p",a);
        PredicateInstance p2 = new PredicateInstance("p",b);

        InEqualityInstance e = new InEqualityInstance(p1,p2);

        goals.add(e);

        ruleNode.setGoals(goals);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE1/Test3",ruleNode);
    }

    /*
   G = {p(a)!=p(a)}

   Expected failed leaf node.
    */
    @Test
    public void test4() throws Exception {
        UniqueIdGenerator.reset();

        InE1RuleNode ruleNode = new InE1RuleNode();

        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        CharConstantInstance a = new CharConstantInstance("a");

        PredicateInstance p1 = new PredicateInstance("p",a);
        PredicateInstance p2 = new PredicateInstance("p",a);

        InEqualityInstance e = new InEqualityInstance(p1,p2);

        goals.add(e);

        ruleNode.setGoals(goals);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE1/Test4",ruleNode);
    }

    /*
   G = {p(q(X),a))!=p(q(Y),b))}

   Expected leaf node ready for constraint solver.
    */
    @Test
    public void test5() throws Exception {
        UniqueIdGenerator.reset();

        InE1RuleNode ruleNode = new InE1RuleNode();

        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");

        PredicateInstance q1 = new PredicateInstance("q",X);
        PredicateInstance q2 = new PredicateInstance("q",Y);
        PredicateInstance p1 = new PredicateInstance("p",q1,a);
        PredicateInstance p2 = new PredicateInstance("p",q2,b);


        InEqualityInstance e = new InEqualityInstance(p1,p2);

        goals.add(e);

        ruleNode.setGoals(goals);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE1/Test5",ruleNode);
    }

    /*
   G = {p(q(X),a))!=p(q(Y),a))}

   Expected leaf node ready for constraint solver.

   Expected leaf node with collected X!=Y
    */
    @Test
    public void test6() throws Exception {
        UniqueIdGenerator.reset();

        InE1RuleNode ruleNode = new InE1RuleNode();

        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        CharConstantInstance a = new CharConstantInstance("a");

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");

        PredicateInstance q1 = new PredicateInstance("q",X);
        PredicateInstance q2 = new PredicateInstance("q",Y);
        PredicateInstance p1 = new PredicateInstance("p",q1,a);
        PredicateInstance p2 = new PredicateInstance("p",q2,a);

        InEqualityInstance e = new InEqualityInstance(p1,p2);

        goals.add(e);

        ruleNode.setGoals(goals);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE1/Test6",ruleNode);
    }

    /*
   G = {p(a,q(X)))!=p(a,q(Y)))}

   Expected leaf node ready for constraint solver.

   Expected leaf node with collected X!=Y
    */
    @Test
    public void test7() throws Exception {
        UniqueIdGenerator.reset();

        InE1RuleNode ruleNode = new InE1RuleNode();

        LinkedList<IInferableInstance> goals = new LinkedList<IInferableInstance>();

        CharConstantInstance a = new CharConstantInstance("a");

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");

        PredicateInstance q1 = new PredicateInstance("q",X);
        PredicateInstance q2 = new PredicateInstance("q",Y);
        PredicateInstance p1 = new PredicateInstance("p",a,q1);
        PredicateInstance p2 = new PredicateInstance("p",a,q2);

        InEqualityInstance e = new InEqualityInstance(p1,p2);

        goals.add(e);

        ruleNode.setGoals(goals);

        JALP.applyRule(ruleNode);
        JALP.getVisualizer("debug/rules/InE1/Test7",ruleNode);
    }
}
