package consulta;

import dao.ProDAO;
import empresavi.BD;
import empresavi.GuiMenuPrincipal;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Produtos;

/**
 *
 * @author Usuario
 */
public class ConsultaProdutos extends JPanel {

    ProDAO produtos;
    Produtos produto;
    ArrayList<Produtos> listaProdutos;
    
    JTable tabelaProdutos;

    JScrollPane scrollProdutos;

    JButton btSair;

    JLabel lbTituloTela;

    public ConsultaProdutos() {
        inicializarComponentes();
        definirEventos();
    }

    private void inicializarComponentes() {
        produtos = new ProDAO();
        produto = new Produtos();
        listaProdutos = new ArrayList<>();
        
        setLayout(null);
        lbTituloTela = new JLabel("Tela de consulta de Produtos");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(320, 50, 400, 50);

        btSair = new JButton(" Sair ");
        btSair.setBounds(850, 700, 100, 25);
        scrollProdutos = new JScrollPane();
        scrollProdutos.setBounds(50, 110, 850, 550);

        listaProdutos = produtos.listarProdutos();

        DefaultTableModel modeloTabela = new DefaultTableModel(
                new String[]{}, 0) {};
        modeloTabela.addColumn("Id Produto");
        modeloTabela.addColumn("Descrição");
        modeloTabela.addColumn("Quantidade");
        modeloTabela.addColumn("Preço de Venda");
        modeloTabela.addColumn("Preço de Compra");
        
        tabelaProdutos = new JTable(modeloTabela);
        tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(15);
        tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(75);
        tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(25);
        tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(25);
        tabelaProdutos.getColumnModel().getColumn(4).setPreferredWidth(25);

        tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel dtmProdutos = (DefaultTableModel) tabelaProdutos.getModel();
        int cont = listaProdutos.size();
        
        for(int i = 0; i < cont; i++) {
            String dados [] = new String[5];
            dados[0] = listaProdutos.get(i).getId();
            dados[1] = listaProdutos.get(i).getDescricao();
            dados[2] = String.valueOf(listaProdutos.get(i).getQuantidade());
            dados[3] = String.valueOf(listaProdutos.get(i).getPreco_venda());
            dados[4] = String.valueOf(listaProdutos.get(i).getPreco_ultima_compra());
            dtmProdutos.addRow(dados);
        }
        scrollProdutos.setViewportView(tabelaProdutos);
        
        add(lbTituloTela);
        add(btSair);
        add(scrollProdutos);
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