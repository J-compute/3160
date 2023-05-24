package interpreter;

import interpreter.Token;
import interpreter.Token.id;

import java.util.ArrayList;
import java.util.*;

public class Parser {
	//static ArrayList<Token> tokenList = new ArrayList<Token>();
	static Map<Token, Integer> varMap = new HashMap<Token, Integer>();
	static String str;
	static int curIndex = 0;
	static Token temp;
	
	static boolean verify(ArrayList<Token> tokens) {
		
		Token tkn = identifier(tokens);
		assignment(tokens);
		int res = exp(tokens);
		System.out.println(res);
		varMap.put(tkn, res);
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
    
    static int exp(ArrayList<Token> tokens){
        int res = term(tokens);
        return res + expPrime(tokens);
    }
    
    static int expPrime(ArrayList<Token> tokens){
    	int res = term(tokens);
        Token curToken = currentToken(tokens);
        if (curIndex < tokens.size() - 1) {
        	/*Token temp = lookAhead(tokens);
        	if (temp.getType().toString().compareTo("IDENTIFIER") != 0 || temp.getType().toString().compareTo("INTLITERAL") != 0 || temp.getType().toString().compareTo("OPENPARENTHESIS") != 0) {
    			error();
    		} */
        }
    	switch (curToken.getType().toString()){
        	
        case "PLUS":
        	readNextToken();
        	//term(tokens);
            return res + expPrime(tokens);
        case "MINUS":
        	readNextToken();      	
            //term(tokens);
            return -1 * expPrime(tokens);
        }
    	return res;
    }
    
    static int term(ArrayList<Token> tokens){
        int res = factor(tokens);

        return res * termPrime(tokens);
    }
    
    static int termPrime(ArrayList<Token> tokens){
    	int res = 1;
        Token curToken = currentToken(tokens);
        if (curIndex < tokens.size() - 1) {
        	temp = lookAhead(tokens);
        	if (temp != null) {
        		curToken = temp;
        	}
        }
    	/*if (temp.getType().toString().compareTo("IDENTIFIER") != 0 || temp.getType().toString().compareTo("INTLITERAL") != 0 
    			|| temp.getType().toString().compareTo("OPENPARENTHESIS") != 0 || temp.getType().toString().compareTo("CLOSEPARENTHESIS") != 0 ||
    			temp.getType().toString().compareTo("MINUS") != 0) {
    		error();
    	}
        }*/ 
        switch (curToken.getType().toString()){

        case "MULTIPLICATION":

            readNextToken();
           return factor(tokens); //* term(tokens) ;
           // termPrime(tokens);
           // return evaluate('*', factor(tokens), termPrime(tokens));
            
        }
		return res;
    }
    
    static int factor(ArrayList<Token> tokens){
    	Token curToken = currentToken(tokens);
        if (curIndex < tokens.size() - 1) {
        	temp = lookAhead(tokens);
        	//if (temp != null) {
        		//curToken = temp;
        	}
        
        int tempNum = 0;
        if (curToken.getType().toString() == "INTLITERAL") {
        	tempNum = Integer.parseInt(curToken.lexeme);
        	readNextToken();
        	return tempNum;
        }
        switch (curToken.getType().toString()){
        case "OPENPARENTHESIS":
            readNextToken();
            tempNum = exp(tokens);
            if (currentToken(tokens).getType().toString() == "CLOSEPARENTHESIS") {
                readNextToken();
                return tempNum;
            } else {
                error();
            }
            break;
            
        case "PLUS":
        	//if (curToken.getType().toString() == "INTLITERAL" || curToken.getType().toString() == "IDENTIFIER")
        	
        	readNextToken();
        	
        	return factor(tokens);
        	
         	//return evaluate('-', tempNum, factor(tokens));
       // 	return tokens.get(curIndex - 1) + factor(tokens);
        case "MINUS":
        	//if (curToken.getType().toString() == "INTLITERAL" || curToken.getType().toString() == "IDENTIFIER") {
        	readNextToken();
        	return -1 * factor(tokens);
        	//}
        	// convert into evaluation function that takes lhs, op, rhs
        	//return evaluate('-', tempNum, factor(tokens));
        case "IDENTIFIER":
        	if (varMap.containsKey(curToken)) {
        		readNextToken();
        		return varMap.get(curToken);
        	}
        	
        default:
            if (isNumber(curToken.lexeme)){
                readNextToken();
            } else {
                error();
            }
        }
		//return 0;
		return tempNum;
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