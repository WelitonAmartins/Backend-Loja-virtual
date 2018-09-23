package com.welitonmartins.resources.execptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.welitonmartins.services.exceptions.ObjectNotFoundException;

/*
 * tratamento de exceção responsavel para retornar a mensagem http
 * 404
 */

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)// aqui indica que é um tratador de exceção desse tipo de exceção ObjectNotFoundException
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		StandardError erro = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());//System.currentTimeMillis pega a hora atual
//linha a cima, instanciando a classes StandardError e passando seus valores(status, msg, e hora)
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}

}
