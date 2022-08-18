package consulta;

import dao.CliDAO;
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
import model.Clientes;

/**
 *
 * @author Usuario
 */
public class ConsultaClientes extends JPanel {

    CliDAO clientes;
    Clientes cliente;
    ArrayList<Clientes> listaClientes;
    EndEntCliDAO enderecosEntregaCliente;
    Cli_entregas cli_entrega;
    ArrayList<EndEntCliDAO> listaEntregaClientes;

    JTable tabelaClientes, tabelaEntregaClientes;

    JScrollPane scrollClientes, scrollEntregaClientes;

    JButton btSair;

    JLabel lbTituloTela;

    public ConsultaClientes() {
        inicializarComponentes();
        definirEventos();
    }

    private void inicializarComponentes() {
        clientes = new CliDAO();
        cliente = new Clientes();
        listaClientes = new ArrayList<>();
        enderecosEntregaCliente = new EndEntCliDAO();
        cli_entrega = new Cli_entregas();
        listaEntregaClientes = new ArrayList<>();
        setLayout(null);
        lbTituloTela = new JLabel("Tela de consulta de Clientes");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(320, 50, 400, 50);

        btSair = new JButton(" Sair ");
        btSair.setBounds(850, 700, 100, 25);
        scrollClientes = new JScrollPane();
        scrollClientes.setBounds(50, 110, 850, 550);

        listaClientes = clientes.listarClientes();

        DefaultTableModel modeloTabela = new DefaultTableModel(
                new String[]{}, 0) {};
        modeloTabela.addColumn("CGC ou CPF");
        modeloTabela.addColumn("Nome ou Razão");
        modeloTabela.addColumn("Cidade");
        modeloTabela.addColumn("Telefone");
        
        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(15);
        tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(75);
        tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(25);
        tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(25);

        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel dtmClientes = (DefaultTableModel) tabelaClientes.getModel();
        int cont = listaClientes.size();
        
        for(int i = 0; i < cont; i++) {
            String dados [] = new String[4];
            dados[0] = listaClientes.get(i).getId_cgc_cpf();
            dados[1] = listaClientes.get(i).getNome_razao();
            dados[2] = listaClientes.get(i).getCidade();
            dados[3] = listaClientes.get(i).getTelefone();
            dtmClientes.addRow(dados);
        }
        scrollClientes.setViewportView(tabelaClientes);
        
        add(lbTituloTela);
        add(btSair);
        add(scrollClientes);
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