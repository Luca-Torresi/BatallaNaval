
package batallanaval;

import static batallanaval.BatallaNaval.flotaPropia;
import static batallanaval.BatallaNaval.tableroIzquierda;
import static batallanaval.Barco.fueraDelMapa;
import java.util.ArrayList;
import java.util.Arrays;

public class Rival {
    
    static ArrayList<Integer> longitudesBarcos = new ArrayList<>();
    static ArrayList<Integer> arregloEspaciosContiguos = new ArrayList<>();
    static ArrayList<Integer> filasAleatorias = new ArrayList<>();
    static ArrayList<Integer> columnasAleatorias = new ArrayList<>();
    static Integer aciertos = 0;    
    static int[] nuevasCoordenadas = new int[2];
    static int[] primerasCoordenadas = new int[2];
    static int barcoMenorTamaño = 2;
    static boolean barcoEncontrado;
    static int direccion;    
    
    public static boolean turno(){                
        calcularCoordenadas();
        
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}
        
        for(Barco b:flotaPropia){
            if(b.esGolpeado(tableroIzquierda, nuevasCoordenadas,2)){
                if(aciertos==1){
                    primerasCoordenadas = Arrays.copyOf(nuevasCoordenadas,2);
                }
                break;
            }
        }
        tableroIzquierda.mostrarTablero();
        
        return Barco.propiosHundidos == 5;
    }
    
    public static void calcularCoordenadas(){
        if(barcoEncontrado == true){
            String espacio;
            
            while(true){
                if(aciertos == 1){
                    nuevasCoordenadas = Arrays.copyOf(primerasCoordenadas,2);
                    direccion = Double.valueOf(Math.random() * 4).intValue();
                }
                
                try{
                    espacio = tableroIzquierda.getMapa()[nuevasCoordenadas[0]][nuevasCoordenadas[1]];
                } catch(ArrayIndexOutOfBoundsException e){
                    espacio = "-";
                }
                
                if(aciertos>=2 && espacio.equals("-")){
                    nuevasCoordenadas = Arrays.copyOf(primerasCoordenadas,2);
                    cambiarDireccion();
                }                
                espacioSiguiente();                  
                
                if(fueraDelMapa(nuevasCoordenadas)){
                    continue;
                }
                 
                espacio = tableroIzquierda.getMapa()[nuevasCoordenadas[0]][nuevasCoordenadas[1]];
                if(espacio.equals("B") || espacio.equals(" ")){                    
                    break;
                }
            }
            
        } else{        
            while(true){
                int num, aleatorio;
                num = Double.valueOf(Math.random() * 2).intValue();
                
                switch(num){                    
                    case 0:
                        if(filasAleatorias.isEmpty()){
                            continue;
                        }
                        
                        coordenadasAleatorias(num);
                        if(contarEspacios(num)){
                            filasAleatorias.remove(Integer.valueOf(nuevasCoordenadas[0]));
                            continue;
                        }
                        aleatorio = Double.valueOf(Math.random() * arregloEspaciosContiguos.size()).intValue();
                        nuevasCoordenadas[1] = arregloEspaciosContiguos.get(aleatorio);
                        break;
                            
                    case 1:
                        if(columnasAleatorias.isEmpty()){
                            continue;
                        }
                        
                        coordenadasAleatorias(num);
                        if(contarEspacios(num)){
                            columnasAleatorias.remove(Integer.valueOf(nuevasCoordenadas[1]));
                            continue;
                        }                        
                        aleatorio = Double.valueOf(Math.random() * arregloEspaciosContiguos.size()).intValue();
                        nuevasCoordenadas[0] = arregloEspaciosContiguos.get(aleatorio);
                        break;
                }
                
                break;                
            }
        }
    }
    
    public static void espacioSiguiente(){
        switch(direccion){
            case 0:
                nuevasCoordenadas[0]++;
                break;
            case 1:
                nuevasCoordenadas[1]++;
                break;
            case 2:
                nuevasCoordenadas[0]--;
                break;
            case 3:
                nuevasCoordenadas[1]--;
                break;
        }
    }
    
    public static void cambiarDireccion(){
        switch(direccion){
            case 0:
                direccion = 2;
                break;
            case 1:
                direccion = 3;
                break;
            case 2:
                direccion = 0;
                break;
            case 3:
                direccion = 1;
                break;
        }
    }       
    
    public static void coordenadasAleatorias(int indice){
        ArrayList<Integer> lineas = new ArrayList<>();
        String espacio;
        
        for(int max=10; max>1; max--){
                                      
            switch(indice){
                case 0:
                    for(Integer i:filasAleatorias){
                        int espaciosVacios = 0;
                        
                        for(int j=0; j<10; j++){
                            espacio = tableroIzquierda.getMapa()[i][j];                            
                            if(alrededores(i,j) == false && (espacio.equals(" ") || espacio.equals("B"))){
                                espaciosVacios++;
                            }
                        }
                        if(espaciosVacios == max){
                            lineas.add(i);
                        }
                    }                    
                    break;
                    
                case 1:
                    for(Integer j:columnasAleatorias){
                        int espaciosVacios = 0;
                        
                        for(int i=0; i<10; i++){
                            espacio = tableroIzquierda.getMapa()[i][j];                            
                            if(alrededores(i,j) == false && (espacio.equals(" ") || espacio.equals("B"))){
                                espaciosVacios++;
                            }
                        }
                        if(espaciosVacios == max){
                            lineas.add(j);
                        }
                    }
                    break;
            }
            
            if(! lineas.isEmpty()){
                int aleatorio = Double.valueOf(Math.random() * lineas.size()).intValue();
                nuevasCoordenadas[indice] = lineas.get(aleatorio);
                
                break;
            }
        }
    }
    
    public static boolean contarEspacios(int num){
        ArrayList<Integer> arregloTemporal = new ArrayList<>();
        int masLargo = 0;
        String espacio;
        
        arregloEspaciosContiguos.clear();
        switch(num){
            case 0:
                int x = nuevasCoordenadas[0];
                for(int j=0; j<10; j++){
                    espacio = tableroIzquierda.getMapa()[x][j];
                    
                    if(alrededores(x,j) == false && (espacio.equals(" ") || espacio.equals("B"))){
                        arregloTemporal.add(j);
                    } else{
                        if(arregloTemporal.size() > masLargo){
                            masLargo = arregloTemporal.size();
                            arregloEspaciosContiguos.clear();
                            arregloEspaciosContiguos.addAll(arregloTemporal);
                        }   
                        arregloTemporal.clear();
                    }
                }
                if(arregloTemporal.size() > masLargo){
                    masLargo = arregloTemporal.size();
                    arregloEspaciosContiguos.clear();
                    arregloEspaciosContiguos.addAll(arregloTemporal);
                }
                break;
                
            case 1:
                int y = nuevasCoordenadas[1];
                for(int i=0; i<10; i++){
                    espacio = tableroIzquierda.getMapa()[i][y];
                    
                    if(alrededores(i,y) == false && (espacio.equals(" ") || espacio.equals("B"))){
                        arregloTemporal.add(i);                        
                    } else{
                        if(arregloTemporal.size() > masLargo){
                            masLargo = arregloTemporal.size();
                            arregloEspaciosContiguos.clear();
                            arregloEspaciosContiguos.addAll(arregloTemporal);
                        }
                        arregloTemporal.clear();
                    }
                }
                if(arregloTemporal.size() > masLargo){
                    masLargo = arregloTemporal.size();
                    arregloEspaciosContiguos.clear();
                    arregloEspaciosContiguos.addAll(arregloTemporal);
                }
                break;
        }
        return barcoMenorTamaño > masLargo;
    }
    
    public static boolean alrededores(int x, int y){
        String espacio;
        int[] ad1, ad2, ad3, ad4;

        ArrayList<int[]> adyacentes = new ArrayList<>();
        adyacentes.add(ad1 = new int[2]);
        adyacentes.add(ad2 = new int[2]);
        adyacentes.add(ad3 = new int[2]);
        adyacentes.add(ad4 = new int[2]);
        
        for(int[] a:adyacentes){
            a[0] = x;
            a[1] = y;
        }        
        ad1[0]++;
        ad2[0]--;
        ad3[1]++;
        ad4[1]--;
            
        int noVacios = 0;
        for(int[] a:adyacentes){
            try{
                espacio = BatallaNaval.tableroIzquierda.getMapa()[a[0]][a[1]];
            }catch (ArrayIndexOutOfBoundsException e){
                espacio = "-";
            }
            
            if(espacio.equals("×")){
                return true;
            }
            if(espacio.equals("-")){
                noVacios++;
            }
        }
        return noVacios==4;
    }
    
    public static void establecerBarcoDeMenorTamaño(){                
        longitudesBarcos.remove(aciertos);
        if(!longitudesBarcos.isEmpty()){
            barcoMenorTamaño = longitudesBarcos.get(0);
        }
    }
    
    public static void llenarArreglos(){                                
        longitudesBarcos.add(2);
        longitudesBarcos.add(3);
        longitudesBarcos.add(3);
        longitudesBarcos.add(4);
        longitudesBarcos.add(5);
        
        for(Integer i=0; i<10; i++){
            filasAleatorias.add(i);
            columnasAleatorias.add(i);
        }
    }
}


