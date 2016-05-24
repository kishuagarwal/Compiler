/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import parser.Grammer;
import parser.GrammerNotLL1Exception;
import parser.LL1Parser;
import parser.ParseErrorException;
import parser.ParseTree;
import parser.ParsingTable;
import parser.Terminal;

/**
 *
 * @author Kishu Agarwal
 */
public class CompilerTester {
    
    public static void main(String args[]) throws GrammerNotLL1Exception, ParseErrorException, FileNotFoundException{
        
   Scanner in = new Scanner(new File("source.txt"));
   StringBuilder source = new StringBuilder();
   while(in.hasNextLine()){
	   source.append(in.nextLine()+"\n");
   }
    LexicalAnalyser la = new LexicalAnalyser(source.toString());
    Grammer g = new Grammer();
    ParsingTable table = new ParsingTable(g); 
        //	Scanner in = new Scanner(new File("tokens"));
			LL1Parser parser = new LL1Parser(table.table, g.startSymbol);
		while(true){
			
			//String[] tokens = in.nextLine().split(" "); 
			Token t =la.nextToken();
			//System.out.println(t);
			if(t == null)
				break;
			parser.nextTerminal(new Terminal(t.terminal));
			}
			final ParseTree output = parser.inputComplete();
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                DisplayTree.createAndShowTree(output);
	            }
	        });
			in.close();
    }
}
