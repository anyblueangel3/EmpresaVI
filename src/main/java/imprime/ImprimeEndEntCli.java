package imprime;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import dao.EndEntCliDAO;
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
import model.Cli_entregasEstendida;

/**
 *
 * @author Ronaldo Rodrigues Godoi
 */
public class ImprimeEndEntCli extends JPanel {

    EndEntCliDAO enderecosEntregaCli;
    JLabel lbTituloTela, lbImprimindo;
    JButton btImprimir, btSair;
    Cli_entregasEstendida cli_entrega;
    ArrayList<Cli_entregasEstendida> listaEnderecosEntregaCli = new ArrayList<>();
    Document documentPDF = new Document(PageSize.A4, 25, 25, 50, 50);

    public ImprimeEndEntCli() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {

        setLayout(null);

        lbTituloTela = new JLabel(" Impressão da lista de Endereços de entrega de Clientes ");
        Font font = new Font("San Serif", Font.PLAIN, 24);
        lbTituloTela.setFont(font);
        lbTituloTela.setBounds(200, 150, 650, 50);

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

        enderecosEntregaCli = new EndEntCliDAO();
    }

    public void definirEventos() {

        btImprimir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imprimirRelatorioEndEntCli();
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

    public void imprimirRelatorioEndEntCli() {
        listaEnderecosEntregaCli = new ArrayList<>();
        listaEnderecosEntregaCli = enderecosEntregaCli.listarEnderecosEntregaClientes();
        if (listaEnderecosEntregaCli == null) {
            JOptionPane.showMessageDialog(null, "Cadastro de Endereços Entrega a Cliente está vazio!");
            return;
        }
        try {
            PdfWriter.getInstance(documentPDF, new FileOutputStream("ListaEndEntCli.pdf"));
            adicionarPaginacao();
            documentPDF.open();
            geraCabecalho();
            int cont = listaEnderecosEntregaCli.size();
            for (int i = 0; i < cont; i++) {
                Paragraph EndEntCliAtual = new Paragraph("Id End.Entrega: " + listaEnderecosEntregaCli.get(i).getId()
                        + " Id Cliente: " + listaEnderecosEntregaCli.get(i).getId_cliente()
                        + " Nome ou razão: " + listaEnderecosEntregaCli.get(i).getNome_razao()
                        + "\n Endereço de entrega: " + listaEnderecosEntregaCli.get(i).getEndereco()
                        + " Número: " + listaEnderecosEntregaCli.get(i).getNumero()
                        + "\n Telefone: " + listaEnderecosEntregaCli.get(i).getTelefone());
                documentPDF.add(EndEntCliAtual);
                documentPDF.add(new Paragraph(" "));
                lbImprimindo.setText("Imprimindo registro: " + i);
                if((i+1)%6 == 0 && (cont - 1) != i) {
                    documentPDF.newPage();
                    geraCabecalho();
                }
            }
            lbImprimindo.setText("Relatório finalizado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de Endereço Cliente!\n" + e);
        }

        documentPDF.close();

    }

    private void geraCabecalho() {
        Paragraph cabecalho = new Paragraph(
                "Relatório de Endereço de Entrega de Cliente\n"
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