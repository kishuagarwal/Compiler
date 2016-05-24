/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Kishu Agarwal
 */
public class Number extends Token{
    public int value;
    public Number(int value){
        super(TokenType.NUM);
        this.value = value;
    }
    
    public String toString(){
        return ""+value;
    }
    
}
