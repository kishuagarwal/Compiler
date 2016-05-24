/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Kishu Agarwal
 */
public class NonTerminal extends GrammerSymbol{
    
    boolean isNullabale;
    public NonTerminal(String symbol){
        super(symbol,false);
        isNullabale = false;
    }
    
    public boolean isNullable(){
        return isNullabale;
    }
    
    public void setNullable(){
        isNullabale = true;
    }
}
