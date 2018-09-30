package com.welitonmartins.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welitonmartins.model.Categoria;
import com.welitonmartins.repositories.CategoriaRepository;
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
		find(obj.getId());
		return cr.save(obj);
	}
	
	
}
