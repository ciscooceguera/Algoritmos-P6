package com.example.practica4algoritmos;
// Librerías
import java.util.Arrays;
public class Ordenamientos {
    public static void seleccionDirecta(double[] arreglo) {
        for (int i = 0; i < arreglo.length; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if (arreglo[j] < arreglo[indiceMinimo]) {
                    indiceMinimo = j;
                }
            }
            double aux = arreglo[i];
            arreglo[i] = arreglo[indiceMinimo];
            arreglo[indiceMinimo] = aux;
        }
    }
    public static void shellSort(double[] arreglo) {
        int salto = arreglo.length / 2;
        while (salto > 0) {
            for (int i = salto; i < arreglo.length; i++) {
                double aux = arreglo[i];
                int j = i;
                while (j >= salto && arreglo[j - salto] > aux) {
                    arreglo[j] = arreglo[j - salto];
                    j -= salto;
                }
                arreglo[j] = aux;
            }
            salto /= 2;
        }
    }
    public static void quicksort(double[] arreglo, int izquierda, int derecha) {
        if (izquierda >= derecha) return;
        double pivote = arreglo[(izquierda + derecha) / 2];
        int i = izquierda;
        int j = derecha;
        while (i <= j) {
            while (arreglo[i] < pivote) i++;
            while (arreglo[j] > pivote) j--;
            if (i <= j) {
                double aux = arreglo[i];
                arreglo[i] = arreglo[j];
                arreglo[j] = aux;
                i++;
                j--;
            }
        }
        if (izquierda < j) quicksort(arreglo, izquierda, j);
        if (i < derecha) quicksort(arreglo, i, derecha);
    }
    public static void mergesort(double[] arreglo) {
        if (arreglo.length < 2) return;
        int mitad = arreglo.length / 2;
        double[] izquierda = Arrays.copyOfRange(arreglo, 0, mitad);
        double[] derecha   = Arrays.copyOfRange(arreglo, mitad, arreglo.length);
        mergesort(izquierda);
        mergesort(derecha);
        combinar(arreglo, izquierda, derecha);
    }
    private static void combinar(double[] arreglo, double[] izquierda, double[] derecha) {
        int i = 0, j = 0, k = 0;
        while (i < izquierda.length && j < derecha.length) {
            if (izquierda[i] <= derecha[j]) {
                arreglo[k++] = izquierda[i++];
            } else {
                arreglo[k++] = derecha[j++];
            }
        }
        while (i < izquierda.length) {
            arreglo[k++] = izquierda[i++];
        }
        while (j < derecha.length) {
            arreglo[k++] = derecha[j++];
        }
    }
    public static void radixSort(int[] arreglo) {
        if (arreglo.length == 0) return;
        int minimo = arreglo[0];
        for (int i = 1; i < arreglo.length; i++) {
            if (arreglo[i] < minimo) {
                minimo = arreglo[i];
            }
        }
        int offset = 0;
        if (minimo < 0) {
            offset = -minimo;
            for (int i = 0; i < arreglo.length; i++) {
                arreglo[i] += offset;
            }
        }
        int maximo = obtenerMaximo(arreglo);
        for (int exp = 1; maximo / exp > 0; exp *= 10) {
            contarPorDigito(arreglo, exp);
        }
        if (offset != 0) {
            for (int i = 0; i < arreglo.length; i++) {
                arreglo[i] -= offset;
            }
        }
    }
    private static int obtenerMaximo(int[] arreglo) {
        int max = arreglo[0];
        for (int i = 1; i < arreglo.length; i++) {
            if (arreglo[i] > max) {
                max = arreglo[i];
            }
        }
        return max;
    }
    private static void contarPorDigito(int[] arreglo, int exp) {
        int n = arreglo.length;
        int[] salida = new int[n];
        int[] conteo = new int[10];
        for (int i = 0; i < n; i++) {
            int digito = (arreglo[i] / exp) % 10;
            conteo[digito]++;
        }
        for (int i = 1; i < 10; i++) {
            conteo[i] += conteo[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            int digito = (arreglo[i] / exp) % 10;
            salida[conteo[digito] - 1] = arreglo[i];
            conteo[digito]--;
        }
        for (int i = 0; i < n; i++) {
            arreglo[i] = salida[i];
        }
    }
    public static void seleccionDirecta(String[] arreglo) {
        for (int i = 0; i < arreglo.length; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if (arreglo[j].compareTo(arreglo[indiceMinimo]) < 0) {
                    indiceMinimo = j;
                }
            }
            String aux = arreglo[i];
            arreglo[i] = arreglo[indiceMinimo];
            arreglo[indiceMinimo] = aux;
        }
    }
    public static void shellSort(String[] arreglo) {
        int salto = arreglo.length / 2;
        while (salto > 0) {
            for (int i = salto; i < arreglo.length; i++) {
                String aux = arreglo[i];
                int j = i;
                while (j >= salto && arreglo[j - salto].compareTo(aux) > 0) {
                    arreglo[j] = arreglo[j - salto];
                    j -= salto;
                }
                arreglo[j] = aux;
            }
            salto /= 2;
        }
    }
    public static void quicksort(String[] arreglo, int izquierda, int derecha) {
        if (izquierda >= derecha) return;
        String pivote = arreglo[(izquierda + derecha) / 2];
        int i = izquierda;
        int j = derecha;
        while (i <= j) {
            while (arreglo[i].compareTo(pivote) < 0) i++;
            while (arreglo[j].compareTo(pivote) > 0) j--;
            if (i <= j) {
                String aux = arreglo[i];
                arreglo[i] = arreglo[j];
                arreglo[j] = aux;
                i++;
                j--;
            }
        }
        if (izquierda < j) quicksort(arreglo, izquierda, j);
        if (i < derecha) quicksort(arreglo, i, derecha);
    }
    public static void mergesort(String[] arreglo) {
        if (arreglo.length < 2) return;
        int mitad = arreglo.length / 2;
        String[] izquierda = Arrays.copyOfRange(arreglo, 0, mitad);
        String[] derecha   = Arrays.copyOfRange(arreglo, mitad, arreglo.length);
        mergesort(izquierda);
        mergesort(derecha);
        combinar(arreglo, izquierda, derecha);
    }
    private static void combinar(String[] arreglo, String[] izquierda, String[] derecha) {
        int i = 0, j = 0, k = 0;
        while (i < izquierda.length && j < derecha.length) {
            if (izquierda[i].compareTo(derecha[j]) <= 0) {
                arreglo[k++] = izquierda[i++];
            } else {
                arreglo[k++] = derecha[j++];
            }
        }
        while (i < izquierda.length) {
            arreglo[k++] = izquierda[i++];
        }
        while (j < derecha.length) {
            arreglo[k++] = derecha[j++];
        }
    }
}
