package com.shaharyi.recursion;

import java.io.File;

public class FolderSize {

	public static void main(String[] args) {

		File myFolder = new File("..");

		long totalSize = getFolderSize(myFolder);

		int totalNumFiles = getNumFiles(myFolder);

		double avgSize = 0;

		System.out.println("Total Bytes: " + totalSize);
		System.out.println("Total Files: " + totalNumFiles);
		System.out.println("Avg file size: " + avgSize);
	}

	public static long getFolderSize(File folder) {
		long totalSize = 0;
		File[] files = folder.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory())
				totalSize += getFolderSize(files[i]);
			else
				totalSize += files[i].length();
		}
		return totalSize;
	}

	public static int getNumFiles(File folder) {
		int totalNum = 0;
		
		return totalNum;
	}

}
