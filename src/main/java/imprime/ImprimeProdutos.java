package imprime;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import dao.ProDAO;
import empresavi.BD;
import empresavi.GuiMenuPrincipal;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Produtos;

/**
 *
 * @author Ronaldo Rodrigues Godoi
 */
public class ImprimeProdutos extends JPanel {
    
    ProDAO produtos;
    Produtos produto;
    ArrayList<Produtos> listaProdutos = new ArrayList<>();
    JLabel lbTituloTela, lbImprimindo;
    JButton btImprimir, btSair;
    Document documentPDF = new Document(PageSize.A4, 25, 25, 50, 50);
    
    public ImprimeProdutos() {
        inicializarComponentes();
        definirEventos();
    }
    
    private void inicializarComponentes() {
        setLayout(null);

        lbTituloTela = new JLabel(" Impressão da lista de Produtos ");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(300, 150, 400, 50);

        lbImprimindo = new JLabel("");
        lbImprimindo.setBounds(350, 350, 200, 25);

        btImprimir = new JButton(" Imprimir Relatório de Produtos ");
        btImprimir.setBounds(340, 520, 260, 25);

        btSair = new JButton(" Sair ");
        btSair.setBounds(360, 600, 200, 25);

        add(lbTituloTela);
        add(lbImprimindo);
        add(btImprimir);
        add(btSair);

        produtos = new ProDAO();
    }
    
    private void definirEventos() {

        btImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imprimirRelatorioProdutos();
            }
        });

        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GuiMenuPrincipal.liberaMenu();
                BD.getInstance().close();
                setVisible(false);
            }
        });
        
    }
    
        public void imprimirRelatorioProdutos() {
        listaProdutos = new ArrayList<>();
        listaProdutos = produtos.listarProdutos();
        if (listaProdutos == null) {
            JOptionPane.showMessageDialog(null, "Cadastro de Fornecedores está vazio!");
            return;
        }
        try {
            PdfWriter.getInstance(documentPDF, new FileOutputStream("ListaProdutos.pdf"));
            adicionarPaginacao();
            documentPDF.open();
            geraCabecalho();
            for (int i = 0; i < listaProdutos.size(); i++) {
                Paragraph produtoAtual = new Paragraph("Id Produto: " + listaProdutos.get(i).getId()
                        + " Descrição: " + listaProdutos.get(i).getDescricao() + "\n"
                        + "Data de cadastro: " + listaProdutos.get(i).getData_cadastro()
                        + " Preço última compra: " + listaProdutos.get(i).getPreco_ultima_compra()
                        + "\n Quantidade em Estoque: " + listaProdutos.get(i).getQuantidade() 
                        + " Preço de venda: " + listaProdutos.get(i).getPreco_venda());
                documentPDF.add(produtoAtual);
                documentPDF.add(new Paragraph(" "));
                lbImprimindo.setText("Imprimindo registro: " + i);
                if((i+1)%9 == 0 && (listaProdutos.size()-1) != i) {
                    documentPDF.newPage();
                    geraCabecalho();
                }
            }
            lbImprimindo.setText("Relatório finalizado.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de Fornecedores!\n" + e);
        }
        documentPDF.close();
    }
        
    private void geraCabecalho() {
        Paragraph cabecalho = new Paragraph(
                "Relatório de Produtos\n"
                + "_______________________________________________________________");
        cabecalho.setAlignment(Element.ALIGN_CENTER);
        documentPDF.add(cabecalho);
    }

    private void adicionarPaginacao() {
        HeaderFooter paginacao = new HeaderFooter(new Phrase("Pág."), true);
        paginacao.setAlignment(Element.ALIGN_RIGHT);
        //paginacao.setBorder(Rectangle.NO_BORDER);
        documentPDF.setHeader(paginacao);
    }
    
}
