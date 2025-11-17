import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfazPaqueteria {

    private JPanel panelPrincipal;
    private JTabbedPane panelTabs;

    /*Aqui se escribe el peso, cédula, entrega, etc
    muestra todos los paquetes registrados
    * */
    private JPanel panelRegistro;
    private JTextField txtPesoIngresar;
    private JTextField txtCedulaIngresar;
    private JComboBox cbxCiudadEntrada;
    private JComboBox cbxCiudadDestino;
    private JButton btnRegistrar;
    private JTextArea areaListado;
    private JLabel lblPesoIngresar;
    private JLabel lblCiudadEntrada;
    private JLabel lblCiudadDestino;
    private JLabel lblCedulaIngresar;
    private JScrollPane scrollRegistro;

    private JPanel panelBuscarPeso;
    private JTextField txtPesoBusqueda;
    private JButton btnBuscarPeso;
    private JTextArea areaResultadoPeso;
    private JLabel lblPesoBusqueda;
    private JScrollPane scrollPeso;

    private JPanel panelBuscarRuta;
    private JComboBox cbxRecepSecuencial;
    private JComboBox cbxEntregSecuencial;
    private JButton btnBuscarRuta;
    private JTextArea areaResultadoSecuencia;
    private JLabel lblRecepSecuencial;
    private JLabel lblEntregSecuencial;
    private JScrollPane scrollRuta;

    // Esta es la lista donde se guardan los paquetes
    private Lista listaPaquetes = new Lista();

    public InterfazPaqueteria() {

// Este es el boton para registrar
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pesoTxt = txtPesoIngresar.getText();
                String ciudadOrigen = cbxCiudadEntrada.getSelectedItem().toString();
                String ciudadDestino = cbxCiudadDestino.getSelectedItem().toString();
                String ced = txtCedulaIngresar.getText();


//Verifica que no hay campos vacíos
                if (pesoTxt.isEmpty() || ced.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Todos los campos deben estar llenos.",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Aqui hace la validacion para que solo permita ingresar numeros y no letras
                if (!pesoTxt.matches("[0-9.]+")) {
                    JOptionPane.showMessageDialog(null,
                            "El peso solo debe contener números.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Esta parte pide que  la cédula solo acpete numero y no letras
                if (!validarCedula(ced)) {
                    JOptionPane.showMessageDialog(null,
                            "La cédula debe tener 10 dígitos numéricos.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (ciudadOrigen.equals(ciudadDestino)) {
                    JOptionPane.showMessageDialog(null,
                            "La ciudad de entrega debe ser diferente a la ciudad de recepción.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double peso;
                try {
                    peso = Double.parseDouble(pesoTxt);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "El peso debe ser numérico.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                listaPaquetes.registrarPaquete(peso, ciudadOrigen, ciudadDestino, ced);
                areaListado.setText(listaPaquetes.obtenerColeccionPaquetes().toString());
            }
        });

//Hace la busqueda binaria segun esta en el aula virtual
        btnBuscarPeso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pesoTxt = txtPesoBusqueda.getText();

                if (pesoTxt.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Ingrese un peso para buscar.",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    

                    double pesoBuscado = Double.parseDouble(pesoTxt);
                    /* Si la convercion es correcta entonces si se puede hacer la busqueda binaria 
                    * usando el peso que el usuario ingreso */
                    

                    List<Paquete> encontrados =
                            listaPaquetes.buscarPorPesoBinario(listaPaquetes.obtenerColeccionPaquetes(),
                                    pesoBuscado);

                    if (encontrados.isEmpty()) {
                        areaResultadoPeso.setText("No existen paquetes con ese peso.");
                    } else {
                        areaResultadoPeso.setText(encontrados.toString());
                    }

                } catch (NumberFormatException ex) {

                    /* Esta parte se ejecuta solo si hay un error al convertir el textto
                    * en numero  */
                    JOptionPane.showMessageDialog(null,
                            "Debe ingresar solo números.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Esta parte es por busqueda secuencial segun el aula virtual

        btnBuscarRuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String origen = cbxRecepSecuencial.getSelectedItem().toString();
                String destino = cbxEntregSecuencial.getSelectedItem().toString();

// No se permite que la misma ciudad tenga el mismo origen y destino
                if (origen.equals(destino)) {
                    JOptionPane.showMessageDialog(null,
                            "Las ciudades deben ser diferentes.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // En esta parte se hace la busqueda secuencial segun su origen
                List<Paquete> ruta = listaPaquetes.buscarPorRutaSecuencial(
                        listaPaquetes.obtenerColeccionPaquetes(), origen, destino);

                if (ruta.isEmpty()) {
                    areaResultadoSecuencia.setText("No se encontraron paquetes con esa ruta.");
                } else {
                    areaResultadoSecuencia.setText(ruta.toString());
                }
            }
        });
    }

    // Esta parte del codigo hace que la cedula tenga solo 10 numeros
    private boolean validarCedula(String ced) {
        if (ced.length() != 10) return false;
        for (char c : ced.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    //Se crea el main para que corra el codigo
    public static void main(String[] args) {
        JFrame frame = new JFrame("InterfazPaqueteria");
        frame.setContentPane(new InterfazPaqueteria().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
