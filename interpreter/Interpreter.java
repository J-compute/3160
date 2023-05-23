package interpreter;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import interpreter.*;

public class Interpreter {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		String[] strings = getInput(sc);
		getTokens(strings);
		
	}
	
	public static String[] getInput(Scanner sc) {
		String input = sc.nextLine();
		String[] strings = input.split("\t");
		return strings;
		
	}
	
	public static String[] getTokens(String[] strings) {
		//String[] tokens;
		ArrayList<ArrayList<Token>> tokenSet = new ArrayList<ArrayList<Token>>();
		ArrayList<Token> tokens = new ArrayList<Token>();
		for (String str : strings) {
			// checking if spaces are present
			String[] substrs = str.split(" ");
			// check for assignment operator
			if (substrs.length < 2) {
				throw new RuntimeException("Error: String is too short to fit the format of \"Identifier = Expression\"");
			}
			if (substrs[1].compareTo("=") != 0) {
				throw new RuntimeException("Error: Assignment operator is missing");
			}
			tokens = Tokenizer.tokenize(str, tokens);
			tokenSet.add(tokens);
			for (Token token : tokens) {
				System.out.println(token);
			}
		}
		if (Parser.verify(tokens)) {
			System.out.println("True");
		}
		
		return strings;
	}
}

