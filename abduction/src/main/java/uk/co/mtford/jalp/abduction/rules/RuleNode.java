package uk.co.mtford.jalp.abduction.rules;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.ChocoConstraintSolverFacade;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.IConstraintInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.*;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.rules.visitor.RuleNodeVisitor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 06:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class RuleNode {

    public static enum NodeMark {
        FAILED,
        SUCCEEDED,
        UNEXPANDED,
        EXPANDED
    };

    protected RuleNode parent;
    protected List<IInferableInstance> query;
    protected List<IInferableInstance> goals; // G - {currentGoal}
    protected Store store; // ST
    protected Map<VariableInstance, IUnifiableAtomInstance> assignments;  // Theta
    protected AbductiveFramework abductiveFramework; // (P,A,IC),Theta
    protected NodeMark nodeMark; // Defines whether or not leaf node or search node.
    protected List<RuleNode> children; // Next states.

    public RuleNode(AbductiveFramework abductiveFramework, RuleNode parent, List<IInferableInstance> query, List<IInferableInstance> restOfGoals) {
        this.parent = parent;
        this.query = query;
        children = new LinkedList<RuleNode>();
        assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        nodeMark = nodeMark.UNEXPANDED;
        this.abductiveFramework = abductiveFramework;
        this.goals = restOfGoals;
        store = new Store();
    }

    public RuleNode(AbductiveFramework abductiveFramework, RuleNode parent, List<IInferableInstance> query, List<IInferableInstance> restOfGoals,
                    Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {

        this.parent = parent;
        this.query = query;
        children = new LinkedList<RuleNode>();
        this.assignments = assignments;
        this.store = store;
        this.abductiveFramework = abductiveFramework;
        this.goals = restOfGoals;
        this.nodeMark = nodeMark.UNEXPANDED;

    }

    public RuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> restOfGoals) {
        this.query = query;
        children = new LinkedList<RuleNode>();
        assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        nodeMark = nodeMark.UNEXPANDED;
        this.abductiveFramework = abductiveFramework;
        this.goals = restOfGoals;
        store = new Store();
    }

    public RuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> restOfGoals,
                    Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        this.query = query;
        children = new LinkedList<RuleNode>();
        this.assignments = assignments;
        this.store = store;
        this.abductiveFramework = abductiveFramework;
        this.goals = restOfGoals;
        this.nodeMark = nodeMark.UNEXPANDED;

    }

    protected RuleNode() {
        query = new LinkedList<IInferableInstance>();
        store = new Store();
        abductiveFramework = new AbductiveFramework();
        goals = new LinkedList<IInferableInstance>();
        assignments = new HashMap<VariableInstance,IUnifiableAtomInstance>();
        children = new LinkedList<RuleNode>();
        nodeMark = nodeMark.UNEXPANDED;
    } // For use whilst cloning.


    public List<RuleNode> getChildren() {
        return children;
    }

    public void setChildren(List<RuleNode> children) {
        this.children = children;
    }

    public Map<VariableInstance, IUnifiableAtomInstance> getAssignments() {
        return assignments;
    }

    public void setAssignments(Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        this.assignments = assignments;
    }

    public NodeMark getNodeMark() {
        return nodeMark;
    }

    public void setNodeMark(NodeMark nodeMark) {
        this.nodeMark = nodeMark;
    }

    public AbductiveFramework getAbductiveFramework() {
        return abductiveFramework;
    }

    public void setAbductiveFramework(AbductiveFramework abductiveFramework) {
        this.abductiveFramework = abductiveFramework;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<IInferableInstance> getGoals() {
        return goals;
    }

    public void setGoals(List<IInferableInstance> goals) {
        this.goals = goals;
    }

    public RuleNode getParent() {
        return parent;
    }

    public void setParent(RuleNode parent) {
        this.parent = parent;
    }

    public abstract RuleNode shallowClone();

    public abstract void acceptVisitor(RuleNodeVisitor v);

    @Override
    public String toString() {
        String message =
                "query = " + query + "\n" +
                "goals = " + goals + "\n" +
                "assignments = " + assignments + "\n\n" +
                "delta = " + store.abducibles + "\n" +
                "delta* = " + store.denials + "\n" +
                "epsilon = " + store.equalities + " "+store.inequalities+"\n" +
                "fd = " + store.constraints + "\n\n" +
                "nodeType = " + this.getClass() + "\n" +
                "nodeMark = " + this.getNodeMark() + "\n" +
                "numChildren = " + this.getChildren().size();
        return message;
    }

    public void applySubstitutions() {
        List<IInferableInstance> newQuery = new LinkedList<IInferableInstance>();
        for (IInferableInstance inferable:query) {
            IInferableInstance newInferable = (IInferableInstance) inferable.shallowClone();
            newInferable.performSubstitutions(assignments);
            newQuery.add(newInferable);
        }
        query=newQuery;
        List<IConstraintInstance> newConstraints = new LinkedList<IConstraintInstance>();
        for (IConstraintInstance constraint:store.constraints) {
            IConstraintInstance newConstraint = (IConstraintInstance) constraint.shallowClone();
            newConstraint.performSubstitutions(assignments);
            newConstraints.add(newConstraint);
        }
        store.constraints=newConstraints;

        LinkedList<IInferableInstance> newGoals = new LinkedList<IInferableInstance>();
        for (IInferableInstance inferable:goals) {
            IInferableInstance newGoal = (IInferableInstance) inferable.shallowClone();
            newGoal.performSubstitutions(assignments);
            newGoals.add(newGoal);
        }

        goals=newGoals;

        LinkedList<DenialInstance> newDenials = new LinkedList<DenialInstance>();
        for (DenialInstance d:store.denials) {
            DenialInstance newDenial = (DenialInstance) d.shallowClone();
            d.performSubstitutions(assignments);
            newDenials.add(newDenial);
        }

        store.denials=newDenials;

        LinkedList<PredicateInstance> newAbducibles= new LinkedList<PredicateInstance>();
        for (PredicateInstance p:store.abducibles) {
            PredicateInstance newAbducible = (PredicateInstance) p.shallowClone();
            newAbducible.performSubstitutions(assignments);
            newAbducibles.add(newAbducible);
        }

        store.abducibles=newAbducibles;

        LinkedList<InEqualityInstance> newInequalities = new LinkedList<InEqualityInstance>();
        for (InEqualityInstance ie:store.inequalities) {
            InEqualityInstance newInEquality = (InEqualityInstance) ie.shallowClone();
            newInEquality.performSubstitutions(assignments);
            newInequalities.add(newInEquality);
        }

        store.inequalities = newInequalities;
    }

    public String toJSON()  {
        String type[] = this.getClass().toString().split("\\.");

        String json="{";

        json+="\\\"query\\\":"+"\\\""+query+"\\\"";

        json+=",";

        json+="\\\"framework\\\":"+abductiveFramework.toJSON();

        json+=",";

        json+="\\\"type\\\":"+"\\\""+type[type.length-1]+"\\\"";

        json+=",";

        json+="\\\"goals\\\""+":[ ";
        for (IInferableInstance inferable: goals) {
            json+="\\\""+inferable+"\\\",";
        }
        json=json.substring(0,json.length()-1);
        json+="]";

        json+=",";

        json+="\\\"assignments\\\""+":{ ";
        for (VariableInstance v:assignments.keySet()) {
            json+="\\\""+v+"\\\""+":"+"\\\""+assignments.get(v)+"\\\""+",";
        }
        json=json.substring(0,json.length()-1);
        json+="}";

        json+=",";

        json+="\\\"abducibles\\\""+":[ ";
        for (PredicateInstance abducible:store.abducibles) {
            json+="\\\""+abducible+"\\\",";
        }
        json=json.substring(0,json.length()-1);
        json+="]";

        json+=",";

        json+="\\\"denials\\\""+":[ ";
        for (DenialInstance denial:store.denials) {
            json+="\\\""+denial+"\\\",";
        }
        json=json.substring(0,json.length()-1);
        json+="]";

        json+=",";

        json+="\\\"equalities\\\""+":[ ";
        for (IEqualityInstance equalities:store.equalities) {
            json+="\\\""+equalities+"\\\",";
        }
        for (InEqualityInstance equalities:store.inequalities) {
            json+="\\\""+equalities+"\\\",";
        }
        json=json.substring(0,json.length()-1);
        json+="]";

        json+=",";

        json+="\\\"constraints\\\""+":[ ";
        for (IConstraintInstance constraint:store.constraints) {
            json+="\\\""+constraint+"\\\",";
        }
        json=json.substring(0,json.length()-1);
        json+="]";

        json+=",";

        json+="\\\"mark\\\":"+"\\\""+nodeMark+"\\\"";

        json+=",";

        json+="\\\"children\\\""+":[ ";
        for (RuleNode child:children) {
            json+=child.toJSON()+",";
        }
        json=json.substring(0,json.length()-1);
        json+="]";

        json+="}";

        return json;
    }

    public List<IInferableInstance> getQuery() {
        return query;
    }

    public void setQuery(List<IInferableInstance> query) {
        this.query = query;
    }
}
