create database empresa;

use empresa;

create table clientes  (id_cgc_cpf varchar(16) primary key,
			fisica_juridica varchar(1),
                        nome_razao varchar(60),
                        email varchar(80),
                        telefone varchar(20),
                        cep varchar(9),
                        endereco varchar(60),
                        numero int,
                        complemento varchar(40),
                        bairro varchar(60),
                        cidade varchar(40),
                        estado varchar(2),
                        data_cadastro datetime);
                        
create table cli_entrega (id varchar(10) primary key,
			id_cliente varchar(16),
                        telefone varchar(20),
			cep varchar(9),
                        endereco varchar(60),
                        numero int,
                        complemento varchar(40),
                        bairro varchar(60),
                        cidade varchar(40),
                        estado varchar(2));

create table fornecedores (id_cgc_cpf varchar(16) primary key,
			fisica_juridica varchar(1),
                        nome_razao varchar(60),
                        email varchar(80),
                        telefone varchar(20),
                        cep varchar(9),
                        endereco varchar(60),
                        numero int,
                        complemento varchar(40),
                        cidade varchar(40),
                        bairro varchar(60),
                        estado varchar(2),
                        data_cadastro datetime);

create table for_entrega (id varchar(10) primary key,
			id_fornecedor varchar(16),
                        telefone varchar(20),
			cep varchar(9),
                        endereco varchar(60),
                        numero int,
                        complemento varchar(40),
                        bairro varchar(60),
                        cidade varchar(40),
                        estado varchar(2));

create table produtos (id varchar(10) primary key,
			descricao varchar(60),
                        categoria varchar(10),
                        quantidade double,
                        unidade varchar(10),
                        preco_venda Double,
                        preco_ultima_compra double,
                        data_cadastro datetime);
                        
create table materia_prima (id varchar(10) primary key,
			descricao varchar(60),
                        categoria varchar(10),
                        quantidade double,
                        unidade varchar(10),
                        preco_venda Double,
                        preco_ultima_compra double,
                        data_cadastro datetime);
                        
create table composicao (id int primary key,
			id_produto varchar(10),
                        id_materia_prima varchar(10),
                        quantidade double);
                        
create table movimento_fabricado (id_ordem int primary key,
				data_ordem date);

create table item_movimento (id int primary key,
			id_ordem int,
			id_produto varchar(10),
                        quantidade double);                      

create table categorias (id varchar(10) primary key,
			descricao varchar(60),
                        data_cadastro datetime);

create table fornec_produto (id int primary key auto_increment,
			 id_produto varchar(10),
			 id_fornecedor varchar(16),
                         preco double,
                         data_compra datetime,
                         data_cadastro datetime);

create table unidades (id varchar(10) primary key,
			descricao varchar(40),
                        data_cadastro datetime);

create table pedidos_cli (id int primary key auto_increment,
			id_cliente varchar(16),
                        id_endereco_entrega varchar(10),
                        condicao_pag varchar(10),
                        data_pedido datetime);

create table item_pedido_cli (id int primary key auto_increment,
			id_pedido_cli int,
                        id_produto varchar(10),
                        quantidade double,
                        preco double,
                        data_entrega datetime);

create table pedidos_for (id int primary key auto_increment,
			id_fornecedor varchar(16),
                        id_endereco_entrega varchar(10),
                        condicao_pag varchar(10),
                        data_pedido datetime);

create table item_pedido_for (id int primary key auto_increment,
			id_pedido_for int,
                        id_produto varchar(10),
                        quantidade double,
                        preco double,
                        data_entrega datetime);
                                
create table usuarios (id int auto_increment primary key,
			Nome varchar(50) unique,
                        senha varchar(50),
                        cadastroClientes boolean, /* Primeiro Menu 8 itens */
                        cadastroFornecedores boolean,
                        cadastroProdutos boolean,
                        cadastroCategoria boolean,
                        cadastroEnderecoEntregaCliente boolean,
                        cadastroEnderecoEntregaFornecedor boolean,
                        cadastroFornecedoresProduto boolean,
                        cadastroUnidades boolean, 
                        pedidoCliente boolean, /* Segundo Menu 2 itens */
                        pedidoFornecedor boolean, 
                        consultaClientes boolean, /* Terceiro Menu 6 itens */
			consultaFornecedor boolean,
                        consultaProduto boolean,
                        consultaCategoria boolean,
                        consultaEnderecoEntregaCliente boolean,
                        consultaEnderecoEntregaFornecedor boolean,
                        relatorioClientes boolean, /* Quarto Menu 5 itens */
                        relatorioFornecedor boolean,
                        relatorioProduto boolean,
                        relatorioCategoria boolean,
                        relatorioUnidades boolean);
                        
create table usuarios2 (id int auto_increment primary Key,
			nome varchar(50) unique,
                        senha varchar(50),
                        cadastroProdutosMP boolean, /* Primeiro menu inicialmente 2 itens*/
                        cadastroFornecedoresProdutoMP boolean,
                        pedidoClienteMP boolean, /* Segundo menu inicialmente 4 itens */
                        pedidoFornecedorMP boolean,
                        composicaoProduto boolean,
                        ordemProducao boolean,
                        consultaProdutoMP boolean, /* Terceiro menu inicialmente 1 item */
                        relatorioProdutoMP boolean /* Quarto menu inicialmente 1 item */);