package consulta;

import dao.EndEntCliDAO;
import empresavi.BD;
import empresavi.GuiMenuPrincipal;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Cli_entregas;
import model.Cli_entregasEstendida;

/**
 *
 * @author Usuario
 */
public class ConsultaEnderecoEntregaCliente extends JPanel {

    EndEntCliDAO enderecosEntregaClientes;
    Cli_entregas enderecoEntregaCliente;
    ArrayList<Cli_entregasEstendida> listaEnderecosEntregaClientes;
    
    JTable tabelaEnderecosEntregaClientes;

    JScrollPane scrollEnderecosEntregaClientes;

    JButton btSair;

    JLabel lbTituloTela;

    public ConsultaEnderecoEntregaCliente() {
        inicializarComponentes();
        definirEventos();
    }

    private void inicializarComponentes() {
        enderecosEntregaClientes = new EndEntCliDAO();
        enderecoEntregaCliente = new Cli_entregas();
        listaEnderecosEntregaClientes = new ArrayList<>();
        
        setLayout(null);
        lbTituloTela = new JLabel("Tela de consulta de Endereços de entrega de Clientes");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(280, 50, 400, 50);

        btSair = new JButton(" Sair ");
        btSair.setBounds(850, 700, 100, 25);
        
        scrollEnderecosEntregaClientes = new JScrollPane();
        scrollEnderecosEntregaClientes.setBounds(50, 110, 850, 550);

        listaEnderecosEntregaClientes = enderecosEntregaClientes.listarEnderecosEntregaClientes();

        DefaultTableModel modeloTabela = new DefaultTableModel(
                new String[]{}, 0) {};
        modeloTabela.addColumn("Id Endereço");
        modeloTabela.addColumn("Id Cliente");
        modeloTabela.addColumn("Nome ou Razão do Cliente");
        modeloTabela.addColumn("Endereço de Entrega");
        modeloTabela.addColumn("Numero da Rua");
        modeloTabela.addColumn("Complemento");
        modeloTabela.addColumn("Bairro");
        modeloTabela.addColumn("Cidade");
        modeloTabela.addColumn("Telefone");
        
        tabelaEnderecosEntregaClientes = new JTable(modeloTabela);
        
        tabelaEnderecosEntregaClientes.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabelaEnderecosEntregaClientes.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabelaEnderecosEntregaClientes.getColumnModel().getColumn(2).setPreferredWidth(300);
        tabelaEnderecosEntregaClientes.getColumnModel().getColumn(3).setPreferredWidth(300);
        tabelaEnderecosEntregaClientes.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaEnderecosEntregaClientes.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabelaEnderecosEntregaClientes.getColumnModel().getColumn(6).setPreferredWidth(100);
        tabelaEnderecosEntregaClientes.getColumnModel().getColumn(7).setPreferredWidth(160);
        tabelaEnderecosEntregaClientes.getColumnModel().getColumn(8).setPreferredWidth(100);
        tabelaEnderecosEntregaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        tabelaEnderecosEntregaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel dtmEnderecosEntregaClientes = (DefaultTableModel) tabelaEnderecosEntregaClientes.getModel();
        int cont = listaEnderecosEntregaClientes.size();
        
        for(int i = 0; i < cont; i++) {
            String dados [] = new String[9];
            dados[0] = listaEnderecosEntregaClientes.get(i).getId();
            dados[1] = listaEnderecosEntregaClientes.get(i).getId_cliente();
            dados[2] = listaEnderecosEntregaClientes.get(i).getNome_razao();
            dados[3] = listaEnderecosEntregaClientes.get(i).getEndereco();
            dados[4] = listaEnderecosEntregaClientes.get(i).getNumero();
            dados[5] = listaEnderecosEntregaClientes.get(i).getComplemento();
            dados[6] = listaEnderecosEntregaClientes.get(i).getBairro();
            dados[7] = listaEnderecosEntregaClientes.get(i).getCidade();
            dados[8] = listaEnderecosEntregaClientes.get(i).getTelefone();
            dtmEnderecosEntregaClientes.addRow(dados);
        }
        scrollEnderecosEntregaClientes.setViewportView(tabelaEnderecosEntregaClientes);
        
        add(lbTituloTela);
        add(btSair);
        add(scrollEnderecosEntregaClientes);
    }

    private void definirEventos() {
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GuiMenuPrincipal.liberaMenu();
                BD.getInstance().close();
                setVisible(false);
            }
        });
    }
}