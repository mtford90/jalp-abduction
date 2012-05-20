package uk.co.mtford.alp;

import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.logic.instance.IASystemInferableInstance;
import uk.co.mtford.alp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.alp.abduction.parse.program.ALPParser;
import uk.co.mtford.alp.abduction.parse.program.ParseException;
import uk.co.mtford.alp.abduction.parse.program.TokenMgrError;
import uk.co.mtford.alp.abduction.parse.query.ALPQueryParser;
import uk.co.mtford.alp.abduction.rules.RuleNode;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point. Provides a terminal like interface.
 *
 * @author mtford
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static final Scanner sc = new Scanner(System.in);

    // Command line options.
    private static final String FILE_OPTION = "-f";
    private static final String HELP_OPTION = "-h";
    private static final String QUERY_OPTION = "-q";
    private static final String DEBUG_OPTION = "-d";

    // ALPS commands.
    private static final char FILE_COMMAND = 'l';
    private static final char QUERY_COMMAND = 'q';
    private static final char CLEAR_COMMAND = 'c';
    private static final char HELP_COMMAND = 'h';
    private static final char DUMP_COMMAND = 'm';
    private static final char DEBUG_COMMAND = 'd';

    // Messages.
    private static final String EXEC_HELP
            = "Options:" + "\n" +
            "-h            : This help." + "\n" +
            "-f <file-path>: Read from file." + "\n" +
            "-q <query>    : Execute a query on the given file" + "\n" +
            "-d            : Enables debug mode ";
    private static final String EXEC_ARG_ERROR
            = "Problem with arguments. Use -h option for help.";
    private static final String ALPS_HELP
            = "Enter facts, rules and integrity constraints or use the following options." + "\n" + "\n" +
            "Options" + "\n" +
            "-------" + "\n" +
            "Load a file    => :l (<filename>)+" + "\n" +
            "Run a query    => :q (p(u1,...,un),)* p(u1,...,un)" + "\n" +
            "Reset system   => :c" + "\n" +
            "Memory dump    => :m" + "\n" +
            "Debug mode     => :d" + "\n" +
            "This help      => :h";
    private static final String UNKNOWN_COMMAND
            = "Command not recognised. Use :h for help.";
    private static final String INVALID_USAGE
            = "Invalid usage. Use :h help.";

    private static boolean debugMode = false;
    private static RuleNode rootNode;
    private static AbductiveFramework framework;

    /**
     * Prints a message to standard error and then quits with error status.
     *
     * @param error
     */
    private static void fatalError(String error) {
        System.err.println(error);
        System.exit(-1);
    }

    /**
     * Prints a message to standard out.
     *
     * @param str
     */
    private static void printMessage(String str) {
        System.out.println(str);
    }

    /**
     * Prints a message to standard out along with the string representation of some throwable.
     *
     * @param str
     * @param t
     */
    private static void printMessage(String str, Throwable t) {
        System.out.println(str);
        t.printStackTrace(System.out);
    }

    private static void mergeFramework(AbductiveFramework newFramework) {
        if (framework == null) {
            framework = newFramework;
        } else {
            framework.getP().addAll(newFramework.getP());
            framework.getIC().addAll(newFramework.getIC());
            framework.getA().putAll(newFramework.getA());
        }
    }

    /**
     * Resets the abductive framework.
     */
    private static void reset() {
        framework = new AbductiveFramework();
    }

    /**
     * Loads and parses an abductive logic program and constructs the framework object.
     *
     * @param nextLine
     */
    private static void loadFiles(String nextLine) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Loading file called " + nextLine);
        String[] fileNames = nextLine.split(" ");
        if (fileNames.length < 2) {
            printMessage(INVALID_USAGE);
        } else {
            for (int i = 1; i < fileNames.length; i++) {
                AbductiveFramework newF = null;
                try {
                    newF = ALPParser.readFromFile(fileNames[i]);
                } catch (FileNotFoundException ex) {
                    printMessage("No such file.", ex);
                } catch (ParseException ex) {
                    printMessage("Parse error in file " + fileNames[i], ex);
                } catch (TokenMgrError ex) {
                    printMessage("Token error\n" + ex.getMessage(), ex);
                }
                if (newF != null) mergeFramework(newF);
            }
        }
    }

    /**
     * Parses and executes a query on the currently held abductive framework. Operates in either debug (step) mode
     * or performs all steps and returns the results.
     *
     * @param query
     * @throws uk.co.mtford.alp.abduction.parse.query.ParseException
     *
     */
    private static void processQuery(String query) throws uk.co.mtford.alp.abduction.parse.query.ParseException {
        List<PredicateInstance> predicates = ALPQueryParser.readFromString(query);
        List<IASystemInferableInstance> goals = new LinkedList<IASystemInferableInstance>();
        goals.addAll(predicates);
        if (debugMode) {
            /*
            Iterator<ASystemState> iterator = system.getStateIterator(goals);
            while (iterator.hasNext()) {
                printMessage("Current state is: \n"+iterator.next());
                printMessage("Enter c to continue or anything else to quit.");
                String s = sc.nextLine();
                if (s.trim().isSameFunction("c")) {
                    continue;
                }
                else {
                    break;
                }
            }
            */
        } else {
            if (LOGGER.isInfoEnabled()) LOGGER.info("Beginning processing of query.");
        }
    }


    private static void startALPS() {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Starting ALPS.");
        while (true) {
            System.out.print("ALPS -> ");
            String nextLine = sc.nextLine();
            char c = ' ';
            if (!nextLine.isEmpty()) c = nextLine.charAt(0);
            if (c == ':') { // Execute a command.
                c = nextLine.charAt(1);
                switch (c) {
                    case FILE_COMMAND:
                        loadFiles(nextLine);
                        break;
                    case QUERY_COMMAND:
                        try {
                            processQuery(nextLine.substring(2, nextLine.length()));
                        } catch (uk.co.mtford.alp.abduction.parse.query.ParseException ex) {
                            printMessage("Invalid query\n" + ex.getMessage());
                        } catch (uk.co.mtford.alp.abduction.parse.query.TokenMgrError ex) {
                            printMessage("Invalid query\n" + ex.getMessage());
                        } catch (Exception e) { // Process query doesn't yet work. So avoid a crash.
                            LOGGER.error("ALPS has encountered a problem.", e);
                        }
                        break;
                    case CLEAR_COMMAND:
                        reset();
                        System.out.println("All cleared.");
                        break;
                    case HELP_COMMAND:
                        printMessage(ALPS_HELP);
                        break;
                    case DUMP_COMMAND:
                        printMessage(framework.toString());
                        break;
                    case DEBUG_COMMAND:
                        debugMode = !debugMode;
                        printMessage(debugMode ? "Debug mode enabled." : "Debug mode disabled.");
                        break;
                    default:
                        printMessage(UNKNOWN_COMMAND);
                }
            } else if (c == ' ') { // Carry on.
                continue;
            } else { //
                AbductiveFramework newF = null;
                try {
                    newF = ALPParser.readFromString(nextLine);
                } catch (ParseException ex) {
                    printMessage("Parse error\n" + ex.getMessage());
                } catch (TokenMgrError ex) {
                    printMessage("Invalid syntax\n" + ex.getMessage());
                }
                if (newF != null) mergeFramework(newF);
            }
        }
    }

    /**
     * Main point of entry. Either starts up the ALPS command line if query not provided via program arguments
     * or performs the derivation and returns the result.
     *
     * @param args
     * @throws uk.co.mtford.alp.abduction.parse.query.ParseException
     *
     */
    public static void main(String[] args) throws uk.co.mtford.alp.abduction.parse.query.ParseException {
        boolean console = false;
        boolean file = false;
        boolean query = false;
        String fileName = null;
        String queryString = null;
        AbductiveFramework f = new AbductiveFramework();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.equals(FILE_OPTION)) {
                if (console) {
                    fatalError("Can choose either console or file.");
                }
                if (file) {
                    fatalError("Already specified a file.");
                }
                file = true;
                i++;
                fileName = args[i];
            } else if (arg.equals(HELP_OPTION)) {
                printMessage(EXEC_HELP);
            } else if (arg.equals(QUERY_OPTION)) {
                query = true;
                i++;
                queryString = args[i];
            } else if (arg.equals(DEBUG_OPTION)) {
                debugMode = true;
            } else {
                printMessage(EXEC_ARG_ERROR);
            }
        }

        if (file) {
            System.out.println("Reading from " + fileName);

            try {
                f = ALPParser.readFromFile(fileName);
                System.out.println("Successfully read " + fileName);
            } catch (FileNotFoundException ex) {
                LOGGER.error("Cannot find file.");
                file = false;
            } catch (ParseException ex) {
                LOGGER.error("Syntax error in " + fileName, ex);
                file = false;
            }

        }

        if (query && file) {
            mergeFramework(f);
            processQuery(queryString);
        } else if (!query) {
            mergeFramework(f);
            startALPS();
        }

    }

}
