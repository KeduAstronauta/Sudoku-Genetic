package sudokugenetic;

import java.util.Random;

public class Pair {
	int block[][]=new int[3][3];
	
	
	public void random(){
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[0].length; j++) {
				Random r=new Random();
				this.block[i][j]=r.nextInt(9)+1;
			}
		}
	}
	static public Pair shuffle(Pair a,Pair b) {
		Pair tmp=new Pair();
		tmp.block[0][0]=a.block[0][0];
		tmp.block[0][1]=a.block[0][1];
		tmp.block[0][2]=a.block[0][2];
		tmp.block[1][0]=b.block[1][0];
		tmp.block[1][1]=b.block[1][1];
		tmp.block[1][2]=b.block[1][2];
		tmp.block[2][0]=a.block[2][0];
		tmp.block[2][1]=a.block[2][1];
		tmp.block[2][2]=a.block[2][2];
		return tmp;
	}
	@Override
	public String toString() {
		String str="";
		for (int i = 0; i < block.length; i++) {
			for (int j = 0; j < block[0].length; j++) {
				str+=this.block[i][j]+" ";
			}
			str+="\n";
		}
		return str;
	}
}
