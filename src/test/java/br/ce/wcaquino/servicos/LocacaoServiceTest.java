package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.ListUtil;

public class LocacaoServiceTest {

	LocacaoService service;
	
	@Before
	public void setUp() {
		service = new LocacaoService();
	}
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void deveVerificarAOhValorDaAlocacaoQueDeveSerOhMesmoDoFilme() {
		Usuario usuario = new Usuario("Rafael Betta");
		
		Filme filme = new Filme("Avatar 2", 100, 89.98);
		
		Locacao locacao = this.service.alugarFilme(usuario, ListUtil.toList(filme));
		
		assertThat(locacao.getValor(), is(equalTo(Double.valueOf(89.98))));
	}
	
	@Test
	public void deveVerificarQueAhDataDeLocacaoEhAhDataAtual() {
		Usuario usuario = new Usuario("Rafael");
		Filme filme = new Filme("Vingadores", 20, 50.0);
		
		Locacao locacao = this.service.alugarFilme(usuario, ListUtil.toList(filme));
		
		assertTrue(isMesmaData(locacao.getDataLocacao(), new Date()));
	}
	
	@Test
	public void deveVerificarQueAhDataDeRetornoEhParaAmanha() {
		Usuario usuario = new Usuario("Rodrigo");
		Filme filme = new Filme("Capitão América", 5, 25.97);
		
		Locacao locacao = this.service.alugarFilme(usuario, ListUtil.toList(filme));
		
		Date amanha = adicionarDias(new Date(), 1);
		assertTrue(isMesmaData(locacao.getDataRetorno(), amanha));
	}
	
	@Test
	public void deveLancarExcecaoCasoTenteAlugarUmFilmeSemEstoque() {
		Usuario usuario = new Usuario("Rafael");
		Filme filme = new Filme("Avatar", 0, 52.69);
		
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Filme sem estoque");
		
		this.service.alugarFilme(usuario, ListUtil.toList(filme));
	}

	@Test
	public void deveSerPossivelAlugarMaisDeUmFilme() {
		Usuario usuario = new Usuario("Rafael");
		
		Filme filmeAvatar = new Filme("Avatar", 5, 10.0);
		Filme filmeVingadores = new Filme("Vingadores", 10, 20.0);
		
		Locacao locacao = this.service.alugarFilme(usuario, 
												   ListUtil.toList(filmeAvatar, filmeVingadores));
		
		assertThat(locacao.getValor(), is(30.0));
	}
}
