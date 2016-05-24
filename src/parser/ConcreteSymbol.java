package parser;

public class ConcreteSymbol extends GeneralizedSymbol{

	private final GrammerSymbol symbol;
	
	public ConcreteSymbol(GrammerSymbol symbol){
		this.symbol = symbol;
	}
	
	public GrammerSymbol getSymbol(){
		return symbol;
	}
	
	public int hashCode()
	{
		return symbol.hashCode();
	}
	
	public boolean equals(Object o){
		if(!( o instanceof ConcreteSymbol))
			return false;
		ConcreteSymbol s = (ConcreteSymbol)o;
		return symbol.equals(s.symbol);
	}
	
	public String toString(){
		return symbol.toString();
	}
	
	
}
