package uk.co.mtford.jalp;

import org.apache.commons.io.FileUtils;
import uk.co.mtford.jalp.abduction.Definition;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.*;
import uk.co.mtford.jalp.abduction.logic.instance.constraints.*;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.EqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.InEqualityInstance;
import uk.co.mtford.jalp.abduction.logic.instance.term.*;
import uk.co.mtford.jalp.abduction.rules.RuleNode;
import uk.co.mtford.jalp.abduction.rules.visitor.AbstractRuleNodeVisitor;
import uk.co.mtford.jalp.abduction.rules.visitor.SimpleRuleNodeVisitor;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *  The Java API for interacting with the JALP System.
 *  @author Michael Ford
 */
public class JALP {

    /**
     * Generates a visualizer for the ASystem derivation tree specified in folder folderName.
     *
     * @param folderName The folder in which all visualizer files will be placed.
     * @param root       The root node of the tree for which a visualizer should be generated.
     * @throws IOException
     */
    public static void getVisualizer(String folderName, RuleNode root) throws IOException {
        File visualizer = new File("visualizer2");
        File destination = new File(folderName);
        FileUtils.copyDirectory(visualizer, destination);
        String js = "var data=\""+root.toJSON()+"\"";
        FileUtils.writeStringToFile(new File(folderName + "/output.js"), js);
    }

    /**
     * Applies the ASystem inference rule specified by the class of the the ruleNode.
     *
     * @param node The rule node to which the inference rule will be applied.
     */
    public static void applyRule(RuleNode node) {
        AbstractRuleNodeVisitor visitor = new SimpleRuleNodeVisitor();
        node.acceptVisitor(visitor);
    }

    /**
     * Returns a new true instance aka Top.
     *
     * @return A true instance.
     */
    public static TrueInstance makeTrueInstance() {
        return new TrueInstance();
    }

    /**
     * Returns a new false instance aka Bottom.
     *
     * @return A false instance.
     */
    public static FalseInstance makeFalseInstance() {
        return new FalseInstance();
    }

    /**
     * Creates and returns an instance of a character-based constant e.g. bob, c.
     *
     * @param string The characters used to create the constant.
     * @return A character based constant instance.
     */
    public static CharConstantInstance makeCharConstantInstance(String string) {
        return new CharConstantInstance(string);
    }

    /**
     *  Creates and returns an instance of an integer-based constant e.g. 12, 105
     *
     * @param n The integer used to create constant.
     * @return An integer based constant instance.
     */
    public static IntegerConstantInstance makeIntegerConstantInstance(int n) {
        return new IntegerConstantInstance(n);
    }

    /**
     *  Creates and returns a variable of specified name e.g. X, Time
     *
     * @param name Name of variable
     * @return A variable instance of given name.
     */
    public static VariableInstance makeVariableInstance(String name) {
        return new VariableInstance(name);
    }

    /**
     * Creates and returns a predicate of given name and parameters e.g. p(X,bob).
     *
     * @param name Name of predicate.
     * @param parameters List of parameters of unifiable type.
     * @return A predicate instance.
     */
    public static PredicateInstance makePredicateInstance(String name, List<IUnifiableInstance> parameters) {
        return new PredicateInstance(name,parameters);
    }

    /**
     * Creates and returns a predicate of given name and parameters e.g. p(X,bob).
     *
     * @param name Name of predicate.
     * @param parameters Array of parameters of unifiable type.
     * @return A predicate instance.
     */
    public static PredicateInstance makePredicateInstance(String name, IUnifiableInstance... parameters) {
        return new PredicateInstance(name,parameters);
    }

    /**
     * Creates an returns an equality instance between the given unifiable instances eg. X=4.
     *
     * @param left A unifiable e.g. a ConstantCharInstance or a VariableInstance
     * @param right A unifiable e.g. a ConstantCharInstance or a VariableInstance
     * @return An equality instance.
     */
    public static EqualityInstance makeEqualityInstance(IUnifiableInstance left, IUnifiableInstance right) {
        return new EqualityInstance(left,right);
    }

    /**
     * Creates an returns an inequality instance between the given unifiable instances.
     *
     * @param left A unifiable e.g. a ConstantCharInstance or a VariableInstance
     * @param right A unifiable e.g. a ConstantCharInstance or a VariableInstance
     * @return An inequality instance.
     */
    public static InEqualityInstance makeInEqualityInstance(IUnifiableInstance left, IUnifiableInstance right) {
        return new InEqualityInstance(left,right);
    }

    /**
     * Converts the given equality instance into an inequality e.g. X!=3
     *
     * @param equalityInstance The equality instance to be changed into an inequality.
     * @return An inequality instance.
     */
    public static InEqualityInstance makeInEqualityInstance(EqualityInstance equalityInstance) {
        return new InEqualityInstance(equalityInstance);
    }

    /**
     * Creates an integer list instance using the collection of integer constant instances e.g. [1,2,3,4]
     *
     * @param integers A java collection of integer constants
     * @return A new integer list instance.
     */
    public static IntegerConstantListInstance makeIntegerConstantListInstance(Collection<IntegerConstantInstance> integers) {
        return new IntegerConstantListInstance(integers);
    }

    /**
     * Creates an integer list instance using the array of integer constant instances e.g. [1,2,3,4]
     *
     * @param integers An array of integer constant instances.
     * @return A new integer list instance.
     */
    public static IntegerConstantListInstance makeIntegerConstantListInstance(IntegerConstantInstance[] integers) {
        return new IntegerConstantListInstance(integers);
    }

    /**
     * Creates an integer list instance using the array of integers e.g. [1,2,3,4]
     *
     * @param integers An array of integers.
     * @return A new integer list instance.
     */
    public static IntegerConstantListInstance makeIntegerConstantListInstance(int ... integers) {
        return new IntegerConstantListInstance(integers);
    }

    /**
     * Creates an empty integer list instance e.g. [ ]
     *
     * @return A new empty integer list instance.
     */
    public static IntegerConstantListInstance makeIntegerConstantListInstance() {
        return new IntegerConstantListInstance();
    }

    /**
     * Creates an character constant list instance using the collection of character constant instances e.g. [bob,john]
     *
     * @param constants An java collection of character-based constant instances.
     * @return A list instance of character based constants.
     */
    public static CharConstantListInstance makeCharConstantListInstance(Collection<CharConstantInstance> constants) {
        return new CharConstantListInstance(constants);
    }

    /**
     * Creates an character constant list instance using the array of character constant instances e.g. [bob,john]
     *
     * @param constants An array of character-based constant instances.
     * @return A list instance of character based constants.
     */
    public static CharConstantListInstance makeCharConstantListInstance(CharConstantInstance[] constants) {
        return new CharConstantListInstance(constants);
    }

    /**
     * Creates an character constant list instance using the array of strings e.g. [bob,john]
     *
     * @param strings An array of strings.
     * @return A list instance of character based constants.
     */
    public static CharConstantListInstance makeCharConstantListInstance(String ... strings) {
        return new CharConstantListInstance(strings);
    }

    /**
     * Creates and returns an empty list of character constant instances e.g. []
     *
     * @return An empty listof character constant instances.
     */
    public static CharConstantListInstance makeCharConstantListInstance() {
        return new CharConstantListInstance();
    }

    /**
     * Creates and returns the negation instance of the given inferable e.g. not X
     *
     * @param inferable The inferable to negate. e.g. p(X), X=4
     * @return A negation instance with the given inferable as a inferable.
     */
    public static NegationInstance makeNegationInstance(IInferableInstance inferable) {
        return new NegationInstance(inferable);
    }

    /**
     * Creates an returns a constraint of the form 'u in [n,...,m]'
     *
     * @param left A term.
     * @param right An integer constant list e.g. [1,2,3,4]
     * @return A constraint of the form 'u in [n,...,m]'
     */
    public static InIntegerListConstraintInstance makeInIntegerListConstraint(ITermInstance left, IntegerConstantListInstance right) {
        return new InIntegerListConstraintInstance(left,right);
    }

    /**
     * Creates an returns a constraint of the form 'u in [c,...,d]'
     *
     * @param left A term.
     * @param right A character constant list e.g. [bob,john]
     * @return A constraint of the form 'u in [c,...,d]'
     */
    public static InConstantListConstraintInstance makeInConstantListConstraint(ITermInstance left, CharConstantListInstance right) {
        return new InConstantListConstraintInstance(left,right);
    }

    /**
     * Creates and returns a constraint of the form t>s
     * @param left A term e.g. p(X), c
     * @param right A term e.g. p(X), c
     * @return A constraint of the form t>s.
     */
    public static GreaterThanConstraintInstance makeGreaterThanConstraintInstance(ITermInstance left, ITermInstance right) {
        return new GreaterThanConstraintInstance(left,right);
    }

    /**
     * Creates and returns a constraint of the form t>=s
     * @param left A term e.g. p(X), c
     * @param right A term e.g. p(X), c
     * @return A constraint of the form t>=s.
     */
    public static GreaterThanEqConstraintInstance makeGreaterThanEqConstraintInstance(ITermInstance left, ITermInstance right) {
        return new GreaterThanEqConstraintInstance(left,right);
    }

    /**
     * Creates and returns a constraint of the form t<s
     * @param left A term e.g. p(X), c
     * @param right A term e.g. p(X), c
     * @return A constraint of the form t<s.
     */
    public static LessThanConstraintInstance makeLessThanConstraintInstance(ITermInstance left, ITermInstance right) {
        return new LessThanConstraintInstance(left,right);
    }

    /**
     * Creates and returns a constraint of the form t<=s
     * @param left A term e.g. p(X), c
     * @param right A term e.g. p(X), c
     * @return A constraint of the form t<=s.
     */
    public static LessThanEqConstraintInstance makeLessThanEqConstraintInstance(ITermInstance left, ITermInstance right) {
        return new LessThanEqConstraintInstance(left,right);
    }

    /**
     * Creates and returns a denial instance.
     *
     * @param universalVariables A list of variables that are universally quantified.
     * @param body A list of inferables that make up the denial body.
     * @return A denial instance.
     */
    public static DenialInstance makeDenialInstance(List<VariableInstance> universalVariables,List<IInferableInstance> body) {
        return new DenialInstance(body,universalVariables);
    }

    /**
     * Creates and returns a denial instance.
     *
     * @param universalVariables An list of variables that are universally quantified.
     * @param body An array of inferables that make up the denial body.
     * @return A denial instance.
     */
    public static DenialInstance makeDenialInstance(List<VariableInstance> universalVariables,IInferableInstance ... body) {
        return new DenialInstance(universalVariables, body);
    }

    /**
     * Creates and returns a denial instance with all variables existentially quantified.
     *
     * @param body A list of inferables that make up the denial body.
     * @return A denial instance.
     */
    public static DenialInstance makeDenialInstance(List<IInferableInstance> body) {
        return new DenialInstance(body);
    }

    /**
     * Creates and returns a denial instance with all variables existentially quantified.
     *
     * @param body An array of inferables that make up the denial body.
     * @return A denial instance.
     */
    public static DenialInstance makeDenialInstance(IInferableInstance ... body) {
        return new DenialInstance(body);
    }

    /**
     * Creates and returns a denial instance with an empty body i.e. '<- .'
     *
     * @return A denial instance.
     */
    public static DenialInstance makeDenialInstance() {
        return new DenialInstance();
    }

    /**
     * Returns a fact which is a rule of the form p(\bar u). i.e. a rule with an empty body.
     *
     * @param headPredicateName Name of head predicate.
     * @param headParameters Array of unifiable parameters.
     * @return A rule instance with no body.
     */
    public static Definition makeFact(String headPredicateName, IUnifiableInstance[] headParameters) {
        return new Definition(new PredicateInstance(headPredicateName,headParameters), (List<IInferableInstance>) null,null);
    }

    /**
     * Returns a fact which is a rule of the form p(\bar u). i.e. a rule with an empty body.
     *
     * @param headPredicateName Name of head predicate.
     * @param headParameters List of unifiable parameters e.g. [X,bob]
     * @return A rule instance with no body.
     */
    public static Definition makeFact(String headPredicateName, List<IUnifiableInstance> headParameters) {
        return new Definition(new PredicateInstance(headPredicateName,new LinkedList<IUnifiableInstance>(headParameters)), (List<IInferableInstance>) null,null);
    }

    /**
     * Creates and returns a rule instance of the form p(\bar u) <- B. Also know as a horn clause.
     * @param headPredicateName  Name of head predicate.
     * @param headParameters  Array of unifiable parameters.
     * @param body An array of inferables to be used as the rule body.
     * @return A rule instance.
     */
    public static Definition makeRule(String headPredicateName, IUnifiableInstance[] headParameters, IInferableInstance[] body) {
        return new Definition(new PredicateInstance(headPredicateName,headParameters),body,null);
    }

    /**
     * Creates and returns a rule instance of the form p(\bar u) <- B. Also know as a horn clause.
     * @param headPredicateName  Name of head predicate.
     * @param headParameters  List of unifiable parameters.
     * @param body An array of inferables to be used as the rule body.
     * @return A rule instance.
     */
    public static Definition makeRule(String headPredicateName, List<IUnifiableInstance> headParameters, IInferableInstance[] body) {
        return new Definition(new PredicateInstance(headPredicateName,new LinkedList<IUnifiableInstance>(headParameters)),body,null);
    }

    /**
     * Creates and returns a rule instance of the form p(\bar u) <- B. Also know as a horn clause.
     * @param headPredicateName  Name of head predicate.
     * @param headParameters  Array of unifiable parameters.
     * @param body A list of inferables to be used as the rule body.
     * @return A rule instance.
     */
    public static Definition makeRule(String headPredicateName, IUnifiableInstance[] headParameters, List<IInferableInstance> body) {
        return new Definition(new PredicateInstance(headPredicateName,headParameters),body,null);
    }

    /**
     * Creates and returns a rule instance of the form p(\bar u) <- B. Also know as a horn clause.
     * @param headPredicateName  Name of head predicate.
     * @param headParameters  List of unifiable parameters.
     * @param body A list of inferables to be used as the rule body.
     * @return A rule instance.
     */
    public static Definition makeRule(String headPredicateName, List<IUnifiableInstance> headParameters, List<IInferableInstance> body) {
        return new Definition(new PredicateInstance(headPredicateName,new LinkedList<IUnifiableInstance>(headParameters)),body,null);
    }

    /**
     * Given the initial query and a list of results, prints those results in a well formatted manner.
     *
     * @param query
     * @param results
     */
    public static void printResults(List<IInferableInstance> query, List<Result> results) {
        Scanner scanner = new Scanner(System.in);
        do {
            Result r = results.remove(0);
            System.out.println("Query");
            System.out.println("  "+query.toString().substring(1,query.toString().length()-1));
            System.out.println(r.toString()+"\n");
            if (!results.isEmpty()) {
                System.out.print("There are "+results.size()+" results remaining. See next? (y/n): ");
                System.out.flush();
                String n = scanner.next();
                System.out.println();
                boolean seeNext = false;
                while (!(n.equals("y")||n.equals("n"))) {
                    System.out.print("There are "+results.size()+" results remaining. See next? (y/n): ");
                    System.out.flush();
                    n = scanner.next();
                    System.out.println();
                }
                if (n.equals("n")) {
                    break;
                }

            }
        }
        while (!results.isEmpty());
    }


}

