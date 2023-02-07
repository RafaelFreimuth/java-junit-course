package br.ce.wcaquino.builders;

import java.io.Serializable;
import java.util.Random;

import br.ce.wcaquino.entidades.Filme;

public class FilmeBuilder implements Serializable {
	private static final long serialVersionUID = 1L;

	private Filme filme;
	
	public FilmeBuilder() {
		filme = new Filme();
	}
	
	public Filme filmeDefault() {
		filme.setNome(buscarNomeFilmeAleatoriamente());
		filme.setEstoque(10);
		filme.setPrecoLocacao(15.0);
		
		return filme;
	}
	
	public Filme filmeSemEstoque() {
		Filme filme = filmeDefault();
		
		filme.setEstoque(0);
		
		return filme;
	}
	
	public Filme filmeByPrecoLocacao(Double precoLocacao) {
		Filme filme = filmeDefault();
		
		filme.setPrecoLocacao(precoLocacao);
		
		return filme;
	}
	
	private String buscarNomeFilmeAleatoriamente() {
		String[] nomesFilmes = getNomesFilmes();
		
		int posicaoAleatoria = new Random().nextInt(nomesFilmes.length);
		
		return nomesFilmes[posicaoAleatoria];
	}
	
	private String[] getNomesFilmes() {
		String[] nomesFilmes = new String[5];
		nomesFilmes[0] = "Guardiões da Galáxia 2";
		nomesFilmes[1] = "Dr. Estranho.";
		nomesFilmes[2] = "Velozes e Furiosos 8";
		nomesFilmes[3] = "Logan";
		nomesFilmes[4] = "O Vigilante do Amanhã";
		
		return nomesFilmes;
	}
}
