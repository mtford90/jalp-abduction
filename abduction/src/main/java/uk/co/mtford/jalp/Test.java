package uk.co.mtford.jalp;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.Variable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.model.variables.set.SetVariable;
import choco.kernel.solver.Solver;
import choco.kernel.solver.variables.integer.IntVar;
import com.sun.javaws.jnl.XMLFormat;

import java.util.Iterator;

import static choco.Choco.*;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 06/06/2012
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main (String[] args) {

        int[] Xdom = {1,2,3};
        int[] Ydom = {1,2};


        IntegerVariable X = makeIntVar("X",Xdom);
        IntegerVariable Y = makeIntVar("Y",Ydom);

        Constraint c3 = lt(X,Y);

        Model m = new CPModel();


        m.addConstraint(c3);


        Solver s = new CPSolver();
        s.read(m);


        boolean success = s.solve();

        if (success) {
            do {
                System.out.println(s.getVar(X));

            } while (success = s.nextSolution() == true);
        }




        }

    }
