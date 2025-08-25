package com.shaharyi.karaoke;

public class Karaoke {
	private String[] catalog;
	private Singer[] singers;
	private int numSingers;

	public Karaoke(String[] catalog, int numSingers) {
		this.catalog = catalog;
		this.singers = new Singer[numSingers];
		this.numSingers = 0;
	}

	public boolean addSinger(String name, int age) {
		if (numSingers < singers.length) {
			singers[numSingers++] = new Singer(name, age);
			return true;
		}
		return false;
	}
	
	public int countSongVotes(String songName) {
		int c = 0;
		for (int i = 0; i < singers.length; i++) {
			if (singers[i].hasSong(songName))
				c++;
		}
		return c;
	}
	
	public String mostPopularSong() {
		int max = countSongVotes(catalog[0]);
		int maxIndex = 0;
		for (int i = 1; i < catalog.length; i++) {
			int c = countSongVotes(catalog[i]);
			if (c > max) {
				max = c;
				maxIndex = i;
			}			
		}
		return catalog[maxIndex];
	}
	
	public String youngestVoteName(String songName) {
		int minIndex = -1;
		int min = -1;
		for (int i = 0; i < singers.length; i++) {
			if (minIndex == -1 || singers[i].getAge() < min) {
				min = singers[i].getAge();
				minIndex = i;
			}
		}
		return singers[minIndex].getName();
	}
}
