package portfolio.sda.ultil;

/**
 * @author Gilvanei e Kelvin
 */
public class Helper {

    /**
     * Retorna a extensão em um nome de um aquivo.
     *
     * @param filename String
     * @return extension String
     */
    public static String getFileExtension(String filename) {

        /* verifica se existe pontos na string, caso sim retorna uma substring 
        a partir do ultimo ponto, não o incluindo */
        return filename.contains(".") ? filename.substring(
                filename.lastIndexOf(".") + 1) : null;
    }
    
    public static void printError(Exception ex){
        System.err.println(ex.getMessage());
    }
    
     /**
     * Recebe um numero inteiro e transforma em string.
     * @param num
     * @return 
     */
    public static String numberToString(int num){
        return num + "";
    }
    
    /**
     * Recebe um numero double e transforma em string.
     * @param num
     * @return 
     */
    public static String numberToString(double num){
        return num + "";
    }
    
    public static void messageEncode(){
        
    }
    
    public static String[] messageDencode(){
        return null;
    }
}
