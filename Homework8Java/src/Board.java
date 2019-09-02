import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
class Board {
	private Cell[] board;
	private HashSet<Integer> assigned;
	
	private Board() {
		board = new Cell[81];
		assigned = new HashSet<>();
	}
	
	Board(File puzzle){
		try{
			board = new Cell[81];
			Scanner puzzleScanner = new Scanner(puzzle); 
			int i=0;
			while(puzzleScanner.hasNext()) {
				board[i] = new Cell(puzzleScanner.next().toCharArray()[0]);
				i++;
			}
			puzzleScanner.close();
			assigned = new HashSet<>();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	void assign(int index, char value) {
		board[index].setValue(value);
		assigned.add(index);
	}
	
	boolean isAssigned(int index) {
		return assigned.contains(index);
	}
	
	HashSet<Integer> getAssigned(){
		return assigned;
	}
	
	char getValue(int index) {
		return board[index].getValue();
	}
	
	public class Cell implements Iterable<Character>{
		private char value;
		private HashSet<Character> available;
		
		Cell(char value) {
			this.value = value;
			
			this.available = new HashSet<>();
			available.add('1');available.add('2');available.add('3');available.add('4');available.add('5');
			available.add('6');available.add('7');available.add('8');available.add('9');
		}
		
		void setValue(char newValue) {
			this.value = newValue;
		}
		
		void removeAvailability(char value) {
			available.remove(value);
		}
		
		char getValue() {
			return value;
		}
		
		Cell copy() {
			Cell copy = new Cell(value);
			copy.available = new HashSet<>();
			copy.available.addAll(available);
			return copy;
		}

		@Override
		public Iterator<Character> iterator() {
			//return new AvailabilityIterator();
			return available.iterator();
		}
	}

	void limit(int cell, char value) {
		board[cell].removeAvailability(value);
	}

	char[] getList() {
		char[] chars = new char[81];
		for(int i=0;i<81;i++) {
			chars[i] = board[i].getValue();
		}
		return chars;
	}
	
	Cell getCell(int index) {
		return board[index];
	}

	Board copy() {
		Board copy = new Board();
		
		for(int i=0;i<81;i++) {
			copy.board[i] = board[i].copy();
		}
		copy.assigned.addAll(assigned);
		
		return copy;
	}
}
