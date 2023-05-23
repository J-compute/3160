package interpreter;

public class Token {
	enum id {
		IDENTIFIER,
		INTLITERAL,
		PLUS,
		MINUS,
		MULTIPLICATION,
		DIVISION,
		EQUALS,
		SEMICOLON,
		OPENPARENTHESIS,
		CLOSEPARENTHESIS;
	}
	
	public id type;
	public String lexeme;
	
	public Token(id t, String str) {
		 type = t;
		 lexeme = str;
		
	}
	
	public id getType() {
		return type;
	}
	
	public String toString(){
		return type + lexeme;
	}
}