package interpreter;


import java.util.ArrayList;

import interpreter.Token.id;

public class Tokenizer {
	static int curIndex = 0;
	static boolean parenthesis = false;

	public static ArrayList<Token> tokenize(String str, ArrayList<Token> tokens) {
		int i = 0;
		while (i < str.length() - 1) {
			i = curIndex;
		/*	switch(str.charAt(i)) {
			case '0';
				
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
					if (Character.isDigit(ch)) {
						if (ch == '0')	{	
							if ((i <= str.length() - 2) && Character.isDigit(str.charAt(i + 1))) {
								throw new Exception("Error: Valid numbers with multiple digits cannot start with 0");
							}
						tokens.add(new Token(id.INTLITERAL, "0"));
						}
						if (ch >= '1' || ch <= '9') {
						checkForLiteral(str);
						tokens.add(new Token(id.INTLITERAL, str.substring(i, curIndex + 1)));
					
						}
					}
			case 'a'
			case 'b'
			case 'c'
			case 'd'
			case 'e'
			case 'f'
			case 'g'
			case 'h'
			case 'i'
			case 'j'
			case 'k'
			case 'l'
			case 'm'
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case '_': 
				if ((str.charAt(curIndex) >= 'a' && str.charAt(curIndex) <= 'z') ||
					(str.charAt(curIndex) >= 'A' && str.charAt(curIndex) <= 'Z') ||
				 	str.charAt(curIndex) == '_') {
					checkForIdentifier(str);
					tokens.add(new Token(id.IDENTIFIER, str.substring(i, curIndex)));
				
			}
			
			case '(':
			case ')':
			case '+':
			case '-':
			case '*':
			case '=':
			case ';':
			
			default:
			
			}*/
				
			char ch = str.charAt(i);
			if (ch == ' ') {
				nextToken();
				continue;
			}
			
			//if (!Character.isAlphabetic(str.charAt(i)) && !Character.isDigit(str.charAt(i)) && !str.charAt(i) != '_' && !str.charAt(i) != '(' && !)
			
			if (Character.isDigit(ch)) {
				if (ch == '0')	{	
					if ((i <= str.length() - 2) && Character.isDigit(str.charAt(i + 1))) {
						throw new RuntimeException("Error: Valid numbers with multiple digits cannot start with 0");
					}
					tokens.add(new Token(id.INTLITERAL, "0"));
				}
				if (ch >= '1' || ch <= '9') {
					checkForLiteral(str);
					tokens.add(new Token(id.INTLITERAL, str.substring(i, curIndex + 1)));
					
				}
			}
			if ((str.charAt(curIndex) >= 'a' && str.charAt(curIndex) <= 'z') ||
				(str.charAt(curIndex) >= 'A' && str.charAt(curIndex) <= 'Z') ||
				 str.charAt(curIndex) == '_') {
				checkForIdentifier(str);
				tokens.add(new Token(id.IDENTIFIER, str.substring(i, curIndex)));
				
			}
			if (ch == '(') {
				parenthesis = true;
				nextToken();
				tokens.add(new Token(id.OPENPARENTHESIS, "("));
				
			}
			if (ch == ')') {
				if (parenthesis == false) {
					throw new RuntimeException("Error: Closing parenthesis detected without any opening parenthesis.");
				} else
				{
					parenthesis = false;
					nextToken();
					tokens.add(new Token(id.CLOSEPARENTHESIS, ")"));
					
				}
			}
			if (ch == '+') {
				if (curIndex == str.length() - 1 || curIndex == 0) {
					opError();
				}
				nextToken();
				tokens.add(new Token(id.PLUS, "+"));
				
			}
			if (ch == '-') {
				if (curIndex == str.length() - 1) {
					opError();
				}
				nextToken();
				tokens.add(new Token(id.MINUS, "-"));
				
			}
			if (ch == '*') {
				if (curIndex == 0) {
					opError();
				}
				nextToken();
				tokens.add(new Token(id.MULTIPLICATION, "*"));
				
			}
			
			if (ch == '/') {
				if (curIndex == 0) {
					opError();
				
				}
				nextToken();
				tokens.add(new Token(id.DIVISION, "/"));
			}
			
			if (ch == '=') {
				nextToken();
				tokens.add(new Token(id.EQUALS, "="));
			}
			if (ch == ';') {
				nextToken();
				tokens.add(new Token(id.SEMICOLON, ";"));
			}
		}
		return tokens;
	}

	
	public static void checkForLiteral(String str) {
		if (Character.isDigit(str.charAt(curIndex))) {

			if ((curIndex < str.length() - 1) && Character.isDigit(str.charAt(curIndex + 1))) {
				nextToken();
				checkForLiteral(str);
			}
		}
		
	}
	
	public static void checkForIdentifier(String str) {
		if (curIndex > str.length() - 1) {
			return;
		}
		if ((curIndex == 0) || (curIndex > 0 && !Character.isAlphabetic(str.charAt(curIndex - 1)))) {
		
			if ((str.charAt(curIndex) >= 'a' && str.charAt(curIndex) <= 'z') ||
				(str.charAt(curIndex) >= 'A' && str.charAt(curIndex) <= 'Z' ||
				 (str.charAt(curIndex) == '_'))) {
				if (curIndex <= str.length() - 2) {
					nextToken();
					checkForIdentifier(str);
					
				}
			}
		} else {
			if (Character.isAlphabetic(str.charAt(curIndex)) || str.charAt(curIndex) == '_') {
				if (curIndex <= str.length() - 2) {
					nextToken();
					checkForIdentifier(str);
				}

			}
		}	
	}
	
	public static void nextToken() {
		curIndex += 1;
	}


	public static void opError() {
		throw new RuntimeException("Error: Incorrect use of operators");
	}
}