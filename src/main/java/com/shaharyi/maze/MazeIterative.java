import java.util.Random;
import java.util.Scanner;

public class MazeIterative {
    static final int W = 31, H = 21;      // odd width, height
    static final char WALL = '#', PATH = ' ';
    static final char[][] M = new char[H][W];

    // explicit stack (arrays)
    static final int[] sx = new int[W * H];
    static final int[] sy = new int[W * H];
    static int sp = 0;

    // directions (2-step jumps)
    static final int[] dx = { 2,-2, 0, 0 };
    static final int[] dy = { 0, 0, 2,-2 };
    static final int[] order = {0,1,2,3};   // will shuffle

    static final Random rnd = new Random(); // use new Random(0) for fixed maze
    static final Scanner in = new Scanner(System.in);
    static int step = 0;

    public static void main(String[] args) {
        // fill with walls
        for (int y=0; y<H; y++)
            for (int x=0; x<W; x++)
                M[y][x] = WALL;

        push(1,1);
        M[1][1] = PATH;
        show(1,1,"Start at (1,1)");

        while (sp > 0) {
            int x = sx[sp-1], y = sy[sp-1];

            shuffle(order); // randomize direction order

            int moved = 0;
            int d = 0;
            while (d < 4 && moved == 0) {
                int nx = x + dx[order[d]], ny = y + dy[order[d]];
                if (0 < nx && nx < W-1 && 0 < ny && ny < H-1 && M[ny][nx] == WALL) {
                    int wx = x + dx[order[d]]/2, wy = y + dy[order[d]]/2;
                    M[wy][wx] = PATH; show(wx,wy,"Knock wall");
                    M[ny][nx] = PATH; push(nx,ny); show(nx,ny,"Move to ("+nx+","+ny+")");
                    moved = 1;
                }
                d++;
            }
            if (moved == 0) {
                pop();
                show(x,y,"Backtrack");
            }
        }

        // entrance and exit
        M[0][1] = PATH;        show(1,0,"Open entrance");
        M[H-1][W-2] = PATH;    show(W-2,H-1,"Open exit");
        show(-1,-1,"Done");
    }

    static void push(int x,int y){ sx[sp]=x; sy[sp]=y; sp++; }
    static void pop(){ if (sp>0) sp--; }

    static void shuffle(int[] a){
        for (int i=a.length-1; i>0; i--){
            int j = rnd.nextInt(i+1);
            int tmp=a[i]; a[i]=a[j]; a[j]=tmp;
        }
    }

    static void show(int cx,int cy,String note){
        System.out.print("\033[H\033[2J"); System.out.flush();
        System.out.println("Iterative DFS (plain arrays, no break) — step " + (++step) + " : " + note);
        System.out.println("Press Enter for next step (Ctrl+C to quit)\n");
        for (int y=0; y<H; y++){
            for (int x=0; x<W; x++){
                if (x==cx && y==cy) System.out.print('@');
                else System.out.print(M[y][x]);
            }
            System.out.println();
        }
        in.nextLine();
    }
}
