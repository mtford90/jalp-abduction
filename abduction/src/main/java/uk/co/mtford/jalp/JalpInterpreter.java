package uk.co.mtford.jalp;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.AbductiveFramework;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
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
    private static final String RESET_COMMAND = COMMAND_START+"r";
    private static final String QUIT_COMMAND = COMMAND_START+"q";

    private final Scanner scanner = new Scanner(System.in);

    private JALPSystem system;

    public JALPInterpreter(JALPSystem system) {
        this.system=system;
    }

    public void start() {
        System.out.println("Welcome to JALP. Type :h for help.");
        String next = null;
        while(true) {
            System.out.print("JALP->");
            next=scanner.nextLine();
            next = next.trim();
            if (next.length()==0) continue;
            if (next.startsWith(LOAD_COMMAND)) loadFrameworkFromFile(next);
            else if (next.startsWith(QUERY_COMMAND)) executeQuery(next);
            else if (next.startsWith(PRINT_COMMAND)) printFramework();
            else if (next.startsWith(HELP_COMMAND)) printHelp();
            else if (next.startsWith(RESET_COMMAND)) resetSystem();
            else if (next.startsWith(QUIT_COMMAND)) quit();
            else loadFrameworkFromString(next);
        }
    }

    private void printHelp() {
        System.out.println(":l <filename> -  Load a file.");
        System.out.println(":q <query> - Execute a query.");
        System.out.println(":f - View framework.");
        System.out.println(":r - Reset framework.");
        System.out.println(":q - Quit.");
    }

    private void quit() {
        System.out.println("Bye.");
        System.exit(0);
    }

    private void resetSystem() {
        system.setFramework(new AbductiveFramework());
    }

    private void executeQuery(String next) {
        try {
            List<Result> results = system.processQuery(next.substring(2,next.length()-1), JALPSystem.Heuristic.NONE);
            if (results.isEmpty()) {
                System.out.println("No explanations available.");
            }
            else {
                for (Result r:results) {
                    JALP.reduceResult(r);
                    System.out.println("Result 1");
                    System.out.println("-----------------------");
                    System.out.println(r.toString());
                }
                System.out.println("-----------------------");
            }

        } catch (JALPException e) {
            System.err.println("JALP encountered an error.");
            System.err.println(e);
        } catch (uk.co.mtford.jalp.abduction.parse.query.ParseException e) {
            System.err.println("Parse error.");
            System.err.println(e);
        }
    }

    private void printFramework() {
        System.out.println(system.getFramework());
    }

    private void loadFrameworkFromFile(String next) {
        try {
            system.mergeFramework(new File(next.substring(2, next.length())));
        } catch (ParseException e) {
            System.err.println("Parse error.");
            System.err.println(e);
        } catch (FileNotFoundException e) {
            System.err.println("Parse error.");
            System.err.println(e);
        }
    }

    private void loadFrameworkFromString(String next) {
        try {
            system.mergeFramework(next);
        } catch (ParseException e) {
            System.err.println("Parse error.");
            System.err.println(e);
        }
    }
}
