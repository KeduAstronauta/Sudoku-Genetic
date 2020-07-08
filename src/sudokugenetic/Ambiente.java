package sudokugenetic;

import java.util.Random;

public class Ambiente {
	int Grid[][];/*= {
		{8,2,7,1,5,4,3,9,6},
		{9,6,5,3,2,7,1,4,8},
		{3,4,1,6,8,9,7,5,2},
		{5,9,3,4,6,8,2,7,1},
		{4,7,2,5,1,3,6,8,9},
		{6,1,8,9,7,2,4,3,5},
		{7,8,6,2,3,5,9,1,4},
		{1,5,4,7,9,6,8,2,3},
		{2,3,9,8,4,1,5,6,7},
	};*/
	
	
	public Ambiente(Pair[] a) {
		array2Ambiente(a);
	}
	
	public Ambiente() {
		this.Grid=new int[9][9];
		this.random();
	}
	
	private void random() {
		Random r=new Random();
		for (int i = 0; i < Grid.length; i++) {
			for (int j = 0; j < Grid[0].length; j++) {
				this.Grid[i][j]=r.nextInt(9)+1;
			}
		}
	}
	
	@Override
	public String toString() {
		String str="";
		for (int i = 0; i < this.Grid.length; i++) {
			for (int j = 0; j < this.Grid[0].length; j++) {
				str+=this.Grid[i][j]+" ";
			}
			str+="\n";
		}
		return str;
	}
	
	public int colisao() {
		int count=0;
		for (int i = 0; i < Grid.length; i++) {
			for (int j = 0; j < Grid[0].length; j++) {
				
				for (int k = 0; k < Grid.length; k++) {
					if(Grid[i][j]==Grid[i][k] && j!=k) {
						count++;
					}
				}
				
				for (int k = 0; k < Grid.length; k++) {
					if(Grid[i][j]==Grid[k][j] && i!=k) {
						count++;
					}
				}
				
				int x =j/3;
				int y =i/3;
				for (int k = y*3+0; k < y*3+3; k++) {
					for (int l = x*3+0; l < x*3+3; l++) {
						if(Grid[k][l]==Grid[i][j] && i!=k && j!=l) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}
	
	public Pair[] ambiente2Array() {
		Pair arr[]=new Pair[this.Grid.length];
		int count=0;
		for (int i = 0; i < this.Grid.length; i=i+3) {
			for (int j = 0; j < this.Grid[0].length; j=j+3) {
				arr[count]=new Pair();
				int x =j/3;
				int y =i/3,tmp=0,tmp1=0;
				for (int k = y*3+0; k < y*3+3; k++) {
					for (int l = x*3+0; l < x*3+3; l++) {
						arr[count].block[tmp][tmp1]=Grid[k][l];
						tmp1++;
					}
					tmp++;
					tmp1=0;
				}
				count++;
			}
		}
		return arr;
	}
	
	public void array2Ambiente(Pair arr[]) {
		int vet[][]=new int[arr.length][arr.length];
		int count=0;
		for (int i = 0; i < this.Grid.length; i=i+3) {
			for (int j = 0; j < this.Grid[0].length; j=j+3) {
				int x =j/3;
				int y =i/3,tmp=0,tmp1=0;
				for (int k = y*3+0; k < y*3+3; k++) {
					for (int l = x*3+0; l < x*3+3; l++) {
						vet[k][l]=arr[count].block[tmp][tmp1];
						tmp1++;
					}
					tmp++;
					tmp1=0;
				}
				count++;
			}
		}
		this.Grid=vet;
	}
	
	public static void main(String[] args) {
		Ambiente a=new Ambiente();
		System.out.println(a.toString());
		System.out.println(a.colisao());
		Pair p[]=a.ambiente2Array();
		for (int i = 0; i < p.length; i++) {
			System.out.println(p[i].toString());
		}
	}
}
