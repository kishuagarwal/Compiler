/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author akash
 */
class LexicalAnalyser {
	private static final String TAG = "Lexical Analyser";

	private String source;
	private Keywords keywords;
	private SymbolTable symbolTable;
	private int sourceLineNumber;
	private int scanned;

	// private ArrayList<String> recognizedTokensBuffer;
	// private int numberOfRecognizedTokens;
	public LexicalAnalyser(String source) {
		this.source = source;
		keywords = Keywords.newInstance();
		symbolTable = SymbolTable.getSymbolTable();
		sourceLineNumber = 1;
		scanned = 0;
		// recognizedTokensBuffer = new String[5];
	}

	public Token nextToken() {
		// System.out.println(scanned);
		if(scanned == source.length()-1)
			return null;
		skipWhitespace();
		if(scanned == source.length()-1)
			return null;
		skipComments();
		
		Token t = null;
		if (isDigit(source.charAt(scanned))) {
			t = nextNumber();
			if (t != null)
				return t;
		}
		if (isAlphabet(source.charAt(scanned))) {
			t = nextIdentifier();
			return t;
		}
		switch (source.charAt(scanned)) {
		case '(':
			return returnSingleCharacterToken(TokenType.LEFT_PARENTHESIS);
		case ')':
			return returnSingleCharacterToken(TokenType.RIGHT_PARENTHESIS);
		case ';':
			return returnSingleCharacterToken(TokenType.SEMICOLON);
		case ',':
			return returnSingleCharacterToken(TokenType.COMMA);
		case '{':
		    return returnSingleCharacterToken(TokenType.LEFT_CURLY_BRACKET);
		case '}':
			return returnSingleCharacterToken(TokenType.RIGHT_CURLY_BRACKET); 
		case '+':
			return returnSingleCharacterToken(TokenType.ADD);
		case '-':
			return returnSingleCharacterToken(TokenType.SUBTRACT);
		case '/':
			return returnSingleCharacterToken(TokenType.DIVIDE);
		case '*':
			return returnSingleCharacterToken(TokenType.MULTIPLY);	
		case '=':
			if (source.charAt(scanned + 1) == '=')
				return returnDoubleCharacterToken(TokenType.EQUAL_EQUAL);
			else
				return returnSingleCharacterToken(TokenType.EQUAL);
		case '<':
			if (source.charAt(scanned + 1) == '=')
				return returnDoubleCharacterToken(TokenType.LESS_EQUAL);
			else
				return returnSingleCharacterToken(TokenType.LESS);
		case '>':
			if (source.charAt(scanned + 1) == '=')
				return returnDoubleCharacterToken(TokenType.GREATER_EQUAL);
			else
				return returnSingleCharacterToken(TokenType.GREATER);
		case '&':
			if (source.charAt(scanned + 1) == '&')
				return returnDoubleCharacterToken(TokenType.AND_AND);
			else {
				System.out.println("Missing & at line number "
						+ sourceLineNumber);
				System.exit(0);
			}
		default:{
			
			System.out.println("Invalid Token.");
			System.exit(0);
		}
		}
		return t;

	}

	public Token returnSingleCharacterToken(String s) {
		scanned++;
		return new Token(s);
	}

	public Token returnDoubleCharacterToken(String s) {
		scanned += 2;
		return new Token(s);
	}

	/*
	 * public void analyse() { char c; for(int scanned = 0; scanned <
	 * source.length(); ) { c = source.charAt(scanned); if(c == ' '){ scanned++;
	 * continue; }
	 * 
	 * // Identifier beginning with alphabet or underscore
	 * 
	 * if(isAlphabet(c) || isUnderscore(c)) { nextIdentifier();
	 * 
	 * 
	 * } else
	 * 
	 * //Operator if(isOperator(c)) { nextOperator();
	 * 
	 * }
	 * 
	 * 
	 * else
	 * 
	 * //Number Constant
	 * 
	 * if(isDigit(c)) { nextConstant();
	 * 
	 * } else { if(c == '\n') sourceLineNumber++; else ErrorHandler.log(TAG,
	 * "Invalid character : Line No."+sourceLineNumber); } }
	 * symbolTable.print(); }
	 */
	private Token nextIdentifier() {
		StringBuilder s = new StringBuilder();
		char c;
		while (true) {
			c = source.charAt(scanned);
			if (isAlphabet(c) || isDigit(c) || isUnderscore(c)) {
				s.append(c);
				scanned++;
			} else {
				if (keywords.isKeyword(s.toString())) {
					return new Keyword(s.toString());
				}
				// System.out.println("Keyword " + s);
				else {
					return new Identifier(s.toString());
					// System.out.println("Identifier " + s);
					// symbolTable.add(TokenType.IDENTIFIER,s.toString(),null);
				}
				// scanned--;

			}

		}
	}

	private Token nextNumber() {
		int value = 0;
		char c;
		while (true) {
			c = source.charAt(scanned);
			if (isDigit(c)) {
				value = value * 10 + c - '0';
				scanned++;
			}

			else {
				if (!isOperator(c) && c != ' ') {
					System.out.println("Invalid Number");
					return null;
				} else {
					// System.out.println("Constant "+constant);

					return new Number(value);
				}
			}

		}
	}

	private void nextOperator() {
		String operator = "" + source.charAt(scanned++);
		char c;
		while (true) {
			c = source.charAt(scanned++);
			if (isOperator(c))
				operator += c;
			else {
				System.out.println("Operator " + operator);
				scanned--;
				break;
			}

		}
	}

	private boolean isOperator(char symbol) {
		boolean result = false;
		switch (symbol) {
		case '+':
		case '-':
		case '*':
		case '%':
		case '/':
		case '^':
		case '&':
		case '=':
		case ';':
		case '{':
		case '}':
		case '(':
		case ')':
			result = true;
			break;
		}
		return result;
	}

	private boolean isUnderscore(char symbol) {
		return (symbol == '_');
	}

	private boolean isDigit(char symbol) {
		return (symbol >= '0' && symbol <= '9');
	}

	public boolean isAlphabet(char symbol) {
		if ((symbol >= 'a' && symbol <= 'z')
				|| (symbol >= 'A' && symbol <= 'Z'))
			return true;
		else
			return false;
	}

	private void skipWhitespace() {
		char c;

		while (true) {

			c = source.charAt(scanned);
			scanned++;
			if (c == ' ')
				continue;
			else if (c == '\t')
				continue;
			else if (c == '\n') {
				sourceLineNumber++;
				continue;
			} else
				break;

		}
		scanned--;

	}

	private void skipComments() {
		if ((source.charAt(scanned) == '/')
				&& (source.charAt(scanned + 1) == '/')) // single line comment
		{
			scanned = scanned + 2;
			while (source.charAt(scanned) != '\n')
				scanned++;
			scanned++;
		}
		if (source.charAt(scanned) == '/' && source.charAt(scanned + 1) == '*') { // multi
																					// line
																					// comment
			scanned = scanned + 2;
			while (source.charAt(scanned) != '*'
					&& source.charAt(scanned + 1) != '/')
				scanned++;
			scanned++;
		}

	}

}
