package uk.co.mtford.jalp;

import org.junit.*;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.CharConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.parse.query.JALPQueryParser;
import uk.co.mtford.jalp.abduction.tools.UniqueIdGenerator;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 14/06/2012
 * Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
public class PredicateTest {

    JALPSystem system;

    public PredicateTest() {

    }

    @Before
    public void noSetup() {

    }

    @After
    public void noTearDown() {

    }

    @org.junit.Test
    public void equalityTest1() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem();
        system.mergeFramework("p(X,Y) :- q(X)=d(Y).");
        system.mergeFramework("q(1).");
        system.mergeFramework("d(1).");

        List<Result> result = system.generateDebugFiles("p(1,1)", "debug/basic/predicates/equality-test-1");
        assertTrue(result.size()==0);
    }

    @org.junit.Test
    public void equalityTest2() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem();
        system.mergeFramework("p(X,Y) :- q(X)=q(Y).");
        system.mergeFramework("q(1).");

        List<Result> result = system.generateDebugFiles("p(1,1)", "debug/basic/predicates/equality-test-2");
        assertTrue(result.size()==1);
    }

    @org.junit.Test
    public void equalityTest3() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem();
        system.mergeFramework("p(X,Y) :- q(X)=d(Y).");
        system.mergeFramework("q(1).");
        system.mergeFramework("d(1).");

        List<Result> result = system.generateDebugFiles("p(1,2)", "debug/basic/predicates/equality-test-3");
        assertTrue(result.size()==0);
    }

    @org.junit.Test
    public void equalityTest4() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem();
        system.mergeFramework("p(X,Y) :- q(X)=q(cat).");

        List<IInferableInstance> query = JALPQueryParser.readFromString("p(X,Y)");

        PredicateInstance p = (PredicateInstance) query.get(0);

        List<Result> result = system.generateDebugFiles(query, "debug/basic/predicates/equality-test-4");
        assertTrue(result.size()==1);
        result.get(0).reduce(p.getVariables());
        assertTrue(result.get(0).getAssignments().get(p.getParameter(0)).equals(new CharConstantInstance("cat")));
    }

    @org.junit.Test
    public void equalityTest5() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem();
        system.mergeFramework("p(X,Y) :- q(e(X))=q(e(cat)).");

        List<IInferableInstance> query = JALPQueryParser.readFromString("p(X,Y)");

        PredicateInstance p = (PredicateInstance) query.get(0);

        List<Result> result = system.generateDebugFiles(query, "debug/basic/predicates/equality-test-5");
        assertTrue(result.size()==1);
        result.get(0).reduce(p.getVariables());
        assertTrue(result.get(0).getAssignments().get(p.getParameter(0)).equals(new CharConstantInstance("cat")));
    }

    @org.junit.Test
    public void equalityTest6() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem();
        system.mergeFramework("p(X,Y) :- q(X)=q(e(cat)).");

        List<IInferableInstance> query = JALPQueryParser.readFromString("p(X,Y)");

        PredicateInstance p = (PredicateInstance) query.get(0);

        List<Result> result = system.generateDebugFiles(query, "debug/basic/predicates/equality-test-6");
        assertTrue(result.size() == 1);
        result.get(0).reduce(p.getVariables());
        assertTrue(result.get(0).getAssignments().get(p.getParameter(0)).equals(new PredicateInstance("e", new CharConstantInstance("cat"))));
    }

    @org.junit.Test
    public void predicateInHeadTest1() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem();
        system.mergeFramework("p(q(a)).");

        List<IInferableInstance> query = JALPQueryParser.readFromString("p(X)");

        PredicateInstance p = (PredicateInstance) query.get(0);

        List<Result> result = system.generateDebugFiles(query, "debug/basic/predicates/predicate-in-head-test-1");
        assertTrue(result.size() == 1);
        result.get(0).reduce(p.getVariables());
        assertTrue(result.get(0).getAssignments().get(p.getParameter(0)).equals(new PredicateInstance("q",new CharConstantInstance("a"))));
    }

    @org.junit.Test
    public void predicateInHeadTest2() throws Exception, ParseException, JALPException, uk.co.mtford.jalp.abduction.parse.query.ParseException {
        UniqueIdGenerator.reset();

        system = new JALPSystem();
        system.mergeFramework("p(q(a,b,c)).");

        List<IInferableInstance> query = JALPQueryParser.readFromString("p(q(X,Y,Z))");

        PredicateInstance p = (PredicateInstance) query.get(0);
        PredicateInstance q = (PredicateInstance) p.getParameter(0);
        VariableInstance X = (VariableInstance) q.getParameter(0);
        VariableInstance Y = (VariableInstance) q.getParameter(1);
        VariableInstance Z = (VariableInstance) q.getParameter(2);

        List<Result> result = system.generateDebugFiles(query, "debug/basic/predicates/predicate-in-head-test-2");
        assertTrue(result.size() == 1);
        result.get(0).reduce(p.getVariables());
        assertTrue(result.get(0).getAssignments().get(X).equals(new CharConstantInstance("a")));
        assertTrue(result.get(0).getAssignments().get(Y).equals(new CharConstantInstance("b")));
        assertTrue(result.get(0).getAssignments().get(Z).equals(new CharConstantInstance("c")));

    }




}
