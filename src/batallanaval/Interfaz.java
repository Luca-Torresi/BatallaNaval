
package batallanaval;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Interfaz extends JFrame{
    
    public JPanel panel;
    public JTextArea areaTextoIzq, areaTextoDer, infoBarcos, reglas;
    public JTextField cajaTextoIzq, cajaTextoDer;
    public JLabel titulo, ingresarCoordenadas, mensaje1, mensaje2, subrayado, intentos, puntuacion, ubicarBarco, posicionBarco, orientacionBarco, flecha;
    public JComboBox listaDesplegable;
    public ArrayList<JCheckBox> casillasVerificacion;
    
    public Interfaz(){        
        setTitle("BATALLA NAVAL");
        setSize(1060,565);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        iniciarComponentes();
    }
    
    private void iniciarComponentes(){
        colocarPanel();
        colocarAreasDeTexto();
        colocarEtiquetas();        
        colocarListaDesplegable();
        colocarCajasDeTexto();
    }
    
    private void colocarPanel(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        this.getContentPane().add(panel);        
    }
    
    private void colocarAreasDeTexto(){
        areaTextoDer = new JTextArea();
        areaTextoDer.setBounds(450,70,365,320);
        areaTextoDer.setFont(new Font("Monospaced",Font.PLAIN,17));
        areaTextoDer.setEditable(false);
        areaTextoDer.setVisible(false);
        panel.add(areaTextoDer);
        
        areaTextoIzq = new JTextArea();
        areaTextoIzq.setBounds(50,70,365,320);
        areaTextoIzq.setFont(new Font("Monospaced",Font.PLAIN,17));
        areaTextoIzq.setEditable(false);
        panel.add(areaTextoIzq);                
        
        infoBarcos = new JTextArea();        
        infoBarcos.setBounds(525,130,160,122);
        infoBarcos.setFont(new Font("Monospaced",Font.PLAIN,17));
        infoBarcos.setEditable(false);
        panel.add(infoBarcos);
        
        for(int i=0; i<5; i++){
            infoBarcos.append(BatallaNaval.nombresBarcos[i] + "\n");
        }
        
        reglas = new JTextArea();
        reglas.setBounds(50,410,910,75);
        reglas.setFont(new Font("Monospaced",Font.PLAIN,17));
        reglas.setEditable(false);
        panel.add(reglas);
        
        reglas.setText("La coordenada ingresada representa la posición incial, luego el barco se completa hacia la\n"
                + "derecha (Horizontal) o hacia abajo (Vertical). Dos barcos no pueden ser ubicados uno en\n"
                + "contacto con el otro, mientras que los barcos enemimgos sí.");
    }
    
    private void colocarEtiquetas(){
        titulo = new JLabel("------------ BATALLA NAVAL ----------------------------------------------------");
        titulo.setBounds(45,25,800,25);
        
        ingresarCoordenadas = new JLabel("Ingresar coordenadas:");        
        ingresarCoordenadas.setBounds(450,410,215,30);
        ingresarCoordenadas.setVisible(false);
        
        mensaje1 = new JLabel();
        mensaje1.setBounds(450,360,550,25);
        
        mensaje2 = new JLabel();
        mensaje2.setBounds(450,445,590,25);
        mensaje2.setVisible(false);
        
        intentos = new JLabel();
        intentos.setBounds(830,265,120,25);
        intentos.setVisible(false);
        
        puntuacion = new JLabel();
        puntuacion.setBounds(825,495,205,25);
        puntuacion.setVisible(false);
        
        ubicarBarco = new JLabel("Elija la posición de los barcos");
        ubicarBarco.setBounds(500,85,365,25);
        
        orientacionBarco = new JLabel("Orientación:");
        orientacionBarco.setBounds(500,280,125,30);
        
        posicionBarco = new JLabel("Coordenadas:");
        posicionBarco.setBounds(500,320,125,30);
        
        subrayado = new JLabel("");
        subrayado.setBounds(490,110,380,10);
        
        flecha = new JLabel("→");
        
        ArrayList<JLabel> etiquetas = new ArrayList<>();
        etiquetas.add(titulo);
        etiquetas.add(ingresarCoordenadas);
        etiquetas.add(mensaje1);
        etiquetas.add(mensaje2);
        etiquetas.add(intentos);
        etiquetas.add(puntuacion);
        etiquetas.add(ubicarBarco);
        etiquetas.add(orientacionBarco);
        etiquetas.add(posicionBarco);
        etiquetas.add(subrayado);
        etiquetas.add(flecha);
        
        for(JLabel e:etiquetas){
            e.setFont(new Font("Monospaced",Font.PLAIN,17));
            e.setOpaque(true);
            e.setBackground(Color.WHITE);
            panel.add(e);
        }                
    }    
    
    private void colocarCasillasDeVerificacion(){       
        casillasVerificacion = new ArrayList<>();
        
        for(int i=0; i<5; i++){          
            casillasVerificacion.add(new JCheckBox());
        }
        
        int ejeX = 830, ejeY = 133;
        for(JCheckBox c:casillasVerificacion){            
            c.setBounds(ejeX,ejeY,21,21);
            c.setBackground(Color.WHITE);
            c.setEnabled(false);            
            panel.add(c);
            
            ejeY+=24;
        }
    }
    
    public void colocarListaDesplegable(){
        String[] lista = {"Horizontal","Vertical"};
        
        listaDesplegable = new JComboBox(lista);
        listaDesplegable.setBounds(640,280,150,30);        
        listaDesplegable.setFont(new Font("Monospaced",Font.PLAIN,17));
        listaDesplegable.setBackground(Color.WHITE);        
        panel.add(listaDesplegable);
    }    
    
    private void colocarCajasDeTexto(){
        cajaTextoIzq = new JTextField();
        cajaTextoIzq.setBounds(640,320,75,30);
        panel.add(cajaTextoIzq);
        
        KeyListener eventoTeclado1 = new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){}

            @Override
            public void keyPressed(KeyEvent e){}

            @Override
            public void keyReleased(KeyEvent e){
                if (e.getKeyChar() == '\n'){
                    synchronized (BatallaNaval.lock) {
                        BatallaNaval.coordenadas = cajaTextoIzq.getText().toUpperCase();
                        BatallaNaval.orientacion = String.valueOf(listaDesplegable.getSelectedItem());
                        BatallaNaval.lock.notify();

                        cajaTextoIzq.setText("");
                    }
                }
            }            
        };        
        cajaTextoIzq.addKeyListener(eventoTeclado1);        
        
        cajaTextoDer = new JTextField();
        cajaTextoDer.setBounds(670,410,75,30);
        cajaTextoDer.setVisible(false);
        panel.add(cajaTextoDer);      
              
        KeyListener eventoTeclado2 = new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e){}
            
            @Override
            public void keyPressed(KeyEvent e){}
            
            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyChar() == '\n'){
                    synchronized (BatallaNaval.lock){
                        BatallaNaval.coordenadas = cajaTextoDer.getText().toUpperCase();
                        BatallaNaval.lock.notify();
                        
                        cajaTextoDer.setText("");
                    }
                }
            }
        };        
        cajaTextoDer.addKeyListener(eventoTeclado2);
    }
    
    public void cerrarPrimerosComponentes(){
        panel.remove(reglas);
        panel.remove(ubicarBarco);
        panel.remove(posicionBarco);
        panel.remove(cajaTextoIzq);
        panel.remove(orientacionBarco);
        panel.remove(listaDesplegable);
        panel.remove(mensaje1);
        panel.remove(subrayado);
        panel.remove(flecha);
        
        areaTextoDer.setVisible(true);
        infoBarcos.setBounds(860,130,160,122);
        ingresarCoordenadas.setVisible(true);
        cajaTextoDer.setVisible(true);
        mensaje2.setVisible(true);
        intentos.setVisible(true);
        puntuacion.setVisible(true);
        colocarCasillasDeVerificacion();
        
        panel.revalidate();
        panel.repaint();                
    }
    
    public void cerrarSegundosComoponentes(){                
        panel.remove(ingresarCoordenadas);
        panel.remove(cajaTextoDer);
        
        mensaje2.setLocation(50, 415);
        
        panel.revalidate();
        panel.repaint();
    }        
}
