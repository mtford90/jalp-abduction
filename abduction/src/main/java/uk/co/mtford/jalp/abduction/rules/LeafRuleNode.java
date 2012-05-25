package uk.co.mtford.jalp.abduction.rules;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.DefinitionException;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.IEqualityInstance;
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
public class LeafRuleNode extends RuleNode {

    private static Logger LOGGER = Logger.getLogger(LeafRuleNode.class);

    public LeafRuleNode(AbductiveFramework framework, Store store, Map<VariableInstance,
            IUnifiableAtomInstance> assignments) {
        super(framework, null, new LinkedList<IInferableInstance>(), store, assignments);
    }

    protected LeafRuleNode() {
    }

    @Override
    public RuleNode shallowClone() {
        throw new UnsupportedOperationException(); // TODO
    }



    public List<Map<VariableInstance, IUnifiableAtomInstance>> constraintSolve() {
        throw new UnsupportedOperationException(); // TODO
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
