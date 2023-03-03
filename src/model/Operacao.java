package model;

import java.util.concurrent.Semaphore;

public abstract class Operacao extends Thread implements OperacaoInterface {
	protected double a, b;
	protected int numeroDeVezes, segundosParaDormir;
	protected Semaphore semAtual, semProximo;
	final int SECONDS_TO_MILISECONDS = 1000;
	final int PERMITS = 1;

	@Override
	public void run() {
		final String blue = "\033[0;36m", green = "\033[0;32m", yellow = "\033[0;33m";		
		long miliSegundosParaDormir = (long) (segundosParaDormir * SECONDS_TO_MILISECONDS);

		try {
			for (int i = 0; i < numeroDeVezes; i++) {
				semAtual.acquire(); // adquire o semáforo atual para iniciar a execução
				System.out.printf(blue + "Eu sou a Thread %s (%.0f) da rodada %d e vou dormir por %d segundos!\n", this.getName(),
						this.calcular(), i+1, segundosParaDormir);

				Thread.sleep(miliSegundosParaDormir);
				
				System.out.printf(green + "Eu sou a Thread %s (%.0f) da rodada %d. Ja se passaram %d segundos, entao terminei!",
						this.getName(), this.calcular(), i+1, segundosParaDormir);
				
				//testa se não é a última rodada do loop para encerrar
				if (i != numeroDeVezes - 1 || !this.getName().equals("DIVISAO")) { 
					semProximo.release(); // libera o semáforo da próxima thread, se não for a última rodada					
				} else {					
					System.out.println(yellow + "\n\nTodas as Threads finalizadas");
				}
				System.out.println("\n");
			}
		} catch (InterruptedException e) {
			System.err.println(
					"A Thread " + this.getName() + " foi interrompida por outro processo, erro: " + e.getMessage());
		}			
	}

	protected Operacao(double a, double b, int segundos, int vezes, Semaphore semAtual, Semaphore semProximo) {
		this.a = a;
		this.b = b;
		this.segundosParaDormir = segundos;
		this.numeroDeVezes = vezes;
		this.semAtual = semAtual;
		this.semProximo = semProximo;
	}
	

	// getters and setters
	protected double getA() {
		return a;
	}

	protected void setA(double a) {
		this.a = a;
	}

	protected double getB() {
		return b;
	}

	protected void setB(double b) {
		this.b = b;
	}

	public int getNumeroDeVezes() {
		return numeroDeVezes;
	}

	public void setNumeroDeVezes(int numeroDeVezes) {
		this.numeroDeVezes = numeroDeVezes;
	}

	public Semaphore getSemAtual() {
		return semAtual;
	}

	public void setSemAtual(Semaphore semAtual) {
		this.semAtual = semAtual;
	}

	public Semaphore getSemProximo() {
		return semProximo;
	}

	public void setSemProximo(Semaphore semProximo) {
		this.semProximo = semProximo;
	}

	public int getSegundosParaDormir() {
		return segundosParaDormir;
	}

	public void setSegundosParaDormir(int segundosParaDormir) {
		this.segundosParaDormir = segundosParaDormir;
	}
	
	

}
