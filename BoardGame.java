package Controller;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BoardGame {
	public cell matrix[][];
	public int countLose;
	public cellBig board[];
	public boolean mark[][];
	public BoardGame() {
		//Khoi tao ma tran ban co 9x9
		matrix=new cell[9][9];
		mark=new boolean[9][9];
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				matrix[i][j]=new cell();
				mark[i][j]=false;
			}
		}
		//Khoi tao ma tran cell lon 3x3 tu ma tran 9x9
		board=new cellBig[9];
		for(int i=0;i<9;i++) board[i]=new cellBig();
	}
	public boolean checkFinish(BoardGame b) {
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				if(b.matrix[i][j].val==0) return false;
		return true;
	}
	public void markUsed(BoardGame b,int row,int col) {
		for(int j=0;j<9;j++) {
			if(b.matrix[row][j].val!=0) {
				b.matrix[row][col].nb.used[b.matrix[row][j].val-1]=true;
			}
		}
		for(int j=0;j<9;j++) {
			if(b.matrix[j][col].val!=0) {
				b.matrix[row][col].nb.used[b.matrix[j][col].val-1]=true;
			}
		}
		int x = row/3, y = col/3;
	    for(int i = 3*x; i < 3*x+3; i++){
	        for(int j = 3*y; j < 3*y+3; j++){
	        	if(b.matrix[i][j].val!=0) {
					b.matrix[row][col].nb.used[b.matrix[i][j].val-1]=true;
				}
	        }
	    }
	}
	public int findRow(int number) {
		int row=0;
		for(int i=0;i<9;i++) if((number-i)%9==0) row=(number-i)/9;
		return row;
	}
	public int findCol(int number,int row) {
		return number-(row*9);
	}
	public int checkCellBlankInRow(BoardGame b,int r) {
		int count=0;
		for(int i=0;i<9;i++) if(b.matrix[r][i].val==0) count++;
		return count;
	}
	public int checkCellBlankInCol(BoardGame b,int c) {
		int count=0;
		for(int i=0;i<9;i++) if(b.matrix[i][c].val==0) count++;
		return count;
	}
	public BoardGame generateBlankCell(BoardGame b) {
		int rand;
		Random rd = new Random();
		for(int i=0;i<9;i++) {
			do {
				rand = rd.nextInt(9);
				if(checkCellBlankInRow(b,i)<4) b.matrix[i][rand].val = 0;
			}while(checkCellBlankInRow(b,i)<4);	
		}		
		return b;
	}
	public BoardGame generate(BoardGame b,int numCell) {
		if(b.checkFinish(b)) return b;
		int i=findRow(numCell);
		int j=findCol(numCell,findRow(numCell));
		int rand;
		Random r = new Random();
		do {
			b.markUsed(b, i, j);
			if (b.matrix[i][j].nb.countNotUse() == 0) {
				b.matrix[i][j].nb.resetNum();
				b.matrix[i][j].val=0;
				b.generate(b,numCell-1);
			}
			rand = r.nextInt(9);
			b.matrix[i][j].val = b.matrix[i][j].nb.num[rand];
		} while (b.matrix[i][j].nb.used[rand]);
		numCell++;
		return b.generate(b,numCell);
	}
	public boolean generateNew(BoardGame b) {
		int rand;
		Random r = new Random();
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(b.matrix[i][j].val==0) {
					
					rand = r.nextInt(9)+1;
						b.matrix[i][j].val=rand;
						System.out.println(i+","+j+":"+rand);
						if(isValid(b, i, j) && generateNew(b)) return true;
//						b.matrix[i][j].val=0;
//					}
					System.out.println("Quay lui");
					return false;
				}
			}
		}
		return true;
	}
	public void copyToCellBig(BoardGame b) {
		for(int row=0;row<9;row++) {
			for(int col=0;col<9;col++) {
				int x=0;
				if(row>=0 && row<=2 && col>=0 && col<=2) {
					x=0;
				}
				if(row>=0 && row<=2 && col>=3 && col<=5) {
					x=1;
				}
				if(row>=0 && row<=2 && col>=6 && col<=8) {
					x=2;
				}
				if(row>=3 && row<=5 && col>=0 && col<=2) {
					x=3;
				}
				if(row>=3 && row<=5 && col>=3 && col<=5) {
					x=4;
				}
				if(row>=3 && row<=5 && col>=6 && col<=8) {
					x=5;
				}
				if(row>=6 && row<=8 && col>=0 && col<=2) {
					x=6;
				}
				if(row>=6 && row<=8 && col>=3 && col<=5) {
					x=7;
				}
				if(row>=6 && row<=8 && col>=6 && col<=8) {
					x=8;
				}
				b.board[x].addToCellBig((b.matrix[row][col].val),row*9+col);
			}	
		}
	}
	public void showBoard() {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				System.out.print(matrix[i][j].val+" ");
			}
			System.out.println();
		}
	}
	public boolean checkLose(BoardGame b,int i,int j) {
		b.matrix[i][j].nb.resetNum();
		b.markUsed(b, i, j);
		if (b.matrix[i][j].nb.countNotUse() == 0) {
			countLose++;
			System.out.println(b.matrix[i][j].nb.countNotUse());
			System.out.println("So lan thua: "+countLose);
			if(countLose>=3) return true;
		}
		return false;
	}
	public boolean isValid(BoardGame b, int row, int column) {
	    return (rowConstraint(b, row) && columnConstraint(b, column) && squareConstraint(b, row, column));
	}
	private boolean rowConstraint(BoardGame b, int row) {
	    boolean[] constraint = new boolean[9];
	    return IntStream.range(0, 9).allMatch(column -> checkConstraint(b, row, constraint, column));
	}
	private boolean columnConstraint(BoardGame b, int column) {
	    boolean[] constraint = new boolean[9];
	    return IntStream.range(0, 9).allMatch(row -> checkConstraint(b, row, constraint, column));
	}
	private boolean squareConstraint(BoardGame b, int row, int column) {
	    boolean[] constraint = new boolean[9];
	    int rs = (row / 3) * 3;
	    int re = rs + 3;
	    int cs = (column / 3) * 3;
	    int ce = cs + 3;

	    for (int r = rs; r < re; r++) {
	        for (int c = cs; c < ce; c++) {
	            if (!checkConstraint(b, r, constraint, c)) return false;
	        }
	    }
	    return true;
	}
	boolean checkConstraint(BoardGame b, int row, boolean[] constraint, int column) {
	    if (b.matrix[row][column].val != 0) {
	        if (!constraint[b.matrix[row][column].val - 1]) {
	            constraint[b.matrix[row][column].val - 1] = true;
	        } else {
	            return false;
	        }
	    }
	    return true;
	}
	public boolean solveGame(BoardGame b) {
		
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(b.matrix[i][j].val==0) {
					for(int k=1;k<=9;k++) {
						b.matrix[i][j].val=k;
						System.out.println(i+","+j+":"+k);
						if(isValid(b, i, j) && solveGame(b)) return true;
						b.matrix[i][j].val=0;
					}
					System.out.println("Quay lui");
					return false;
				}
			}
		}
		return true;
	}
	public static void main(String[] args) {
		BoardGame b=new BoardGame();
		b.generate(b,0);
		b.generateBlankCell(b);
		b.showBoard();
		System.out.println();
		b.solveGame(b);
		System.out.println();
		b.showBoard();
	}
}
