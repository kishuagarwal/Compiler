/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Kishu Agarwal
 */
public class GrammerSymbol {
    String symbol;
    boolean isTerminal;
    
    
    public GrammerSymbol(String s,boolean isTer)
    {
        isTerminal = isTer;
        symbol = s;
    }
    
    public boolean isTerminal(){
        return isTerminal;
    }
    
    public String toString(){
        if(isTerminal)
    	  	return symbol;
        else
        	return "<" + symbol + ">";
    
    }
    
    public int hashCode(){
    	return 31* symbol.hashCode() + (isTerminal? 1:0);
    }
    
    @Override
    public boolean equals(Object gs){
       if(!(gs instanceof GrammerSymbol)) 
    	   return false;
       GrammerSymbol g = (GrammerSymbol)gs;
       return g.isTerminal == isTerminal && symbol.equals(g.symbol);
    }
    
}
