package portfolio.sda.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import portfolio.sda.ultil.PathSetting;

/**
 * @author Gilvanei e Kelvin
 */
public class ImageBuilder {

    // Define o caminho da imagem de fundo usadas nas imagens des posts.
    private static final String BACKGROUND_PATH = PathSetting.IMAGE_PATH + "back.png";

    //Define nomeclatiras padrão para os tipos de imagens.
    private static final String IMAGE_DEFAULT = "default";
    private static final String THUMBNAIL_IMAGE = "thumbnail";

    //Define as dimenções das imagens quadradas (1x1) usadas como thumbs.
    private final int thumbnailDimensions = 120;

    // Recebe as dimensões para cada redimencionamento de imagem. 
    private double imageResizingWidth = 0;
    private double imageResizingHeight = 0;

    private double backgroundResizingWidth = 0;
    private double backgroundResizingHeight = 0;

    private final double wDefault = 700.000;
    private final double hDefault = 400.000;

    /**
     * ******************* VERIFICAR FUNCIONAMENTO E DEPOIS COMENTAR
     */
    public void createDefaultImage(File frontImage) throws IOException {

        if (frontImage == null) {
            throw new IOException("Não foi definida uma imagem para uso!");
        }

        BufferedImage bckgBufferedImage = ImageIO.read(new File(BACKGROUND_PATH));
        BufferedImage frontBufferedImage = ImageIO.read(frontImage);

        calculateBackgroundDimensions(frontBufferedImage);
        calculateImageResizing(frontBufferedImage);

        bckgBufferedImage = resizingImage(bckgBufferedImage, backgroundResizingWidth, backgroundResizingHeight, Image.SCALE_DEFAULT);
        frontBufferedImage = resizingImage(frontBufferedImage, imageResizingWidth, imageResizingHeight, Image.SCALE_DEFAULT);
        

        double offsetx = (backgroundResizingWidth - imageResizingWidth) / 2;
        double offsety = (backgroundResizingHeight - imageResizingHeight) / 2;

        Graphics g = bckgBufferedImage.getGraphics();
        
        //offsetx = ((int) offsetx < 0 ? offsetx * (-1) : offsetx);
        //offsety = ((int) offsety < 0 ? offsety * (-1) : offsety);

        g.drawImage(frontBufferedImage, (int) offsetx, (int) offsety, null);
        //g.drawString("Portylou", 2 * w_fi - 70, 2 * h_fi - 20);
        g.dispose();

        bckgBufferedImage = resizingImage(bckgBufferedImage, wDefault, hDefault, Image.SCALE_DEFAULT);
        imageSaver(bckgBufferedImage, IMAGE_DEFAULT);
    }

    /**
     * Recebe uma imagem qualque e a corda nas borda salvando uma nova imagem no
     * formato 1x1, com as dimensões especificada pela constante
     * thumbnailDimensions.
     *
     * @param fileImage Image
     * @throws IOException
     */
    public void createThumbnail(File fileImage) throws IOException {

        BufferedImage img = ImageIO.read(fileImage);

        int start_x = 0, start_y = 0, width = 0, height = 0;

        // Recumpera as dimensões da imagem recebida.
        int w_up = img.getWidth();
        int h_up = img.getHeight();

        // Setam as variavéis start_x, start_y, width e height de acordo as
        // dimensões da imagem recebida, definindo a região de corte desta.
        if (w_up == h_up) {

            // Altura e largira não precisam serem alteradas.
            width = w_up;
            height = h_up;
        } else if (w_up > h_up) {

            // Define a posição de corte no eixo x, eixo y não precisa, e faz
            // a altura e largura igual a menor dimensão da imagem recebida,
            // no caso a altura, tornando a imagem 1x1.
            start_x = (w_up - h_up) / 2;
            start_y = 0;
            width = h_up;
            height = h_up;
        } else if (w_up < h_up) {

            // Define a posição de corte no eixo y, eixo x não precisa, e faz
            // a altura e largura igual a menor dimensão da imagem recebida,
            // no caso a larguratornando a imagem 1x1.
            start_x = 0;
            start_y = (h_up - w_up) / 2;
            width = w_up;
            height = w_up;
        }

        try {

            // Cria a imagem nas  especificações acima apartir da imagem recebida,
            // faz o redicionamento da mesma e salva no caminho desejado.
            BufferedImage newImage = img.getSubimage(start_x, start_y, width, height);
            newImage = resizingImage(newImage, thumbnailDimensions, thumbnailDimensions, Image.SCALE_DEFAULT);
            imageSaver(newImage, THUMBNAIL_IMAGE);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Salva as novas imagens criadas em diretórios específicos, criando todo o
     * diretório se for necessário.
     *
     * @param bufferedImage
     * @param imageType
     * @throws IOException
     */
    private void imageSaver(BufferedImage bufferedImage, String imageType) throws IOException {

        File imagesDefaultDir = new File(PathSetting.PATH_IMG_PROFILE);
        File imagesThumbnailDir = new File(PathSetting.PATH_IMG_THUMB);

        // Verifica a existencia da pasta e, caso não exista, cria todas as pastas
        // usadas no diretória de salvamento.
        if (!imagesDefaultDir.exists() || !imagesThumbnailDir.exists()) {
            imagesDefaultDir.mkdirs();
            imagesThumbnailDir.mkdirs();
        }

        // Determina o diretório de salvamento apartir do tipo da imagem.
        switch (imageType) {

            case IMAGE_DEFAULT:
                ImageIO.write(bufferedImage, "PNG", new File(
                        PathSetting.PATH_IMG_PROFILE + "IMG-" + imageRename() + ".png"));
                System.out.println("Imagem IMG-" + imageRename() + ".png salva com sucesso!");
                break;

            case THUMBNAIL_IMAGE:
                ImageIO.write(bufferedImage, "PNG", new File(
                        PathSetting.PATH_IMG_THUMB + "thumb-" + imageRename() + ".png"));
                break;
        }
    }

    /**
     * Cria um novo nome para a imagem carregada baseada na data e horário
     * atuais. Formatando a data e hora para o tipo: ddMMyyyyHHmmss que irá
     * compor o nome da imagem
     *
     * @return String new name
     */
    public String imageRename() {

        Date currentDate = new Date();

        String date = new SimpleDateFormat("ddMMyyyy").format(currentDate);
        String hour = new SimpleDateFormat("HHmmss").format(currentDate);

        return date + hour;
    }

    /**
     * Redimenciona uma imagem para qualquer dimensão desejada, não cortando a
     * imagem original.
     *
     * @param img
     * @param initWidth
     * @param initHeight
     * @param modo
     * @return
     */
    private BufferedImage resizingImage(BufferedImage img, double initWidth, double initHeight, int mode) {

        // Cria uma imagem temporária com as dimensões desejadas.
        Image temp = img.getScaledInstance((int) initWidth, (int) initHeight, mode);
        int width = temp.getWidth(null);
        int height = temp.getHeight(null);

        // Converte a nova imagem para o formato BufferedImage (objeto desejado).
        BufferedImage newImagem = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics graphic = newImagem.createGraphics();
        graphic.drawImage(temp, 0, 0, null);
        graphic.dispose();

        return newImagem;
    }

    public void calculateBackgroundDimensions(BufferedImage img) {

        backgroundResizingHeight = img.getHeight();
        backgroundResizingWidth = img.getHeight() / (hDefault / wDefault);
    }

    public void calculateImageResizing(BufferedImage img) {

        double myProp = ((img.getHeight() + 0.000) / (img.getWidth() + 0.000));
        
        if(img.getWidth() > (int) backgroundResizingWidth && img.getHeight() == (int) backgroundResizingHeight){
            //hf = h * id
            //wf = w
            imageResizingWidth = img.getWidth();
            imageResizingHeight = img.getHeight() * myProp;
        }else  if(img.getWidth() < (int) backgroundResizingWidth && img.getHeight() == (int) backgroundResizingHeight){
            //hf = h
            //wf = w
            imageResizingWidth = img.getWidth();
            imageResizingHeight = img.getHeight();
        }else  if(img.getWidth() > (int) backgroundResizingWidth && img.getHeight() > (int) backgroundResizingHeight){
            tieBreaker(myProp, img.getWidth(), img.getHeight());
        }else  if(img.getWidth() > (int) backgroundResizingWidth && img.getHeight() < (int) backgroundResizingHeight){
            //wf = wb
            //hf = h * id
            imageResizingWidth = backgroundResizingWidth;
            imageResizingHeight = img.getHeight() * myProp;
        }else  if(img.getWidth() < (int) backgroundResizingWidth && img.getHeight() > backgroundResizingHeight){
            //hf = hb
            //wf = w * id
            imageResizingWidth = img.getWidth() * myProp;
            imageResizingHeight = backgroundResizingHeight;
        }else  if(img.getWidth() < (int) backgroundResizingWidth && img.getHeight() < backgroundResizingHeight){
            tieBreaker(myProp, img.getWidth(), img.getHeight());
        }
        
        
        /*if (myProp < (hDefault / wDefault)) {
            imageResizingWidth = backgroundResizingWidth;
            imageResizingHeight = img.getHeight() * myProp;
        } else if(myProp > (hDefault / wDefault)) {
            imageResizingHeight = backgroundResizingHeight;
            imageResizingWidth = img.getWidth() * myProp + (myProp * 100);
        }else{
            imageResizingHeight = backgroundResizingHeight;
            imageResizingWidth = backgroundResizingWidth;
        }*/
    }
    
    private void tieBreaker(double p, double width, double height){
        if (p < (hDefault / wDefault)) {
            imageResizingWidth = backgroundResizingWidth;
            imageResizingHeight = height* p;
        } else if(p > (hDefault / wDefault)) {
            imageResizingHeight = backgroundResizingHeight;
            imageResizingWidth = width * p;
        }else{
            imageResizingHeight = backgroundResizingHeight;
            imageResizingWidth = backgroundResizingWidth;
        }
    }
}
