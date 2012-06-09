package uk.co.mtford.jalp.abduction.rules;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.co.mtford.jalp.JALP;
import uk.co.mtford.jalp.abduction.logic.instance.DenialInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 07/06/2012
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class A1Test {

    public A1Test() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    /*

    G = {ab(X,Y,Z)}

    Delta = {ab(a,b,c), ab(d,e,f}}

    Branch 1 should generate 2 E1 nodes containing G =  {X=a, Y=b, Z=c} or {X=d, Y=e, Z=f}

    Branch 2 should generate 1 E2 node containing G =  {'<- X=a, Y=b, Z=c', '<- X=d, Y=e, Z=f'}
    and Delta = {ab(a,b,c), ab(d,e,f},ab(X,Y,Z)}

     */
    /*
    @Test
    public void test1() {
        UniqueIdGenerator.reset();

        VariableInstance X = new VariableInstance("X");
        VariableInstance Y = new VariableInstance("Y");
        VariableInstance Z = new VariableInstance("Z");

        CharConstantInstance a = new CharConstantInstance("a");
        CharConstantInstance b = new CharConstantInstance("b");
        CharConstantInstance c = new CharConstantInstance("c");

        CharConstantInstance d = new CharConstantInstance("d");
        CharConstantInstance e = new CharConstantInstance("e");
        CharConstantInstance f = new CharConstantInstance("f");

        PredicateInstance ab1 = new PredicateInstance("ab",X,Y,Z);
        PredicateInstance ab2 = new PredicateInstance("ab",a,b,c);
        PredicateInstance ab3 = new PredicateInstance("ab",d,e,f);

        A1RuleNode A1Node = new A1RuleNode();
        A1Node.getStore().abducibles.add(ab2);
        A1Node.getStore().abducibles.add(ab3);
        A1Node.currentGoal=ab1;

        try {
            JALP.applyRule(A1Node);
        } catch (Exception e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            JALP.getVisualizer("debug/rules/A1/Test1",A1Node);
        } catch (IOException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    /*

   G = {ab(X,Y,Z), p(Z)}

   Delta = {ab(a,b,c), ab(d,e,f), ba(d,e,f)}
   Delta* = {ic :- ab(g,h,i), p(N)}

    */
    /*
    @Test
    public void test2() {
        UniqueIdGenerator.reset();
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

        CharConstantInstance j = new CharConstantInstance("j");
        CharConstantInstance k = new CharConstantInstance("k");
        CharConstantInstance l = new CharConstantInstance("l");

        PredicateInstance p1 = new PredicateInstance("p",Z);
        PredicateInstance p2 = new PredicateInstance("p",N);


        PredicateInstance ab1 = new PredicateInstance("ab",X,Y,Z);
        PredicateInstance ab2 = new PredicateInstance("ab",a,b,c);
        PredicateInstance ab3 = new PredicateInstance("ab",d,e,f);
        PredicateInstance ba = new PredicateInstance("ba",d,e,f);
        PredicateInstance ab4 = new PredicateInstance("ab",g,h,i);
        PredicateInstance ab5 = new PredicateInstance("ab",j,k,l);

        DenialInstance denial1 = new DenialInstance();
        denial1.getUniversalVariables().add(N);
        denial1.getBody().add(ab4);
        denial1.getBody().add(p2);

        DenialInstance denial2 = new DenialInstance();
        denial2.getUniversalVariables().add(N);
        denial2.getBody().add(ab5);
        denial2.getBody().add(p2);

        DenialInstance denial3 = new DenialInstance();
        denial3.getBody().add(ba);

        A1RuleNode A1Node = new A1RuleNode();
        A1Node.getStore().abducibles.add(ab2);
        A1Node.getStore().abducibles.add(ab3);
        A1Node.getStore().abducibles.add(ba);
        A1Node.currentGoal=ab1;
        A1Node.getGoals().add(p1);
        A1Node.getStore().denials.add(denial1);
        A1Node.getStore().denials.add(denial2);
        A1Node.getStore().denials.add(denial3);

        try {
            JALP.applyRule(A1Node);
        } catch (Exception e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            JALP.getVisualizer("debug/rules/A1/Test2",A1Node);
        } catch (IOException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
    */



}
