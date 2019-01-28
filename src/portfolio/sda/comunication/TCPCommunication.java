package portfolio.sda.comunication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;
import portfolio.sda.ultil.Helper;

/**
 * <strong>TCPCommunication: </strong>
 * Gerencia a comunicação com o servidor, envia a ele um conjunto de dados,
 * recebe a resposta e a trata. Cliente contem dois objetos para fluxo de dados,
 * um para saida e outro para entrada. Essa troca de informações e feita por
 * meio de Strings utilizando Socket e # (hashtag) como separador das mensagem.
 *
 * @author Gilvanei Bispo
 * @author Kelvin
 */
public class TCPCommunication {

    private static final String HOST = "127.0.0.1";     //Localhost.
    private static final int PORTA = 3636;              //Porta de comunicação.
    private String mensagem;                            //Mensagem para envio.
    private final Socket clienteSocket;                 //Objeto Socket para comunicação.
    private DataOutputStream objetoDeSaida;             //Objeto de saida de dados.
    private BufferedReader objetoDeEntrada;             //Objeto de recepção de dados.

    /**
     * <strong>Contrutor: </strong>
     * Inicia o Socket, setando o host e a porta com as constantes de mesmo
     * nome.
     *
     * @throws IOException
     */
    public TCPCommunication() throws IOException {
        
        clienteSocket = new Socket(HOST, PORTA);
    }

    /**
     * <strong>Executa: </strong>
     * Depois da classe iniciada, a chamada deste método é obrigatória para que
     * a comunicação ocorra, isso porque ela que inicia a função de comunição
     * propriamente dita. Desde o envia a recepção e o encaminhamento das
     * mensagens.
     *
     * @param msg
     * @throws IOException
     */
    public void executa(String msg) throws IOException {
        
        mensagem = msg;
        objetoDeSaida = new DataOutputStream(clienteSocket.getOutputStream());
        objetoDeEntrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream(), "UTF-8"));
        objetoDeSaida.writeBytes(mensagem + "\n");

        StringTokenizer tokens = new StringTokenizer(objetoDeEntrada.readLine(), "#");
        int tokenUm = Integer.parseInt(tokens.nextToken());

        mensagem = "";
        
        while (tokens.hasMoreTokens()) {
            mensagem = mensagem + " " + tokens.nextToken();
        }
        
        checarProtocolo(tokenUm);
    }

    /**
     * <strong>Encerrar Conexão: </strong>
     * Finaliza a comunicação entre o cliente e o servidor, devolvendo o boolean
     * true para quem a chama caso a conexão seja realmente finalizada.
     *
     * @return boolean
     * @throws IOException
     */
    public boolean encerrarConexao() throws IOException {
        try {
            
            clienteSocket.close();
            objetoDeSaida.close();
            return true;
        } catch (IOException io) {
            throw new IOException("Erro ao tentar executar a instrução!" + io.getMessage());
        }
    }

    public void checarProtocolo(int acao) {

        switch (acao) {
            case Protocol.EXEMPLE_TWO:
                //acao
                break;
            case Protocol.GROUP_CREATE:
                System.out.println("OK: " + this.mensagem.trim());
                break;
            default:
        }
    }

    public static void main(String[] args) {
        
        try {
        
            TCPCommunication tc = new TCPCommunication();
            //Texto de teste
            tc.executa("1#Uma mensagem");
        } catch (IOException ex) {
            
            Helper.printError(ex);
        }
    }
}
