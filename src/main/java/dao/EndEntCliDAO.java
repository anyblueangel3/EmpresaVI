package dao;

import model.Cli_entregas;
import empresavi.BD;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Cli_entregasEstendida;

/**
 *
 * @author Ronaldo Rodrigues Godoi
 */
public class EndEntCliDAO {
    
    public Cli_entregas cli_entrega;
    public Cli_entregasEstendida cli_entrega2;
    public BD bd;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String men, sql;
    public static final byte INCLUSAO = 1;
    public static final byte ALTERACAO = 2;
    public static final byte EXCLUSAO = 3;
    
    public EndEntCliDAO() {
        bd = BD.getInstance();
        cli_entrega = new Cli_entregas();
    }
    
    public boolean localizar() {
        sql = "select * from cli_entrega where id = ?";
        try {
            statement = bd.connection.prepareStatement(sql);
            statement.setString(1, cli_entrega.getId());
            resultSet = statement.executeQuery();
            resultSet.next();
            cli_entrega.setId_cliente(resultSet.getString(2));
            cli_entrega.setTelefone(resultSet.getString(3));
            cli_entrega.setCep(resultSet.getString(4));
            cli_entrega.setEndereco(resultSet.getString(5));
            cli_entrega.setNumero(resultSet.getString(6));
            cli_entrega.setComplemento(resultSet.getString(7));
            cli_entrega.setBairro(resultSet.getString(8));
            cli_entrega.setCidade(resultSet.getString(9));
            cli_entrega.setEstado(resultSet.getString(10));
            return true;
        } catch(SQLException erro) {
            System.out.println("erro: " + erro.toString() + sql + cli_entrega.getId());
            return false;
        }
    }
    
    public String atualizar(int operacao) {
        men = "Operação realizada com sucesso!";
        try {
            if(operacao == INCLUSAO) {
                sql = "insert into cli_entrega values (?, ?, ?, ?, ?, ?, ? , ?, ?, ?)";                
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, cli_entrega.getId());
                statement.setString(2, cli_entrega.getId_cliente());
                statement.setString(3, cli_entrega.getTelefone());
                statement.setString(4, cli_entrega.getCep());
                statement.setString(5, cli_entrega.getEndereco());
                statement.setString(6, cli_entrega.getNumero());
                statement.setString(7, cli_entrega.getComplemento());
                statement.setString(8, cli_entrega.getBairro());
                statement.setString(9, cli_entrega.getCidade());
                statement.setString(10, cli_entrega.getEstado());    
                System.out.println("Id: " + cli_entrega.getId() +
                        " Id cliente: " + cli_entrega.getId_cliente() + 
                        " Telefone: " + cli_entrega.getTelefone());
            } else if(operacao == ALTERACAO) {
                sql = "update cli_entrega set id_cliente = ?,"
                        + " telefone = ?,"
                        + " cep = ?,"
                        + " endereco = ?,"
                        + " numero = ?,"
                        + " complemento = ?,"
                        + " bairro = ?,"
                        + " cidade = ?,"
                        + " estado = ? where id = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(10, cli_entrega.getId());
                statement.setString(1, cli_entrega.getId_cliente());
                statement.setString(2, cli_entrega.getTelefone());
                statement.setString(3, cli_entrega.getCep());
                statement.setString(4, cli_entrega.getEndereco());
                statement.setString(5, cli_entrega.getNumero());
                statement.setString(6, cli_entrega.getComplemento());
                statement.setString(7, cli_entrega.getBairro());
                statement.setString(8, cli_entrega.getCidade());
                statement.setString(9, cli_entrega.getEstado());
            } else if(operacao == EXCLUSAO) {
                sql = "delete from cli_entrega where id = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, cli_entrega.getId());
            }
            
            if(statement.executeUpdate() == 0) {
                men = "Falha na operação!";
            }
            
        } catch (SQLException erro) {
            men = "Falha na operação! " + erro.toString()+" "+sql;
        }
        
        return men;
        
    }

    public ArrayList<Cli_entregasEstendida> listarEnderecosEntregaClientes() {
        ArrayList<Cli_entregasEstendida> listaEnderecosEntregaClientes = new ArrayList<>();
        sql = "SELECT e.id, "
                + "e.id_cliente, "
                + "e.telefone, "
                + "e.cep, "
                + "e.endereco, "
                + "e.numero, "
                + "e.complemento, "
                + "e.bairro, "
                + "e.cidade, "
                + "e.estado, "
                + "c.nome_razao "
                + "FROM cli_entrega as e JOIN clientes as c "
                + "ON e.id_cliente = c.id_cgc_cpf ORDER BY c.nome_razao;";
        try {
            statement = bd.connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                this.cli_entrega2 = new Cli_entregasEstendida();
                this.cli_entrega2.setId(resultSet.getString(1));
                this.cli_entrega2.setId_cliente(resultSet.getString(2));
                this.cli_entrega2.setTelefone(resultSet.getString(3));
                this.cli_entrega2.setCep(resultSet.getString(4));
                this.cli_entrega2.setEndereco(resultSet.getString(5));
                this.cli_entrega2.setNumero(resultSet.getString(6));
                this.cli_entrega2.setComplemento(resultSet.getString(7));
                this.cli_entrega2.setBairro(resultSet.getString(8));
                this.cli_entrega2.setCidade(resultSet.getString(9));
                this.cli_entrega2.setEstado(resultSet.getString(10));
                this.cli_entrega2.setNome_razao(resultSet.getString(11));
                listaEnderecosEntregaClientes.add(this.cli_entrega2);
            }                 
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao acessar o arquivo!\n" + e);
        }
        return listaEnderecosEntregaClientes;
    }
}
/*
Table: cli_entrega
Columns:
id varchar(10) PK 
id_cliente varchar(16) 
telefone varchar(20) 
cep varchar(9) 
endereco varchar(60) 
numero int 
complemento varchar(40) 
bairro varchar(60) 
cidade varchar(40) 
estado varchar(2)
*/
