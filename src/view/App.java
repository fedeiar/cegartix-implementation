package view;
import java.util.Set;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import controller.MainWindowController;
import extensions.ExtensionEnumerator;
import parser.AFDataStructures;
import parser.FileManager;
import parser.Parser;
import semantics.Admissibility;
import semantics.ConflictFreenes;
import semantics.Preferred;
import semantics.Semantics;


public class App {
    public static void main(String[] args) throws Exception {

        try{
            AFDataStructures structures = new AFDataStructures();
            structures.calculateAFDataStructures("C:\\Users\\fede\\Desktop\\cegartix-implementation\\af_examples\\af_1.txt");
            
            Semantics semantics = new Preferred(structures); 
            Set<Set<String>> extensions = semantics.getExtensions();
            String latexFormula = semantics.getLatexFullFormula();
            
            System.out.println(extensions.toString());
            System.out.println(latexFormula);

            MainWindowController mainWindowController = new MainWindowController();
            MainWindow mainWindowView = new MainWindow(mainWindowController);
            mainWindowController.setMainWindowView(mainWindowView);
            mainWindowView.setVisible(true);

            mainWindowView.setLatexLabel(latexFormula);
            mainWindowView.setTAExtensionsText(extensions);
            mainWindowView.setTAExplanationText(semantics.getExplanation());

        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        

        // PRINT ARGUMENTS
            /* for(String argument : conflictFreenes.arguments.keySet()){
                System.out.println(argument +" - "+ conflictFreenes.arguments.get(argument));
            } */

            // PRINT CLAUSES
           /*  System.out.println("CLAUSES:"); 
            for(int i = 0 ; i < clauses.size() ; i++){
                IVecInt clause = clauses.get(i);
                for(int j = 0 ; j < clause.size() ; j++){
                    System.out.print(clause.get(j)+" ");
                }
                System.out.println();
            } */
        
    }


   
}