package parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParseTree implements Iterable<ParseTree> {

	
	private final GrammerSymbol symbol;
	
	private List<ParseTree> children;
	
	public ParseTree(GrammerSymbol sym, List<ParseTree> c  ){
		symbol = sym;
		children = c;
	}

	@Override
	public Iterator<ParseTree> iterator() {
		// TODO Auto-generated method stub
		return children.iterator();
	}
	
	public ParseTree(GrammerSymbol sym){
		this(sym, new ArrayList<ParseTree>());
	}
	
	public GrammerSymbol getSymbol(){
		return symbol;
	}
	
	public List<ParseTree> getChildren(){
		return children;
	}
	
	public String toString(){
//		StringBuilder builder = new  StringBuilder();
//		builder.append(symbol);
//		if(symbol instanceof NonTerminal){
//			builder.append("->");
//			builder.append(children);
//		}
//		return builder.toString();
		return symbol.toString();
	}
	
	
}
