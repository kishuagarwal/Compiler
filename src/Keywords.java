/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author akash
 */
public class Keywords {
	private static Keywords mKeywords;
	private static Set<String> keywords;

	public Keywords() {
		keywords = new HashSet<String>();
		init();
	}

	public static Keywords newInstance() {
		if (mKeywords == null) {
			mKeywords = new Keywords();

		}
		return mKeywords;
	}

	private void init() {
		keywords.add(TokenType.CHAR);
		keywords.add(TokenType.ELSE);
		keywords.add(TokenType.FOR);
		keywords.add(TokenType.IF);
		keywords.add(TokenType.INT);
		keywords.add(TokenType.RETURN);
		keywords.add(TokenType.VOID);
		keywords.add(TokenType.WHILE);
	}

	public boolean isKeyword(String s) {
		return keywords.contains(s);
	}

	}