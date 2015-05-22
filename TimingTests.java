/*
 Sami Badra
 
 CS 310, Spring 2014
 Assignment #3, red-black BST
 FILE: TimingTests.java
 
 Copyright (c) 2015 Sami Badra. All rights reserved.
 
 DISCLAIMER: Any unauthorized use, including but not limited to, copying or
 redistributing any chunk of the source code (or an entire file) will result in
 punishment by law. I, Sami Badra, own all rights to the files and their contents.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import RedBlackTree;

public class TimingTests {
	public static void main(String[] args) throws FileNotFoundException{
		RedBlackTree<Word> Tree = new RedBlackTree();
		File file = new File("textFile");
		Scanner fileScanner = new Scanner(file);
		String s;
		
		long start = System.currentTimeMillis();
		while (fileScanner.hasNext()) {
			s = fileScanner.next();
			Tree.add(new Word(s));
		}
		long stop = System.currentTimeMillis();
		
		Word a = new Word("assessment");
		Word b = new Word("central");
		Word c = new Word("helped");
		Word d = new Word("influence");
		Word e = new Word("manager");
		Word f = new Word("official");
		Word g = new Word("radically");
		Word h = new Word("throws");
		Word i = new Word("website");
		Word j = new Word("yearning");
		
		long findStart = System.nanoTime();
		Tree.find(a);
		Tree.find(b);
		Tree.find(c);
		Tree.find(d);
		Tree.find(e);
		Tree.find(f);
		Tree.find(g);
		Tree.find(h);
		Tree.find(i);
		Tree.find(a);
		long findStop = System.nanoTime();
		
		long total = stop - start;
		
		long findAverage = (findStop - findStart) / 10000;
		
		System.out.println("The Red-Black Tree took " + total + " milliseconds to add " + Tree.size() + " words to the tree.");
		System.out.println("The Red-Black Tree took an average of " + findAverage + " microseconds (per word) to find 10 random words in the tree.");
	}
}
