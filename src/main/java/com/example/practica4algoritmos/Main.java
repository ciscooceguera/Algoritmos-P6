package com.example.practica4algoritmos;
// Librerías
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Main extends Application {
    // Inicia la interfaz
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("interfaz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 768);
        stage.setTitle("Eigh Off Game");
        stage.setScene(scene);
        stage.show();
    }
    // Launchea la interfaz
    public static void main(String[] args) {
        launch();
    }
    /*
     * Lee el archivo y carga una lista agregando instancias de la clase
     * RegistroClima
     */
    public static List<RegistroClima> cargarLista(String file){
        List<RegistroClima> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String linea;

            while ((linea = br.readLine())!=null){
                String[] datos = linea.split(",");
                lista.add(new RegistroClima(
                        datos[0],
                        datos[1],
                        datos[2],
                        Double.parseDouble(datos[3]),
                        Double.parseDouble(datos[4]),
                        Double.parseDouble(datos[5]),
                        Double.parseDouble(datos[6]),
                        Double.parseDouble(datos[7]),
                        Double.parseDouble(datos[8])
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
    /*
     * Recibe la columna de la que se desea obtener un listado de valores,
     * y la lista, y retorna un vector con todos los datos enlistados
     */
    public static double[] obtenerColumna(List<RegistroClima> datos, String columna){
        double[] array = new double[datos.size()];
        for (int i = 0; i < datos.size(); i++){
            switch (columna){
                case "Temperatura":
                    array[i] = datos.get(i).getTemperatura();
                    break;
                case "TemperaturaAparente":
                    array[i] = datos.get(i).getTemperaturaAparente();
                    break;
                case "Humedad":
                    array[i] = datos.get(i).getHumedad();
                    break;
                case "VientoVelocidad":
                    array[i] = datos.get(i).getVientoVelocidad();
                    break;
                case "VientoAngulo":
                    array[i] = datos.get(i).getVientoAngulo();
                    break;
                case "Visibilidad":
                    array[i] = datos.get(i).getVisibilidad();
                    break;
            }
        }
        return array;
    }
    /*
     * Hace lo mismo que obtenerColumna pero retorna un vector tipo String
     */
    public static String[] obtenerColumnaStr(List<RegistroClima> datos, String columna){
        String[] array = new String[datos.size()];
        for (int i = 0; i < datos.size(); i++){
            switch (columna){
                case "Fecha":
                    array[i] = datos.get(i).getFecha();
                    break;
                case "Resumen":
                    array[i] = datos.get(i).getResumen();
                    break;
                case "TipoPrecipitacion":
                    array[i] = datos.get(i).getTipoPrecipitacion();
                    break;
            }
        }
        return array;
    }
    /*
     * Recibe la lista y la columna de las cuales se desea tomar mediciones de tiempo,
     * se obtiene un vector que contiene el listado de valores de la columna que se desea
     * ordenar, y se crea una copia por cada tipo de ordenamiento que se evaluará,
     * después se realiza la medición del tiempo y se guarda en una variable tipo long,
     * y finalmente se concatenan los resultados en un String.
     */
    public static String medirTiemposOrdenamientosStr(List<RegistroClima> datos, String columna) {
        String[] original = obtenerColumnaStr(datos, columna);
        String[] arrQuick = original.clone();
        String[] arrMerge = original.clone();
        String[] arrShell = original.clone();
        String[] arrSelec = original.clone();
        String[] arrSort  = original.clone();
        String[] arrPar   = original.clone();
        long tQuick = medirTiempo(() -> Ordenamientos.quicksort(arrQuick, 0, arrQuick.length - 1));
        long tMerge = medirTiempo(() -> Ordenamientos.mergesort(arrMerge));
        long tShell = medirTiempo(() -> Ordenamientos.shellSort(arrShell));
        long tSelec = medirTiempo(() -> Ordenamientos.seleccionDirecta(arrSelec));
        long tSort  = medirTiempo(() -> Arrays.sort(arrSort));
        long tPar   = medirTiempo(() -> Arrays.parallelSort(arrPar));
        long[] vector = new long[6];
        vector[0] = tQuick;
        vector[1] = tMerge;
        vector[2] = tShell;
        vector[3] = tSelec;
        vector[4] = tSort;
        vector[5] = tPar;
        String[] nombres = {
                "Quicksort",
                "Mergesort",
                "Shell sort",
                "Selección directa",
                "Arrays.sort",
                "parallelSort"
        };
        StringBuilder sb = new StringBuilder();
        sb.append("Columna: ").append(columna).append("\n\n");
        sb.append("Quicksort:         ").append(tQuick).append(" ns\n");
        sb.append("Mergesort:         ").append(tMerge).append(" ns\n");
        sb.append("Shell sort:        ").append(tShell).append(" ns\n");
        sb.append("Selección directa: ").append(tSelec).append(" ns\n");
        sb.append("Arrays.sort:       ").append(tSort).append(" ns\n");
        sb.append("parallelSort:      ").append(tPar).append(" ns\n");
        long min = vector[0];
        int idxMin = 0;
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < min) {
                min = vector[i];
                idxMin = i;
            }
        }
        sb.append("\nEl menor tiempo fue: ")
                .append(nombres[idxMin])
                .append(" con ")
                .append(min)
                .append(" ns\n");
        return sb.toString();
    }
    /*
     * Hace lo mismo que medirTiemposOrdenamientosString pero los vectores son de
     * tipo Double en lugar de tipo String
     */
    public static String medirTiemposOrdenamientos(List<RegistroClima> datos, String columna) {
        double[] original = obtenerColumna(datos, columna);
        double[] arrQuick = original.clone();
        double[] arrMerge = original.clone();
        double[] arrShell = original.clone();
        double[] arrSelec = original.clone();
        double[] arrSort  = original.clone();
        double[] arrPar   = original.clone();
        int[] arrRadix = new int[original.length];
        for (int i = 0; i < original.length; i++) {
            arrRadix[i] = (int)(original[i] * 100);
        }
        long tQuick = medirTiempo(() -> Ordenamientos.quicksort(arrQuick, 0, arrQuick.length - 1));
        long tMerge = medirTiempo(() -> Ordenamientos.mergesort(arrMerge));
        long tShell = medirTiempo(() -> Ordenamientos.shellSort(arrShell));
        long tSelec = medirTiempo(() -> Ordenamientos.seleccionDirecta(arrSelec));
        long tRadix = medirTiempo(() -> Ordenamientos.radixSort(arrRadix));
        long tSort  = medirTiempo(() -> Arrays.sort(arrSort));
        long tPar   = medirTiempo(() -> Arrays.parallelSort(arrPar));
        long[] vector = new long[7];
        vector[0] = tQuick;
        vector[1] = tMerge;
        vector[2] = tShell;
        vector[3] = tSelec;
        vector[4] = tRadix;
        vector[5] = tSort;
        vector[6] = tPar;
        String[] nombres = {
                "Quicksort",
                "Mergesort",
                "Shell sort",
                "Selección directa",
                "Radix Sort",
                "Arrays.sort",
                "parallelSort"
        };
        StringBuilder sb = new StringBuilder();
        sb.append("Columna: ").append(columna).append("\n\n");
        sb.append("Quicksort:         ").append(tQuick).append(" ns\n");
        sb.append("Mergesort:         ").append(tMerge).append(" ns\n");
        sb.append("Shell sort:        ").append(tShell).append(" ns\n");
        sb.append("Selección directa: ").append(tSelec).append(" ns\n");
        sb.append("Arrays.sort:       ").append(tSort).append(" ns\n");
        sb.append("parallelSort:      ").append(tPar).append(" ns\n");
        sb.append("Radix sort (int):  ").append(tRadix).append(" ns\n");
        long min = vector[0];
        int idxMin = 0;
        for (int i = 1; i < vector.length; i++) {
            if (vector[i] < min) {
                min = vector[i];
                idxMin = i;
            }
        }
        sb.append("\nEl menor tiempo fue: ")
                .append(nombres[idxMin])
                .append(" con ")
                .append(min)
                .append(" ns\n");
        return sb.toString();
    }
    /*
     * Recibe la función, guarda el tiempo, después la ejecuta, y en su término
     * realiza otra medición
     */
    private static long medirTiempo(Runnable metodo) {
        long inicio = System.nanoTime();
        metodo.run();
        return System.nanoTime() - inicio;
    }
}