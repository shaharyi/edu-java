package com.shaharyi.knapsack;

class Main {
  static int max(int a, int b) { return (a > b)? a : b; } 
       
  // Returns the maximum value that can be put in a knapsack of capacity W 
  static int knapSack(int W, int wt[], int val[], int n) { 
    // Base Case 
    if (n == 0 || W == 0) 
      return 0; 
      
    // If weight of the nth item is more than Knapsack capacity W, then 
    // this item cannot be included in the optimal solution 
    if (wt[n-1] > W) 
        return knapSack(W, wt, val, n-1); 
      
    // Return the maximum of two cases:  
    // (1) nth item included  
    // (2) not included 
    else {
      int v1 =  val[n-1] + knapSack(W-wt[n-1], wt, val, n-1);
      int v2 = knapSack(W, wt, val, n-1);
      return max(v1, v2);
    }
  }
  
    
  public static void main(String[] args) {
    test1();
    test2();
  }
  
  /* greedy algorithm will fail this test
   if it works by value/weight ratio  */
  public static void test1(String[] args) {
    int val[] = new int[] {60, 100, 120}; 
    int wt[] = new int[]  {10,  20,  30}; 
    int  W = 50; 
    int n = val.length; 
    System.out.println(knapSack(W, wt, val, n)); 
  }
  
  /* that greedy algorithm will fail this test also
     even if you have multiple copies of every object
  */
  public static void test2(String[] args) {
    int val[] = new int[] {9, 7}; 
    int wt[] = new int[]  {5, 4}; 
    int  W = 50; 
    int n = val.length; 
    System.out.println(knapSack(W, wt, val, n)); 
  }
}
