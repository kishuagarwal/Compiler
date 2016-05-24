package parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParsingTable {

	
	public Map<LL1Key , Production> table;
	Grammer g;
	public ParsingTable(Grammer g) throws GrammerNotLL1Exception{
		table = new HashMap<LL1Key, Production>();
		this.g = g;
		init();
		System.out.println("Initialized.");
	}
	
	public void init() throws GrammerNotLL1Exception{
		System.out.println("Initializing Parsing table ");
		
		for(Production p : g.productions){
			Set<GeneralizedSymbol> first = GrammerHelper.getFirstSetForSequence(p.body, g.firstSet);
			//System.out.println(p);
			for(GeneralizedSymbol s: first){
				if(s.equals(SpecialSymbols.EPSILON)) continue;
				LL1Key key = new LL1Key(p.head, s);
				Production old = table.get(key);
				if(table.put(key, p) != null){
					throw new GrammerNotLL1Exception("Conflict detected "+p.getHead()+", "+s+" for production "+ p +",already present "+old);
				}
			}
			if(first.contains(SpecialSymbols.EPSILON))
			{
				Set<GeneralizedSymbol> follow = g.followSet.get(p.head);
				for(GeneralizedSymbol s:follow){
					LL1Key key = new LL1Key(p.head, s);
					Production old = table.get(key); 
				//	System.out.println(table.get(key) +" "+ p);
				 if(table.put(key, p) != null) 
					 throw new GrammerNotLL1Exception("Conflict detected " +p.getHead()+", "+s+" for production "+ p +",already present "+ old);
				}
				}
		}
	}
}
