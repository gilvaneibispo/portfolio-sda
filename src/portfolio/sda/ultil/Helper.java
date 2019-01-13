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
}
