package consulta;

import dao.CatDAO;
import empresavi.BD;
import empresavi.GuiMenuPrincipal;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Categorias;

/**
 *
 * @author Usuario
 */
public class ConsultaCategorias extends JPanel {

    CatDAO categorias;
    Categorias categoria;
    ArrayList<Categorias> listaCategorias;
    
    JTable tabelaCategorias;

    JScrollPane scrollCategorias;

    JButton btSair;

    JLabel lbTituloTela;

    public ConsultaCategorias() {
        inicializarComponentes();
        definirEventos();
    }

    private void inicializarComponentes() {
        categorias = new CatDAO();
        categoria = new Categorias();
        listaCategorias = new ArrayList<>();
        
        setLayout(null);
        lbTituloTela = new JLabel("Tela de consulta de Categorias");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(320, 50, 400, 50);

        btSair = new JButton(" Sair ");
        btSair.setBounds(850, 700, 100, 25);
        
        scrollCategorias = new JScrollPane();
        scrollCategorias.setBounds(50, 110, 850, 550);

        listaCategorias = categorias.listarCategorias();

        DefaultTableModel modeloTabela = new DefaultTableModel(
                new String[]{}, 0) {};
        modeloTabela.addColumn("Id Categoria");
        modeloTabela.addColumn("Descrição");
        modeloTabela.addColumn("Data de Cadastro");
        
        tabelaCategorias = new JTable(modeloTabela);
        tabelaCategorias.getColumnModel().getColumn(0).setPreferredWidth(15);
        tabelaCategorias.getColumnModel().getColumn(1).setPreferredWidth(75);
        tabelaCategorias.getColumnModel().getColumn(2).setPreferredWidth(25);
        
        tabelaCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel dtmCategorias = (DefaultTableModel) tabelaCategorias.getModel();
        int cont = listaCategorias.size();
        
        for(int i = 0; i < cont; i++) {
            String dados [] = new String[3];
            dados[0] = listaCategorias.get(i).getId();
            dados[1] = listaCategorias.get(i).getDescricao();
            dados[2] = String.valueOf(listaCategorias.get(i).getData_cadastro());
            dtmCategorias.addRow(dados);
        }
        scrollCategorias.setViewportView(tabelaCategorias);
        
        add(lbTituloTela);
        add(btSair);
        add(scrollCategorias);
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