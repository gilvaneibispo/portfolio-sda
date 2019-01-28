package portfolio.sda.ultil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Gilvanei
 */
public class DataSerializer {

    /**
     * <strong>Serializa Dados: </strong>
     * Responsável pela persistência de uma lista do objeto Conta. É chamada
     * no final de cada método capaz de alterar algum dado contido nelaclasse,
     * garantindo a disponibilidade da informaÃ§Ã£o correta e atualizada.
     */
    public void serializaDados() {
        try {
            FileOutputStream arqSaida = new FileOutputStream("filedataoutput.obj");
            ObjectOutputStream objSaida = new ObjectOutputStream(arqSaida);
            objSaida.writeObject(null);
            objSaida.close();
            arqSaida.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * <strong>Desserializa Dados: </strong>
     * Com a funcionalidade complementar ao método serializaDados(), esta
     * classe tem o dever de recuperar o as informações. É chamada no
     * construtor dasta classe, garantido que os dados estarÃ£o a disposiÃ§Ã£o
     * semprenque esta classe estiver em funcionamento.
     */
    public void desserializaDados() {
        try {
            FileInputStream arqEntrada = new FileInputStream("filedataoutput.obj");
            ObjectInputStream objEntrada = new ObjectInputStream(arqEntrada);
            ArrayList g = (ArrayList) objEntrada.readObject();
            objEntrada.close();
            arqEntrada.close();
            System.out.println("Arquivos de dados recuperados...");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } catch (ClassNotFoundException c) {
            System.out.println("Erro na recuperação dos dados: " + c.getMessage());
        }
    }
}
