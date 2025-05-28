
package batallanaval;

import javax.swing.JTextArea;

public class Tablero {
    
    static char[] letras = {'A','B','C','D','E','F','G','H','I','J'};
    private String[][] mapa;
    private JTextArea areaTexto;
    
    public Tablero(JTextArea areaTexto){
        this.areaTexto = areaTexto;
    }
    
    public void mostrarTablero(){        
        areaTexto.setText("     0  1  2  3  4  5  6  7  8  9\n");
        areaTexto.append("   ┌─────────────────────────────────┐\n");
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                switch(j){
                    case 0:
                        areaTexto.append(" " +letras[i] + " │ " + mapa[i][j] + "  ");
                        break;
                    case 9:
                        areaTexto.append(mapa[i][j] + " │");
                        break;                
                    default:
                        areaTexto.append(mapa[i][j] + "  ");
                        break;
                }
            }
            areaTexto.append("\n");
        }
        areaTexto.append("   └─────────────────────────────────┘");
    }
       
    public String[][] getMapa(){
        return mapa;
    }
    public void setMapa(String[][] mapa){
        this.mapa = mapa;
    }
    public JTextArea getAreaTexto(){
        return areaTexto;
    }
    public void setAreaTexto(JTextArea areaTexto){
        this.areaTexto = areaTexto;
    }
}

