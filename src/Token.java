/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kishu Agarwal
 */
public class Token {
    public String terminal;
    public Token(String terminal){
        this.terminal = terminal; 
    }
    @Override
    public String toString(){
        return terminal;
    }
    
}
