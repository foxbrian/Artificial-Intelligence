
public class Tester {
	public static void main(String[] args) {
		Solver solve = new Solver("../sudokus/s16.txt");
		//solve.printBoard();
		char[][] solved = solve.solve();
		for(int i = 0;i<9;i++) {
			for(int j=0;j<9;j++) {
				System.out.print(solved[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
}
