package com.welitonmartins.resources;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.welitonmartins.dto.ClienteDTO;
import com.welitonmartins.dto.ClienteNewDTO;
import com.welitonmartins.model.Cliente;
import com.welitonmartins.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	

	
	//metado que busca a classe de regra de negocio "service" para buscar por id
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		//ResponseEntity tem varias informaçoes do protocolo http
		//linha abaixo uma forma de falar que aconteceu tudo ok, e tem como corpo o "obj"
		return ResponseEntity.ok().body(obj);
		
	}
	//metado de inserir cliente
			@RequestMapping(method=RequestMethod.POST)
			public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){
				Cliente obj = service.fromDTO(objDto);
				obj = service.insert(obj); 
				//pegando a requisição uri que vai ser inserida a nova informação
				URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}").buildAndExpand(obj.getId()).toUri();
				return ResponseEntity.created(uri).build();
				//created(uri) -> gera o codigo 201
			}
			
	
	//metado de atualizar categoria
			@RequestMapping(value="/{id}", method=RequestMethod.PUT)
			public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
				Cliente obj = service.fromDTO(objDto);
				obj.setId(id);//desencargo de conciencia, pra ter certeza que vai receber um id
				obj = service.update(obj);
				return ResponseEntity.noContent().build();
			}
			
			//metado para deletar uma categoria
			@RequestMapping(value="/{id}",method=RequestMethod.DELETE)	
			public ResponseEntity<Void> delete(@PathVariable Integer id) {
				service.delete(id);
				return ResponseEntity.noContent().build();
			}
			//listando um DTO
			@RequestMapping(method=RequestMethod.GET)	
			public ResponseEntity<List<ClienteDTO>> findAll() {
			List<Cliente> list = service.findAll();
			//percorendo a lista com strem onde /map vai efetuar uma operacao para cada elemento da lista e collect(Collectors.toList()); volta esse tipo de obj para tipo list
			List<ClienteDTO> listDTO = list.stream().map(objetoDTO -> new ClienteDTO(objetoDTO)).collect(Collectors.toList());
			//linha abaixo uma forma de falar que aconteceu tudo ok, e tem como corpo o "obj"
			return ResponseEntity.ok().body(listDTO);	
		}
			
			//metado de paginação
			@RequestMapping(value="/page", method=RequestMethod.GET)	
			public ResponseEntity<Page<ClienteDTO>> findPage(
					@RequestParam(value="page", defaultValue="0")Integer page, 
					@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage, 
					@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
					@RequestParam(value="direction", defaultValue="ASC")String direction) {
			Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
			//percorendo a lista com map vai efetuar uma operacao para cada elemento da lista, como page é um função do java8 ele aceita o .map direto
			Page<ClienteDTO> listDTO = list.map(objetoDTO -> new ClienteDTO(objetoDTO));
			//linha abaixo uma forma de falar que aconteceu tudo ok, e tem como corpo o "obj"
			return ResponseEntity.ok().body(listDTO);	
		}
}
