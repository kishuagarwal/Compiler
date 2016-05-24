/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Kishu Agarwal
 */
public class Grammer implements Iterable<Production> {
    
    List<Production> productions;
    List<NonTerminal> nonTerminals;
    List<Terminal> terminals;
    Map<NonTerminal,Set<GeneralizedSymbol>> firstSet;
    Map<NonTerminal,Set<GeneralizedSymbol>> followSet;
    public NonTerminal startSymbol;
    public Grammer(){
        productions = new ArrayList<Production>();
        nonTerminals = new ArrayList<NonTerminal>();
        terminals = new ArrayList<Terminal>();
        init();
       // System.out.println(terminals);
       // System.out.println(nonTerminals);
       
      //    printGrammer();
     //   printSet(firstSet);
      //  GrammerHelper.leftFactor(this);
        System.out.println("Removing Left Recursion.");
        GrammerHelper.removeLeftRecursion(this);
        System.out.println("Done");
        System.out.println("Left Factoring the Grammer.");
        GrammerHelper.leftFactor(this);
        System.out.println("Done");
      //  System.out.println(terminals);
        
        firstSet = GrammerHelper.computeFirstSets(this);
        followSet = GrammerHelper.computeFollowSets(this, firstSet);
       // System.out.println(firstSet);
       // printGrammer();
       }
    
    public void init(){
        Scanner in = null;
        try {
            in = new Scanner(new File("grammer.txt"));
          //  in = new Scanner(new File("tester.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Grammer.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String> rules = new ArrayList<String>(); 
        while(in.hasNextLine()){
            rules.add(in.nextLine());
       }
        in.close();
        for(String s:rules){
            String pro[] = s.split("->");
            nonTerminals.add(new NonTerminal(pro[0].trim()));
        }
        startSymbol = nonTerminals.get(0);
        String p;
        String pro[];
        StringTokenizer tokens;
        for(String s:rules){
            pro = s.split("->");
            tokens = new StringTokenizer(pro[1],"|");
            while(tokens.hasMoreTokens()){
                p = tokens.nextToken(" ").trim();
               // if(p.equals("e")){
               //     getNonTerminal(pro[0].trim()).setNullable();
               //     continue;
               // }
                if(getNonTerminal(p) == null && getTerminal(p) == null)
                    terminals.add(new Terminal(p));
                
            }
            
        }
        NonTerminal head;
        List<GrammerSymbol> body;
        StringTokenizer bodyTokens;
        for(String s:rules){
            pro = s.split("->");
            head = getNonTerminal(pro[0].trim());
            
            tokens = new StringTokenizer(pro[1],"|");
            
            while(tokens.hasMoreTokens()){
            bodyTokens = new StringTokenizer(tokens.nextToken()," ");
                body = new ArrayList<GrammerSymbol>();
                while(bodyTokens.hasMoreTokens()){
                p = bodyTokens.nextToken(" ").trim();
               // if(p.equals("e"))
               //     continue;
                if(getNonTerminal(p) != null){
                    body.add(getNonTerminal(p));
                }
                else
                    body.add(getTerminal(p));
                }
               // if(body.size() != 0 )
            productions.add(new Production(head, body));    
            }
            
        }
        
    }
    
    public NonTerminal getNonTerminal(String p){
    	
    	for(NonTerminal nt : nonTerminals){
    		
    		if(nt.symbol.equals(p))
    			return nt;
    	}
    	return null;
    }
    
    
    public Terminal getTerminal(String p){
    	
    	for(Terminal  t : terminals){
    		
    		if(t.symbol.equals(p))
    			return t;
    	}
    	return null;
    }
    
    
    
    public void printGrammer(){
        for(Production p : productions)
        System.out.println(p);
        
    }
    
    public void printSet(Map<NonTerminal,Set<GeneralizedSymbol>> s){

    	for(NonTerminal nt: nonTerminals)
    	System.out.println(nt +" -> "+s.get(nt));
    	
    }
    
    
    
	@Override
	public Iterator<Production> iterator() {
		// TODO Auto-generated method stub
		return productions.iterator();
	}
    
}
