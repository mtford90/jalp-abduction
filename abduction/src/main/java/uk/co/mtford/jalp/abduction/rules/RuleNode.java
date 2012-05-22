package uk.co.mtford.jalp.abduction.rules;

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

    protected IASystemInferableInstance currentGoal;
    protected List<IASystemInferableInstance> nextGoals; // G - {currentGoal}
    protected Store store; // ST
    protected Map<VariableInstance, IUnifiableAtomInstance> assignments;  // Theta
    protected AbductiveFramework abductiveFramework; // (P,A,IC)
    protected NodeMark nodeMark; // Defines whether or not leaf node or search node.
    protected List<RuleNode> children; // Next states.

    public RuleNode(AbductiveFramework abductiveFramework, IASystemInferableInstance goal, List<IASystemInferableInstance> restOfGoals) {
        children = new LinkedList<RuleNode>();
        assignments = new HashMap<VariableInstance, IUnifiableAtomInstance>();
        nodeMark = nodeMark.UNEXPANDED;
        this.abductiveFramework = abductiveFramework;
        this.currentGoal = goal;
        this.nextGoals = restOfGoals;
        store = new Store();
    }

    public RuleNode(AbductiveFramework abductiveFramework, IASystemInferableInstance goal, List<IASystemInferableInstance> restOfGoals,
                    Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        children = new LinkedList<RuleNode>();
        this.assignments = assignments;
        this.store = store;
        this.abductiveFramework = abductiveFramework;
        this.currentGoal = goal;
        this.nextGoals = restOfGoals;

    }

    protected RuleNode() {
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

    public List<IASystemInferableInstance> getNextGoals() {
        return nextGoals;
    }

    public void setNextGoals(List<IASystemInferableInstance> nextGoals) {
        this.nextGoals = nextGoals;
    }

    public IASystemInferableInstance getCurrentGoal() {
        return currentGoal;
    }

    public void setCurrentGoal(IASystemInferableInstance currentGoal) {
        this.currentGoal = currentGoal;
    }

    public abstract RuleNode shallowClone(); // TODO: Use reflection instead?

    public abstract void acceptVisitor(RuleNodeVisitor v) throws DefinitionException;

    @Override
    public String toString() {
        String message =
                "currentGoal = " + currentGoal + "\n" +
                "nextGoals = " + nextGoals + "\n" +
                "assignments = " + assignments + "\n\n" +
                "delta = " + store.abducibles + "\n" +
                "delta* = " + store.denials + "\n" +
                "epsilon = " + store.equalities + "\n\n" +
                "nodeType = " + this.getClass() + "\n" +
                "nodeMark = " + this.getNodeMark() + "\n" +
                "numChildren = " + this.getChildren().size();
        if (this.getChildren().size()>0) {
            message+="\nchildren = ";
            for (RuleNode child:children) {
                message += "\n--------------------------------------------------------------" + "\n" + child;
            }
            message += "\n--------------------------------------------------------------";
        }
        return message;
    }

    public String toString(int space) {
        String spaces = "";
        for (int i=0;i<space;i++) spaces+=" ";
        String message =
                spaces+"currentGoal = " + currentGoal + "\n" +
                        spaces+"nextGoals = " + nextGoals + "\n" +
                        spaces+"assignments = " + assignments + "\n\n" +
                        spaces+"delta = " + store.abducibles + "\n" +
                        spaces+"delta* = " + store.denials + "\n" +
                        spaces+"epsilon = " + store.equalities + "\n\n" +
                        spaces+"nodeType = " + this.getClass() + "\n" +
                        spaces+"nodeMark = " + this.getNodeMark() + "\n" +
                        spaces+"numChildren = " + this.getChildren().size();
        if (this.getChildren().size()>0) {
            message+="\n"+spaces+"children = ";
            for (RuleNode child:children) {
                message += "\n"+ spaces+"--------------------------------------------------------------" + "\n" + child.toString(4);
            }
            message += "\n"+ spaces+"--------------------------------------------------------------";
        }
        return message;
    }

    public String toXML() {
        String type[] = this.getClass().toString().split("\\.");

        String xml="<node>\n"+
                   "<type>"+type[type.length-1]+"</type>\n"+
                   "<current-goal>"+currentGoal+"</current-goal>\n"+
                   "<next-goals>\n";
        for (IASystemInferableInstance inferable:nextGoals) {
            xml+="<goal>"+inferable+"</goal>\n";
        }
        xml+="</next-goals>\n<substitutions>\n";
        for (VariableInstance v:assignments.keySet()) {
            xml+="<substitution>\n"+"<variable>"+v+"</variable>\n"+"<assignment>"+assignments.get(v)+"</assignment>\n"+"</substitution>\n";
        }
        xml+="</substitutions>\n<store>\n<delta>\n";
        for (PredicateInstance abducible:store.abducibles) {
            xml+="<abducible>"+abducible+"</abducible>\n";
        }
        xml+="</delta>\n<delta-star>\n";
        for (DenialInstance denial:store.denials) {
            xml+="<denial>"+denial+"</denial>\n";
        }
        xml+="</delta-star>\n<epsilon>\n";
        for (IEqualityInstance equalitie:store.equalities) {
            xml+="<equality>"+equalitie+"</equality>\n";
        }
        xml+="</epsilon>\n</store>\n<mark>"+this.getNodeMark()+"</mark>\n";

               xml+= "<children>\n";
        for (RuleNode child:children) {
            xml+=child.toXML();
        }
        xml+="</children>\n</node>\n";
        return xml;
    }

    public String toJSON()  {
        String type[] = this.getClass().toString().split("\\.");

        String json="{";

        json+="\\\"type\\\":"+"\\\""+type[type.length-1]+"\\\"";

        json+=",";

        json+="\\\"currentGoal\\\":"+"\\\""+currentGoal+"\\\"";

        json+=",";

        json+="\\\"nextGoals\\\""+":[ ";
        for (IASystemInferableInstance inferable:nextGoals) {
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
}
