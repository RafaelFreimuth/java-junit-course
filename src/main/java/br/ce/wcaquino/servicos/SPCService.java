package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;

public interface SPCService {

	Boolean isUsuarioComSaldoNegativado(Usuario usuario);
}
