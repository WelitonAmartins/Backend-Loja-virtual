package com.welitonmartins.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.welitonmartins.dto.ClienteDTO;
import com.welitonmartins.dto.ClienteNewDTO;
import com.welitonmartins.model.Cidade;
import com.welitonmartins.model.Cliente;
import com.welitonmartins.model.Endereco;
import com.welitonmartins.model.enums.TipoCliente;
import com.welitonmartins.repositories.ClienteRepository;
import com.welitonmartins.repositories.EnderecoRepository;
import com.welitonmartins.services.exceptions.DataIntegrityException;
import com.welitonmartins.services.exceptions.ObjectNotFoundException;



@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	//metado que busca por id
	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);	
		//se caso nao encontrar o id retorna null com e entra na exceção 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
		@Transactional//essa anotação é para informar que vai ocorrer um transação de preenchimento de endereços para o banco
		public Cliente insert(Cliente obj) {
			obj.setId(null);
			obj = clienteRepository.save(obj);
			enderecoRepository.saveAll(obj.getEnderecos());
			return obj;
		}
	
	//metado para atualizar, o "save" serve tanto para inserir tanto para atualziar
		public Cliente update(Cliente obj) {
			Cliente newObj = find(obj.getId());
			updateData(newObj, obj);
			return clienteRepository.save(newObj);
		}
		//metado para deletar categoria
		public void delete(Integer id) {
			try {
				clienteRepository.deleteById(id);
			}
			//caso 
			catch (DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas");
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
			return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
		}//cidade cid = new cidade19objDto.getcidadeid(),null,null
		
		public Cliente fromDTO(ClienteNewDTO objDto) {													//convertendo o tipoCliente
			Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
			Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
			Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
			cli.getEnderecos().add(end);
			cli.getTelefones().add(objDto.getTelefone1());//aqui ta passando o telefone obrigatoria que é pelo menos 1
			//se caso insesrir mais algum telefone ele deixa de ser nulo e entra na excesão abaixo
			if(objDto.getTelefone2()!= null) {
				cli.getTelefones().add(objDto.getTelefone2());
			}
			if(objDto.getTelefone3()!= null) {
				cli.getTelefones().add(objDto.getTelefone3());
			}
			return cli;
		}
		//metado auxilar para o update, PUT, para permitir que autualizar no os campo nome e email
		private void updateData(Cliente newObj, Cliente obj) {
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());
		}


}
