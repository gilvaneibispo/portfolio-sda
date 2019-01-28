package portfolio.sda.comunication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;
import portfolio.sda.ultil.Helper;

/**
 * <strong>ClienteThread: </strong>
 * Cada instÃ¢ncia desta classe roda dentro de uma thread no servidor, causando
 * independencia entre as solicitações de vários clientes ao servidor. Recebe em
 * seu construtor uma instÃ¢ncia do servidor (todas as threads conhece a mesma
 * instÃ¢ncia) e outra do socket responsável por ler os dados de um especifico
 * cliente.
 */
public class CommunicationServer implements Runnable {

    private final Socket representaCliente;
    private String[] dadosDoCliente;                  //Para dados vindo do cliente.
    private DataOutputStream objetoDeSaida;      //Objeto do saida de dados.
    private BufferedReader recebeDoCliente;         //Para recepÃ§Ã£o de dados.

    /**
     * <strong>Construtor ClienteThread: </strong>
     * inicializa alguns atributos desta classe.
     *
     * @param cliente
     * @paramservidor
     * @throws IOException
     */
    public CommunicationServer(Socket cliente) throws IOException {

        this.representaCliente = cliente;
    }

    @Override
    public void run() {
        try {
            this.executa();
        } catch (IOException ex) {
            Helper.printError(ex);
        }
    }

    /**
     * <strong>Executa: </strong>
     * Execussão obrigatária para que ocorra a comunicação. Recebe os dados
     * do cliente e direciona para regiões específicas de acordo com o audico
     * de comunicação recebido.
     *
     * @throws IOException
     */
    public void executa() throws IOException {

        //while (true) {

            objetoDeSaida = new DataOutputStream(representaCliente.getOutputStream());
            recebeDoCliente = new BufferedReader(new InputStreamReader(representaCliente.getInputStream(), "UTF-8"));

            try {

                String dados = recebeDoCliente.readLine();

                StringTokenizer tokens = new StringTokenizer(dados, "#");
                checarProtocolo(Integer.parseInt(tokens.nextToken()));
                
                while (tokens.hasMoreTokens()) {
                   
                    System.out.println(tokens.nextToken());
                }

                //dadosDoCliente = dados.split("#");                               
                //checarProtocolo(Integer.parseInt(dadosDoCliente[0]));
            } catch (SocketException se) {
                
                System.err.println("Conexão Finalizada");
                representaCliente.close();
                objetoDeSaida.close();
                recebeDoCliente.close();
            }
        //}
    }

    private void checarProtocolo(int acao) throws IOException {

        switch (acao) {
            case Protocol.GROUP_CREATE:
                //Ação aqui...
                enviarMensagem("1#Outra mensagem como resposta");
                break;
            case Protocol.EXEMPLE_TWO:
            //Ação aqui...
            default:
                Helper.printError(new Exception("Protocolo inválido"));
        }
    }

    /**
     * <strong>Responde Cliente: </strong>
     * Saída de dados servidor/cliente.
     *
     * @param sentenca
     * @throws IOException
     */
    public void enviarMensagem(String sentenca) throws IOException {
        objetoDeSaida.writeBytes(sentenca + "\n");
    }
}
