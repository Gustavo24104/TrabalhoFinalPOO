public interface Logavel {
    public void ValidaSenha(String senha) throws SenhaInvalidaException;
    public void Login(String usuario, String senha);
    public void Menu();
}
