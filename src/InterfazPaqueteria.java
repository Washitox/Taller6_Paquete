import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfazPaqueteria {

    // Panel principal
    private JPanel panelPrincipal;
    private JTabbedPane panelTabs;

    // Panel Ingreso
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

    // Panel Buscar Peso
    private JPanel panelBuscarPeso;
    private JTextField txtPesoBusqueda;
    private JButton btnBuscarPeso;
    private JTextArea areaResultadoPeso;
    private JLabel lblPesoBusqueda;
    private JScrollPane scrollPeso;

    // Panel Buscar Ruta (Secuencial)
    private JPanel panelBuscarRuta;
    private JComboBox cbxRecepSecuencial;
    private JComboBox cbxEntregSecuencial;
    private JButton btnBuscarRuta;
    private JTextArea areaResultadoSecuencia;
    private JLabel lblRecepSecuencial;
    private JLabel lblEntregSecuencial;
    private JScrollPane scrollRuta;

    // Lógica
    private Lista listaPaquetes = new Lista();


    public InterfazPaqueteria() {

        // -----------------------------
        // BOTÓN REGISTRAR PAQUETE
        // -----------------------------
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pesoTxt = txtPesoIngresar.getText();
                String ciudadOrigen = cbxCiudadEntrada.getSelectedItem().toString();
                String ciudadDestino = cbxCiudadDestino.getSelectedItem().toString();
                String ced = txtCedulaIngresar.getText();

                // Validación de campos vacíos
                if (pesoTxt.isEmpty() || ced.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Todos los campos deben estar llenos.",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Validación de cédula
                if (!validarCedula(ced)) {
                    JOptionPane.showMessageDialog(null,
                            "La cédula debe tener 10 dígitos.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validación ciudades distintas
                if (ciudadOrigen.equals(ciudadDestino)) {
                    JOptionPane.showMessageDialog(null,
                            "La ciudad de entrega debe ser diferente a la ciudad de recepción.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validación peso numérico
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

                // Registrar paquete
                listaPaquetes.registrarPaquete(peso, ciudadOrigen, ciudadDestino, ced);

                // Mostrar lista en el área
                areaListado.setText(listaPaquetes.obtenerColeccionPaquetes().toString());
            }
        });


        // -----------------------------
        // BOTÓN BUSCAR POR PESO (BINARIO)
        // -----------------------------
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

                    List<Paquete> encontrados =
                            listaPaquetes.buscarPorPesoBinario(listaPaquetes.obtenerColeccionPaquetes(),
                                    pesoBuscado);

                    if (encontrados.isEmpty()) {
                        areaResultadoPeso.setText("No existen paquetes con ese peso.");
                    } else {
                        areaResultadoPeso.setText(encontrados.toString());
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Debe ingresar solo números.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // -----------------------------
        // BOTÓN BUSCAR RUTA SECUENCIAL
        // -----------------------------
        btnBuscarRuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String origen = cbxRecepSecuencial.getSelectedItem().toString();
                String destino = cbxEntregSecuencial.getSelectedItem().toString();

                if (origen.equals(destino)) {
                    JOptionPane.showMessageDialog(null,
                            "Las ciudades deben ser diferentes.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

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


    // -----------------------------------
    // VALIDAR CÉDULA (10 dígitos)
    // -----------------------------------
    private boolean validarCedula(String ced) {
        return ced.length() == 10;
    }


    // -----------------------------------
    // MAIN PARA EJECUTAR VENTANA
    // -----------------------------------
    public static void main(String[] args) {
        JFrame frame = new JFrame("InterfazPaqueteria");
        frame.setContentPane(new InterfazPaqueteria().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
