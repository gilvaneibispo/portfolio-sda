package portfolio.sda.comunication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import portfolio.sda.model.Group;
import portfolio.sda.ultil.DataSender;
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
    private ObjectOutputStream objetoDeSaida;             //Objeto de saida de dados.
    //private BufferedReader objetoDeEntrada;             //Objeto de recepção de dados.
    private ObjectInputStream objetoDeEntrada;
    private ArrayList<Group> listGroup;
    private DataSender dados;

    /**
     * <strong>Contrutor: </strong>
     * Inicia o Socket, setando o host e a porta com as constantes de mesmo
     * nome.
     *
     * @throws IOException
     */
    public TCPCommunication() throws IOException {

        clienteSocket = new Socket(HOST, PORTA);
        listGroup = new ArrayList();
    }

    /**
     * <strong>Executa: </strong>
     * Depois da classe iniciada, a chamada deste método é obrigatória para que
     * a comunicação ocorra, isso porque ela que inicia a função de comunição
     * propriamente dita. Desde o envia a recepção e o encaminhamento das
     * mensagens.
     *
     * @throws IOException
     * @throws java.lang.ClassNotFoundException
     */
    public void executa() throws IOException, ClassNotFoundException {

        objetoDeSaida = new ObjectOutputStream(clienteSocket.getOutputStream());
        objetoDeEntrada = new ObjectInputStream(clienteSocket.getInputStream());

        boolean saiu = false;

        while (!saiu) {

            System.out.printf(" 1 - Fazer login \n 9 - Para sair \n\t Opção desejada: ");
            Scanner ler = new Scanner(System.in);

            int acao = ler.nextInt();

            switch (acao) {
                case 1:
                    solicitarLogin();
                    break;
                case 2:
                    ///
                    break;
                default:
                    System.err.println("Ação não existe!");
            }

            //mensagem = msg;
            //objetoDeEntrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream(), "UTF-8"));
            //objetoDeSaida.writeBytes(mensagem + "\n");
            //StringTokenizer tokens = new StringTokenizer(objetoDeEntrada.readLine(), "#");
            //int tokenUm = Integer.parseInt(tokens.nextToken());
            //while (tokens.hasMoreTokens()) {
            //mensagem = mensagem + " " + tokens.nextToken();
            //}
            Object classe = objetoDeEntrada.readObject();

            if (classe instanceof DataSender) {
                dados = (DataSender) classe;
            }

            checarProtocolo(dados.getProtocolo());
            System.out.println("+ - - - - - - - - - - - - - +");
        }
    }

    private void enviarMensagem(DataSender d) throws IOException {

        objetoDeSaida.writeObject(d);
    }

    public void solicitarLogin() throws IOException {

        DataSender d = new DataSender(Protocol.FAZER_LOGIN);

        System.out.printf("\t Informe o nickname: ");
        Scanner ler = new Scanner(System.in);
        String nick = ler.nextLine();

        System.out.printf("\t Informe a senha: ");
        //Scanner ler = new Scanner(System.in);
        String pass = ler.nextLine();
        //d.setSenha();

        System.out.println("+ - - - - - - - - - - - - - +");
        System.out.println("Aguardando a resposta da solicitação...");

        d.setSenha(pass);
        d.setNick(nick);

        enviarMensagem(d);
        return;
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
            case Protocol.RESPOSTA_LOGIN:
                System.out.println("P: " + dados.getProtocolo());
                atualizarDados();
                break;
            case Protocol.RESPOSTA_LOGIN_FALHOU:
                System.out.println("O Login falhou: Senha ou Pass incorreta!");
            //    break;
            default:
        }
    }

    private void atualizarDados() {
        this.listGroup = (ArrayList<Group>) dados.getGrupos();
        
        for(Group g : listGroup){
            System.out.println(g.getName());
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {

        try {

            TCPCommunication tc = new TCPCommunication();
            //Texto de teste
            tc.executa();
        } catch (IOException ex) {

            Helper.printError(ex);
        }
    }
}
