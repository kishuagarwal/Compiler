/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kishu Agarwal
 */
public class Identifier extends Token{
    public String value;
    public Identifier(String value){
        super(TokenType.IDENTIFIER);
        this.value = value;
    }
    
    public String toString(){
        return value;
    }
    
}
