package uk.co.mtford.jalp;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.logic.instance.equalities.IInEqualitySolver;
import uk.co.mtford.jalp.abduction.logic.instance.term.VariableInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.parse.query.JALPQueryParser;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 03/06/2012
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class JALPInterpreter {

    Logger LOGGER = Logger.getLogger(JALPInterpreter.class);

    private static final String COMMAND_START = ":";
    private static final String LOAD_COMMAND = COMMAND_START+"l";
    private static final String QUERY_COMMAND = COMMAND_START+"q";
    private static final String PRINT_COMMAND = COMMAND_START+"f";
    private static final String HELP_COMMAND = COMMAND_START+"h";
    private static final String CLEAR_COMMAND = COMMAND_START+"c";
    private static final String REDUCE_COMMAND = COMMAND_START+"r";
    private static final String QUIT_COMMAND = COMMAND_START+"q";

    private Scanner scanner = new Scanner(System.in);

    private JALPSystem system;

    boolean reduceMode;

    public JALPInterpreter(JALPSystem system) {
        this.system=system;
        this.reduceMode = false;
    }

    public void start() {
        System.out.println("Welcome to JALP. Type :h for help.");
        String next = null;
        while(true) {
            scanner = new Scanner(System.in); // Quick bug fix where JALP-> appears twice...
            System.out.flush();
            System.err.flush();
            System.out.print("JALP->");
            next=scanner.nextLine();
            next = next.trim();
            if (next.length()==0) continue;
            if (next.startsWith(LOAD_COMMAND)) {
                try {
                    loadFrameworkFromFile(next);
                } catch (FileNotFoundException e) {
                    System.err.println("File " + next + " does not exist.");
                } catch (ParseException e) {
                    System.err.println("Parse error: " + e.getMessage().split("\n")[0]);
                }
            }
            else if (next.startsWith(QUERY_COMMAND)) {
                try {
                    executeQuery(next);
                } catch (uk.co.mtford.jalp.abduction.parse.query.ParseException e) {
                    System.err.println("Parse error: " + e.getMessage().split("\n")[0]);
                }
            }
            else if (next.startsWith(PRINT_COMMAND)) printFramework();
            else if (next.startsWith(HELP_COMMAND)) printHelp();
            else if (next.startsWith(CLEAR_COMMAND)) resetSystem();
            else if (next.startsWith(REDUCE_COMMAND)) toggleReduceMode();
            else if (next.startsWith(QUIT_COMMAND)) quit();
            else {
                try {
                    loadFrameworkFromString(next);
                } catch (ParseException e) {
                    System.err.println("Parse error: " + e.getMessage().split("\n")[0]);
                }
            }
        }
    }

    private void printHelp() {
        System.out.println(":l <filename> -  Load a file.");
        System.out.println(":q <query> - Execute a query.");
        System.out.println(":f - View framework.");
        System.out.println(":c - Reset framework.");
        System.out.println(":r - Enable reduce mode.");
        System.out.println(":q - Quit.");
    }

    private void quit() {
        System.out.println("Bye.");
        System.exit(0);
    }

    private void resetSystem() {
        system.setFramework(new AbductiveFramework());
    }

    private void printDashes(int n) {
        for (int i=0;i<n;i++) {
            printDash();
        }
        System.out.println();
    }

    private void printDash() {
        System.out.print("-");
    }

    private void executeQuery(String next) throws uk.co.mtford.jalp.abduction.parse.query.ParseException {
            List<IInferableInstance> query = JALPQueryParser.readFromString(next.substring(2, next.length() - 1));
            List<VariableInstance> queryVariables = new LinkedList<VariableInstance>();
            for (IInferableInstance i:query) {
                queryVariables.addAll(i.getVariables());
            }
            List<Result> results = system.query(query);
            if (results.isEmpty()) {
                System.out.println("No explanations available.");
            }
            else {
                if (reduceMode) {
                    for (Result r:results) {
                        r.reduce(queryVariables);
                    }
                }
                JALP.printResults(query, results);
            }
    }

    private void printFramework() {
        System.out.println(system.getFramework());
    }

    private void loadFrameworkFromFile(String next) throws FileNotFoundException, ParseException {
        File file = new File(next.substring(2, next.length()).trim());
        system.mergeFramework(file);
    }

    private void loadFrameworkFromString(String next) throws ParseException {
        system.mergeFramework(next);
    }

    private void toggleReduceMode() {
        reduceMode=!reduceMode;
        System.out.println(reduceMode?"Reduce mode enabled.":"Reduce mode disabled.");
    }

}
