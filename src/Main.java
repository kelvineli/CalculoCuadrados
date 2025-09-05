import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> numeros= new ArrayList<>();
        for (int i= 1; i<= 20; i++) {
            numeros.add(i);
        }

        ExecutorService pool= Executors.newFixedThreadPool(4);

        List<Future<Integer>> resultados= new ArrayList<>();

        for (int numero : numeros) {
            Callable<Integer> tarea= () -> {
                int cuadrado= numero*numero;
                System.out.printf("Número: %02d | Cuadrado: %04d | Hilo: %s%n",
                        numero, cuadrado, Thread.currentThread().getName());
                return cuadrado;
            };
            Future<Integer> futuro= pool.submit(tarea);
            resultados.add(futuro);
        }

        pool.shutdown();

        List<Integer> lista_cuadrados= new ArrayList<>();
        for (Future<Integer> futuro : resultados) {
            try {
                lista_cuadrados.add(futuro.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nResultados finales en orden:");
        for (int i= 0; i< numeros.size(); i++) {
            System.out.printf("Número: %02d | Cuadrado: %04d%n",
                    numeros.get(i), lista_cuadrados.get(i));
        }
    }
}