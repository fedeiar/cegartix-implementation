package semantics;

import java.util.HashSet;
import java.util.Set;

import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import extensions.ExtensionEnumerator;
import parser.AFDataStructures;

public class Preferred extends Semantics{
    
    protected Admissibility admissibility;

    public Preferred(AFDataStructures structures){
        super(structures);
        latexGenericFormula = "\\\\ prf_{Ar, att} := adm_{Ar, att} \\land (\\nexists Ar' \\ | \\ Ar \\subset Ar' \\land adm_{Ar', att}),\\\\ where \\ Ar' \\subseteq Args";
        
        admissibility = new Admissibility(structures);
        latexFormulaHeader = admissibility.getLatexFormulaHeader();
        
        explanation = "In order to get preferred extensions, instead of directly use the formula for preferred extensions and look for all of the subsets of Ar' in order to find the models of prf, we will get all the admissible sets, and keep only the maximal ones of them. This works because one of the possible definitions for a preferred extension is that is a maximal admissible set with respect to set inclusion.";
    }

    public IVec<IVecInt> calculateReduction(){
        IVec<IVecInt> clauses = admissibility.calculateReduction();
        latexFormulaBody = admissibility.getLatexFormulaBody();
        return clauses;
    }


    //TODO: esta bien?
    public Set<Set<String>> getExtensions() throws Exception{
        IVec<IVecInt> clauses = this.calculateReduction();
        ExtensionEnumerator extensionEnumerator = new ExtensionEnumerator(arguments, clauses);
        Set<Set<String>> extensions = extensionEnumerator.getExtensions();

        Set<Set<String>> preferredExtensions = new HashSet<Set<String>>();
        boolean isPreferredExtension;
        for(Set<String> extension1 : extensions){
            isPreferredExtension = true;
            for(Set<String> extension2: extensions){
                if((extension1 != extension2) && (extension2.containsAll(extension1))){
                    isPreferredExtension = false;
                    break;
                }
            }
            if(isPreferredExtension){
                preferredExtensions.add(extension1);
            }
        }

        return preferredExtensions;
    }
}
