package portfolio.sda;

import java.io.File;
import java.io.IOException;
import portfolio.sda.ultil.Helper;
import portfolio.sda.view.ImageBuilder;
import portfolio.sda.ultil.PathSetting;

/**
 *
 * @author Gilvanei e Kelvin
 */
public class PortfolioSda {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        
        
        
        
        File base_dir = new File(PathSetting.IMAGE_PATH + "img-teste");

        if (base_dir.isDirectory() && base_dir.exists()) {

            File[] files = base_dir.listFiles();

            if (files.length != 0) {

                for (File file : files) {
Thread.sleep(1000);
                    if (!file.isDirectory() && file.isFile()) {
                        ImageBuilder ih = new ImageBuilder();

                        ih.createDefaultImage(file);
                        ih.createThumbnail(file);
                    }
                    System.out.println("- - - -\nProcessando nova imagem...");
                    
                }
            }
        }
    }
}
