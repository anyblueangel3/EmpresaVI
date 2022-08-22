package consulta;

import dao.EndEntForDAO;
import empresavi.BD;
import empresavi.GuiMenuPrincipal;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.For_entregasEstendida;

/**
 *
 * @author Usuario
 */
public class ConsultaEnderecoEntregaFornecedor extends JPanel {

    EndEntForDAO enderecosEntregaFornecedores;
    For_entregasEstendida enderecoEntregaFornecedor;
    ArrayList<For_entregasEstendida> listaEnderecosEntregaFornecedores;
    
    JTable tabelaEnderecosEntregaFornecedores;

    JScrollPane scrollEnderecosEntregaFornecedores;

    JButton btSair;

    JLabel lbTituloTela;

    public ConsultaEnderecoEntregaFornecedor() {
        inicializarComponentes();
        definirEventos();
    }

    private void inicializarComponentes() {
        enderecosEntregaFornecedores = new EndEntForDAO();
        enderecoEntregaFornecedor = new For_entregasEstendida();
        listaEnderecosEntregaFornecedores = new ArrayList<>();
        
        setLayout(null);
        lbTituloTela = new JLabel("Tela de consulta de Endereços de entrega de Fornecedores");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(280, 50, 400, 50);

        btSair = new JButton(" Sair ");
        btSair.setBounds(850, 700, 100, 25);
        
        scrollEnderecosEntregaFornecedores = new JScrollPane();
        scrollEnderecosEntregaFornecedores.setBounds(50, 110, 850, 550);

        listaEnderecosEntregaFornecedores = enderecosEntregaFornecedores.listarEnderecosEntregaFornecedores();

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
        
        tabelaEnderecosEntregaFornecedores = new JTable(modeloTabela);
        
        tabelaEnderecosEntregaFornecedores.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabelaEnderecosEntregaFornecedores.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabelaEnderecosEntregaFornecedores.getColumnModel().getColumn(2).setPreferredWidth(300);
        tabelaEnderecosEntregaFornecedores.getColumnModel().getColumn(3).setPreferredWidth(300);
        tabelaEnderecosEntregaFornecedores.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaEnderecosEntregaFornecedores.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabelaEnderecosEntregaFornecedores.getColumnModel().getColumn(6).setPreferredWidth(100);
        tabelaEnderecosEntregaFornecedores.getColumnModel().getColumn(7).setPreferredWidth(160);
        tabelaEnderecosEntregaFornecedores.getColumnModel().getColumn(8).setPreferredWidth(100);
        tabelaEnderecosEntregaFornecedores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        tabelaEnderecosEntregaFornecedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel dtmEnderecosEntregaFornecedores = (DefaultTableModel) tabelaEnderecosEntregaFornecedores.getModel();
        int cont = listaEnderecosEntregaFornecedores.size();
        
        for(int i = 0; i < cont; i++) {
            String dados [] = new String[9];
            dados[0] = listaEnderecosEntregaFornecedores.get(i).getId();
            dados[1] = listaEnderecosEntregaFornecedores.get(i).getId_fornecedor();
            dados[2] = listaEnderecosEntregaFornecedores.get(i).getNome_razao();
            dados[3] = listaEnderecosEntregaFornecedores.get(i).getEndereco();
            dados[4] = listaEnderecosEntregaFornecedores.get(i).getNumero();
            dados[5] = listaEnderecosEntregaFornecedores.get(i).getComplemento();
            dados[6] = listaEnderecosEntregaFornecedores.get(i).getBairro();
            dados[7] = listaEnderecosEntregaFornecedores.get(i).getCidade();
            dados[8] = listaEnderecosEntregaFornecedores.get(i).getTelefone();
            dtmEnderecosEntregaFornecedores.addRow(dados);
        }
        scrollEnderecosEntregaFornecedores.setViewportView(tabelaEnderecosEntregaFornecedores);
        
        add(lbTituloTela);
        add(btSair);
        add(scrollEnderecosEntregaFornecedores);
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