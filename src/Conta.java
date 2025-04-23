import java.util.Calendar;

public abstract class Conta implements Logavel { //Essa eh a outra classse requisitada como abstrata pela questao 5
    private String nroConta, senha;
    private double saldoAtual;
    private Calendar dataAbertura, ultimaMovimentacao;
    private boolean estaAtiva, estaBloqueada;
    private Cliente[] donoDaConta;
    private Agencia ag;
    private int tentativasErradas;

    public Conta(String nroConta, String senha, double saldoAtual, Calendar dataAbertura,
                 Calendar ultimaMovimentacao, boolean estaAtiva, Cliente donoDaConta1, Agencia ag) {
        this.nroConta = nroConta;
        this.senha = senha;
        this.saldoAtual = saldoAtual;
        this.dataAbertura = dataAbertura;
        this.ultimaMovimentacao = ultimaMovimentacao;
        this.estaAtiva = estaAtiva;
        this.donoDaConta = new Cliente[2];
        this.donoDaConta[0] = donoDaConta1;
        this.donoDaConta[1] = null;
        this.ag = ag;
        this.estaBloqueada = false;
        this.tentativasErradas = 0;
    }

    public void ValidaSenha(String senha) throws SenhaInvalidaException {
        if(!this.senha.equals(senha)) {
            tentativasErradas++;
            if(tentativasErradas == 3) estaBloqueada = false;
            throw new SenhaInvalidaException("Senha da conta invalida!");
        }
        tentativasErradas = 0;
    }

    public void Deposito(double valor) throws ValorInvalidoException {
        if(valor <= 0) {
            throw new ValorInvalidoException("Depósito não pode ser menor que 0!");
        }
        saldoAtual += valor;
    }

    public void setUltimaMovimentacao(Calendar data) {
        ultimaMovimentacao = data;
    }

    public abstract void Saque(double valor);

    //Getters and Setters
    public boolean isEstaBloqueada() {
        return estaBloqueada;
    }
    public void setEstaBloqueada(boolean estaBloqueada) {
        this.estaBloqueada = estaBloqueada;
    }

    public String getNroConta() {
        return nroConta;
    }
    public void setNroConta(String nroConta) {
        this.nroConta = nroConta;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public double getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }
    public Calendar getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Calendar dataAbertura) {
        this.dataAbertura = dataAbertura;
    }
    public Calendar getUltimaMovimentacao() {
        return ultimaMovimentacao;
    }

    public boolean isEstaAtiva() {
        return estaAtiva;
    }
    public void setEstaAtiva(boolean estaAtiva) {
        this.estaAtiva = estaAtiva;
    }

    public Cliente[] getDonoDaConta() {
        return donoDaConta;
    }
    public void setDonoDaConta(Cliente[] donoDaConta) {
        this.donoDaConta = donoDaConta;
    }

    public Agencia getAg() {
        return ag;
    }
    public void setAg(Agencia ag) {
        this.ag = ag;
    }

    public int getTentativasErradas() {
        return tentativasErradas;
    }

    public void setTentativasErradas(int tentativasErradas) {
        this.tentativasErradas = tentativasErradas;
    }

    public boolean Login(String usuario, String senha) {
        try {
            ValidaSenha(senha);
            if(usuario.equals(this.nroConta)) return true;
        }
        catch (Exception e) {
            return false;
        }
        return false;
    }

}
