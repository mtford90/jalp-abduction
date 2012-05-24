package uk.co.mtford.jalp.abduction.rules;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.DefinitionException;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.rules.visitor.RuleNodeVisitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 20/05/2012
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class LeafNode extends RuleNode {

    private static Logger LOGGER = Logger.getLogger(LeafNode.class);

    public LeafNode(AbductiveFramework framework, Store store, Map<VariableInstance,
            IUnifiableAtomInstance> assignments) {
        super(framework, null, new LinkedList<IASystemInferableInstance>(), store, assignments);
    }

    protected LeafNode() {
    }

    @Override
    public RuleNode shallowClone() {
        throw new UnsupportedOperationException(); // TODO
    }

    public Map<VariableInstance, IUnifiableAtomInstance> equalitySolve()  {
        Map<VariableInstance, IUnifiableAtomInstance> newAssignments
                = new HashMap<VariableInstance, IUnifiableAtomInstance>(assignments);

        List<IEqualityInstance> equalities = new LinkedList<IEqualityInstance>(this.getStore().equalities);

        for (IEqualityInstance equality:equalities) {
            if (equality.equalitySolve(newAssignments)) continue;
            return null;  // TODO null... really?
        }

        return newAssignments;
    }

    @Override
    public void acceptVisitor(RuleNodeVisitor v) throws DefinitionException {
        v.visit(this);
    }

    @Override
    public String toString(int space) {
        String spaces = "";
        for (int i=0;i<space;i++) spaces+=" ";
        String message =
                        spaces+"delta = " + store.abducibles + "\n" +
                        spaces+"delta* = " + store.denials + "\n" +
                        spaces+"epsilon = " + store.equalities + "\n\n" +
                        spaces+"nodeType = " + this.getClass() + "\n" +
                        spaces+"nodeMark = " + this.getNodeMark() + "\n";
        return message;
    }

}
