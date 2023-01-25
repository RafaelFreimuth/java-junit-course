package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;

import java.util.Date;

import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	@Test
	public void main() {
		Usuario usuario = new Usuario("Rafael Betta");
		
		Filme filme = new Filme("Avatar 2", 100, 89.98);
		
		Locacao locacao = new LocacaoService().alugarFilme(usuario, filme);
		
		System.out.println(locacao.getValor() == 89.98);
		System.out.println(isMesmaData(locacao.getDataLocacao(), new Date()));
		System.out.println(isMesmaData(locacao.getDataRetorno(), adicionarDias(new Date(), 1)));
	}
}