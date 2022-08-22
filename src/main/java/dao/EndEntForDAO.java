package dao;

import model.For_entregas;
import empresavi.BD;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.For_entregasEstendida;

/**
 *
 * @author Ronaldo Rodrigues Godoi
 */
public class EndEntForDAO {
    
    public For_entregas for_entrega;
    public For_entregasEstendida for_entrega2;
    public BD bd;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String men, sql;
    public static final byte INCLUSAO = 1;
    public static final byte ALTERACAO = 2;
    public static final byte EXCLUSAO = 3;
    
    public EndEntForDAO() {
        bd = BD.getInstance();
        for_entrega = new For_entregas();
    }
    
    public boolean localizar() {
        sql = "select * from for_entrega where id = ?";
        try {
            statement = bd.connection.prepareStatement(sql);
            statement.setString(1, for_entrega.getId());
            resultSet = statement.executeQuery();
            resultSet.next();
            for_entrega.setId_fornecedor(resultSet.getString(2));
            for_entrega.setTelefone(resultSet.getString(3));
            for_entrega.setCep(resultSet.getString(4));
            for_entrega.setEndereco(resultSet.getString(5));
            for_entrega.setNumero(resultSet.getString(6));
            for_entrega.setComplemento(resultSet.getString(7));
            for_entrega.setBairro(resultSet.getString(8));
            for_entrega.setCidade(resultSet.getString(9));
            for_entrega.setEstado(resultSet.getString(10));
            return true;
        } catch(SQLException erro) {
            System.out.println("erro: " + erro.toString() + sql + for_entrega.getId());
            return false;
        }
    }
    
    public String atualizar(int operacao) {
        men = "Operação realizada com sucesso!";
        try {
            if(operacao == INCLUSAO) {
                sql = "insert into for_entrega values (?, ?, ?, ?, ?, ?, ? , ?, ?, ?)";                
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, for_entrega.getId());
                statement.setString(2, for_entrega.getId_fornecedor());
                statement.setString(3, for_entrega.getTelefone());
                statement.setString(4, for_entrega.getCep());
                statement.setString(5, for_entrega.getEndereco());
                statement.setString(6, for_entrega.getNumero());
                statement.setString(7, for_entrega.getComplemento());
                statement.setString(8, for_entrega.getBairro());
                statement.setString(9, for_entrega.getCidade());
                statement.setString(10, for_entrega.getEstado());    
            } else if(operacao == ALTERACAO) {
                sql = "update for_entrega set id_fornecedor = ?,"
                        + " telefone = ?,"
                        + " cep = ?,"
                        + " endereco = ?,"
                        + " numero = ?,"
                        + " complemento = ?,"
                        + " bairro = ?,"
                        + " cidade = ?,"
                        + " estado = ? where id = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(10, for_entrega.getId());
                statement.setString(1, for_entrega.getId_fornecedor());
                statement.setString(2, for_entrega.getTelefone());
                statement.setString(3, for_entrega.getCep());
                statement.setString(4, for_entrega.getEndereco());
                statement.setString(5, for_entrega.getNumero());
                statement.setString(6, for_entrega.getComplemento());
                statement.setString(7, for_entrega.getBairro());
                statement.setString(8, for_entrega.getCidade());
                statement.setString(9, for_entrega.getEstado());
            } else if(operacao == EXCLUSAO) {
                sql = "delete from for_entrega where id = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, for_entrega.getId());
            }
            
            if(statement.executeUpdate() == 0) {
                men = "Falha na operação!";
            }
            
        } catch (SQLException erro) {
            men = "Falha na operação! " + erro.toString()+" "+sql;
        }
        
        return men;
        
    }
    
    public ArrayList<For_entregasEstendida> listarEnderecosEntregaFornecedores() {
        ArrayList<For_entregasEstendida> listaEnderecosEntregaFornecedores = new ArrayList<>();
        sql = "SELECT e.id, "
                + "e.id_fornecedor, "
                + "e.telefone, "
                + "e.cep, "
                + "e.endereco, "
                + "e.numero, "
                + "e.complemento, "
                + "e.bairro, "
                + "e.cidade, "
                + "e.estado, "
                + "f.nome_razao "
                + "FROM for_entrega as e JOIN fornecedores as f "
                + "ON e.id_fornecedor = f.id_cgc_cpf ORDER BY f.nome_razao;";
        try {
            statement = bd.connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                this.for_entrega2 = new For_entregasEstendida();
                this.for_entrega2.setId(resultSet.getString(1));
                this.for_entrega2.setId_fornecedor(resultSet.getString(2));
                this.for_entrega2.setTelefone(resultSet.getString(3));
                this.for_entrega2.setCep(resultSet.getString(4));
                this.for_entrega2.setEndereco(resultSet.getString(5));
                this.for_entrega2.setNumero(resultSet.getString(6));
                this.for_entrega2.setComplemento(resultSet.getString(7));
                this.for_entrega2.setBairro(resultSet.getString(8));
                this.for_entrega2.setCidade(resultSet.getString(9));
                this.for_entrega2.setEstado(resultSet.getString(10));
                this.for_entrega2.setNome_razao(resultSet.getString(11));
                listaEnderecosEntregaFornecedores.add(this.for_entrega2);
            }                 
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao acessar o arquivo!\n" + e);
        }
        return listaEnderecosEntregaFornecedores;
    }
    
}

/*
Table: cli_entrega
Columns:
id varchar(10) PK 
id_fornecedor varchar(16) 
telefone varchar(20) 
cep varchar(9) 
endereco varchar(60) 
numero int 
complemento varchar(40) 
bairro varchar(60) 
cidade varchar(40) 
estado varchar(2)
*/
