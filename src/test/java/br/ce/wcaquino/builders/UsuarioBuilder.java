package br.ce.wcaquino.builders;

import java.io.Serializable;

import br.ce.wcaquino.entidades.Usuario;

public class UsuarioBuilder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	public UsuarioBuilder() {
		usuario = new Usuario();
	}
	
	public Usuario usuarioDefault() {
		usuario.setNome("Usuário default");
		
		return usuario;
	}
}
