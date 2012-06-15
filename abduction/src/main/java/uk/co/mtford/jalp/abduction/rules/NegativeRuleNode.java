package uk.co.mtford.jalp.abduction.rules;

import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Store;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.IConstraintInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.IEqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.InEqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;

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

    public NegativeRuleNode(AbductiveFramework abductiveFramework, RuleNode parent,List<IInferableInstance> query, List<IInferableInstance> restOfGoals) {
        super(abductiveFramework, query, restOfGoals);
    }

    public NegativeRuleNode(AbductiveFramework abductiveFramework, RuleNode parent,List<IInferableInstance> query,List<IInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        super(abductiveFramework, query, restOfGoals, store, assignments);
    }

    protected NegativeRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> restOfGoals) {
        super(abductiveFramework, query, restOfGoals);
    }

    protected NegativeRuleNode(AbductiveFramework abductiveFramework, List<IInferableInstance> query, List<IInferableInstance> restOfGoals, Store store, Map<VariableInstance, IUnifiableAtomInstance> assignments) {
        super(abductiveFramework, query, restOfGoals, store, assignments);
    }

    protected NegativeRuleNode() {
        super();
    }

    @Override
    public String toString() {
        String message=
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


    @Override
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

}
