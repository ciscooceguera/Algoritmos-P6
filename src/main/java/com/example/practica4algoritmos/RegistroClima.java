package com.example.practica4algoritmos;
public class RegistroClima {
    // Atributos
    private String fecha;
    private String resumen;
    private String tipoPrecipitacion;
    private double temperatura;
    private double temperaturaAparente;
    private double humedad;
    private double vientoVelocidad;
    private double vientoAngulo;
    private double visibilidad;
    // Constructor, recibe todos los valores que se leen en un renglón del archivo CSV
    public RegistroClima(
            String fecha, String resumen, String tipoPrecipitacion,
            double temperatura, double temperaturaAparente,
            double humedad, double vientoVelocidad, double vientoAngulo,
            double visibilidad
    ) {
        // Guarda los parámetros recibidos en los atributos
        this.fecha = fecha;
        this.resumen = resumen;
        this.tipoPrecipitacion = tipoPrecipitacion;
        this.temperatura = temperatura;
        this.temperaturaAparente = temperaturaAparente;
        this.humedad = humedad;
        this.vientoVelocidad = vientoVelocidad;
        this.vientoAngulo = vientoAngulo;
        this.visibilidad = visibilidad;

    }
    // Getters
    public String getFecha() {
        return fecha;
    }
    public String getResumen() {
        return resumen;
    }
    public String getTipoPrecipitacion() {
        return tipoPrecipitacion;
    }
    public double getTemperatura() {
        return temperatura;
    }
    public double getTemperaturaAparente() {
        return temperaturaAparente;
    }
    public double getHumedad() {
        return humedad;
    }
    public double getVientoVelocidad() {
        return vientoVelocidad;
    }
    public double getVientoAngulo() {
        return vientoAngulo;
    }
    public double getVisibilidad() {
        return visibilidad;
    }
}
