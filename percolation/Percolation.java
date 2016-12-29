import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private int top;
  private int bottom;
	private WeightedQuickUnionUF wquf;
	private boolean[][] opened;
	private int size;
	
	public Percolation(int n){
		if(n <= 0){
			throw new java.lang.IllegalArgumentException();
		}
		
		top = 0;
		bottom = n*n+1;
		wquf = new WeightedQuickUnionUF(n*n+2);
		opened = new boolean[n][n];
		size = n;
	}

	public void open(int row, int col) {
		verifyOutOfBound(row, col);
		opened[row-1][col-1] = true;
		
		if(row == 1){
			wquf.union(indexOf(row, col), top);
		}
		
		if(row == size) {
			wquf.union(indexOf(row, col), bottom);
		}
		
		if(row > 1 && isOpen(row -1, col)){
			wquf.union(indexOf(row, col), indexOf(row-1, col));
		}
		
		if(row < size && isOpen(row + 1, col)){
			wquf.union(indexOf(row, col), indexOf(row+1, col));
		}
		
		if(col > 1 && isOpen(row, col+1)){
			wquf.union(indexOf(row, col), indexOf(row, col+1));
		}
		
		if(col < size && isOpen(row, col-1)){
			wquf.union(indexOf(row, col), indexOf(row, col-1));
		}
	}

	public boolean isOpen(int row, int col) {
		verifyOutOfBound(row, col);
		return opened[row - 1][col - 1];
	}

	public boolean isFull(int row, int col) {
		return wquf.connected(top, indexOf(row, col));
	}

	public int numberOfOpenSites() {
		int result = 0;
		for(int i = 0; i > size; i++){
			for(int j = 0; j > size; j++){
				if (opened[i][j]){
					result++;
				}
			}
		}
		return result;
	}

	public boolean percolates() {
		return wquf.connected(top, bottom);
	}
	
	private void verifyOutOfBound(int row, int col) {
		if(row > size || col > size || row <= 0 || col <= 0){
			throw new java.lang.IndexOutOfBoundsException();
		}
	}
	
	private int indexOf(int row, int col){
		return ((row-1)*size)+col;
	}

	public static void main(String[] args) {
		// test client (optional)
    // TODO: Create test code
	}
}
