package com.example.practica4algoritmos;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ControllerInterfaz {

    @FXML private TableView<RegistroClima> tablaClima;
    @FXML private ComboBox<String> comboColumnas;
    @FXML private TextArea areaTiempos;
    @FXML private HBox contenedorHistogramas;
    private List<RegistroClima> datos = new ArrayList<>();


    @FXML
    public void initialize() {
        TableColumn<RegistroClima, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha()));

        TableColumn<RegistroClima, String> colResumen = new TableColumn<>("Resumen");
        colResumen.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getResumen()));

        TableColumn<RegistroClima, String> colTipoPrec = new TableColumn<>("Tipo Precipitación");
        colTipoPrec.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipoPrecipitacion()));

        TableColumn<RegistroClima, Number> colTemp = new TableColumn<>("Temperatura");
        colTemp.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getTemperatura()));

        TableColumn<RegistroClima, Number> colTempApar = new TableColumn<>("Temp. Aparente");
        colTempApar.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getTemperaturaAparente()));

        TableColumn<RegistroClima, Number> colHumedad = new TableColumn<>("Humedad");
        colHumedad.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getHumedad()));

        TableColumn<RegistroClima, Number> colVientoVel = new TableColumn<>("Viento Velocidad");
        colVientoVel.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getVientoVelocidad()));

        TableColumn<RegistroClima, Number> colVientoAng = new TableColumn<>("Viento Ángulo");
        colVientoAng.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getVientoAngulo()));

        TableColumn<RegistroClima, Number> colVisibilidad = new TableColumn<>("Visibilidad");
        colVisibilidad.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getVisibilidad()));

        tablaClima.getColumns().addAll(
                colFecha, colResumen, colTipoPrec,
                colTemp, colTempApar, colHumedad,
                colVientoVel, colVientoAng, colVisibilidad
        );

        comboColumnas.setItems(FXCollections.observableArrayList(
                "Fecha",
                "Resumen",
                "TipoPrecipitacion",
                "Temperatura",
                "TemperaturaAparente",
                "Humedad",
                "VientoVelocidad",
                "VientoAngulo",
                "Visibilidad"
        ));
    }

    @FXML
    private void onCargarDatos() {
        datos = cargarLista("C:\\Users\\RedBo\\OneDrive\\Escritorio\\ALGORITMOS\\Algoritmos-P6\\src\\main\\java\\weatherHistory.csv");
        tablaClima.setItems(FXCollections.observableArrayList(datos));
        areaTiempos.setText("Datos cargados: " + datos.size() + " registros.\n");
        actualizarHistogramas();
    }

    @FXML
    private void onMedirTiempos() {
        if (datos.isEmpty()) {
            areaTiempos.setText("Primero carga los datos.");
            return;
        }
        String columna = comboColumnas.getValue();
        if (columna == null) {
            areaTiempos.setText("Selecciona una columna numérica.");
            return;
        }
        String res;
        switch (columna) {
            case "Fecha":
                res = medirTiemposOrdenamientosString(datos, columna);
                break;
            case "Resumen":
                res = medirTiemposOrdenamientosString(datos, columna);
                break;
            case "TipoPrecipitacion":
                res = medirTiemposOrdenamientosString(datos, columna);

                break;
            default:
                res = medirTiemposOrdenamientos(datos, columna);
                break;

        }
        areaTiempos.setText(res);
    }

    private List<RegistroClima> cargarLista(String file){
        return Main.cargarLista(file);
    }
    private String medirTiemposOrdenamientos(List<RegistroClima> datos, String columna) {
        return Main.medirTiemposOrdenamientos(datos, columna);
    }
    private String medirTiemposOrdenamientosString(List<RegistroClima> datos, String columna) {
        return Main.medirTiemposOrdenamientosStr(datos, columna);
    }

    private void actualizarHistogramas() {
        contenedorHistogramas.getChildren().clear();
        crearHistogramaFecha();
        crearHistogramaColumna("Temperatura",       r -> r.getTemperatura());
        crearHistogramaColumna("Temp. Aparente",    r -> r.getTemperaturaAparente());
        crearHistogramaColumna("Humedad",           r -> r.getHumedad());
        crearHistogramaColumna("Viento Velocidad",  r -> r.getVientoVelocidad());
        crearHistogramaColumna("Viento Ángulo",     r -> r.getVientoAngulo());
        crearHistogramaColumna("Visibilidad",       r -> r.getVisibilidad());
    }
    private void crearHistogramaFecha() {
        if (datos.isEmpty()) return;

        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (RegistroClima r : datos) {
            String fecha = r.getFecha();
            conteo.put(fecha, conteo.getOrDefault(fecha, 0) + 1);
        }

        int maxCategorias = 25;
        List<String> fechas = new ArrayList<>(conteo.keySet());
        if (fechas.size() > maxCategorias) {
            fechas = fechas.subList(0, maxCategorias);
        }

        int maxFreq = 0;
        for (String f : fechas) {
            int freq = conteo.get(f);
            if (freq > maxFreq) maxFreq = freq;
        }
        CategoryAxis ejeX = new CategoryAxis();
        ejeX.setTickLabelsVisible(false);
        ejeX.setTickMarkVisible(false);
        ejeX.setGapStartAndEnd(false);
        NumberAxis ejeY = new NumberAxis(0, maxFreq, Math.max(1, maxFreq / 4.0));
        ejeY.setTickLabelsVisible(false);
        ejeY.setMinorTickVisible(false);
        BarChart<String, Number> barra = new BarChart<>(ejeX, ejeY);
        barra.setLegendVisible(false);
        barra.setCategoryGap(0);
        barra.setBarGap(0);
        barra.setTitle("Fecha");
        barra.setPrefSize(160, 120);
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for (String f : fechas) {
            serie.getData().add(new XYChart.Data<>(f, conteo.get(f)));
        }
        barra.getData().add(serie);
        contenedorHistogramas.getChildren().add(barra);
    }
    @FunctionalInterface
    private interface GetterValor {
        double get(RegistroClima r);
    }
    private void crearHistogramaColumna(String titulo, GetterValor getter) {
        if (datos.isEmpty()) return;
        int numBarras = 15;
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;
        for (RegistroClima r : datos) {
            double v = getter.get(r);
            if (v < min) min = v;
            if (v > max) max = v;
        }
        if (min == max) max = min + 1;
        double anchoBin = (max - min) / numBarras;
        int[] frecuencias = new int[numBarras];
        for (RegistroClima r : datos) {
            double v = getter.get(r);
            int indice = (int)((v - min) / anchoBin);
            if (indice == numBarras) indice--;
            frecuencias[indice]++;
        }
        CategoryAxis ejeX = new CategoryAxis();
        ejeX.setTickLabelsVisible(false);
        ejeX.setTickMarkVisible(false);
        ejeX.setGapStartAndEnd(false);
        int maxFreq = 0;
        for (int f : frecuencias) if (f > maxFreq) maxFreq = f;
        NumberAxis ejeY = new NumberAxis(0, maxFreq, Math.max(1, maxFreq / 4.0));
        ejeY.setTickLabelsVisible(false);
        ejeY.setMinorTickVisible(false);
        BarChart<String, Number> barra = new BarChart<>(ejeX, ejeY);
        barra.setLegendVisible(false);
        barra.setCategoryGap(0);
        barra.setBarGap(0);
        barra.setTitle(titulo);
        barra.setPrefSize(160, 120);
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for (int i = 0; i < numBarras; i++) {
            double inicio = min + i * anchoBin;
            double fin = inicio + anchoBin;
            String etiqueta = String.format("%.1f-%.1f", inicio, fin);
            serie.getData().add(new XYChart.Data<>(etiqueta, frecuencias[i]));
        }
        barra.getData().add(serie);
        contenedorHistogramas.getChildren().add(barra);
    }
}
