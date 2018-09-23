package com.welitonmartins.services.exceptions;
/*
 * Tratamento de exceção responsavel por verificar se tem id valido e informar a menasgem la no controllador 
 */
public class ObjectNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
