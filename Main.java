import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = inserirFuncionarios();

        funcionarios.remove(1);

        funcionarios.forEach(System.out::println);

        System.out.println("\nSALARIOS AUMENTADOS EM 10%:");
        funcionarios.forEach(f -> {
            BigDecimal porcentagem = f.getSalario().multiply(new BigDecimal("0.1"));

            BigDecimal aumento = f.getSalario().add(porcentagem);

            f.setSalario(aumento);
        });

        funcionarios.forEach(System.out::println);

        System.out.println("\nAGRUPANDO FUNCIONARIOS");

        Map<String, List<Funcionario>> funcionariosAgrupados = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        funcionariosAgrupados.forEach((funcao, listaFuncionarios) -> {
            System.out.println("\nFunção: " + funcao);
            listaFuncionarios.forEach(System.out::println);
        });

        // exibindo funcionarios que fazem aniversario no mes 10 e 12
        List<Funcionario> mes10 = new ArrayList<>();
        List<Funcionario> mes12 = new ArrayList<>();

        funcionarios.forEach(f -> {
            if (f.getDataNascimento().getMonthValue() == 10) mes10.add(f);
            if (f.getDataNascimento().getMonthValue() == 12) mes12.add(f);
        });

        System.out.println("\nFUNCIONARIOS QUE FAZEM ANIVERSARIO NO MES 10:");

        if (!mes10.isEmpty()) mes10.forEach(System.out::println);
        else System.out.println("N/A");

        System.out.println("\nFuncionarios que fazem aniversario no mes 12:");

        if (!mes12.isEmpty()) mes12.forEach(System.out::println);
        else System.out.println("N/A");

        // exibindo funcionario com maior e menor idade

        List<Funcionario> funcionariosOrdenados = funcionarios.stream().sorted((previous, current) -> {
            final int previousIdade = previous.calcularIdade();
            final int currentIdade = current.calcularIdade();

            return Integer.compare(currentIdade, previousIdade);

        }).toList();

        Funcionario maiorIdade = funcionariosOrdenados.getFirst();
        Funcionario menorIdade = funcionariosOrdenados.getLast();

        System.out.println("\nFuncionario com a maior idade: \n- " + maiorIdade.getNome() + "\n- " + maiorIdade.calcularIdade() + " anos");
        System.out.println("\nFuncionario com a menor idade: \n- " + menorIdade.getNome() + "\n- " + menorIdade.calcularIdade() + " anos");

        System.out.println("\nFUNCIONARIOS POR ORDEM ALFABETICA:");

        funcionarios.stream().map(Pessoa::getNome).sorted().toList().forEach(System.out::println);

        System.out.println("\nTOTAL DOS SALARIOS DOS FUNCIONARIOS:");
        BigDecimal total = BigDecimal.ZERO;

        for (Funcionario f : funcionarios) {
            total = total.add(f.getSalario());
        }

        System.out.println(Funcionario.formatarValorMonetario(total));

        System.out.println("\nQuantos salários minimos ganham cada funcionario: ");

        funcionarios.forEach(f -> {
            final int salariosMinimosCount = f.getSalario().divide(new BigDecimal("1212.00"), RoundingMode.DOWN).intValue();

            System.out.println("- "+ f.getNome() + "\n- "+ salariosMinimosCount + " salarios\n");
        });
    }

    private static List<Funcionario> inserirFuncionarios() {
        return new ArrayList<>(
                Arrays.asList(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));
    }
}
