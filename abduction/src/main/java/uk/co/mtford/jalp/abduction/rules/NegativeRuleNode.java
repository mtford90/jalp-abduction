package uk.co.mtford.jalp.abduction.rules;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.IConstraintInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.IEqualityInstance;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 18/05/2012
 * Time: 06:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class NegativeRuleNode extends RuleNode {
    protected List<DenialInstance> nestedDenialsList;

    public NegativeRuleNode(AbductiveFramework abductiveFramework, IInferableInstance goal, List<IInferableInstance> restOfGoals, List<DenialInstance> denial) {
        super(abductiveFramework, goal, restOfGoals);
        this.nestedDenialsList = denial;
    }

    public NegativeRuleNode(AbductiveFramework abductiveFramework, IInferableInstance goal, List<IInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments, List<DenialInstance> denial) {
        super(abductiveFramework, goal, restOfGoals, store, assignments);
        this.nestedDenialsList = denial;
    }

    public List<DenialInstance> getNestedDenialsList() {
        return nestedDenialsList;
    }

    public void setNestedDenialsList(List<DenialInstance> nestedDenialsList) {
        this.nestedDenialsList = nestedDenialsList;
    }

    protected NegativeRuleNode() {
    }

    @Override
    public String toString() {
        String message=
                "currentGoal = " + currentGoal + "\n" +
                "nextGoals = " + nextGoals + "\n" +
                "nestedDenialList = " + nestedDenialsList + "\n" +
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

    @Override
    public String toXML() {
        String type[] = this.getClass().toString().split("\\.");

        String xml="<node>\n"+
                "<type>"+type[type.length-1]+"</type>\n"+
                "<current-goal>"+currentGoal+"</current-goal>\n"+
                "<next-goals>\n";
        for (IInferableInstance inferable:nextGoals) {
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

    @Override
    public String toJSON()  {
        String type[] = this.getClass().toString().split("\\.");

        String json="{";

        json+="\\\"type\\\":"+"\\\""+type[type.length-1]+"\\\"";

        json+=",";

        json+="\\\"currentGoal\\\":"+"\\\""+currentGoal+"\\\"";

        json+=",";

        json+="\\\"nextGoals\\\""+":[ ";
        for (IInferableInstance inferable:nextGoals) {
            json+="\\\""+inferable+"\\\",";
        }
        json=json.substring(0,json.length()-1);
        json+="]";

        json+=",";

        json+="\\\"nestedDenials\\\""+":[ ";
        for (DenialInstance denialInstance: nestedDenialsList) {
            json+="\\\""+denialInstance+"\\\",";
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
}
