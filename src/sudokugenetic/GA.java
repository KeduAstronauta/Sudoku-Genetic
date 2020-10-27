package sudokugenetic;

import java.util.Arrays;
import java.util.Random;

public class GA {

	public Pair populacao[][];
	public int geracao = 0;
	public double mut_r = 0.08;
	public double cross_r = 0.78;

	public int fitness(Pair cromossomo[]) {
		Ambiente tmp=new Ambiente(cromossomo);
		return tmp.colisao();
	}

	public void initPopulacao(int qtd_ind) {
		this.populacao=new Pair[qtd_ind][];
		for (int i = 0; i < qtd_ind; i++) {
			Ambiente a=new Ambiente();
			this.populacao[i]=a.ambiente2Array();
			System.out.println(a.toString());
			System.out.println();
		}
	}

	public Pair[][] selecao() {
		int fit[]=new int[this.populacao.length];
		int anterior=0;
		
		for(int i=0;i<fit.length;i++) {
			Ambiente a=new Ambiente(this.populacao[i]);
			anterior+=a.colisao();
			fit[i]=anterior;
		}
		Random r=new Random();
		Pair tmp[][]=new Pair[this.populacao.length][];
		int count=0;
		for (int i = 0; i < tmp.length; i++) {
			int f=r.nextInt(fit[fit.length-1]);
			for (int j = 0; j < fit.length; j++) {
				if (f<fit[j]) {
					tmp[count]=this.populacao[j];
					count++;
					break;
				}
			}
		}
		return tmp;
	}
	
	// 123 321 321 123
	// 456 654 456 654
	// 789 987 987 789
	public Pair[][] crossover(Pair[][] tmp){
		for (int i = 0; i < tmp.length-1; i=i+2) {
			for (int j = 0; j < tmp[0].length; j++) {
				int aux[][]=tmp[i][j].block;
				tmp[i][j].block[0][0]=tmp[i+1][j].block[0][0];
				tmp[i][j].block[0][1]=tmp[i+1][j].block[0][1];
				tmp[i][j].block[0][2]=tmp[i+1][j].block[0][2];
//				tmp[i][j].block[1][0]=tmp[i+1][j].block[1][0];
//				tmp[i][j].block[1][1]=tmp[i+1][j].block[1][1];
//				tmp[i][j].block[1][2]=tmp[i+1][j].block[1][2];
				tmp[i][j].block[2][0]=tmp[i+1][j].block[2][0];
				tmp[i][j].block[2][1]=tmp[i+1][j].block[2][1];
				tmp[i][j].block[2][2]=tmp[i+1][j].block[2][2];
				
				tmp[i+1][j].block[0][0]=aux[0][0];
				tmp[i+1][j].block[0][1]=aux[0][1];
				tmp[i+1][j].block[0][2]=aux[0][2];				
//				tmp[i+1][j].block[1][0]=aux[0][0];
//				tmp[i+1][j].block[1][1]=aux[0][1];
//				tmp[i+1][j].block[1][2]=aux[0][2];
				tmp[i+1][j].block[2][0]=aux[2][0];
				tmp[i+1][j].block[2][1]=aux[2][1];
				tmp[i+1][j].block[2][2]=aux[2][2];
			}
		}
		return tmp;
	}
	
	
	public void mutacao(){
		Ambiente a=new Ambiente();
		this.populacao[populacao.length-1]=a.ambiente2Array();
	}
	
	public void run() {
		geracao=0;
		int menor=10000;
		// gerar populacao inicial
		initPopulacao(5);
		// enquanto o objetivo nao for encontrado, faca
		boolean run = true;
		Pair[] board = null;
		while (run) {
			int fitness_medio = 0;
			int count = 0;
			for (Pair p[]: populacao) {
				fitness_medio += this.fitness(p);
				count++;
				if (this.fitness(p)<menor) {
					menor=this.fitness(p);
				}
				if(this.fitness(p)<=100){
					board = p;
					run = false;
				}
			}
			// selecao
			Pair aux[][]=selecao();
			// crossover
			boolean cross = false;
			if(rate(0, 1) < cross_r) {
				aux=crossover(aux);
				cross = true;
			}
			populacao=aux;
			// mutacao
			boolean mutation = false;
			if(rate(0, 1) < mut_r) {
				mutacao();
				mutation = true;
			}
			System.out.println("Geração: "+geracao);
			System.out.println("Fitness Médio: "+fitness_medio/count);
			System.out.println("Crossover: "+cross);
			System.out.println("Mutação: "+mutation);
			System.out.println("Menor fitness: "+menor);
			System.out.println();
			geracao++;
		}
		Ambiente amb = null;
		amb.array2Ambiente(board);
		System.out.println(amb.toString());
	}
	
	public static void main(String[] args) {
		GA g=new GA();
		/*g.initPopulacao(5);
		for (Pair p[]: g.populacao) {
			System.out.println(g.fitness(p));
		}
		System.out.println();
		Pair aux[][]=g.selecao();
		aux=g.crossover(aux);
		for (Pair p[]:aux) {
			System.out.println(Arrays.toString(p));
		}*/
		
		g.run();
	}
	
	private double rate(double min, double max) {
		return min + Math.random() * ((max - min)+1);
	}
}

