insert into categoria(nome)values('escritorio');
insert into categoria(nome)values('informatica');

insert into produto(nome,preco)values('computador', 2000.00);
insert into produto(nome,preco)values('Impressora', 800.00);
insert into produto(nome,preco)values('mouse', 80.00);

insert into PRODUTO_CATEGORIA(produto_id,categoria_id)values(1,1);
insert into PRODUTO_CATEGORIA(produto_id,categoria_id)values(2,1);
insert into PRODUTO_CATEGORIA(produto_id,categoria_id)values(2,2);
insert into PRODUTO_CATEGORIA(produto_id,categoria_id)values(3,1);

insert into estado(nome,)values('Minas Gerais');
insert into estado(nome,)values('Sao Paulo');



insert into cidade(nome,estado_id)values('Sao Paulo',2);
insert into cidade(nome,estado_id)values('Campinas',2);
insert into cidade(nome,estado_id)values('Uberlândia',1);


