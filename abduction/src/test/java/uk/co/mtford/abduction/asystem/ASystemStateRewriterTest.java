/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.util.LinkedList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import uk.co.mtford.abduction.AbductiveFramework;
import uk.co.mtford.abduction.logic.DenialInstance;
import uk.co.mtford.abduction.logic.LogicFactory;
import uk.co.mtford.abduction.logic.PredicateInstance;
import uk.co.mtford.abduction.logic.VariableInstance;

/**
 *
 * @author mtford
 */
public class ASystemStateRewriterTest {
    
    ASystemStateRewriter rewriter;
    
    public ASystemStateRewriterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        rewriter = new ASystemStateRewriter();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of computeExplanation method, of class ASystemStateRewriter.
     * TODO
     */
    @Test
    public void testComputeExplanation() {    
        LinkedList<Rule> P = new LinkedList<Rule>();
        LinkedList<PredicateInstance> A = new LinkedList<PredicateInstance>();
        LinkedList<DenialInstance> IC = new LinkedList<DenialInstance>();
        LinkedList<IASystemInferable> query = new LinkedList<IASystemInferable>();
        LogicFactory f = new LogicFactory();
        
        VariableInstance X = f.getVariableInstance("X");
        VariableInstance Y = f.getVariableInstance("Y");
        VariableInstance T = f.getVariableInstance("T");
        PredicateInstance on = f.getPredicateInstance("on", X,Y,T);
        PredicateInstance initially = f.getPredicateInstance("initially", X,Y);
        PredicateInstance moved = f.getPredicateInstance("moved",X,Y,f.getConstantInstance("0"),T);
   
        
        
        fail("The test case is a prototype.");
    }
}
