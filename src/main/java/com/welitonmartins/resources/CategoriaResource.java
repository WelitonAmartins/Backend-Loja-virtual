package com.welitonmartins.resources;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.welitonmartins.model.Categoria;
import com.welitonmartins.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	//metado que busca a classe de regra de negocio "service" para buscar por id
		@RequestMapping(value="/{id}",method=RequestMethod.GET)	
		public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		//ResponseEntity tem varias informaçoes do protocolo http
		//linha abaixo uma forma de falar que aconteceu tudo ok, e tem como corpo o "obj"
		return ResponseEntity.ok().body(obj);	
	}
		
		@RequestMapping(method=RequestMethod.POST)
		public ResponseEntity<Void> insert(@RequestBody Categoria obj){
			obj = service.insert(obj); 
			//pegando a requisição uri que vai ser inserida a nova informação
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).build();
			//created(uri) -> gera o codigo 201
		}
		
		@RequestMapping(value="/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
			obj.setId(id);//desencargo de conciencia, pra ter certeza que vai receber um id
			obj = service.update(obj);
			return ResponseEntity.noContent().build();
		}
}
