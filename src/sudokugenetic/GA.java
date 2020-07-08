package sudokugenetic;

import java.util.Arrays;
import java.util.Random;

public class GA {

	Pair populacao[][];
	int geracao = 0;

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
				tmp[i][j].block[2][0]=tmp[i+1][j].block[2][0];
				tmp[i][j].block[2][1]=tmp[i+1][j].block[2][1];
				tmp[i][j].block[2][2]=tmp[i+1][j].block[2][2];
				
				tmp[i+1][j].block[0][0]=aux[0][0];
				tmp[i+1][j].block[0][1]=aux[0][1];
				tmp[i+1][j].block[0][2]=aux[0][2];
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
		while (true) {
			for (Pair p[]: populacao) {
				if (this.fitness(p)<menor) {
					menor=this.fitness(p);
				}
				if(this.fitness(p)<100){
					break;
				}
			}
			// selecao
			Pair aux[][]=selecao();
			// crossover
			aux=crossover(aux);
			// mutacao
			mutacao();
			populacao=aux;
			System.out.println("Geração: "+geracao);
			System.out.println("Menor fitness: "+menor);
			System.out.println();
			geracao++;
		}
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
}

