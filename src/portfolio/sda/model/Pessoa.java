package portfolio.sda.model;

/**
 *
 * @author Gilvanei
 */
public class Pessoa {

    private String nick;
    private String senha;

    public Pessoa(String nick, String senha) {

        this.nick = nick;
        this.senha = senha;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNick() {
        return nick;
    }

    public String getSenha() {
        return senha;
    }
}
