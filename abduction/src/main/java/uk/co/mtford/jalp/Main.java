package uk.co.mtford.jalp;

import org.apache.log4j.Logger;
import uk.co.mtford.jalp.abduction.Result;
import uk.co.mtford.jalp.abduction.logic.instance.IInferableInstance;
import uk.co.mtford.jalp.abduction.parse.program.ParseException;
import uk.co.mtford.jalp.abduction.parse.query.JALPQueryParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Controls point of access to either command line or the interpreter.
 *
 */
public class Main {

    private static Logger LOGGER = Logger.getLogger(Main.class);

    private static final String CMD_START = "-";
    private static final String REDUCE_OPTION = CMD_START+"r";
    private static final String QUERY_OPTION = CMD_START+"q";
    private static final String DEBUG_OPTION = CMD_START+"d";

    private static boolean reduce = false;
    private static boolean debug = false;

    private static String query = null;
    private static LinkedList<String> fileNames = new LinkedList<String>();
    private static String debugFolder = null;

    private static void printError(String text) {
        System.err.println(text);
        System.exit(-1);
    }

    private static void printError(String text, Throwable throwable) {
        System.err.println(text);
        System.err.println(throwable);
        System.exit(-1);
    }

    public static void main(String[] args)  {
        for (int i=0;i<args.length;i++) {

            String s = args[i];
            if (s.equals(REDUCE_OPTION)) {
                reduce = true;
            }
            else if (s.equals(QUERY_OPTION)) {
                i++;
                query = args[i];
            }
            else if (s.equals(DEBUG_OPTION)) {
                debug = true;
                i++;
                debugFolder = args[i];
            }
            else {
                fileNames.add(args[i]);

            }
        }


        if (query!=null && fileNames.isEmpty()) {
            printError("You can't run a query when no abductive theory has been loaded.");
            System.exit(-1);
        }

        JALPSystem system = new JALPSystem();

        for (String fileName:fileNames) {
            System.out.println("Loading "+fileName);
            try {
                system.mergeFramework(new File(fileName));
            } catch (FileNotFoundException e) {
                printError("File "+fileName+" doesn't exist.");
            } catch (ParseException e) {
                printError("Parse error.",e);
            }
        }

        if (debug) {
            System.out.println("Generating visualizer and logs in folder "+debugFolder);
            try {
                system.generateDebugFiles(query,debugFolder);
            } catch (IOException e) {
                printError("IO problem whilst generating output.",e);
            } catch (JALPException e) {
                printError("JALP encountered a problem.",e);
            } catch (uk.co.mtford.jalp.abduction.parse.query.ParseException e) {
                printError("Error parsing query.",e);
            }
        }

        else {
            if (query!=null) {
                try {
                    List<IInferableInstance> queryList = JALPQueryParser.readFromString(query);
                    List<Result> results = system.query(new LinkedList<IInferableInstance>(queryList));
                    JALP.printResults(queryList,results);
                    System.out.println("Exiting...");
                } catch (JALPException e) {
                    printError("JALP encountered a problem.",e);
                } catch (uk.co.mtford.jalp.abduction.parse.query.ParseException e) {
                    printError("Error parsing query",e);
                }
            }
            else {
                JALPInterpreter jalpInterpreter = new JALPInterpreter(system);
                jalpInterpreter.start();
            }


        }

    }
}
