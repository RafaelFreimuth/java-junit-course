package br.ce.wcaquino.builders;

import java.io.Serializable;
import java.util.Date;

import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import br.ce.wcaquino.utils.ListUtil;

public class LocacaoBuilder implements Serializable {
	private static final long serialVersionUID = 1L;

	private Locacao locacao;
	
	public LocacaoBuilder() {
		locacao = new Locacao();
	}
	
	public Locacao locacaoDefault() {
		locacao.setUsuario(new UsuarioBuilder().usuarioDefault());
		locacao.setFilmes(ListUtil.toList(new FilmeBuilder().filmeDefault()));
		locacao.setValor(10.0);
		locacao.setDataLocacao(new Date());
		locacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(1));
		
		return locacao;
	}
	
	public Locacao locacaoComUsuario(Usuario usuario) {
		locacao = locacaoDefault();
		locacao.setUsuario(usuario);
		
		return locacao;
	}
}
