import java.util.Calendar;

public abstract class Conta { //Essa eh a outra classse requisitada como abstrata pela questao 5
    protected String nroConta, senha;
    protected double saldoAtual;
    protected Calendar dataAbertura, ultimaMovimentacao;
    protected boolean estaAtiva;
    protected Cliente[] donoDaConta;
    protected Agencia ag;

    public String getSenha() {
        return senha;
    }

    public abstract void Saque(String senha, double valor);

    public double getSaldoAtual(String pwd) {
        if(senha.equals(pwd)) return saldoAtual;
        else return -1; //trocar pra exception
    }

    public Conta(String nroConta, String senha, double saldoAtual, Calendar dataAbertura,
                 Calendar ultimaMovimentacao, boolean estaAtiva, Cliente[] donoDaConta, Agencia ag) {
        this.nroConta = nroConta;
        this.senha = senha;
        this.saldoAtual = saldoAtual;
        this.dataAbertura = dataAbertura;
        this.ultimaMovimentacao = ultimaMovimentacao;
        this.estaAtiva = estaAtiva;
        this.donoDaConta = donoDaConta;
        this.ag = ag;
    }

    public void Deposito(double valor) {
        saldoAtual += valor;
    }

    public void setUltimaMovimentacao(Calendar data) {
        ultimaMovimentacao = data;
    }



}
