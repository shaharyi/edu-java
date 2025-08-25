package com.shaharyi.karaoke;

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		test();
	}


	public static String[] readFile(String filename) {
		StringBuilder content = new StringBuilder();
		try {
			Scanner scanner = new Scanner(new File(filename));
			while (scanner.hasNextLine()) {
				content.append(scanner.nextLine()).append("\n");
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

		// Split into array by line breaks
		String[] result = content.toString().split("\\R"); // \R handles all line terminators
		return result;

	}

	public static void test() {
		String catalog[] = readFile("data/top100.txt");
		Karaoke k = new Karaoke(catalog, 20);
		k.addSinger("1", 3);
		k.addSinger("2", 13);
		k.addSinger("3", 23);

	}
}
