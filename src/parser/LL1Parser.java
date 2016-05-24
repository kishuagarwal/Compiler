package parser;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LL1Parser {

	
	private Map<LL1Key, Production> parsingTable;
	private final ParseTree tree;
	private final Deque<StackEntry> parsingStack = new ArrayDeque<LL1Parser.StackEntry>();
	
	public LL1Parser(Map<LL1Key,Production> table, NonTerminal start){
		parsingTable = table;
		tree = new ParseTree(start);
		parsingStack.offerFirst(new StackEntry(SpecialSymbols.EOF,null));
		
		parsingStack.offerFirst(new StackEntry(new ConcreteSymbol(start), tree));
	}
	
	public void nextTerminal(Terminal terminal) throws ParseErrorException{
		processSymbol(new ConcreteSymbol(terminal));
	}
	
	public ParseTree inputComplete() throws ParseErrorException{
		ParseTree tree = processSymbol(SpecialSymbols.EOF);
		assert tree != null;
		return tree;
	}
	
	private ParseTree processSymbol(GeneralizedSymbol symbol) throws ParseErrorException {
		// TODO Auto-generated method stub
		if(parsingStack.isEmpty()) throw new ParseErrorException("Parsing already completed");
		
		while(true){
				StackEntry top = parsingStack.pollFirst();
				if(top.symbol.equals(symbol)){
					return (symbol.equals(SpecialSymbols.EOF) ? tree: null);
				}
				
				if(!( top.symbol instanceof ConcreteSymbol && ((ConcreteSymbol)top.symbol).getSymbol() instanceof NonTerminal))
				{
					System.out.println("On top " + top.symbol);
					throw new ParseErrorException("Expected "+ top.symbol +", found " + symbol);
				}
				NonTerminal nonTerminal = (NonTerminal)(((ConcreteSymbol)top.symbol).getSymbol());
				Production p = parsingTable.get(new LL1Key(nonTerminal, symbol));
				if(p == null)
					throw new ParseErrorException("No production for "+ nonTerminal +" on seeing "+ symbol);
				List<GrammerSymbol> body = p.getBody();
				for(int i = body.size() -1; i >= 0; i--){
					GrammerSymbol s = body.get(i);
					ParseTree tr = new ParseTree(s);
					parsingStack.offerFirst(new StackEntry(new ConcreteSymbol(s), tr));
							
					
				}
				
				Iterator<StackEntry> iter = parsingStack.iterator();
				for(int i = 0; i < body.size(); i++){
					top.tree.getChildren().add(iter.next().tree);
				}
				
		}
	}



	private static final class StackEntry{
		
		public final GeneralizedSymbol symbol;
		
		public final ParseTree tree;
		
		public StackEntry(GeneralizedSymbol s, ParseTree t){
			symbol = s;
			tree = t;
		}
		
		
	}
}
