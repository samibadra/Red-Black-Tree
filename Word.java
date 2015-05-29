/*
 Sami Badra
 
 CS 310, Spring 2014
 Assignment #3, red-black BST
 FILE: Word.java
 
 Copyright (c) 2015 Sami Badra. All rights reserved.
 */

public class Word implements Comparable<Word> {
	private String s;
	
	/**
	 * constructor for creating the word
	 * @param s sets this word to the string passed in
	 */
	public Word(String s){
		this.s = s;
	}
	
	/**
	 * gets the string of this word
	 * @return the string of this word
	 */
	public String getS() {
		return s;
	}
	
	/**
	 * sets the string of this word
	 * @param s the string that we are setting this word to
	 */
	public void setS(String s) {
		this.s = s;
	}
	
	/**
	 * compareTo method compares two words to each other
	 */
	@Override
	public int compareTo(Word w) {
		String s1, s2;
		s1 = this.s.replaceAll("\\W","");
		s2 = w.getS().replaceAll("\\W","");
		return s1.compareToIgnoreCase(s2);
	}
	
	/**
	 * toString method used when printing out the word object
	 */
	public String toString() {
		return s;
	}
}
