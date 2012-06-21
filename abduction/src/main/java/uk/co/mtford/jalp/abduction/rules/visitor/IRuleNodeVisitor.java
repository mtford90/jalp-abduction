package uk.co.mtford.jalp.abduction.rules.visitor;

import uk.co.mtford.jalp.JALPException;
import uk.co.mtford.jalp.abduction.DefinitionException;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.ConstraintInstance;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.InListConstraintInstance;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.NegativeConstraintInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.InEqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ConstantInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.ListInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.*;

import java.util.*;

/**
 * Interface defining the types of nodes that a RuleNodeVisitor must be capable of visiting and applying state rewriting
 * rules to.
 */
public interface IRuleNodeVisitor {
    public void visit(A1RuleNode ruleNode);
    public void visit(A2RuleNode ruleNode);
    public void visit(D1RuleNode ruleNode);
    public void visit(D2RuleNode ruleNode);
    public void visit(E1RuleNode ruleNode);
    public void visit(InE1RuleNode ruleNode);
    public void visit(E2RuleNode ruleNode);
    public void visit(E2bRuleNode ruleNode);
    public void visit(E2cRuleNode ruleNode);
    public void visit(InE2RuleNode ruleNode);
    public void visit(N1RuleNode ruleNode);
    public void visit(N2RuleNode ruleNode);
    public void visit(F1RuleNode ruleNode);
    public void visit(F2RuleNode ruleNode);
    public void visit(F2bRuleNode ruleNode);
    public void visit(PositiveTrueRuleNode ruleNode);
    public void visit(NegativeTrueRuleNode ruleNode);
    public void visit(PositiveFalseRuleNode ruleNode);
    public void visit(NegativeFalseRuleNode ruleNode);
    public void visit(LeafRuleNode ruleNode);
}
