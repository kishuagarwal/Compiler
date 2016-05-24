package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrammerHelper {

	public static List<GrammerSymbol> common;
	
	
	public static Map<NonTerminal, Set<GeneralizedSymbol>> computeFirstSets(Grammer g ){
		
		Map<NonTerminal, Set<GeneralizedSymbol>> first = initializeNonterminalMapping(g);
		
		boolean shouldContinue = true;
		
		while(shouldContinue){
	//		System.out.println(first);	
			shouldContinue = false;
			for(Production p: g){
				
				Set<GeneralizedSymbol> computedFirst = getFirstSetForSequence(p.getBody(), first);
				
				if(first.get(p.head).addAll(computedFirst))
					shouldContinue = true;
			}
			
		}
		
		return first;
		
		
	}
	
	public static  Map<NonTerminal,Set<GeneralizedSymbol>> computeFollowSets(Grammer g , Map<NonTerminal,Set<GeneralizedSymbol>> first){
		
		Map<NonTerminal, Set<GeneralizedSymbol>> follow = initializeNonterminalMapping(g);
		boolean shouldContinue = true;
		
		follow.get(g.startSymbol).add(SpecialSymbols.EOF);
		
		while(shouldContinue){
		 
		 shouldContinue = false;
		     for(Production p : g)
		     {
		    	 List<GrammerSymbol> sequence = p.getBody();
		    	 for(int i = 0; i < sequence.size(); i++){
		    		 
		    		 if(sequence.get(i) instanceof Terminal)
		    			 continue;
		    		 
		    		 NonTerminal nt = (NonTerminal) sequence.get(i);
		    		 Set<GeneralizedSymbol> computedFirst = getFirstSetForSequence(sequence.subList(i+1, sequence.size() ), first);
		    		 
		    		 for(GeneralizedSymbol s: computedFirst){
		    			 if(s.equals(SpecialSymbols.EPSILON))
		    				 continue;
		    			 if(follow.get(nt).add(s))
		    				 shouldContinue = true;
		    		 }
		    		 
		    		 if(computedFirst.contains(SpecialSymbols.EPSILON))
		    		 {
		    			 if(follow.get(nt).addAll(follow.get(p.getHead())))
		    				 shouldContinue = true;
		    		 }
		    		 
		    	 }
		     }
		
		}
		return follow;
		
	}
	
	
	
	public static  Map<NonTerminal,Set<GeneralizedSymbol>> initializeNonterminalMapping(Grammer g){
		
		Map<NonTerminal, Set<GeneralizedSymbol>> result = new HashMap<NonTerminal,Set<GeneralizedSymbol>>();
		
		for(NonTerminal nt: g.nonTerminals){
			result.put(nt, new HashSet<GeneralizedSymbol>()); 
		}
		
		return result;
	}
 	
	
	public static Set<GeneralizedSymbol> getFirstSetForSequence(List<GrammerSymbol> sequence,Map<NonTerminal,Set<GeneralizedSymbol>> first){
		
		Set<GeneralizedSymbol> result = new HashSet<GeneralizedSymbol>(); 
		
		for(GrammerSymbol g : sequence){
			if(g.isTerminal()){
				result.add(new ConcreteSymbol(g));
				return result;
				
			}
			NonTerminal nt = (NonTerminal) g;
		    if(!(first.get(nt).contains(SpecialSymbols.EPSILON))){
		    	result.addAll(first.get(nt));
		    	return result; 
		    }
		    
		    result.addAll(first.get(nt));
		    result.remove(SpecialSymbols.EPSILON);
		}
		
		result.add(SpecialSymbols.EPSILON);
		return result;
	}
	
	public static void removeLeftRecursion(Grammer g){
		
		List<NonTerminal> nt = g.nonTerminals;
		
		for(int i = 0; i < nt.size(); i++){
			for(int j = 0; j < i;j++ ){
				for(int k = 0; k < g.productions.size(); k++){
					Production p = g.productions.get(k);
					if(p.getBody().size() == 0)
						continue;
					if(p.getHead().equals(nt.get(i)) && p.getBody().get(0).equals(nt.get(j))){
						List<Production> list = getProductionsWithNonterminal(nt.get(j), g);
						for(Production pr : list){
							List<GrammerSymbol> l = new ArrayList<GrammerSymbol>();
							l.addAll(pr.getBody());
							l.addAll(p.getBody().subList(1, p.getBody().size()));
							g.productions.add(new Production(nt.get(i), l));
						}
						g.productions.remove(p);
						k--;
					}
				}
			}
			
			List<Production> iPro = getProductionsWithNonterminal(nt.get(i), g);
			List<Production> alpha = new ArrayList<Production>();
			List<Production> beta = new ArrayList<Production>();
			for(Production p: iPro  ){
				if(p.getBody().size() == 0)
					continue;
				if(p.getBody().get(0).equals(p.getHead())){
					alpha.add(p);
				}
				else
					beta.add(p);
			}
			
			if(alpha.size() != 0){
				NonTerminal newHead = new NonTerminal(nt.get(i).symbol+"'");
				g.nonTerminals.add(newHead );
				for(Production x: beta){
					x.getBody().add(newHead);
				}
				for(Production x: alpha ){
					List<GrammerSymbol> newBody = new ArrayList<GrammerSymbol>();
					newBody.addAll(x.getBody());
					newBody.remove(0);
					newBody.add(newHead);
					g.productions.add(new Production(newHead, newBody));
					g.productions.remove(x);
				}
				g.productions.add(new Production(newHead, new ArrayList<GrammerSymbol>()));
				
			}
			
		}
	}
	
	public static void leftFactor(Grammer g){
		
		List<Production> productionWithCommonFactors = new ArrayList<Production>(); 
		boolean shouldContinue;
		NonTerminal newHead = null;
		//int nonTerminal = 1; 
		for(int i = 0; i < g.nonTerminals.size(); i++){
			NonTerminal nt = g.nonTerminals.get(i);
			
			shouldContinue = true;
			while(shouldContinue){
				shouldContinue = false;
				List<Production> list = getProductionsWithNonterminal(nt, g); 
				productionWithCommonFactors = greatestCommonPart(list);
				if(productionWithCommonFactors.size() == 0)
					shouldContinue = false;
				else{
					shouldContinue = true;
					newHead = new NonTerminal(nt.symbol+"'");
					g.nonTerminals.add(newHead);
					List<GrammerSymbol> c = new ArrayList<>(common);
					c.add(newHead);		
					g.productions.add(new Production(nt, c));
				//	nonTerminal++;
				}
				for(Production p: productionWithCommonFactors){
					List<GrammerSymbol> sublist = p.getBody().subList(common.size(), p.getBody().size());
					
					g.productions.add(new Production(newHead, sublist));
					g.productions.remove(p);
				
				}
				common.clear();
			}
			
		}
		
	}
	
	public static List<GrammerSymbol> commonPart(List<GrammerSymbol> list1, List<GrammerSymbol> list2){
		List<GrammerSymbol> commonList  = new ArrayList<GrammerSymbol>();
		
		int min = Math.min(list1.size(), list2.size());
		for(int i = 0; i < min; i++){
			if(list1.get(i).equals(list2.get(i)))
				commonList.add(list1.get(i));
			else
				break;
		}
		
		return commonList;
	}
	
	public static List<Production> greatestCommonPart(List<Production> list){
		GrammerSymbol gs; 
		List<GrammerSymbol> max= new ArrayList<GrammerSymbol>();
	    for(int i = 0; i< list.size(); i++)
	    {
	    	for(int j = i+1; j < list.size(); j++){
	    		List<GrammerSymbol> result= commonPart(list.get(i).getBody(), list.get(j).getBody());
	    		if(result.size() > max.size())
	    			max = result;
	    	}
		}
	    
	    common = max;
	    
	    List<Production> commonP = new ArrayList<Production>();
	    if(max.size() == 0)
	    	return commonP;
	    for(Production p : list){
	    	if(commonPart(p.getBody(),max).size() == max.size())
	    		commonP.add(p);
	    }
		
	    return commonP;
	}
	
	public static List<Production> getProductionsWithNonterminal(NonTerminal nt,Grammer g){
		List<Production> result = new ArrayList<Production>();
	    for(Production p : g)
	    {
	    	if(p.getHead().equals(nt))
	    		result.add(p);
	    }
		return result;
	}
	
}
