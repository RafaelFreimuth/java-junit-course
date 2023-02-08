package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.ce.wcaquino.dao.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

public class LocacaoService {
	
	private LocacaoDAO locacaoDAO;
	private SPCService spcService;
	private EnviarEmailService enviarEmailService;
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) {
		
		if (filmes == null || filmes.isEmpty()) {
			throw new RuntimeException("Não foram passados filmes para locação");
		}
		
		validarEstoqueDosFilmes(filmes);
		validarUsuarioNegativadoSPC(usuario);
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(obterValorTotalLocacao(filmes));

		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		locacaoDAO.salvar(locacao);
		
		return locacao;
	}
	
	public void enviarEmailsUsuariosDevolucaoAtrasadas() {
		List<Locacao> locacoesAtrasadas = locacaoDAO.obterLocacoesComDevolucacaoAtrasada();
		
		for (Locacao locacao : locacoesAtrasadas) {
			enviarEmailService.enviar(locacao.getUsuario());
		}
	}

	private void validarUsuarioNegativadoSPC(Usuario usuario) {
		if (spcService.isUsuarioComSaldoNegativado(usuario)) {
			throw new RuntimeException("O usuário está negativado no SPC");
		}
	}

	private void validarEstoqueDosFilmes(List<Filme> filmes) {
		for (Filme filme : filmes) {
			if (filme.getEstoque().equals(0)) {
				throw new RuntimeException("Filme sem estoque");
			}
		}
	}
	
	private Double obterValorTotalLocacao(List<Filme> filmes) {
		Double totalizador = 0.0;
		
		Boolean aplicarDescontoDe50Porcento = filmes.size() >= 3;
		
		for (Filme filme : filmes) {
			Double precoLocacao = filme.getPrecoLocacao();
			
			if (aplicarDescontoDe50Porcento) {
				precoLocacao = precoLocacao * 0.5;
			}
			
			totalizador = totalizador + precoLocacao;
		}
		
		return totalizador;
	}
	
	public void setLocacaoDAO(LocacaoDAO locacaoDAO) {
		this.locacaoDAO = locacaoDAO;
	}
	
	public void setSPCService(SPCService spcService) {
		this.spcService = spcService;
	}
	
	public void setEnviarEmailService(EnviarEmailService enviarEmailService) {
		this.enviarEmailService = enviarEmailService;
	}
}