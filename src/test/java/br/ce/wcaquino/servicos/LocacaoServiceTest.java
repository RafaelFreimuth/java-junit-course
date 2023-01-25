package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

public class LocacaoServiceTest {

	@Test
	public void deveRealizarAhLocacaoEhRetornarOsDadosCorretamente() {
		Usuario usuario = new Usuario("Rafael Betta");
		
		Filme filme = new Filme("Avatar 2", 100, 89.98);
		
		Locacao locacao = new LocacaoService().alugarFilme(usuario, filme);
		
		assertTrue(locacao.getValor() == 89.98);
		assertTrue(isMesmaData(locacao.getDataLocacao(), new Date()));
		assertTrue(isMesmaData(locacao.getDataRetorno(), adicionarDias(new Date(), 1)));
	}

}
