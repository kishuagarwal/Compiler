/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Kishu Agarwal
 */
public class SpecialSymbols extends GeneralizedSymbol{
    
    public static SpecialSymbols EPSILON = new SpecialSymbols('\u03B5'+"");
    public static SpecialSymbols EOF = new SpecialSymbols("$");
    private final String symbol; 
    private SpecialSymbols(String symbol){
        this.symbol = symbol;
    }
    
    public String toString(){
    	return symbol.toString();
    			
    }
    
    public boolean equals(Object o){
    	if(!(o instanceof SpecialSymbols))
    		return false;
    	return symbol.equals(((SpecialSymbols)o).symbol);
    }
    
}
