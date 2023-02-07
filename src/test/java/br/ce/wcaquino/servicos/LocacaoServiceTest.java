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
import org.mockito.Mockito;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.ListUtil;

public class LocacaoServiceTest {

	LocacaoService service;
	Usuario usuarioDefault;
	
	@Before
	public void setUp() {
		service = new LocacaoService();
		LocacaoDAO locacaoDAO = Mockito.mock(LocacaoDAO.class);
		service.setLocacaoDAO(locacaoDAO);
		
		usuarioDefault = new UsuarioBuilder().usuarioDefault();
	}
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void deveVerificarAOhValorDaAlocacaoQueDeveSerOhMesmoDoFilme() {
		Filme filme = new FilmeBuilder().filmeByPrecoLocacao(89.98);
		
		Locacao locacao = this.service.alugarFilme(usuarioDefault, ListUtil.toList(filme));
		
		assertThat(locacao.getValor(), is(equalTo(Double.valueOf(89.98))));
	}
	
	@Test
	public void deveVerificarQueAhDataDeLocacaoEhAhDataAtual() {
		Filme filme = new FilmeBuilder().filmeDefault();
		
		Locacao locacao = this.service.alugarFilme(usuarioDefault, ListUtil.toList(filme));
		
		assertTrue(isMesmaData(locacao.getDataLocacao(), new Date()));
	}
	
	@Test
	public void deveVerificarQueAhDataDeRetornoEhParaAmanha() {
		Filme filme = new FilmeBuilder().filmeDefault();
		
		Locacao locacao = this.service.alugarFilme(usuarioDefault, ListUtil.toList(filme));
		
		Date amanha = adicionarDias(new Date(), 1);
		assertTrue(isMesmaData(locacao.getDataRetorno(), amanha));
	}
	
	@Test
	public void deveLancarExcecaoCasoTenteAlugarUmFilmeSemEstoque() {
		Filme filme = new FilmeBuilder().filmeSemEstoque();
		
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage("Filme sem estoque");
		
		this.service.alugarFilme(usuarioDefault, ListUtil.toList(filme));
	}

	@Test
	public void deveSerPossivelAlugarMaisDeUmFilme() {
		Filme filmeAvatar = new FilmeBuilder().filmeDefault();
		Filme filmeVingadores = new FilmeBuilder().filmeDefault();
		
		Locacao locacao = this.service.alugarFilme(usuarioDefault, 
												   ListUtil.toList(filmeAvatar, filmeVingadores));
		
		assertThat(locacao.getValor(), is(30.0));
	}
	
	@Test
	public void deveDarDescontoDeCinquentaPorCentoParaLocacaoAPartirDeTRESUnidades() {
		Filme avatar = new FilmeBuilder().filmeByPrecoLocacao(4.0);
		Filme vingadores = new FilmeBuilder().filmeByPrecoLocacao(4.0);
		Filme huck = new FilmeBuilder().filmeByPrecoLocacao(4.0);
		
		Locacao locacao = this.service.alugarFilme(usuarioDefault, ListUtil.toList(avatar, vingadores, huck));
		
		assertThat(locacao.getValor(), is(6.0));
	}
}
