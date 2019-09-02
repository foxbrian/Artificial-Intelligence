import java.io.File;

public class Solver {
	private static int[][] constraints = {
			{0,1,2,3,4,5,6,7,8},
			{9, 10, 11, 12, 13, 14, 15, 16, 17},
			{18, 19, 20, 21, 22, 23, 24, 25, 26},
			{27, 28, 29, 30, 31, 32, 33, 34, 35},
			{36, 37, 38, 39, 40, 41, 42, 43, 44},
			{45, 46, 47, 48, 49, 50, 51, 52, 53},
			{54, 55, 56, 57, 58, 59, 60, 61, 62},
			{63, 64, 65, 66, 67, 68, 69, 70, 71},
			{72, 73, 74, 75, 76, 77, 78, 79, 80},
			{0, 9, 18, 27, 36, 45, 54, 63, 72},
			{1, 10, 19, 28, 37, 46, 55, 64, 73},
			{2, 11, 20, 29, 38, 47, 56, 65, 74},
			{3, 12, 21, 30, 39, 48, 57, 66, 75},
			{4, 13, 22, 31, 40, 49, 58, 67, 76},
			{5, 14, 23, 32, 41, 50, 59, 68, 77},
			{6, 15, 24, 33, 42, 51, 60, 69, 78},
			{7, 16, 25, 34, 43, 52, 61, 70, 79},
			{8, 17, 26, 35, 44, 53, 62, 71, 80},
			{0, 1, 2, 9, 10, 11, 18, 19, 20},
			{3, 4, 5, 12, 13, 14, 21, 22, 23},
			{6, 7, 8, 15, 16, 17, 24, 25, 26},
			{27, 28, 29, 36, 37, 38, 45, 46, 47},
			{30, 31, 32, 39, 40, 41, 48, 49, 50},
			{33, 34, 35, 42, 43, 44, 51, 52, 53},
			{54, 55, 56, 63, 64, 65, 72, 73, 74},
			{57, 58, 59, 66, 67, 68, 75, 76, 77},
			{60, 61, 62, 69, 70, 71, 78, 79, 80}
	};
	private Board initialBoard;
	
	public Solver(String filename) {
		File puzzleFile = new File(filename);
		initialBoard = new Board(puzzleFile);
		
	}
	
	public void printBoard() {
		for(int i =0;i<81;i++) {
			System.out.print(initialBoard.getValue(i));
		}
	}
	
	private void assignPreFilled() {
		for (int i =0;i<81;i++) {
			if(initialBoard.getValue(i) != '0') {
				initialBoard.assign(i, initialBoard.getValue(i));
			}
		}
	}
	private void forwardCheck() {
		assignPreFilled();
		for (Integer index : initialBoard.getAssigned()) {
			limitAvailabilityBy(initialBoard,index);
		}
	}
	
	private boolean contains(int[] array,int check) {
		for (int i : array) {
			if(i==check) {
				return true;
			}
		}
		return false;
	}
	
	private void limitAvailabilityBy(Board board,Integer index) {
		for(int[] constraint : constraints) {
			if(contains(constraint,index)) {
				for(int cell : constraint) {
					if(cell!=index) {
						board.limit(cell,board.getValue(index));
					}
				}
			}
		}
	}
	
	public char[][] solve() {
		forwardCheck();
		char[] singleList = search(initialBoard,0);
		char[][] doubleList = new char[9][9];
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				doubleList[i][j] = singleList[i*9+j];
			}
		}
		return doubleList;
	}

	private char[] search(Board board,int index) {
		
		if(index==81) {
			return board.getList();
		}
		
		if(board.isAssigned(index)) {
			return search(board,index+1);
		}
		
		for(Character value : board.getCell(index)) {
			Board nextBoard = board.copy();
			nextBoard.assign(index, value);
			if(violation(board,index)) {
				continue;
			}
			limitAvailabilityBy(nextBoard,index);
			char[] result = search(nextBoard,index+1);
			if(result != null) {
				return result;
			}
		}
		return null;
		
	}

	private boolean violation(Board board, int index) {
		return false;
	}
	
}
