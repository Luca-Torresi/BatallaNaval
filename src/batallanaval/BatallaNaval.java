
package batallanaval;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BatallaNaval{

    static final Object lock = new Object();
    static final String[] nombresBarcos = {"Portaaviones(5)","Acorazado(4)","Submarino(3)","Crucero(3)","Destructor(2)"};
    
    static ArrayList<Barco> flotaEnemiga = new ArrayList();
    static ArrayList<Barco> flotaPropia = new ArrayList();
    static Tablero tableroIzquierda, tableroDerecha;
    static String coordenadas, orientacion;
    static Interfaz interfaz;
    static int cantIntentos = 0;
    
    public static void main(String[] args){       
        interfaz = new Interfaz();
        interfaz.setVisible(true);

        int record = mostrarMejorPuntaje();
        
        generarFlotaPropia();
        generarFlotaEnemiga();        
        comenzarGuerra();
        mostrarResultados(record);
        
        try{
            Thread.sleep(30000);
        } catch(InterruptedException e){}
        
        interfaz.dispose();
    }            
    
    public static void generarFlotaPropia(){
        tableroIzquierda = new Tablero(interfaz.areaTextoIzq);        
        completarTablero(tableroIzquierda);
        crearBarcos(flotaPropia);
        tableroIzquierda.mostrarTablero();
        
        int altura = 132;
        for(Barco b:flotaPropia){
            interfaz.flecha.setBounds(500,altura,20,25);
            
            while(true){
                int[] coordenadasNumericas = new int[2];
                
                while(true){
                    synchronized(lock){
                        try{
                            lock.wait();
                        } catch(InterruptedException e){}
                    }
                    
                    try{
                        validarCoordenadas(coordenadas, coordenadasNumericas);
                        break;
                    } catch(StringIndexOutOfBoundsException | NumberFormatException e){
                        interfaz.mensaje1.setText("La posición ingresada no es válida");
                    }
                }
                                
                if(b.ubicarBarco(orientacion, coordenadasNumericas[0],coordenadasNumericas[1])){
                    if(b.barcosAdyacentes()){
                        if(b.colocarEnTablero(tableroIzquierda)){
                            break;                        
                        }                        
                    } else{
                        interfaz.mensaje1.setText("Ya existe un barco en esa posición");                        
                    }
                } else{
                    interfaz.mensaje1.setText("No es posible asignar el barco a la posición ingresada");
                }                
            }
            interfaz.mensaje1.setText("");
            tableroIzquierda.mostrarTablero();
            altura +=24;                      
        }
        
        try{
            Thread.sleep(500);
        } catch(InterruptedException e){}
        
        interfaz.cerrarPrimerosComponentes();
    }
    
    public static void generarFlotaEnemiga(){
        tableroDerecha = new Tablero(interfaz.areaTextoDer);
        completarTablero(tableroDerecha);
        crearBarcos(flotaEnemiga);
        tableroDerecha.mostrarTablero();

        int iterador = 0;
        boolean posicionValida, lugarVacio;
        for(Barco b:flotaEnemiga){
            b.setCasilla(interfaz.casillasVerificacion.get(iterador));
                        
            do{                
                do{
                    int num = Double.valueOf(Math.random()*2).intValue();
                    orientacion = (num==0)? "Horizontal" : "Vertical";
                    
                    int posInicialX = Double.valueOf(Math.random()*10).intValue();
                    int posInicialY = Double.valueOf(Math.random()*10).intValue();
                    
                    posicionValida = b.ubicarBarco(orientacion,posInicialX,posInicialY);                
                } while(posicionValida == false);
                                
                lugarVacio = b.colocarEnTablero(tableroDerecha);
            } while(lugarVacio == false);
            
            iterador++;            
        }
        Rival.llenarArreglos();
    }
      
    public static void comenzarGuerra(){
        completarTablero(tableroDerecha);
        tableroDerecha.mostrarTablero();

        do{             
            int[] coordenadasNumericas = new int[2];
                        
            while(true){                 
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e){}
                }
                
                try{
                    validarCoordenadas(coordenadas,coordenadasNumericas);                    
                    if(tableroDerecha.getMapa()[coordenadasNumericas[0]][coordenadasNumericas[1]].equals(" ")){
                        break;
                    } else{
                        interfaz.mensaje2.setText("La coordenada ya fue ingresada");
                    }                    
                } catch(StringIndexOutOfBoundsException | NumberFormatException e){
                    interfaz.mensaje2.setText("La coordenada ingresada no es válida");
                }                                                                                                
            }
            interfaz.mensaje2.setText("");
                        
            for(Barco b:flotaEnemiga){
                if(b.esGolpeado(tableroDerecha,coordenadasNumericas,1)){
                    break;
                }
            }
            tableroDerecha.mostrarTablero();
            
            cantIntentos++;
            interfaz.intentos.setText("\nIntentos: " + cantIntentos);
                        
            if(Rival.turno()){
                break;
            }
            
        } while(Barco.enemigosHundidos<5);
        interfaz.cerrarSegundosComoponentes();               
    }
    
    public static void mostrarResultados(int record){
        if(Barco.propiosHundidos<5){                        
            interfaz.mensaje2.setText("Has hundido la flota enemiga y ganado la guerra!");
            
            if(cantIntentos<record){
                actualizarMejorPuntaje(record);
            }            
            
        } else if(Barco.enemigosHundidos<5){
            interfaz.mensaje2.setText("El enemigo ha hundido tu flota y perdiste la guerra!");
        } else{
            interfaz.mensaje2.setText("Ambas flotas fueron hundidas al mismo tiempo! Es un empate");
        }        
    }
            
    public static void crearBarcos(ArrayList<Barco> flota){
        boolean repeticion = false;
        for(int i=5; i>=2; i--){
            flota.add(new Barco(i));
            
            if(repeticion==false && i==3){
                repeticion = true;
                i++;
            }            
        }
    }
    
    public static void completarTablero(Tablero tablero){
        String mapa[][] = new String[10][10];
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){                        
                mapa[i][j] = " ";
            }    
        }
        tablero.setMapa(mapa);
    }
    
    public static void validarCoordenadas(String coordenadas, int[] coordenadasNumericas){        
        int indice = -1;
                                
        for(int i=0; i<Tablero.letras.length; i++){
            if(coordenadas.charAt(0) == Tablero.letras[i]){
                indice = i;
                break;
            }
        }                           
        coordenadasNumericas[0] = indice;
        coordenadasNumericas[1] = Integer.parseInt(String.valueOf(coordenadas.charAt(1)));
                
        if(coordenadas.length()!=2 || coordenadasNumericas[0]==-1){
            throw new NumberFormatException();
        }
    }
    
    public static int mostrarMejorPuntaje(){
        int record = 0;
        
        try {
            FileReader fr = new FileReader("Puntaje.txt");
            BufferedReader bf = new BufferedReader(fr);
            record = Integer.parseInt(bf.readLine());
        } catch(IOException e){
            System.out.println("No fue posible leer el archivo");
        }
        
        interfaz.puntuacion.setText("Mejor puntuación: " + record);
        return record;
    }    
    
    public static void actualizarMejorPuntaje(int record){               
        try {
            try (FileWriter fw = new FileWriter("Puntaje.txt")) {
                fw.write(String.valueOf(cantIntentos));
            }
        } catch (IOException e){}
        
        interfaz.puntuacion.setText("Mejor puntuación: " + cantIntentos);
    }
}    
