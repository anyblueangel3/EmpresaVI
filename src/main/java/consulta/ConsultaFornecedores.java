package consulta;

import dao.EndEntForDAO;
import dao.ForDAO;
import empresavi.BD;
import empresavi.GuiMenuPrincipal;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.For_entregas;
import model.Fornecedores;

/**
 *
 * @author Usuario
 */
public class ConsultaFornecedores extends JPanel {

    ForDAO fornecedores;
    Fornecedores fornecedor;
    ArrayList<Fornecedores> listaFornecedores;
    EndEntForDAO enderecosEntregaFornecedores;
    For_entregas for_entrega;
    ArrayList<EndEntForDAO> listaEntregaFornecedores;

    JTable tabelaFornecedores, tabelaEntregaFornecedores;

    JScrollPane scrollFornecedores, scrollEntregaFornecedores;

    JButton btSair;

    JLabel lbTituloTela;

    public ConsultaFornecedores() {
        inicializarComponentes();
        definirEventos();
    }

    private void inicializarComponentes() {
        fornecedores = new ForDAO();
        fornecedor = new Fornecedores();
        listaFornecedores = new ArrayList<>();
        enderecosEntregaFornecedores = new EndEntForDAO();
        for_entrega = new For_entregas();
        listaEntregaFornecedores = new ArrayList<>();
        setLayout(null);
        lbTituloTela = new JLabel("Tela de consulta de Fornecedores");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(320, 50, 400, 50);

        btSair = new JButton(" Sair ");
        btSair.setBounds(850, 700, 100, 25);
        scrollFornecedores = new JScrollPane();
        scrollFornecedores.setBounds(50, 110, 850, 550);

        listaFornecedores = fornecedores.listarFornecedores();

        DefaultTableModel modeloTabela = new DefaultTableModel(
                new String[]{}, 0) {};
        modeloTabela.addColumn("CGC ou CPF");
        modeloTabela.addColumn("Nome ou Razão");
        modeloTabela.addColumn("Cidade");
        modeloTabela.addColumn("Telefone");
        
        tabelaFornecedores = new JTable(modeloTabela);
        tabelaFornecedores.getColumnModel().getColumn(0).setPreferredWidth(15);
        tabelaFornecedores.getColumnModel().getColumn(1).setPreferredWidth(75);
        tabelaFornecedores.getColumnModel().getColumn(2).setPreferredWidth(25);
        tabelaFornecedores.getColumnModel().getColumn(3).setPreferredWidth(25);

        tabelaFornecedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel dtmClientes = (DefaultTableModel) tabelaFornecedores.getModel();
        int cont = listaFornecedores.size();
        
        for(int i = 0; i < cont; i++) {
            String dados [] = new String[4];
            dados[0] = listaFornecedores.get(i).getId_cgc_cpf();
            dados[1] = listaFornecedores.get(i).getNome_razao();
            dados[2] = listaFornecedores.get(i).getCidade();
            dados[3] = listaFornecedores.get(i).getTelefone();
            dtmClientes.addRow(dados);
        }
        scrollFornecedores.setViewportView(tabelaFornecedores);
        
        add(lbTituloTela);
        add(btSair);
        add(scrollFornecedores);
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