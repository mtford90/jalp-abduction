package uk.co.mtford.alp;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;
import uk.co.mtford.alp.abduction.AbductiveFramework;
import uk.co.mtford.alp.abduction.asystem.ASystemBasicStateRewriter;
import uk.co.mtford.alp.abduction.asystem.ASystemStore;
import uk.co.mtford.alp.abduction.asystem.IASystemInferable;
import uk.co.mtford.alp.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.alp.abduction.parse.program.ALPParser;
import uk.co.mtford.alp.abduction.parse.program.ParseException;
import uk.co.mtford.alp.abduction.parse.program.TokenMgrError;
import uk.co.mtford.alp.abduction.parse.query.ALPQueryParser;

/** Main entry point. Provides a terminal like interface.
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
    
    // ALPS commands.
    private static final char FILE_COMMAND = 'l';
    private static final char QUERY_COMMAND = 'q';
    private static final char CLEAR_COMMAND = 'c';
    private static final char HELP_COMMAND = 'h';
    private static final char DUMP_COMMAND = 'd';
    
    // Messages.
    private static final String EXEC_HELP 
            = "Options:" + "\n" +
              "-h: This help." + "\n" +
              "-f <file-path>: Read from file."  + "\n";
    private static final String EXEC_ARG_ERROR
            = "Problem with arguments. Use -h option for help.";
    private static final String ALPS_HELP
            = "Enter facts, rules and integrity constraints or use the following options." + "\n" + "\n" +
              "Options" + "\n" + 
              "-------" + "\n" +
              "Load a file    => :l (<filename>)+" + "\n" +
              "Run a query    => :q (p(u1,...,un),)* p(u1,...,un)" + "\n" +
              "Reset system   => :c" + "\n" +
              "Memory dump    => :d" + "\n" +
              "This help      => :h";
    private static final String UNKNOWN_COMMAND 
            = "Command not recognised. Use :h for help.";
    private static final String INVALID_USAGE
            = "Invalid usage. Use :h help.";
    
    private static ASystemBasicStateRewriter system;
    
    private static void fatalError(String error) {
        System.err.println(error);
        System.exit(-1);
    }
    
    private static void printMessage(String str) {
        System.out.println(str);
    }
    
    private static void incorporateNewFramework(AbductiveFramework f) {
        LOGGER.info("Incorporating results from new framework.");
        system.getAbductiveFramework().getP().addAll(f.getP());
        system.getAbductiveFramework().getA().addAll(f.getA());
        system.getAbductiveFramework().getIC().addAll(f.getIC());
    }
    
    private static void loadFiles(String nextLine) {
        LOGGER.info("Loading file called "+nextLine);
        String[] fileNames = nextLine.split(" ");
        if (fileNames.length<2) {
            printMessage(INVALID_USAGE);
        }
        else {
            for (int i=1;i<fileNames.length;i++) {
                AbductiveFramework newF = null;
                try {
                    newF = ALPParser.readFromFile(fileNames[i]);
                } 
                catch (FileNotFoundException ex) {
                    printMessage("No such file.");
                } 
                catch (ParseException ex) {
                    printMessage("Parse error in file "+fileNames[i]);
                }
                catch (TokenMgrError ex) {
                    printMessage("Token error\n"+ex.getMessage());
                }
                if (newF!=null) incorporateNewFramework(newF);
            }
        }
    }
    
    private static void processQuery(String query) throws uk.co.mtford.alp.abduction.parse.query.ParseException { 
        if (LOGGER.isInfoEnabled()) LOGGER.info("Beginning processing of query.");
        List<PredicateInstance> predicates = ALPQueryParser.readFromString(query);
        List<IASystemInferable> goals = new LinkedList<IASystemInferable>();
        goals.addAll(predicates);
        List<ASystemStore> possibleExplanations = system.computeExplanation(goals);
        printMessage("Found "+possibleExplanations.size()+" possible explanations.");
        for (ASystemStore s:possibleExplanations) printMessage(s.toString());
    }
    
    private static void initALPS(AbductiveFramework f) {
        system = new ASystemBasicStateRewriter(f);
    }
    
    private static void startALPS() {
        if (LOGGER.isInfoEnabled()) LOGGER.info("Starting ALPS.");
        while (true) {
            System.out.print("ALPS -> ");
            String nextLine = sc.nextLine();
            char c = nextLine.charAt(0);
            if (c==':') {
                c=nextLine.charAt(1);
                switch (c) {
                    case FILE_COMMAND: 
                        loadFiles(nextLine);
                        break;
                    case QUERY_COMMAND: 
                        try {
                            processQuery(nextLine.substring(2, nextLine.length()));
                        } 
                        catch (uk.co.mtford.alp.abduction.parse.query.ParseException ex) {
                            printMessage("Invalid query\n"+ex.getMessage());
                        }
                        catch (uk.co.mtford.alp.abduction.parse.query.TokenMgrError ex) {
                            printMessage("Invalid query\n"+ex.getMessage());
                        }
                        catch (Exception e) { // Process query doesn't yet work. So avoid a crash.
                            LOGGER.error("ALPS has encountered a problem.", e);
                        }
                        break;
                    case CLEAR_COMMAND: 
                        system.reset();
                        System.out.println("All cleared.");
                        break;
                    case HELP_COMMAND: 
                        printMessage(ALPS_HELP);
                        break;
                    case DUMP_COMMAND:
                        printMessage(system.getAbductiveFramework().toString());
                        break;
                    default: 
                        printMessage(UNKNOWN_COMMAND);
                }
            }

            else { 
                AbductiveFramework newF = null;
                try {
                    newF = ALPParser.readFromString(nextLine);
                } 
                catch (ParseException ex) {
                    printMessage("Parse error\n"+ex.getMessage());
                }
                catch (TokenMgrError ex) {
                    printMessage("Invalid syntax\n"+ex.getMessage());
                }
                if (newF != null) incorporateNewFramework(newF);
            }
        }
    }
    
    // Initialise
    public static void main(String[] args) throws uk.co.mtford.alp.abduction.parse.query.ParseException {
       boolean console = false;
       boolean file = false;
       boolean query = false;
       String fileName = null;
       String queryString = null;
       AbductiveFramework f = new AbductiveFramework();
       
       for (int i=0;i<args.length;i++) {
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
           }
           
           else if (arg.equals(HELP_OPTION)) {
               printMessage(EXEC_HELP);
           }
           
           else if (arg.equals(QUERY_OPTION)) {
               query=true;
               i++;
               queryString = args[i];
           }
           
           else {
               printMessage(EXEC_ARG_ERROR);
           }
       }
       
       if (file) {
           System.out.println("Reading from "+fileName);
           
            try {
                f = ALPParser.readFromFile(fileName);
                 System.out.println("Successfully read "+fileName);
            } catch (FileNotFoundException ex) {
                LOGGER.error("Cannot find file.");
                file = false;
            } catch (ParseException ex) {
                LOGGER.error("Syntax error in "+fileName,ex);
                file = false;
            }
          
       }
       if (query && file) {
           initALPS(f);
           processQuery(queryString);
       }
       else if (!query) {
           initALPS(f);
           startALPS();
       }
       
       
    }
    
} 
