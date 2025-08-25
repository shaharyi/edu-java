package com.shaharyi.karaoke;

public class Singer {
	private String name;
	private int age;
	private String[] songs;
	private int numSongs;
	
	public Singer(String name, int age) {
		this.name = name;
		this.age = age;		
		this.numSongs = 0;
		this.songs = new String[5];
	}
	
	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public int getNumSongs() {
		return numSongs;
	}

	public boolean addSong(String s) {
		if (numSongs < songs.length) {
			songs[numSongs++] = s;
			return true;
		}
		return false;
	}
	
	public boolean delSong(String s) {
		boolean found = false;
		int i;
		for (i = 0; i < numSongs && !found; i++) {
			found = songs[i].equals(s);
		}
		if (!found)
			return false;
		for (int j = i; j < numSongs-1; j++) {
			songs[j] = songs[j+1];
		}
		return true;
	}
	
	public boolean hasSong(String t) {
		for (int i = 0; i < songs.length; i++) {
			if (songs[i].equals(t))
				return true;
		}
		return false;
	}
}
