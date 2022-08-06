package dao;

/**
 *
 * @author Ronaldo Rodrigues Godoi
 */

import model.Clientes;
import empresavi.BD;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Ronaldo R. Godoi
 */
public class CliDAO {
    
    public Clientes cliente;
    public BD bd;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String men, sql;
    public static final byte INCLUSAO = 1;
    public static final byte ALTERACAO = 2;
    public static final byte EXCLUSAO = 3;
    
    public CliDAO() {
        bd = BD.getInstance();
        cliente = new Clientes();
    }
    
    
    
    public boolean localizar() {
        sql = "select * from clientes where id_cgc_cpf = ?";
        try {
            statement = bd.connection.prepareStatement(sql);
            statement.setString(1, cliente.getId_cgc_cpf());
            resultSet = statement.executeQuery();
            resultSet.next();
            cliente.setId_cgc_cpf(resultSet.getString(1));
            cliente.setFisica_juridica(resultSet.getString(2));
            cliente.setNome_razao(resultSet.getString(3));
            cliente.setEmail(resultSet.getString(4));
            cliente.setTelefone(resultSet.getString(5));
            cliente.setCep(resultSet.getString(6));
            cliente.setEndereco(resultSet.getString(7));
            cliente.setNumero(resultSet.getString(8));
            cliente.setComplemento(resultSet.getString(9));
            cliente.setBairro(resultSet.getString(10));
            cliente.setCidade(resultSet.getString(11));
            cliente.setEstado(resultSet.getString(12));
            cliente.setData_cadastro("" + resultSet.getString(13));
            return true;
        } catch(SQLException erro) {
            System.out.println("erro: " + erro.toString() + sql + cliente.getId_cgc_cpf());
            return false;
        }
    }
    
    public String atualizar(int operacao) {
        men = "Operação realizada com sucesso!";
        try {
            if(operacao == INCLUSAO) {
                sql = "insert into clientes values (?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?)";                
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, cliente.getId_cgc_cpf());
                statement.setString(2, cliente.getFisica_juridica());
                statement.setString(3, cliente.getNome_razao());
                statement.setString(4, cliente.getEmail());
                statement.setString(5, cliente.getTelefone());
                statement.setString(6, cliente.getCep());
                statement.setString(7, cliente.getEndereco());
                statement.setString(8, cliente.getNumero());
                statement.setString(9, cliente.getComplemento());
                statement.setString(10, cliente.getBairro());
                statement.setString(11, cliente.getCidade());
                statement.setString(12, cliente.getEstado());
                statement.setString(13, cliente.getData_cadastro());
                
            } else if(operacao == ALTERACAO) {
                sql = "update clientes set fisica_juridica = ?,"
                        + " nome_razao = ?,"
                        + " email = ?,"
                        + " telefone = ?,"
                        + " cep = ?,"
                        + " endereco = ?,"
                        + " numero = ?,"
                        + " complemento = ?,"
                        + " bairro = ?,"
                        + " cidade = ?,"
                        + " estado = ?,"
                        + " data_cadastro = ? where id_cgc_cpf = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(13, cliente.getId_cgc_cpf());
                statement.setString(1, cliente.getFisica_juridica());
                statement.setString(2, cliente.getNome_razao());
                statement.setString(3, cliente.getEmail());
                statement.setString(4, cliente.getTelefone());
                statement.setString(5, cliente.getCep());
                statement.setString(6, cliente.getEndereco());
                statement.setString(7, cliente.getNumero());
                statement.setString(8, cliente.getComplemento());
                statement.setString(9, cliente.getBairro());
                statement.setString(10, cliente.getCidade());
                statement.setString(11, cliente.getEstado());
                statement.setString(12, cliente.getData_cadastro());
            } else if(operacao == EXCLUSAO) {
                sql = "delete from clientes where id_cgc_cpf = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, cliente.getId_cgc_cpf());
            }
            
            if(statement.executeUpdate() == 0) {
                men = "Falha na operação!";
            }
            
        } catch (SQLException erro) {
            men = "Falha na operação! " + erro.toString()+" "+sql;
        }
        
        return men;
        
    }
    
    public ArrayList<Clientes> listarClientes() {
        ArrayList<Clientes> listaClientes = new ArrayList<>();
        sql = "SELECT * FROM clientes ORDER BY nome_razao;";
        try {
            statement = bd.connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                this.cliente = new Clientes();
                this.cliente.setId_cgc_cpf(resultSet.getString(1));
                this.cliente.setFisica_juridica(resultSet.getString(2));
                this.cliente.setNome_razao(resultSet.getString(3));
                this.cliente.setEmail(resultSet.getString(4));
                this.cliente.setTelefone(resultSet.getString(5));
                this.cliente.setCep(resultSet.getString(6));
                this.cliente.setEndereco(resultSet.getString(7));
                this.cliente.setNumero(resultSet.getString(8));
                this.cliente.setComplemento(resultSet.getString(9));
                this.cliente.setBairro(resultSet.getString(10));
                this.cliente.setCidade(resultSet.getString(11));
                this.cliente.setEstado(resultSet.getString(12));
                this.cliente.setData_cadastro("" + resultSet.getString(13));
                listaClientes.add(cliente);
            }                 
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao acessar o arquivo!\n" + e);
        }
        return listaClientes;
    }
    
}

/*
Table: clientes
Columns:
id_cgc_cpf varchar(16) PK 
fisica_juridica varchar(1) 
nome_razao varchar(60) 
email varchar(80) 
telefone varchar(20) 
cep varchar(9) 
endereco varchar(60) 
numero int 
complemento varchar(40) 
bairro varchar(60) 
cidade varchar(40) 
estado varchar(2) 
data_cadastro datetime
*/