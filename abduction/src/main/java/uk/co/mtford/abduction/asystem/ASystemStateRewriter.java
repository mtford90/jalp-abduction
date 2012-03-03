/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.mtford.abduction.asystem;

import java.io.FileNotFoundException;
import java.util.List;
import uk.co.mtford.abduction.AbductiveFramework;
import uk.co.mtford.abduction.IAbductiveLogicProgrammingSystem;
import uk.co.mtford.abduction.parse.ALPParser;
import uk.co.mtford.abduction.parse.ParseException;

/**
 *
 * @author mtford
 */
public class ASystemStateRewriter implements IAbductiveLogicProgrammingSystem {
    
    final static String FILE_OPTION = "-f";
    final static String CONSOLE_OPTION = "-c";
    final static String HELP_OPTION = "-h";
    
    protected boolean success;
    
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

    public ASystemStateRewriter() {
        success = false;
    }
    
    private void resetSystem() {
        success = false;
    }
    
    /** Computes an abductive explanation in the form of an ASystem
     *  store.
     * 
     * @param query
     * @param abductiveFramework
     * @return 
     */
    public ASystemStore computeExplanation(List<IASystemInferable> query, 
                                    AbductiveFramework abductiveFramework) {
        ASystemState state = new ASystemConcreteState(query, abductiveFramework);
        while (state.moveToNextState()) {
            // Keep going.
        }
        if (state.hasGoalsRemaining()) {
            return null;
        }
        return state.getStore();
    }
    
} 
