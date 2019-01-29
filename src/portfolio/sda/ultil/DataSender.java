package portfolio.sda.ultil;

import java.io.Serializable;
import java.util.ArrayList;
import portfolio.sda.model.Group;
import portfolio.sda.model.Pessoa;

/**
 * @author Gilvanei
 */
public class DataSender implements Serializable{
    
    private ArrayList<Pessoa> listaDePessoas;
    private ArrayList<Group> listaDeGrupos;
    private int protocolo;
    private String senha;
    private String nick;
    
    public DataSender(int protocolo){
        
        this.protocolo = protocolo;
        this.listaDeGrupos = new ArrayList();
        this.listaDePessoas = new ArrayList();
    }
    
    public int getProtocolo(){
        return protocolo;
    }
    
    public void setProtocolo(int protocolo){
        this.protocolo = protocolo;
    }
    
    public void setPessoas(ArrayList<Pessoa> lp){
        this.listaDePessoas = lp;
    }
    
    public void setGrupos(ArrayList<Group> lg){
        this.listaDeGrupos = lg;
    }
        
    public ArrayList<Pessoa> getPessoas(){
        return listaDePessoas;
    }
    
    public ArrayList<Group> getGrupos(){
         return listaDeGrupos;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSenha() {
        return senha;
    }

    public String getNick() {
        return nick;
    }
    
}
