package com.shaharyi.recursion;

public class TowerPuzzle {

	static final int TOP = 105;
	
	static int ncalls = 0;
	static int[][] memo = new int[TOP + 1][TOP + 1];
	static int[] bestJump = new int[TOP + 1];
	
	public static void printStrategy() {
        System.out.println("\nOptimal Drop Sequence:");
        int currentFloor = 0;
        int dropNumber = 1;
        
        while (currentFloor < TOP) {
            int jump = bestJump[currentFloor];
            currentFloor += jump;
            
            if (currentFloor > TOP) {
                currentFloor = TOP;
            }
            
            System.out.println("Drop " + dropNumber + ": test floor " + currentFloor);
            dropNumber++;
        }
    }

    public static void main(String[] args) {
        System.out.println("Maximum drops needed: " + solve(0, 0));
        System.out.println("ncalls=" + ncalls);
        printStrategy();
    }
    
    public static int solve(int floor, int count) {
    	ncalls++;
		//System.out.println("f="+floor+", c="+count);
		
		if (floor >= TOP)
			return count;
		if (memo[floor][count] != 0) {
            return memo[floor][count];
        }		
		int best = TOP;
		int optimalS = 1; // Track which jump size is the winner
		
		for (int s = 1; s <= TOP- floor; s++) {
			int a = solve(floor + s, count + 1); //for ball intact 
			int b = count + s;				   	//ball broken
			int worstCaseForThisJump = Math.max(a, b);
            
            // If we found a better strategy, save it and remember the jump 's'
            if (worstCaseForThisJump < best) {
                best = worstCaseForThisJump;
                optimalS = s;
            }
        }
        
        // NEW: Save the winning jump size for this floor state
        bestJump[floor] = optimalS;			
		memo[floor][count] = best;		
		return best;
	}

}
