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
