package imprime;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import dao.UnidadesDAO;
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
import model.Unidades;

/**
 *
 * @author Ronaldo Rodrigues Godoi
 */
public class ImprimeUnidades extends JPanel {

    UnidadesDAO unidades;
    JLabel lbTituloTela, lbImprimindo;
    JButton btImprimir, btSair;
    Unidades unidade;
    ArrayList<Unidades> listaUnidades = new ArrayList<>();
    Document documentPDF = new Document(PageSize.A4, 25, 25, 50, 50);

    public ImprimeUnidades() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {

        setLayout(null);

        lbTituloTela = new JLabel(" Impressão da lista de Unidades ");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(320, 150, 400, 50);

        lbImprimindo = new JLabel("");
        lbImprimindo.setBounds(350, 350, 200, 25);

        btImprimir = new JButton(" Imprimir Relatório de Unidades ");
        btImprimir.setBounds(340, 520, 300, 25);

        btSair = new JButton(" Sair ");
        btSair.setBounds(400, 600, 200, 25);

        add(lbTituloTela);
        add(lbImprimindo);
        add(btImprimir);
        add(btSair);

        unidades = new UnidadesDAO();
    }

    public void definirEventos() {

        btImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imprimirRelatorioUnidades();
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

    public void imprimirRelatorioUnidades() {
        listaUnidades = new ArrayList<>();
        listaUnidades = unidades.listarUnidades();
        if (listaUnidades == null) {
            JOptionPane.showMessageDialog(null, "Cadastro de Unidades está vazio!");
            return;
        }
        try {
            PdfWriter.getInstance(documentPDF, new FileOutputStream("ListaUnidades.pdf"));
            adicionarPaginacao();
            documentPDF.open();
            geraCabecalho();
            for (int i = 0; i < listaUnidades.size(); i++) {
                Paragraph unidadeAtual = new Paragraph("Id Unidade: " + listaUnidades.get(i).getId()
                        + " Descrição: " + listaUnidades.get(i).getDescricao()
                        + " Data Cadastro: " + listaUnidades.get(i).getData_cadastro());
                documentPDF.add(unidadeAtual);
                documentPDF.add(new Paragraph(" "));
                lbImprimindo.setText("Imprimindo registro: " + i);
                if((i+1)%24 == 0 && (listaUnidades.size()-1) != i) {
                    documentPDF.newPage();
                    geraCabecalho();
                }
            }
            lbImprimindo.setText("Relatório finalizado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de Unidades!\n" + e);
        }

        documentPDF.close();

    }

    private void geraCabecalho() {
        Paragraph cabecalho = new Paragraph(
                "Relatório de Unidades\n"
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