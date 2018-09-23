package com.welitonmartins.model.enums;

public enum EstadoPagemento {
	
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Canecelado");
	
	private int cod;
	private String descricao;
	
	private EstadoPagemento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagemento toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		//algoritmo de busca
		for(EstadoPagemento x : EstadoPagemento.values()){
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("id inválido :" +cod);// tratamento de exceção caso não encontre
	}

}
