package imprime;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import dao.EndEntForDAO;
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
import model.For_entregasEstendida;

/**
 *
 * @author Ronaldo Rodrigues Godoi
 */
public class ImprimeEndEntFor extends JPanel {

    EndEntForDAO enderecosEntregaFor;
    JLabel lbTituloTela, lbImprimindo;
    JButton btImprimir, btSair;
    For_entregasEstendida For_entrega;
    ArrayList<For_entregasEstendida> listaEnderecosEntregaFor = new ArrayList<>();
    Document documentPDF = new Document(PageSize.A4, 25, 25, 50, 50);

    public ImprimeEndEntFor() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {

        setLayout(null);

        lbTituloTela = new JLabel(" Impressão da lista de Endereços de entrega de Fornecedores ");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(150, 150, 700, 50);

        lbImprimindo = new JLabel("");
        lbImprimindo.setBounds(350, 350, 200, 25);

        btImprimir = new JButton(" Imprimir Relatório de Endereço Entrega ");
        btImprimir.setBounds(340, 520, 300, 25);

        btSair = new JButton(" Sair ");
        btSair.setBounds(400, 600, 200, 25);

        add(lbTituloTela);
        add(lbImprimindo);
        add(btImprimir);
        add(btSair);

        enderecosEntregaFor = new EndEntForDAO();
    }

    public void definirEventos() {

        btImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imprimirRelatorioEndEntFor();
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

    public void imprimirRelatorioEndEntFor() {
        listaEnderecosEntregaFor = new ArrayList<>();
        listaEnderecosEntregaFor = enderecosEntregaFor.listarEnderecosEntregaFornecedores();
        if (listaEnderecosEntregaFor == null) {
            JOptionPane.showMessageDialog(null, "Cadastro de Endereços Entrega de Fornecedor está vazio!");
            return;
        }
        try {
            PdfWriter.getInstance(documentPDF, new FileOutputStream("ListaEndEntFor.pdf"));
            adicionarPaginacao();
            documentPDF.open();
            geraCabecalho();
            int cont = listaEnderecosEntregaFor.size();
            for (int i = 0; i < cont; i++) {
                Paragraph EndEntForAtual = new Paragraph("Id End.Entrega: " + listaEnderecosEntregaFor.get(i).getId()
                        + " Id Cliente: " + listaEnderecosEntregaFor.get(i).getId_fornecedor()
                        + " Nome ou razão: " + listaEnderecosEntregaFor.get(i).getNome_razao()
                        + "\n Endereço de entrega: " + listaEnderecosEntregaFor.get(i).getEndereco()
                        + " Número: " + listaEnderecosEntregaFor.get(i).getNumero()
                        + "\n Telefone: " + listaEnderecosEntregaFor.get(i).getTelefone());
                documentPDF.add(EndEntForAtual);
                documentPDF.add(new Paragraph(" "));
                lbImprimindo.setText("Imprimindo registro: " + i);
                if((i+1)%6 == 0 && (cont - 1) != i) {
                    documentPDF.newPage();
                    geraCabecalho();
                }
            }
            lbImprimindo.setText("Relatório finalizado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de Endereço para Fornecedor!\n" + e);
        }

        documentPDF.close();

    }

    private void geraCabecalho() {
        Paragraph cabecalho = new Paragraph(
                "Relatório de Endereço de Entrega de Fornecedor\n"
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