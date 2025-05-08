import java.io.*;
import java.util.*;

public class Banco {
    static TreeMap<String, Agencia> agencias; //mapear agencia e numero



    public static void main(String[] args) {
        agencias = new TreeMap<>();
        Gerente.setComissao(1299.99); //Comissao dos gerentes
        CarregarDados();
        Menu();
    }

    public static Agencia EncontrarAgencia(String nro) {
        return agencias.get(nro);
    }

    public static Endereco CriarEndereco() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Cep?");
        String cep = sc.nextLine();

        System.out.println("Cidade?");
        String cidade = sc.nextLine();

        System.out.println("Estado?");
        String estado = sc.nextLine();

        System.out.println("Bairro?");
        String bairro = sc.nextLine();

        return new Endereco(cidade, estado, bairro, cep);
    }

    public static Calendar CriarData() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Dia (número): ");
        int dia = sc.nextInt();

        System.out.println("Mês (número): ");
        int mes = sc.nextInt();

        System.out.println("Ano (número): ");
        int ano = sc.nextInt();

        return new Calendar.Builder().
                setFields(Calendar.YEAR, ano, Calendar.MONTH, mes, Calendar.DAY_OF_MONTH, dia).build();
    }


    public static void Menu() {
        int escolha = 0;
        Scanner sc = new Scanner(System.in);
        while (escolha != -1) {
            System.out.println("""
                    Selecione:
                    1 - Listar agencias
                    2 - Selecionar agencia
                    3 - Cadastrar nova agencia
                    4 - Remover agencia
                    -1 Salvar e sair""");
            escolha = sc.nextInt();
            sc.nextLine(); //limpar buffer
            switch (escolha) {

                case 1: {
                    if (agencias.isEmpty()) System.out.println("Lista de agencias vazia!");
                    for (Agencia ag : agencias.values()) {
                        System.out.println(ag + "\n");
                    }

                    continue;
                }

                case 2: {
                    if (agencias.isEmpty()) {
                        System.out.println("Lista de agencias vazia!");
                        continue;
                    }
                    System.out.println("Digite número da agência que deseja administrar");
                    String nro = sc.nextLine();
                    Agencia ag = agencias.get(nro);
                    if (ag == null) {
                        System.out.println("Agencia de número " + nro + " não encontrada!");
                        continue;
                    }
                    ag.Menu();
                    continue;
                }

                case 3: {
                    System.out.println("Nome ficticio da agencia?");
                    String nome = sc.nextLine();
                    String nro;
                    while (true) {
                        System.out.println("Numero da agencia? (6 digitos)");
                        nro = sc.nextLine();
                        if (nro.length() != 6) {
                            System.out.println("Número de digitos incorreto!");
                            continue;
                        }
                        if (agencias.containsKey(nro)) {
                            System.out.println("Ja existe uma agência com esse número! Utilize outro");
                            continue;
                        }
                        break;
                    }
                    System.out.println("Insira o endereço");
                    Endereco end = CriarEndereco();

                    Agencia nova = new Agencia(nome, nro, end);
                    agencias.put(nro, nova);

                    System.out.println("Cadastre o gerente: ");
                    Funcionario f = new Funcionario();
                    f.LerDoTeclado(nova);
                    nova.CadastraFuncionario(f); //o gerente também eh funcionário
                    Gerente g = new Gerente(f);
                    g.LerDoTeclado(true);
                    nova.setGerente(g);
                    continue;
                }

                case 4: {
                    if (agencias.isEmpty()) {
                        System.out.println("Lista de agencias vazia!");
                        continue;
                    }
                    System.out.println("Digite nro da agência que deseja remover");
                    String nro = sc.nextLine();
                    Agencia result = agencias.remove(nro);
                    if (result == null) {
                        System.out.println("Agencia de número " + nro + " não encontrada!");
                        continue;
                    }
                    System.out.println("Agencia de número " + nro + " removida com sucesso!");
                    continue;
                }

                case -1: {
                    SalvarDados();
                    return;
                }

                default: {
                    System.out.println("Escolha invalida!");
                    continue;
                }
            }
        }
    }

    public static void SalvarDados() {
        FileOutputStream fs = null;
        ObjectOutputStream os = null;
        try {
            fs = new FileOutputStream("agencias.sav");
            os = new ObjectOutputStream(fs);
            os.writeObject(agencias);

            fs.close();
            os.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fs != null) fs.close();
                if (os != null) os.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void CarregarDados() {
        FileInputStream fs = null;
        ObjectInputStream os = null;

        try {
            fs = new FileInputStream("agencias.sav");
            os = new ObjectInputStream(fs);
            agencias = (TreeMap<String, Agencia>) os.readObject();
            fs.close();
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de salvamento nao encontrado!" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Algo deu errado! " + e.getMessage());
        } finally {
            try {
                if (os != null) os.close();
                if (fs != null) fs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}