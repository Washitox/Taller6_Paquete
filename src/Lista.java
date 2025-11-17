import java.util.*;

public class Lista {

    private List<Paquete> coleccionPaquetes;

    public Lista() {
      
        coleccionPaquetes = new ArrayList<Paquete>();
    }


    public List<Paquete> obtenerColeccionPaquetes() {
        return coleccionPaquetes;
    }

  
    public void registrarPaquete(double peso, String ciudadRecepcion, String ciudadEntrega, String cedula) {
        // Uso el nuevo nombre de la variable
        coleccionPaquetes.add(new Paquete(peso, ciudadRecepcion, ciudadEntrega,  cedula));
        // Ordena los datos de la lista según el compareTo
        Collections.sort(coleccionPaquetes);
    }

    public List<Paquete> buscarPorPesoBinario(List<Paquete> lista, double pesoBuscado) {

        List<Paquete> resultado = new ArrayList<>();

        if(lista.isEmpty()){
            return resultado;
        }

        int left = 0;
        int right = lista.size() - 1;

        while (left <= right) {

            int mid = (left + right) / 2;
            Paquete p = lista.get(mid);

            if (p.getPeso() == pesoBuscado) {
                resultado.add(p);


                int i = mid - 1;
                while (i >= 0 && lista.get(i).getPeso() == pesoBuscado) {
                    resultado.add(lista.get(i));
                    i--;
                }


                int j = mid + 1;
                while (j < lista.size() && lista.get(j).getPeso() == pesoBuscado) {
                    resultado.add(lista.get(j));
                    j++;
                }

                return resultado;
            }

            if (p.getPeso() < pesoBuscado) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return resultado;
    }


    public List<Paquete> buscarPorRutaSecuencial(List<Paquete> lista, String ciudadRecepcion, String ciudadEntrega) {
        List<Paquete> resultado = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            Paquete p = lista.get(i);

            if (p.getCiduadRecepcion().equalsIgnoreCase(ciudadRecepcion) && p.getCiudadEntrega().equalsIgnoreCase(ciudadEntrega)) {
                resultado.add(p);
            }
        }

        return resultado;
    }
}
