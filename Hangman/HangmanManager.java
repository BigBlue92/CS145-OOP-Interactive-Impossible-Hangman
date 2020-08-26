
//CS 145 Section B
//Ryan Mackenzie, Rayne Smith, and Kyle Fuglestad
//Lab 4: Hangman
//HangmanManager v1.0
//This program is an object class that's designed to work with the "HangmanMain" client class. 
//The programs work together to play a hangman game with the user. 

import java.util.*;

public class HangmanManager {

	private SortedSet<Character> guesses = new TreeSet<Character>();
	private int currentGuesses;
	private Map<String, SortedSet<String>> patterns = new TreeMap<String, SortedSet<String>>();
	private SortedSet<String> words = new TreeSet<String>();
	private String currentPattern = "";

	
	//Constructor method
	public HangmanManager(List<String> dictionary, int length, int max) {
		if (length < 1 || max < 0) {
			throw new IllegalArgumentException("Length is less than 1 or max is a negative number.");
		}
		currentGuesses = max;
		for (String str : dictionary) {
			if (str.length() == length) {
				words.add(str);
			}
		}
		for (int i = 0; i < length; i++) {
			currentPattern += "- ";
		}
		currentPattern = currentPattern.trim();
	}

	
	
	public Set<String> words() {
		return words;
	}

	
	
	public int guessesLeft() {
		return currentGuesses;
	}

	
	
	public SortedSet<Character> guesses() {
		return guesses;
	}

	
	
	public String pattern() {
		return currentPattern;
	}

	
	//Pre: Takes a char from the user to compare to the current list of words.
	//Post: Builds a map of patterns and the words that go with them. Picks the pattern with the most words, 
	//and subtracts a guess if it doesn't have to unveil a letter. Returns how many of the guessed letter was unveiled.
	public int record(char guess) {
		if (currentGuesses < 1 || words.isEmpty()) {
			throw new IllegalStateException ("You're out of guesses or the word list is empty, game over man.");
		}
		
		guesses.add(guess);
		int bestPattern = 0;
		String winningPattern = "";

		patterns = mapBuilder(guess);// Calls a private method below to save space, fills a map with key strings and string set values.

		Set<String> keys = patterns.keySet();

		for (String str : keys) {
			int count = patterns.get(str).size();
			if (count > bestPattern) {
				bestPattern = count;
				winningPattern = str;
			}
		}

		words = patterns.get(winningPattern);
		int correctLetters = 0;
		for (int i = 0; i < winningPattern.length(); i++) {
			if (winningPattern.charAt(i) == guess) {
				currentPattern = currentPattern.substring(0, i) + guess + currentPattern.substring(i + 1, currentPattern.length());
				correctLetters++;
			}
		}
		if (correctLetters == 0) {
			currentGuesses--;
		}
		return correctLetters;
	}

	
	//Pre:Takes the users guess char.
	//Post: Builds the map containing the words and patterns, and returns that map.
	private Map<String, SortedSet<String>> mapBuilder(char guess) {
		Map<String, SortedSet<String>> patterns = new TreeMap<String, SortedSet<String>>();
		for (String str : words) {
			String pattern = "";
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == guess) {
					pattern += guess + " ";
				} else
					pattern += "- ";
			}
			pattern = pattern.trim();
			if (patterns.containsKey(pattern)) {
				patterns.get(pattern).add(str);
			} else {
				SortedSet<String> wordsInPattern = new TreeSet<String>();
				wordsInPattern.add(str);
				patterns.put(pattern, wordsInPattern);
			}
		}
		return patterns;
	}

}// End Class
