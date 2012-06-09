package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALP;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 07/06/2012
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class A2Test {
    public A2Test() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*
    Goal = {ic(X) :- ab(X,Y,Z), p(X)}

    Delta = {ab(a,b,c), ab(d,e,f), ba(g,h,i)}

     */
    /*
    @Test
    public void test1() {

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        VariableInstance Z = new VariableInstance("Z");

        VariableInstance N = new VariableInstance("N");

        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");
        CharConstantInstance c = new CharConstantInstance("c");

        CharConstantInstance d = new CharConstantInstance("d");
        CharConstantInstance e = new CharConstantInstance("e");
        CharConstantInstance f = new CharConstantInstance("f");

        CharConstantInstance g = new CharConstantInstance("g");
        CharConstantInstance h = new CharConstantInstance("h");
        CharConstantInstance i = new CharConstantInstance("i");

        PredicateInstance p1 = new PredicateInstance("p",X);

        PredicateInstance ab1 = new PredicateInstance("ab",X,Y,Z);
        PredicateInstance ab2 = new PredicateInstance("ab",a,b,c);
        PredicateInstance ab3 = new PredicateInstance("ab",d,e,f);
        PredicateInstance ba = new PredicateInstance("ba",g,h,i);

        DenialInstance denial = new DenialInstance();
        denial.getUniversalVariables().add(X);
        denial.getBody().add(ab1);
        denial.getBody().add(p1);


        A2RuleNode A2Node = new A2RuleNode();
        A2Node.getStore().abducibles.add(ab2);
        A2Node.getStore().abducibles.add(ab3);
        A2Node.getStore().abducibles.add(ba);

        A2Node.currentGoal=denial.getBody().remove(0);
        A2Node.nestedDenialsList.add(denial);
        A2Node.getGoals().add(p1);

        try {
            JALP.applyRule(A2Node);
        } catch (Exception e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            JALP.getVisualizer("debug/rules/A2/Test1",A2Node);
        } catch (IOException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    */

}
