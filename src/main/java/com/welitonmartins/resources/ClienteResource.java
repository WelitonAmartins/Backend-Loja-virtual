package com.welitonmartins.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.welitonmartins.model.Cliente;
import com.welitonmartins.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	//metado que busca a classe de regra de negocio "service" para buscar por id
	@GetMapping(value="/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		//ResponseEntity tem varias informaçoes do protocolo http
		//linha abaixo uma forma de falar que aconteceu tudo ok, e tem como corpo o "obj"
		return ResponseEntity.ok().body(obj);
		
	}
}
