package Controller;

public class number {
	public int num[];
	public boolean used[];
	public number() {
		num=new int[9];
		used=new boolean[9];
		for(int i=0;i<9;i++) {
			num[i]=i+1;
			used[i]=false;
		}
//		num[9]=-1;
//		used[9]=false;
	}
	public void showNum() {
		for(int i=0;i<9;i++) {
			if(used[i]==false) System.out.print(num[i]+" ");
		}
		System.out.println();
	}
	public void resetNum() {
		for(int i=0;i<9;i++) {
			num[i]=i+1;
			used[i]=false;
		}
	}
	public int countNotUse() {
		int count=9;
		for(int i=0;i<9;i++) {
			if(used[i]==true) count--;
		}
//		for(int i=0;i<9;i++) {
//			if(used[i]==false) count++;
//		}
		return count;
	}
	
	public static void main(String[] args) {
		number nb=new number();
		nb.showNum();
	}
	
}
