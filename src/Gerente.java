import java.util.Calendar;
import java.util.Scanner;

public class Gerente extends Funcionario {
    private static double comissao;
    private Calendar dataDeIngressoComoGerente;
    private Agencia gerenciada;
    private boolean temCurso;

    public Gerente(double salarioBase, String RG, String carteiraTrabalho, String sexo,
                   Calendar anoDeIngresso, Calendar dataDeIngressoComoGerente, Agencia gerenciada, boolean temCurso,
                   String name, String CPF, String scolarship, String civil, Calendar data, Endereco address,
                   String senha) {
        super(salarioBase, RG, "Gerente", carteiraTrabalho, sexo, anoDeIngresso, name, CPF,
                scolarship, civil, data, address, senha, gerenciada);
        this.dataDeIngressoComoGerente = dataDeIngressoComoGerente;
        this.gerenciada = gerenciada;
        this.temCurso = temCurso;
        CalcularSalario(salarioBase);
    }


    protected void CalcularSalario(double sal) {
        CalcularSalario(sal);
        setSalario(getSalario() + comissao);
    }


    public void Menu() {
        if(!isLogado()) {
            System.out.println("Usuário não logado!");
            return;
        }
        int escolha = 0;
        Scanner sc = new Scanner(System.in);
        while(escolha != -1) {
            System.out.println("Faça sua seleção:\n" +
                    "1 - Demitir funcionário\n" +
                    "2 - Contratar funcionário\n" +
                    "3 - Trocar salário de funcionário\n" +
                    "4 - Menu de funcionário (gerenciamento de contas)\n" +
                    "-1 - Voltar");
            escolha = sc.nextInt();
            switch (escolha) {
                case 1: {
                    //TODO
                }
                case 2: {
                    //todo
                }
                case 3: {
                    //todo
                }
                case 4: {
                    super.Menu();
                }
                case -1: {
                    break;
                }
                default: {
                    System.out.println("Opção inválida!");
                    break;
                }
            }
        }
        setLogado(false);
    }

    //Getters and Setters
    public static double getComissao() {
        return comissao;
    }

    public static void setComissao(double comissao) {
        Gerente.comissao = comissao;
    }

}
