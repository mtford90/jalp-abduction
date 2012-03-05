/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.io.FileNotFoundException;
import java.util.List;
import org.apache.log4j.Logger;
import uk.co.mtford.abduction.AbductiveFramework;
import uk.co.mtford.abduction.IAbductiveLogicProgrammingSystem;
import uk.co.mtford.abduction.logic.instance.PredicateInstance;
import uk.co.mtford.abduction.parse.ALPParser;
import uk.co.mtford.abduction.parse.ParseException;

/**
 *
 * @author mtford
 */
public abstract class ASystemStateRewriter implements IAbductiveLogicProgrammingSystem {
    
    final private static Logger LOGGER = Logger.getLogger(ASystemStateRewriter.class);
    
    final protected static String FILE_OPTION = "-f";
    final protected static String CONSOLE_OPTION = "-c";
    final protected static String HELP_OPTION = "-h";
    
    protected AbductiveFramework abductiveFramework;
        
    public static void main(String[] args) throws ParseException, FileNotFoundException {
       boolean console = false;
       boolean file = false;
       String fileName = null;
       AbductiveFramework f = new AbductiveFramework();
       for (int i=0;i<args.length;i++) {
           String arg = args[i];
           if (arg.equals(FILE_OPTION)) {
               if (console) {
                   error("Can choose either console or file.");
               }
               if (file) {
                   error("Already specified a file.");
               }
               file = true;
               i++;
               fileName = args[i];
           }
           else if (arg.equals(CONSOLE_OPTION)) {
               console = true;
               if (file) {
                   error("Can choose either console or file.");
               }
           }
           else if (arg.equals(HELP_OPTION)) {
               printHelp();
           }
           else {
               error("Problem with arguments. Use -h option for help.");
           }
       }
       if (console) {
          f = ALPParser.readFromConsole(); 
       }
       else if (file) {
           f = ALPParser.readFromFile(fileName);
       }
    }
    
    public static void printHelp() {
        System.out.println("Options:");
        System.out.println("-h: This help.");
        System.out.println("-c: Read from console.");
        System.out.println("-f <file-path>: Read from file.");
    }
    
    public static void error(String error) {
        System.err.println(error);
        System.exit(-1);
    }
    
    public ASystemStateRewriter(AbductiveFramework abductiveFramework) {
        this.abductiveFramework=abductiveFramework;
    }
    
    /** Computes an abductive explanation in the form of an ASystem
     *  store. Returns null if no possible explanation.
     * 
     * @param query
     * @param abductiveFramework
     * @return 
     */
    public ASystemStore computeExplanation(List<IASystemInferable> query) {
        ASystemState currentState = new ASystemState(query); // Initial state.
        IASystemInferable chosenGoal;
        while ((chosenGoal = getNextGoal(currentState))!=null) {
            currentState = stateTransition(chosenGoal,(ASystemState)currentState.clone());
            if (currentState==null) {
                return null; // Failed to move to another state.
            }
        }
        return currentState.getStore();
    }
    
    protected abstract IASystemInferable getNextGoal(ASystemState state);
    
    /** Returns the next state. Returns null if not possible to move to next.
     * 
     * @param goal
     * @return 
     */
    protected abstract ASystemState stateTransition(IASystemInferable goal, ASystemState state);
    
    

    
} 
