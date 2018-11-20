package com.welitonmartins.resources;


import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.welitonmartins.dto.CategoriaDTO;
import com.welitonmartins.model.Categoria;
import com.welitonmartins.model.Pedido;
import com.welitonmartins.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	//metado que busca a classe de regra de negocio "service" para buscar por id
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido obj = service.find(id);
		//ResponseEntity tem varias informaçoes do protocolo http
		//linha abaixo uma forma de falar que aconteceu tudo ok, e tem como corpo o "obj"
		return ResponseEntity.ok().body(obj);
		
	}
	
	//metado de inserir pedido
			@RequestMapping(method=RequestMethod.POST)
			public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){
				obj = service.insert(obj); 
				//pegando a requisição uri que vai ser inserida a nova informação
				URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}").buildAndExpand(obj.getId()).toUri();
				return ResponseEntity.created(uri).build();
				//created(uri) -> gera o codigo 201
			}
	
}
