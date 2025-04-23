public interface Logavel {
    public void ValidaSenha(String senha) throws SenhaInvalidaException;
    public boolean Login(String usuario, String senha);
}
