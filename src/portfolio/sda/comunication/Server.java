package portfolio.sda.comunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import portfolio.sda.ultil.Helper;

/**
 * <strong>Servidor: </strong>
 * Responsável por manter a comunição com o cliente, assim o este possui objetos
 * especificos para saida e entrada de dados. Essa classe trabalha com auxílio
 * de threads, sendo a classe ClienteThread responsável por lidar com multiplas
 * solicitações de comunicação. Suas linhas de códigos poderiam perfeitamente
 * está contida nesta classe. Cabe a esta classe ainda a arquivamento e
 * recuparação de dados, a persistência é dada por meio de serialização.
 *
 * @author Gilvanei Bispo
 */
public final class Server {

    private final ServerSocket servidor;
    private final ServerSocket serIm;
    private Socket representaCliente;
    private static final int PORTA = 3636;
    private static final int PIMG = 5566;

    /**
     * <strong>Construtor Servidor: </strong>
     * Apenas para inicializaçãoo de atributos, não recebe parametros.
     *
     * @throws IOException
     */
    public Server() throws IOException {
        
        servidor = new ServerSocket(PORTA);
        serIm = new ServerSocket(PIMG);
        // this.desserializaDados();
        System.out.println("Servidor em funcionamento...");
    }

    /**
     * <strong>Executa: </strong>
     * Obrigatório para comunição entre cliente e esta classe. Deve ser
     * chamada quando desejada a execussÃ£o propriamente, recebendo a
     * solicitação do cliente e repassando para que uma nova thread a trate em
     * ClienteThread.
     *
     * @throws IOException
     */
    public void executa() throws IOException {

        while (true) {

            representaCliente = servidor.accept();
            Socket imm = serIm.accept();

            System.out.println("Nova conexão com " + representaCliente.getInetAddress().getHostAddress());

            CommunicationServer cs = new CommunicationServer(representaCliente);
            new Thread(cs).start();
            
            
        }
    }

    public static void main(String[] args) {

        try {
            
            Server s = new Server();
            s.executa();
        } catch (IOException ex) {
            
            Helper.printError(ex);
        }
    }
}