package portfolio.sda.comunication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import portfolio.sda.model.Group;
import portfolio.sda.model.Pessoa;
import portfolio.sda.ultil.DataSender;
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
    private ObjectOutputStream objetoDeSaida;      //Objeto do saida de dados.
    //private BufferedReader recebeDoCliente;         //Para recepÃ§Ã£o de dados.
    private ObjectInputStream recebeDoCliente;
    private StringTokenizer tokens;
    private ArrayList<Pessoa> listaUsers;
    private ArrayList<Group> listaDeGrupos;
    private DataSender dados;

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
        listaUsers = new ArrayList();
        listaUsers.add(new Pessoa("gil123", "senha123"));
        listaDeGrupos = new ArrayList();
        adicionandoGrupos();
    }

    @Override
    public void run() {
        try {
            this.executa();
        } catch (IOException ex) {
            Helper.printError(ex);
        } catch (Exception ex) {
            Helper.printError(ex);
        }
    }

    /**
     * <strong>Executa: </strong>
     * Execussão obrigatária para que ocorra a comunicação. Recebe os dados do
     * cliente e direciona para regiões específicas de acordo com o audico de
     * comunicação recebido.
     *
     * @throws IOException
     */
    public void executa() throws IOException, ClassNotFoundException, Exception {

        //while (true) {
        objetoDeSaida = new ObjectOutputStream(representaCliente.getOutputStream());
        //recebeDoCliente = new BufferedReader(new InputStreamReader(representaCliente.getInputStream(), "UTF-8"));
        recebeDoCliente = new ObjectInputStream(representaCliente.getInputStream());

        try {

            //String dados = recebeDoCliente.readLine();
            Object classeRecebida = recebeDoCliente.readObject();

            if (classeRecebida instanceof DataSender) {
                dados = (DataSender) classeRecebida;
            } else {
                throw new Exception("Erro ao converter os dados para DataSender");
            }

            //tokens = new StringTokenizer(dados, "#");
            //checarProtocolo(Integer.parseInt(tokens.nextToken()));
            checarProtocolo(dados.getProtocolo());

            //while (tokens.hasMoreTokens()) {
            //System.out.println(tokens.nextToken());
            //}
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
            case Protocol.FAZER_LOGIN:

                //Varificar existência de usuário...
                //String nickName = tokens.nextToken();
                //String senha = tokens.nextToken();
                System.out.println("Verificando dados do usuário...");
                loginUser(dados.getNick(), dados.getSenha());
                break;
            //case Protocol.EXEMPLE_TWO:
            //Ação aqui...
            default:
                Helper.printError(new Exception("Protocolo inválido"));
        }
    }

    /**
     * <strong>Responde Cliente: </strong>
     * Saída de dados servidor/cliente.
     *
     * @param d
     * @throws IOException
     */
    public void enviarMensagem(DataSender d) throws IOException {
        objetoDeSaida.writeObject(d);
    }

    private void loginUser(String nickName, String senha) throws IOException {

        if (userExists(nickName, senha)) {

            DataSender rt = new DataSender(Protocol.RESPOSTA_LOGIN);
            rt.setGrupos(this.listaDeGrupos);

            System.out.println("Respondendo cliente...");
            enviarMensagem(rt);
        }else{
            enviarMensagem(new DataSender(Protocol.RESPOSTA_LOGIN_FALHOU));
        }
    }

    private boolean userExists(String nickName, String senha) {

        if (!listaUsers.isEmpty()) {
            for (Pessoa pessoa : listaUsers) {

                if (pessoa.getNick().equals(nickName) || pessoa.getSenha().equals(senha)) {
                    return true;
                }
            }
        } else {
            //Helper.printError(new Exception("Ainda não há registro de usuário!"));
            System.err.println("Ainda não há registro de usuário!");
        }

        return false;
    }
    
    private void adicionandoGrupos(){
        
        Group g1 =  new Group();        
        g1.setName("eng");
        g1.setIpMulcast("244.16.0.1");
        this.listaDeGrupos.add(g1);
        
        Group g2 =  new Group();
        g2.setName("art");
        g2.setIpMulcast("244.16.0.1");
        this.listaDeGrupos.add(g2);
        
        Group g3 =  new Group();
        g3.setName("prof");
        g3.setIpMulcast("244.16.0.1");
        this.listaDeGrupos.add(g3);
        
        Group g4 =  new Group();
        g4.setName("desen. web");
        g4.setIpMulcast("244.16.0.1");
        this.listaDeGrupos.add(g4);
    }
}
