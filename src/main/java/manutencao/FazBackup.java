
package manutencao;

import java.io.IOException;
import java.util.Locale;
import javax.swing.JOptionPane;

public class FazBackup {

    String dbName = "Empresa";
    String dbUser = "root";
    String dbPass = "04latosensu10";
    String backupPath = "c:\\backup.sql";
    String caminhoMySQLBin = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin";

    public void fazerBackup() {
        
        int resposta = JOptionPane.showConfirmDialog(
                null,
                "Devo fazer o Backup?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );
        
        if (resposta == JOptionPane.YES_OPTION) {
            try {
                // Comando para desligar o servidor MySQL
                executarComando("\"" +caminhoMySQLBin + "\\mysqladmin\" -u" + dbUser + " -p" + dbPass + " shutdown");

                // Aguarde um tempo suficiente para o servidor MySQL encerrar completamente
                Thread.sleep(5000);

                // Comando mysqldump para realizar o backup
                executarComando("\"" + caminhoMySQLBin + "\\mysqldump\" -u" + 
                        dbUser + " -p" + dbPass + " " + dbName +
                        " --result-file=" + backupPath +" > c:\\output.txt 2>&1");

                // Comando para iniciar novamente o servidor MySQL
                executarComando("\"" + caminhoMySQLBin + "\\mysqld\" --console");

                JOptionPane.showMessageDialog(null, "Backup concluído com sucesso. Arquivo salvo em: " + backupPath);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void executarComando(String comando) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", comando);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            JOptionPane.showMessageDialog(null, "Comando executado com sucesso.");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Erro ao executar o comando.\n" + "cmd.exe /c " + comando + "\n Código de saída: " + exitCode);
        }
    }

}