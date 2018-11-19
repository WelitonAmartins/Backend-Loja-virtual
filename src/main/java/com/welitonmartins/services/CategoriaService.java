package com.welitonmartins.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.welitonmartins.dto.CategoriaDTO;
import com.welitonmartins.model.Categoria;
import com.welitonmartins.repositories.CategoriaRepository;
import com.welitonmartins.services.exceptions.DataIntegrityException;
import com.welitonmartins.services.exceptions.ObjectNotFoundException;



@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository cr;
	
	//metado que busca por id
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = cr.findById(id);	
		//se caso nao encontrar o id retorna null com e entra na exceção 
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	//metado para inserir 
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return cr.save(obj);
	}
	//metado para atualizar, o "save" serve tanto para inserir tanto para atualziar
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return cr.save(newObj);
	}
	//metado para deletar categoria
	public void delete(Integer id) {
		try {
		cr.deleteById(id);
		}
		//caso 
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
		}
	}
	//metado para retorna todas as categorias
	public List<Categoria>findAll(){
			return cr.findAll();
	}
	//metado de paginação
	//class do spring Page captura e encapsula informaçoes e operaçoes sobre a paginação
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
			//parametros em cima - contagem de pagina,quantas linha por pagina eu qero, por qual atributo que vou ordenar, e direção se é acendente ou descente
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return cr.findAll(pageRequest);
	}
	//metado auxilar instacia um objeto a parti do meu objDto para o put da categoria
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	//metado auxilar para o update, PUT, para permitir que autualizar no o campo nome 
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}

}
