/*
* Distributed under the terms of the MIT License.
* Copyright (c) 2009, Marcelo Criscuolo.
*/

package commondb.mock;

import java.util.LinkedList;
import java.util.List;

public class CSVLineSplitter {
	private List<String> tokens;
	private int currentChar;
	private int startChar;
	
	public String[] split(String line) {

		reset();
		
		if (line.length() > 0) {
			State state = new Start();
			
			while (state != null) {
				state = state.next(line);
			}
		}
		
		return tokens.toArray(new String[tokens.size()]);
	}

	private void reset() {
		tokens = new LinkedList<String>();
		currentChar = 0;
	}
	
	
	abstract class State {
		State next(String line) {
			if (isLineEnd(line)) {
				String token = extractToken(line);
				tokens.add(token);
				
				return null;
			}
			
			return process(line);
		}

		protected String extractToken(String line) {
			return line.substring(startChar, currentChar);
		}
		
		abstract State process(String line);
		
		private boolean isLineEnd(String line) {
			if (currentChar >= line.length()) {
				return true;
			}
			
			char c = line.charAt(currentChar);
			return (c == '\r') || (c == '\n');
		}
	}
	
	class Start extends State {

		@Override
		State process(String line) {
			State state = null;
			char c = line.charAt(currentChar);
			startChar = currentChar;
			currentChar++;
			

			if (c == '"') {
				state = new QuotedToken();
			} else if (c == ',') {
				state = new EmptyField();
			} else {
				state = new NonQuotedToken();
			}
			

			return state;
		}
	}
	
	class QuotedToken extends State {
		private int quotes = 1;
		
		@Override
		State process(String line) {
			State state = this;
			
			char c = line.charAt(currentChar);
			currentChar++;
			
			if (c == '"') {
				quotes++;
				
			} else if ( isTokenEnd(c)) {
				String token = extractToken(line);
				token = token.substring(0, token.length() - 1);
				tokens.add(token);
				state = new Start();
			}
			
			return state;
		}

		@Override
		protected String extractToken(String line) {
			return line.substring(startChar + 1, currentChar - 1);
		}
		
		private boolean isTokenEnd(char c) {
			return ((c == ',') && (quotes % 2) == 0);
		}
	}
	
	class NonQuotedToken extends State {

		@Override
		State process(String line) {
			State state = this;
			
			char c = line.charAt(currentChar);
			currentChar++;

			if (c == ',') {
				String token = extractToken(line);
				tokens.add(token);
				state = new Start();
			}
			
			return state;
		}

		@Override
		protected String extractToken(String line) {
			return line.substring(startChar, currentChar - 1);
		}
	}
	
	class EmptyField extends State {

		@Override
		State process(String line) {
			tokens.add(extractToken(line));
			
			return new Start();
		}
		
		@Override
		protected String extractToken(String line) {
			return "";
		}
		
	}
	
	static void test() {
		CSVLineSplitter sp = new CSVLineSplitter();

		for (String t : sp.split("33,,\"N\"")) {
			System.out.println(t);
		}

	}

	public static void main(String[] args) {
		test();
	}
}
