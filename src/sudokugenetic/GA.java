package sudokugenetic;

import java.util.Arrays;
import java.util.Random;

public class GA {

	public Pair populacao[][];
	public int geracao = 0;
	public int cross_r = 78;

	public int fitness(Pair cromossomo[]) {
		Ambiente tmp = new Ambiente(cromossomo);
		return tmp.colisao();
	}

	public void initPopulacao(int qtd_ind) {
		this.populacao = new Pair[qtd_ind][];
		for (int i = 0; i < qtd_ind; i++) {
			Ambiente a = new Ambiente();
			this.populacao[i] = a.ambiente2Array();
			System.out.print(a.toString());
			System.out.println(a.colisao());
		}
	}

	public Pair[][] selecao() {
		int fit[] = new int[this.populacao.length];
		int anterior = 0;

		for (int i = 0; i < fit.length; i++) {
			Ambiente a = new Ambiente(this.populacao[i]);
			anterior += a.colisao();
			fit[i] = anterior;
		}
		Random r = new Random();
		Pair tmp[][] = new Pair[this.populacao.length][];
		int count = 0;
		for (int i = 0; i < tmp.length; i++) {
			int f = r.nextInt(fit[fit.length - 1]);
			for (int j = 0; j < fit.length; j++) {
				if (f < fit[j]) {
					tmp[count] = this.populacao[j];
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
	public Pair[][] crossover1(Pair[][] tmp) {
		Pair result[][] = new Pair[this.populacao.length][9];
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[0].length - 1; j += 2) {
				Pair temp = Pair.shuffle(tmp[i][j], tmp[i][j + 1]);
				result[i][j] = Pair.shuffle(tmp[i][j + 1], tmp[i][j]);
				result[i][j + 1] = temp;
			}
			result[i][tmp[0].length-1]=tmp[i][tmp[0].length-1];
		}
		return result;
	}
	//0000 1111 
	public Pair[][] crossover2(Pair[][] tmp){
		Random r = new Random();
		Pair result[][] = new Pair[this.populacao.length][9];
		int corte=r.nextInt(tmp[0].length);
		for (int i = 0; i < tmp.length-1; i+=2) {
			for (int j = 0; j < tmp[0].length ; j++) {
				if(j<corte) {
					result[i][j]=tmp[i][j];
					result[i+1][j]=tmp[i+1][j];
				}else {
					result[i][j]=tmp[i+1][j];
					result[i+1][j]=tmp[i][j];
				}
			}
		}
		return result;
	}
	
	public Pair[][] mutacao(Pair[][] tmp,int taxa) {
		Random r=new Random();
		Pair result[][] = new Pair[this.populacao.length][9];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				result[i][j]=tmp[i][j];
				if(r.nextInt(100)<taxa){
					int x=r.nextInt(3);
					int y=r.nextInt(3);
					int x1=r.nextInt(3);
					int y1=r.nextInt(3);
					
					int aux=result[i][j].block[x1][y1];
					result[i][j].block[x1][y1]=result[i][j].block[x][y];
					result[i][j].block[x][y]=aux;
				}
			}
		}
		return result;
	}

	public void run(int pop,int taxamut) {
		geracao = 0;
		int menor = 10000;
		int men=10000;
		// gerar populacao inicial
		initPopulacao(pop);
		// enquanto o objetivo nao for encontrado, faca
		for (int i = 0; menor>40; i++) {
			// selecao
			Pair aux[][] = selecao();
			// crossover
			if(new Random().nextInt(100) < cross_r) {
				aux=crossover2(aux);
			}
			// mutacao
			aux=mutacao(aux,taxamut);
			for (int j = 0; j < aux.length; j++) {
				populacao[j] = aux[j];
			}
			System.out.println("Geração: " + geracao);
			System.out.println("Menor fitness da gração: " + menor);
			System.out.println("Menor fitness: " + men);
			System.out.println();
			geracao++;
			menor=10000;
			for (Pair p[] : populacao) {
				if (this.fitness(p) < menor) {
					menor = this.fitness(p);
				}
				if(this.fitness(p) <men) {
					men=this.fitness(p);
				}
			}
		}
	}

	public static void main(String[] args) {
		GA g = new GA();

		g.run(100,8);
	}
}
