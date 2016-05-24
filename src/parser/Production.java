/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Kishu Agarwal
 */
public class Production  implements Iterable<GrammerSymbol> {
    
    NonTerminal head;
    List<GrammerSymbol> body;
    
    public Production(NonTerminal h, List<GrammerSymbol> g){
        head = h;
        body = g;
    } 
    
    public NonTerminal getHead(){
        return head;
    }
    
    public List<GrammerSymbol> getBody(){
        return body;
    }
    
    public String toString(){
        
        return head + " -> "+ body;
        
    }

	@Override
	public Iterator<GrammerSymbol> iterator() {
		// TODO Auto-generated method stub
		return body.iterator();
	}
            
    
}
