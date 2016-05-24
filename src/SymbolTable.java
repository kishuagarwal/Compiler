/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;

/**
 *
 * @author akash
 */
public class SymbolTable {
    
    private static  ArrayList<Token> table;
    private static SymbolTable symbolTable;
    private SymbolTable()
    {
        table = new ArrayList<Token>();
    }
    
    public static SymbolTable getSymbolTable()
    {
        if(symbolTable  == null)
        {
            symbolTable = new SymbolTable();
        }
        return symbolTable;
    }
    
    public void add(TokenType tt, String name,String value)        
    {
        Token a = new Token(tt,name,value);
        for(Token e:table)
        {
            if(e.name.equals(name))
                return;
        }
        table.add(a);
    }
    
    public void print()
    {
        for (Token e:table)
        {
            System.out.println("Token Type:"+e.tt +",Name:"+e.name);
        }
    }
    
    private class Token
    {
       TokenType tt;
       String name;
       String value;
       public Token(TokenType tt,String name,String value)
       {
           this.tt = tt;
           this.name = name;
           this.value = value;
       }
    }
    
}
