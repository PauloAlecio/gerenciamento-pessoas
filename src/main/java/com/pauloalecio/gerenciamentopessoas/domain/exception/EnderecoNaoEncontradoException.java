package com.pauloalecio.gerenciamentopessoas.domain.exception;

public class EnderecoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public EnderecoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public EnderecoNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de endereço com id %d", id));
	}

	public EnderecoNaoEncontradoException() {
		this("Não encontrado");
	}
	
}
