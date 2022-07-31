package imprime;

import empresavi.*;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
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
            
            int cont = itensPedido.size();
            
            documentPDF.add(new Paragraph(" "));
            
            double valorTotal = 0;
            for(int i = 0; i < cont; i++) {
                Paragraph corpoPedido = new Paragraph(itensPedido.get(i).getId_produto()+
                        " " + itensPedido.get(i).getDescricao_produto() +
                        " " + itensPedido.get(i).getQuantidade() +
                        " " + itensPedido.get(i).getPreco() +
                        " " + itensPedido.get(i).getQuantidade() * itensPedido.get(i).getPreco());
                valorTotal = valorTotal + itensPedido.get(i).getQuantidade() * itensPedido.get(i).getPreco();
                documentPDF.add(corpoPedido);
            }
            
            documentPDF.add(new Paragraph(" "));
            
            Paragraph totalizacao = new Paragraph("Valor total: " + valorTotal);
            documentPDF.add(totalizacao);
                    
            
        } catch (Exception erro) {
            erro.printStackTrace();
        }
        
        documentPDF.close();
        
    }
}