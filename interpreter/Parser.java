package interpreter;

import interpreter.Token;
import interpreter.Token.id;

import java.util.ArrayList;

public class Parser {
	//static ArrayList<Token> tokenList = new ArrayList<Token>();
	static String str;
	static int curIndex = 0;
	Token temp;
	
	static boolean verify(ArrayList<Token> tokens) {
		
		identifier(tokens);
		assignment(tokens);
		exp(tokens);
	
		return true;
	}
	
	static int evaluate(char operator, int lhs, int rhs) {
		int num = 0;
		
		switch (operator) {
			case '+':
				return lhs + rhs;
				
			case '-':
				return lhs - rhs;
				
			case '*':
				return lhs * rhs;
		}
				
		return num;
	}

	static Token currentToken(ArrayList<Token> tokens){
        if (curIndex >= tokens.size() -1){
            return new Token(id.SEMICOLON, ";");
        }
        return tokens.get(curIndex);
    }
    
    static void readNextToken(){
        curIndex += 1;
    }
    
    static void exp(ArrayList<Token> tokens){
        term(tokens);
        expPrime(tokens);
    }
    
    static void expPrime(ArrayList<Token> tokens){
    	int val = 0;
        Token curToken = currentToken(tokens);
        if (curIndex < tokens.size() - 1) {
        	Token temp = lookAhead(tokens);
        	if (temp.getType().toString().compareTo("IDENTIFIER") != 0 || temp.getType().toString().compareTo("INTLITERAL") != 0 || temp.getType().toString().compareTo("OPENPARENTHESIS") != 0) {
    			error();
    		}
        }
    	switch (curToken.getType().toString()){
        	
        case "PLUS":
        	readNextToken();
        	term(tokens);
            expPrime(tokens);
        case "MINUS":
        	readNextToken();      	
            term(tokens);
            expPrime(tokens);
        }
    	//return val;
    }
    
    static void term(ArrayList<Token> tokens){
        factor(tokens);
        termPrime(tokens);
    }
    
    static int termPrime(ArrayList<Token> tokens){
    	int val = 1;
        Token curToken = currentToken(tokens);
        if (curIndex < tokens.size() - 1) {
    	Token temp = lookAhead(tokens);
    	if (temp.getType().toString().compareTo("IDENTIFIER") != 0 || temp.getType().toString().compareTo("INTLITERAL") != 0 
    			|| temp.getType().toString().compareTo("OPENPARENTHESIS") != 0 || temp.getType().toString().compareTo("CLOSEPARENTHESIS") != 0 ||
    			temp.getType().toString().compareTo("MINUS") != 0) {
    		error();
    	}
        }
        switch (curToken.getType().toString()){

        case "MULTIPLICATION":

            readNextToken();
           // factor(tokens);
           // termPrime(tokens);
            return evaluate('*', factor(tokens), termPrime(tokens));
            
        }
		return val;
    }
    
    static int factor(ArrayList<Token> tokens){
    	Token curToken = currentToken(tokens);
        Token temp = tokens.get(curIndex - 1);
        int tempNum = 0;
        if (temp.getType().toString() == "INTLITERAL") {
        	tempNum = Integer.parseInt(temp.lexeme);
        }
        switch (curToken.getType().toString()){
        case "OPENPARENTHESIS":
            readNextToken();
            exp(tokens);
            if (curToken.getType().toString() == "CLOSEPARENTHESIS") {
                readNextToken();
            } else {
                error();
            }
            break;
            
        case "PLUS":
        	if (curToken.getType().toString() == "INTLITERAL" || curToken.getType().toString() == "IDENTIFIER")
        	readNextToken();
        	
        	
         	return evaluate('-', tempNum, factor(tokens));
       // 	return tokens.get(curIndex - 1) + factor(tokens);
        case "MINUS":
        	if (curToken.getType().toString() == "INTLITERAL" || curToken.getType().toString() == "IDENTIFIER")
        	readNextToken();
        	
        	// convert into evaluation function that takes lhs, op, rhs
        	return evaluate('-', tempNum, factor(tokens));
            
        default:
            if (isNumber(curToken.lexeme)){
                readNextToken();
            } else {
                error();
            }
        }
		return 0;
    }
    
	static void assignment(ArrayList<Token> tokens) {
		Token curToken = currentToken(tokens);
		if (curToken.type.toString() == "EQUALS") {
			
			readNextToken();
			return;
		}
		else {
			error();
		}
	}

	static Token identifier(ArrayList<Token> tokens) {
		Token curToken = currentToken(tokens);
		if (curToken.type.toString() == "IDENTIFIER") {
			readNextToken();
			return curToken;
		}
		else {
			error();
		}
		return null;
	}

            
    static void error(){
        throw new RuntimeException("Error: Incorrect syntax detected.");
    }
    
    static Token lookAhead(ArrayList<Token> tokens) {
    	if (curIndex < tokens.size() - 1) {
    		return tokens.get(curIndex + 1);
    	}
   	return null;
    } 
   
    static boolean isNumber(String str) {
    	if (str == null) {
    		return false;
    	}
    	for (int i = 0; i < str.length() - 1; i++) {
    		if (!Character.isDigit(str.charAt(i))) {
    			return false;
    		}
    	}
    	return true;
    }
}
