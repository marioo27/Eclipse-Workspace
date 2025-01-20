package Auuuuuu;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Button;

public class Ventana extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ventana frame = new Ventana();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Ventana() {
        getContentPane().setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new GridLayout(5, 5, 1, 1));

        Button button_1 = new Button("Botón Inútil");
        getContentPane().add(button_1);

        JSeparator separator = new JSeparator();
        getContentPane().add(separator);

        // Primer slider para controlar el componente rojo del color
        JSlider slider = new JSlider(0, 255);
        getContentPane().add(slider);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int valor1 = slider.getValue();
                getContentPane().setBackground(new Color(valor1, 255 - valor1, 150));
            }
        });

        // Segundo slider para controlar el componente verde del color
        JSlider slider2 = new JSlider(0, 255);
        getContentPane().add(slider2);

        slider2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int valorVerde = slider2.getValue();
                getContentPane().setBackground(new Color(valorVerde, 255-valorVerde, 0));
            }
        });
    }
}
