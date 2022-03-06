package Controller;

public class cellBig {
	public cell c[];
	public int last;
	public int idxMatrix[];
	public cellBig() {
		last=0;
		c=new cell[9];
		idxMatrix=new int[9];
		for(int i=0;i<9;i++) {
			idxMatrix[i]=0;
			c[i]=new cell();
		}
			
	}
	public void setCellBig() {
		for(int i=0;i<9;i++)
			c[i].setVal(0);
	}
	public void addToCellBig(int val,int idx) {
		if(last<9){
			c[last].val=val;
			idxMatrix[last]=idx;
			last++;
		}
	}
	public void showCellBig() {
		for(int i=0;i<9;i++) {
			System.out.print(c[i].val+" ");
		}
	}
	public void checkCellBig() {
		for(int i=0;i<9;i++) {
			if(c[i].val!=0) c[i].nb.used[c[i].val-1]=true;
		}
	}
	public static void main(String[] args) {
		cellBig c=new cellBig();
//		c.addToCellBig(9);
		c.showCellBig();
	}
	
}
