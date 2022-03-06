package Controller;

public class cell {
	public int x,y,w,h,row,col,val;
	public number nb;
	public cell() {
		x=0;
		y=0;
		val=0;
		row=0;
		col=0;
		w=0;
		h=0;
		nb=new number();
	}

	public void markUsed(int x) {
		nb.used[x]=true;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
