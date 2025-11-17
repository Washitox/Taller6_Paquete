public class Paquete implements Comparable<Paquete>{
    private int tracking;
    private double peso;
    private String ciduadRecepcion;
    private String ciudadEntrega;
    private String cedula;
    private String estado;

    public Paquete(double peso, String ciduadRecepcion, String ciudadEntrega, String cedula) {
        this.peso = peso;
        this.ciduadRecepcion = ciduadRecepcion;
        this.ciudadEntrega = ciudadEntrega;
        this.cedula = cedula;
        this.estado = "Receptado";
    }

    public int getTracking() {
        
        return tracking;
    }

    public void setTracking(int tracking) {
        
        this.tracking = tracking;
    }

    public double getPeso() {
        
        return peso;
    }

    public void setPeso(double peso) {
        
        this.peso = peso;
    }

    public String getCiduadRecepcion() {
        
        return ciduadRecepcion;
    }

    public void setCiduadRecepcion(String ciduadRecepcion) {
        
        this.ciduadRecepcion = ciduadRecepcion;
    }

    public String getCiudadEntrega() {
        
        return ciudadEntrega;
    }

    public void setCiudadEntrega(String ciudadEntrega) {
        
        this.ciudadEntrega = ciudadEntrega;
    }

    public String getCedula() {
        
        return cedula;
    }

    public void setCedula(String cedula) {
        
        this.cedula = cedula;
    }

    public String getEstado() {
        
        return estado;
    }

    public void setEstado(String estado) {
        
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Paqueteria" +
                "\nTracking: " + tracking +
                "\nPeso: " + peso +
                "\nCiduad Recepcion: " + ciduadRecepcion +
                "\nCiudad Entrega: " + ciudadEntrega +
                "\nCedula: " + cedula +
                "\nEstado: " + estado +
                "\n";
    }
    
    public int compareTo(Paquete p) {
        return Double.compare(this.peso, p.peso);
    }

}
