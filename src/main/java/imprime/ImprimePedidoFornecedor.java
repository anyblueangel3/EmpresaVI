package imprime;

import empresavi.*;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.ArrayList;
import model.ItensPedidoFornecedorEstendida;
import model.PedidosFornecedor;

/**
 * 
 * Autor: Ronaldo Rodrigues Godoi
 * 
 */
public class ImprimePedidoFornecedor {
    public ImprimePedidoFornecedor(PedidosFornecedor pedidoFornecedor, ArrayList<ItensPedidoFornecedorEstendida> itensPedido) {
        Document documentPDF = new Document();
        try {
            PdfWriter.getInstance(documentPDF, new FileOutputStream("PedidoFornecedor.pdf"));
            documentPDF.open();
            Paragraph cabecalho = new Paragraph("Número do pedido: " + pedidoFornecedor.getId() +
                    "\n Id Cliente: " + pedidoFornecedor.getId_fornecedor() +
                    "\n Condição de pagamento: " + pedidoFornecedor.getCondicao_pag() +
                    "\n Id Endereço de entrega: " + pedidoFornecedor.getId_endereco_entrega() +
                    "\n Data do Pedido: " + pedidoFornecedor.getData_pedido());
            documentPDF.add(cabecalho);
            
            PdfPTable tabelaProdutos = this.criarTabela();
            
            int cont = itensPedido.size();
            
            documentPDF.add(new Paragraph(" "));
            
            double valorTotal = 0;
            for(int i = 0; i < cont; i++) {
                
                PdfPCell celulaIdProduto = new PdfPCell(new Phrase(itensPedido.get(i).getId_produto()));
                PdfPCell celulaDescricao = new PdfPCell(new Phrase(itensPedido.get(i).getDescricao_produto()));
                PdfPCell celulaQuantidade = new PdfPCell(new Phrase(""+itensPedido.get(i).getQuantidade()));
                PdfPCell celulaPreco = new PdfPCell(new Phrase(""+itensPedido.get(i).getPreco()));
                PdfPCell celulaTotal = new PdfPCell(new Phrase(""+itensPedido.get(i).getQuantidade() * itensPedido.get(i).getPreco()));
                
                tabelaProdutos.addCell(celulaIdProduto);
                tabelaProdutos.addCell(celulaDescricao);
                tabelaProdutos.addCell(celulaQuantidade);
                tabelaProdutos.addCell(celulaPreco);
                tabelaProdutos.addCell(celulaTotal);
                
                valorTotal = valorTotal + itensPedido.get(i).getQuantidade() * itensPedido.get(i).getPreco();
                
            }
            
            documentPDF.add(tabelaProdutos);
            
            documentPDF.add(new Paragraph(" "));
            
            Paragraph totalizacao = new Paragraph("Valor total: " + valorTotal);
            documentPDF.add(totalizacao);
                    
            
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        
        documentPDF.close();
        
    }
    
    public PdfPTable criarTabela() {
        
        PdfPTable tabelaProdutos = new PdfPTable(5);
        tabelaProdutos.setWidthPercentage(98);
        tabelaProdutos.setWidths(new float[] {1f, 3f, 1f, 1f, 1f});
        
        PdfPCell celulaTitulo = new PdfPCell(new Phrase("Id Prod."));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tabelaProdutos.addCell(celulaTitulo);
        
        celulaTitulo = new PdfPCell(new Phrase("Descrição"));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tabelaProdutos.addCell(celulaTitulo);
        
        celulaTitulo = new PdfPCell(new Phrase("Quantidade"));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tabelaProdutos.addCell(celulaTitulo);
        
        celulaTitulo = new PdfPCell(new Phrase("Preço"));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tabelaProdutos.addCell(celulaTitulo);
        
        celulaTitulo = new PdfPCell(new Phrase("Total"));
        celulaTitulo.setHorizontalAlignment(Element.ALIGN_CENTER);
        celulaTitulo.setBackgroundColor(Color.LIGHT_GRAY);
        tabelaProdutos.addCell(celulaTitulo);
        
        return tabelaProdutos;
    }
        
}