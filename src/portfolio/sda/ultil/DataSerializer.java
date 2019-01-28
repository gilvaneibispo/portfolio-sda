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
     * ResponsÃ¡vel pela persistÃªncia de uma lista do objeto Conta. Ã‰ chamada
     * no final de cada mÃ©todo capaz de alterar algum dado contido nelaclasse,
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
     * Com a funcionalidade complementar ao mÃ©todo serializaDados(), esta
     * classe tem o dever de recuperar o as informaÃ§Ãµes. Ã‰ chamada no
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
            System.out.println("Erro na recuperaÃ§Ã£o dos dados: " + c.getMessage());
        }
    }
}
