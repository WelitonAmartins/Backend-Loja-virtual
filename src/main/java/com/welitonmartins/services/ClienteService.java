package com.welitonmartins.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.welitonmartins.dto.ClienteDTO;
import com.welitonmartins.model.Cliente;
import com.welitonmartins.model.Cliente;
import com.welitonmartins.repositories.ClienteRepository;
import com.welitonmartins.services.exceptions.DataIntegrityException;
import com.welitonmartins.services.exceptions.ObjectNotFoundException;



@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	//metado que busca por id
	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);	
		//se caso nao encontrar o id retorna null com e entra na exceção 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	//metado para atualizar, o "save" serve tanto para inserir tanto para atualziar
		public Cliente update(Cliente obj) {
			find(obj.getId());
			return clienteRepository.save(obj);
		}
		//metado para deletar categoria
		public void delete(Integer id) {
			try {
				clienteRepository.deleteById(id);
			}
			//caso 
			catch (DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
			}
		}
		//metado para retorna todas as categorias
		public List<Cliente>findAll(){
				return clienteRepository.findAll();
		}
		//metado de paginação
		//class do spring Page captura e encapsula informaçoes e operaçoes sobre a paginação
		public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
				//parametros em cima - contagem de pagina,quantas linha por pagina eu qero, por qual atributo que vou ordenar, e direção se é acendente ou descente
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
			return clienteRepository.findAll(pageRequest);
		}
		//metado auxilar instacia um objeto a parti do meu objDto, para a atualizacao, put  do cliente
		public Cliente fromDTO(ClienteDTO objDto) {
			return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
		}


}
