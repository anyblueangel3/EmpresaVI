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

/**
 *
 * @author Ronaldo Rodrigues Godoi
 */
public class ImprimeClientes extends JPanel {

    CliDAO clientes;
    JLabel lbTituloTela, lbImprimindo;
    JButton btImprimir, btSair;
    Clientes cliente;
    ArrayList<Clientes> listaClientes = new ArrayList<>();
    Document documentPDF = new Document(PageSize.A4, 25, 25, 50, 50);

    public ImprimeClientes() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {

        setLayout(null);

        lbTituloTela = new JLabel(" Impressão da lista de Clientes ");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(320, 150, 400, 50);

        lbImprimindo = new JLabel("");
        lbImprimindo.setBounds(350, 350, 200, 25);

        btImprimir = new JButton(" Imprimir Relatório de Clientes ");
        btImprimir.setBounds(360, 520, 200, 25);

        btSair = new JButton(" Sair ");
        btSair.setBounds(360, 600, 200, 25);

        add(lbTituloTela);
        add(lbImprimindo);
        add(btImprimir);
        add(btSair);

        clientes = new CliDAO();
    }

    public void definirEventos() {

        btImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imprimirRelatorioClientes();
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

    public void imprimirRelatorioClientes() {
        listaClientes = clientes.listarClientes();
        if (listaClientes == null) {
            JOptionPane.showMessageDialog(null, "Cadastro de Clientes está vazio!");
            return;
        }
        try {
            PdfWriter.getInstance(documentPDF, new FileOutputStream("ListaClientes.pdf"));
            adicionarPaginacao();
            documentPDF.open();
            geraCabecalho();
            for (int i = 0; i < listaClientes.size(); i++) {
                Paragraph clienteatual = new Paragraph("CGC ou CPF: " + listaClientes.get(i).getId_cgc_cpf()
                        + " Nome ou razão social: " + listaClientes.get(i).getNome_razao() + "\n"
                        + "Endereço: " + listaClientes.get(i).getEndereco()
                        + " Número: " + listaClientes.get(i).getNumero()
                        + " Cidade: " + listaClientes.get(i).getCidade());
                documentPDF.add(clienteatual);
                documentPDF.add(new Paragraph(" "));
                if((i+1)%12 == 0) {
                    documentPDF.newPage();
                    geraCabecalho();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de Clientes!\n" + e);
        }

        documentPDF.close();

    }

    private void geraCabecalho() {
        Paragraph cabecalho = new Paragraph(
                "Relatório de Clientes\n"
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
