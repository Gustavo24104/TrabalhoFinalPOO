import java.io.Serializable;
import java.util.*;

public class Agencia implements Serializable {
    private String nomeFicticio;
    private String nroAgencia; //6 digitos
    private Endereco end;
    private Gerente gerente;
    private TreeMap<String, Funcionario> funcs; // mapeia funcionario e cpf
    private TreeMap<String, Cliente> clientes; //maepia clientes e cpf

    public Agencia(String nomeFicticio, String nroAgencia, Endereco end){
        this.nomeFicticio = nomeFicticio;
        this.nroAgencia = nroAgencia;
        this.end = end;
        this.funcs = new TreeMap<>();
        clientes = new TreeMap<>();
    }

    public Agencia(String nomeFicticio, String nroAgencia) {
        this.nroAgencia = nroAgencia;
        this.nomeFicticio = nomeFicticio;
    }

    public void CadastraFuncionario(Funcionario f) {
        if(funcs.containsKey(f.getCpf())) {
            System.out.println("Funcionário de cpf " + f.getCpf() + " já cadastrado!");
            return;
        }
        funcs.put(f.getCpf(), f);
        System.out.println("Funcionário " + f.getNome() + " cadastrado com sucesso! Bem vindo à empresa!");
    }

    public Funcionario EncontraFuncionario(String key) {
        return funcs.get(key);
    }

    public void DemiteFuncionario(String chave){
        Funcionario result = funcs.remove(chave);
        if(result == null) {
            System.out.println("Funcionário de cpf " + chave + " não encontrado!");
            return;
        }
        System.out.println("Funcionário " + result.getNome() + " removido com sucesso!");
    }

    public void CadastraCliente(Cliente c) {
        clientes.put(c.getCpf(), c);
    }

    public Cliente EncontraCliente(String cpf) {
        return clientes.get(cpf);
    }

    public TreeMap<String, Funcionario> getFuncionariosMap() {
        return funcs;
    }
    public Map<String, Cliente> getClientes() {
        return clientes;
    }

    public void Menu() {
        Scanner sc = new Scanner(System.in);
        Logavel l = null;
        int escolha = 0;
        String user = "";
        System.out.println("Agência logada: " + nomeFicticio);
        while(escolha != -1) {
            System.out.println("""
                    Faça sua seleção
                    1 - Logar como funcionario
                    2 - Logar como gerente
                    3 - Logar como cliente
                    4 - Ver informações do gerente
                    -1 - Voltar""");
            escolha = sc.nextInt();
            sc.nextLine(); //limpar buffer

                if(escolha == 1) {
                    System.out.println("Digite cpf funcionário");
                    String cpf = sc.nextLine();
                    l = funcs.get(cpf);
                    user = cpf;
                    if(l == null) {
                        System.out.println("Funcionario de cpf " + cpf + " não encontrado!");
                        continue;
                    }
                    break;
                }

            if (escolha == 2) {
                System.out.println("Digite CPF do gerente:");
                String cpf = sc.nextLine();
                // Verifica se o CPF digitado confere com o CPF do gerente cadastrado
                if (cpf.equals(gerente.getCpf())) {
                    l = gerente;
                    user = cpf;   // o usuário do gerente é o seu CPF
                    break;
                } else {
                    System.out.println("CPF informado não é a do gerente desta agência.");
                    continue;
                }
            }

                if(escolha == 3) {
                    System.out.println("Digite cpf do cliente:");
                    String cpf = sc.nextLine();
                    l = clientes.get(cpf);
                    user = cpf;
                    if(l == null) {
                        System.out.println("Cliente de cpf " + cpf + " não encontrado!");
                        continue;
                    }
                    break;
                }

                if(escolha == 4) {

                    System.out.println(gerente);
                    continue;
                }

                if(escolha == -1) {
                    return;
                }
                else {
                    System.out.println("Escolha invalida!");
                    continue;
                }

        }
        System.out.println("Digite senha: ");
        String senha = sc.nextLine();
        l.Login(user, senha);
        Menu();
    }

    //Gera um número para uma conta, baseando-se no CPF do cliente, no nro da agencia e um numero aleatorio
    public String GerarNumeroDeConta(Cliente dono) {
        Random r = new Random();
        String result = "";
        int j = 0; //usado para indexar o cpf (ignorar '.' e '-')
        //String x1, x2, x3, x4, x5, x6;
        for(int i = 0; i < 6; ++i) {
            if(dono.getCpf().charAt(j) == '.' || dono.getCpf().charAt(j) == '-') j++; //ignorar '.' e '-'
            int aux = Integer.parseInt(String.valueOf(nroAgencia.charAt(i))) +
                    Integer.parseInt(String.valueOf(dono.getCpf().charAt(j++))) * r.nextInt(1, 100);
            aux = (aux % 9)+1;
            result = result.concat(String.valueOf(aux));
        }
        return result;
    }

    public String toString() {
        return "Nome: " + nomeFicticio + ". Número: " + nroAgencia +
                "\nEndereço: " + end +
                "\nGerente: " + gerente.getNome();
    }


//Getters and Setters
    public String getNomeFicticio() {return nomeFicticio;}
    public void setNomeFicticio(String nomeFicticio) {this.nomeFicticio = nomeFicticio;}

    public String getNroAgencia() {return nroAgencia;}
    public void setNroAgencia(String nroAgencia) {this.nroAgencia = nroAgencia;}

    public Endereco getEnd() {return end;}
    public void setEnd(Endereco end) {this.end = end;}

    public Gerente getGerente() {return gerente;}
    public void setGerente(Gerente gerente) {this.gerente = gerente;}
}
