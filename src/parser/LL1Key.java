package parser;

public class LL1Key {

	NonTerminal nonTerminal;
	GeneralizedSymbol symbol;
	
	
	public LL1Key(NonTerminal nt, GeneralizedSymbol sym){
		nonTerminal = nt;
		symbol = sym;;
	}
	
	
	public int hashCode(){
		return 31*nonTerminal.hashCode()+symbol.hashCode();
	}
	
	public boolean equals(Object o)
	{
		if(!(o instanceof LL1Key))
			return false;
		else
		{
			LL1Key key = (LL1Key)o;
			return nonTerminal.equals(key.nonTerminal) && symbol.equals(key.symbol);
		}
	}
	
	public String toString(){
		return nonTerminal +" " + symbol;
	}
}
