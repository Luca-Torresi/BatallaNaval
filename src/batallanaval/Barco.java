
package batallanaval;

import java.util.ArrayList;
import javax.swing.JCheckBox;

public class Barco {
    
    public static int enemigosHundidos, propiosHundidos;    
    private int longitud;
    private int cantDañado = 0;
    private JCheckBox casilla;
    private ArrayList<int[]> espacios = new ArrayList<>();    
   
    public Barco(int longitud){
        this.longitud = longitud;
    }
    
    public boolean ubicarBarco(String orientacion, int posInicialX, int posInicialY){
        boolean posicionValida = true;                
        
        int[] posicionInicial = {posInicialX,posInicialY};
        espacios.clear();
        espacios.add(posicionInicial);
        
        for(int i=1; i<longitud; i++){                        
            if(orientacion.equals("Horizontal")){
                posInicialY++;
            } else if(orientacion.equals("Vertical")){
                posInicialX++;
            }
            
            int[] nuevaPosicion = {posInicialX,posInicialY};
            if(fueraDelMapa(nuevaPosicion)){
                posicionValida = false;     
                break;
            }                
                            
            espacios.add(nuevaPosicion);
        }                
        return posicionValida;
    }
        
    public boolean colocarEnTablero(Tablero tablero){        
        for(int[] i:espacios){            
            if(tablero.getMapa()[i[0]][i[1]].equals("B")){
                return false;
            }                                                  
        }
        
        for(int[] i:espacios){
            tablero.getMapa()[i[0]][i[1]] = "B";                
        }                
        return true;
    }
    
    public boolean barcosAdyacentes(){
        String espacio = "";
        int[] ad1, ad2, ad3, ad4;

        ArrayList<int[]> adyacentes = new ArrayList<>();
        adyacentes.add(ad1 = new int[2]);
        adyacentes.add(ad2 = new int[2]);
        adyacentes.add(ad3 = new int[2]);
        adyacentes.add(ad4 = new int[2]);
        
        for(int[] i:espacios){
            for(int[] a:adyacentes){
                a[0] = i[0];
                a[1] = i[1];
            }
            ad1[0]++;
            ad2[0]--;
            ad3[1]++;
            ad4[1]--;
            
            for(int[] a:adyacentes){                
                try{
                    espacio = BatallaNaval.tableroIzquierda.getMapa()[a[0]][a[1]];
                } catch(ArrayIndexOutOfBoundsException e){}
                
                if(espacio.equals("B")){
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean esGolpeado(Tablero tablero, int[] coordenadasNumericas, int opcion){
        boolean acierto = false;
        
        int fila = coordenadasNumericas[0];
        int columna = coordenadasNumericas[1];
        
        for(int[] i:espacios){
            if(i[0]==fila && i[1]==columna){                                
                cantDañado++;
                
                switch(opcion){
                    case 1:
                        tablero.getMapa()[fila][columna] = "B";
                        esHundido(tablero,opcion);
                        break;
                    case 2: 
                        Rival.barcoEncontrado = true;
                        Rival.aciertos++;
                        
                        tablero.getMapa()[fila][columna] = "×";
                        esHundido(tablero,opcion);
                        break;
                }                
                acierto = true;
            }
        }
        
        if(acierto == false){
            tablero.getMapa()[fila][columna] = "-";
        }        
        return acierto;
    }
    
    public void esHundido(Tablero tablero, int opcion){
        if(cantDañado == longitud){

            switch(opcion){
                case 1:
                    for(int[] i:espacios){
                        tablero.getMapa()[i[0]][i[1]] = "H";
                    }
                    casilla.setSelected(true);
                    enemigosHundidos++;
                    break;
                case 2:
                    Rival.establecerBarcoDeMenorTamaño();
                    Rival.barcoEncontrado = false;
                    Rival.aciertos = 0;
                    propiosHundidos++;
                    break;
            }
        }
    }        
    
    public static boolean fueraDelMapa(int[] espacio){
        return (espacio[0]<0 | espacio[0]>9 | espacio[1]<0 | espacio[1]>9);
    }            
    
    public int getLongitud(){
        return longitud;
    }
    public void setLongitud(int longitud){
        this.longitud = longitud;
    }
    public int getCantDañado(){
        return cantDañado;
    }
    public void setCantDañado(int cantDañado){
        this.cantDañado = cantDañado;
    }
    public ArrayList<int[]> getEspacios(){
        return espacios;
    }
    public JCheckBox getCasilla(){
        return casilla;
    }
    public void setCasilla(JCheckBox casilla){
        this.casilla = casilla;
    }
    public void setEspacios(ArrayList<int[]> espacios){
        this.espacios = espacios;
    }
}
