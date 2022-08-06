package imprime;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import dao.CliDAO;
import dao.ForDAO;
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
import model.Clientes;
import model.Fornecedores;

/**
 *
 * @author Ronaldo Rodrigues Godoi
 */
public class ImprimeFornecedores extends JPanel {

    ForDAO fornecedores;
    JLabel lbTituloTela, lbImprimindo;
    JButton btImprimir, btSair;
    Fornecedores fornecedor;
    ArrayList<Fornecedores> listaFornecedores = new ArrayList<>();
    Document documentPDF = new Document(PageSize.A4, 25, 25, 50, 50);

    public ImprimeFornecedores() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {

        setLayout(null);

        lbTituloTela = new JLabel(" Impressão da lista de Fornecedores ");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(300, 150, 400, 50);

        lbImprimindo = new JLabel("");
        lbImprimindo.setBounds(350, 350, 200, 25);

        btImprimir = new JButton(" Imprimir Relatório de Fornecedores ");
        btImprimir.setBounds(340, 520, 260, 25);

        btSair = new JButton(" Sair ");
        btSair.setBounds(360, 600, 200, 25);

        add(lbTituloTela);
        add(lbImprimindo);
        add(btImprimir);
        add(btSair);

        fornecedores = new ForDAO();
    }

    public void definirEventos() {

        btImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imprimirRelatorioFornecedores();
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

    public void imprimirRelatorioFornecedores() {
        listaFornecedores = new ArrayList<>();
        listaFornecedores = fornecedores.listarFornecedores();
        if (listaFornecedores == null) {
            JOptionPane.showMessageDialog(null, "Cadastro de Fornecedores está vazio!");
            return;
        }
        try {
            PdfWriter.getInstance(documentPDF, new FileOutputStream("ListaFornecedores.pdf"));
            adicionarPaginacao();
            documentPDF.open();
            geraCabecalho();
            for (int i = 0; i < listaFornecedores.size(); i++) {
                Paragraph fornecedorAtual = new Paragraph("CGC ou CPF: " + listaFornecedores.get(i).getId_cgc_cpf()
                        + " Nome ou razão social: " + listaFornecedores.get(i).getNome_razao() + "\n"
                        + "Endereço: " + listaFornecedores.get(i).getEndereco()
                        + " Número: " + listaFornecedores.get(i).getNumero()
                        + " Cidade: " + listaFornecedores.get(i).getCidade());
                documentPDF.add(fornecedorAtual);
                documentPDF.add(new Paragraph(" "));
                lbImprimindo.setText("Imprimindo registro: " + i);
                if((i+1)%3 == 0 && (listaFornecedores.size()-1) != i) {
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
                "Relatório de Fornecedores\n"
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
