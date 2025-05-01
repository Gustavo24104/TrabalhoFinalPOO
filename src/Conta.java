import java.io.Serializable;
import java.util.Calendar;
import java.util.Scanner;

public abstract class Conta implements Logavel, Serializable { //Essa eh a outra classse requisitada como abstrata pela questao 5
    private String nroConta, senha;
    private double saldoAtual;
    private Calendar dataAbertura, ultimaMovimentacao;
    private boolean estaAtiva, estaBloqueada;
    private Cliente[] donoDaConta;
    private Agencia ag;
    private int tentativasErradas;
    private boolean logado = false;

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

    public Conta(String nroConta, String senha, Calendar dataAbertura, Cliente donoDaConta1, Agencia ag) {
        this.nroConta = nroConta;
        this.senha = senha;
        this.dataAbertura = dataAbertura;
        this.donoDaConta = new Cliente[2];
        this.donoDaConta[0] = donoDaConta1;
        this.donoDaConta[1] = null;
        this.ag = ag;
    }

    public Conta(String nroConta, String senha, Calendar dataAbertura, Cliente donoDaConta1, Cliente donoDaConta2,
                 Agencia ag) {
        this.nroConta = nroConta;
        this.senha = senha;
        this.dataAbertura = dataAbertura;
        this.donoDaConta = new Cliente[2];
        this.donoDaConta[0] = donoDaConta1;
        this.donoDaConta[1] = donoDaConta2;
        this.ag = ag;
    }


    public void ValidaSenha(String senha) throws SenhaInvalidaException {
        if (!this.senha.equals(senha)) {
            tentativasErradas++;
            if (tentativasErradas == 3) {
                System.out.println("3 tentativas de login erradas seguidas, conta foi bloqueada!");
                estaBloqueada = true;
            }
            throw new SenhaInvalidaException("Senha da conta invalida! mais " + (3 - tentativasErradas) +
                    " tentativas restantes até o bloqueio da conta");
        }
        tentativasErradas = 0;
    }

    public void Deposito(double valor) throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("Depósito não pode ser menor que 0!");
        }
        saldoAtual += valor;
    }

    public void setUltimaMovimentacao(Calendar data) {
        ultimaMovimentacao = data;
    }

    public abstract void Saque(double valor);

    public void Login(String usuario, String senha) {
        if(estaBloqueada) {
            System.out.println("Conta bloqueada! Por favor desbloqueie antes de tentar logar.");
            return;
        }
        try {
            ValidaSenha(senha);
            if(usuario.equals(this.nroConta)) {
                logado = true;
                Menu();
            }
        }
        catch (Exception e) {
            System.out.println("Senha incorreta!");
        }
    }

    public void DesbloquarConta(String senha) {

        try {
            ValidaSenha(senha);
            this.estaBloqueada = false;
            System.out.println("Conta desbloqueada!");
        } catch (Exception e) {
            System.out.println("Senha incorreta!");
        }
    }

    public void Menu() {
        if (!logado) {
            System.out.println("Usuario nao logado!");
            return;
        }

        System.out.println("Bem vindo.");
        MostraInfos();
        int escolha = 0;
        Scanner sc = new Scanner(System.in);

        while (escolha != -1) {
            escolha = sc.nextInt();
            System.out.println("""
                    Faça sua escolha:
                    1 - Sacar dinheiro
                    2 - Depositar dinheiro
                    3 - Transferencia bancária
                    4 - Trocar senha
                    -1 Sair""");
            switch (escolha) {
                case 1: {
                    //TODO!
                    //Transacao(valor);
                }
                case 2: {
                    //TODO!
                    //Transacao(valor);
                }
                case 3: {
                    //TODO!
                    //Transacao();
                }
                case 4: {
                    System.out.println("Digite nova senha: ");
                    senha = sc.nextLine();
                    System.out.println("Senha modificada!");
                    continue;
                }
                case -1: {
                    break;
                }
                default: {
                    System.out.println("Opção inválida!");
                    continue;
                }
            }
    }
        logado = false;
}

    public void MostraInfos(){
        System.out.println("Numero da conta: " + nroConta);
        System.out.println("Status " + (estaAtiva ? "ativa" : "inativa"));
        System.out.println("Saldo atual: " + saldoAtual);
        System.out.println("Ultima movimentacao em: " + ultimaMovimentacao.get(Calendar.DAY_OF_MONTH)
        + "/" + ultimaMovimentacao.get(Calendar.MONTH) + "/" +  ultimaMovimentacao.get(Calendar.YEAR));
    }


    //Getters and Setters
    public boolean isEstaBloqueada() {
        return estaBloqueada;
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

}
