package portfolio.sda.ultil;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author Gilvanei e Kelvin
 */
public class ImageBuilder {

    private static final String BACKGROUND_PATH = "qualquer.jpg";
    private static final String IMAGE_DEFAULT = "default";
    private static final String THUMBNAIL_IMAGE = "thumbnail";

    private final String imagesDefaultPath = "caminho\\para\\pasta\\img\\";
    private final String imagesThumbnailPath = "caminho\\para\\pasta\\thumb\\";
    private final int thumbnailDimensions = 100;

    private float resizeWidth = 0;
    private float resizeHeight = 0;

    public Image createDefaultImage(File frontImage) throws IOException {

        if (frontImage == null) {
            throw new IOException("Não foi definida uma imagem para uso!");
        }

        BufferedImage bckgBufferedImage = ImageIO.read(new File(ImageBuilder.BACKGROUND_PATH));
        BufferedImage frontBufferedImage = ImageIO.read(frontImage);

        int widthImage = thumbnailDimensions;
        int heightImage = thumbnailDimensions;

        frontBufferedImage = resizingImage(frontBufferedImage, widthImage, heightImage, Image.SCALE_DEFAULT);

        int w_bi = bckgBufferedImage.getWidth() / 2;
        int h_bi = bckgBufferedImage.getHeight() / 2;

        int w_fi = frontBufferedImage.getWidth() / 2;
        int h_fi = frontBufferedImage.getHeight() / 2;

        int w_offset = w_bi - w_fi;
        int h_offset = h_bi - h_fi;

        Graphics g = bckgBufferedImage.getGraphics();

        g.drawImage(frontBufferedImage, w_offset, h_offset, null);
        g.drawString("Portylou", 2 * w_fi - 70, 2 * h_fi - 20);
        g.dispose();

        imageSaver(bckgBufferedImage, ImageBuilder.IMAGE_DEFAULT);

        return bckgBufferedImage;
    }

    public void thumbnailGenerete(File fileImage) throws IOException {

        BufferedImage img = ImageIO.read(fileImage);

        int start_x = 0, start_y = 0, w = 0, h = 0;

        int wup = img.getWidth();
        int hup = img.getHeight();

        int offset = 0;

        if (wup == hup) {

            start_x = 0;
            start_y = 0;
            w = wup;
            h = hup;
        } else if (wup > hup) {

            offset = (wup - hup) / 2;
            start_x = offset;
            start_y = 0;
            w = hup;
            h = hup;
        } else if (wup < hup) {

            offset = (hup - wup) / 2;
            start_x = 0;
            start_y = offset;
            w = wup;
            h = wup;
        }

        try {

            BufferedImage newImage = img.getSubimage(start_x, start_y, w, h);
            newImage = resizingImage(newImage, thumbnailDimensions, thumbnailDimensions, Image.SCALE_DEFAULT);
            imageSaver(newImage, ImageBuilder.THUMBNAIL_IMAGE);
        } catch (IOException e) {

        }
    }

    private int[] calculateNewImageWidth(BufferedImage back, BufferedImage img) {

        /*
        
        divide a maior pela menor... 
        
        1 se a largura de img for maior
        2 se a altura de img for maior
        3 se ambas for maior
        
        1 largura e altura iguais... retorna uma das larguras.
        2 largura back maior
         */
        if (back.getWidth() < img.getWidth() && back.getHeight() >= img.getHeight()) {

            float h = (back.getWidth() / img.getWidth()) * img.getHeight();
            float w = back.getWidth();
        } else if (back.getHeight() < img.getHeight() && back.getWidth() >= img.getWidth()) {
            //gera um indice... faz a
            float w = (back.getHeight() / img.getHeight()) * img.getWidth();
            float h = back.getHeight();
        } else if (back.getWidth() < img.getWidth() && back.getHeight() < img.getHeight()) {
            float b = back.getWidth() / back.getHeight();
            float c = img.getWidth() / img.getHeight();

            /* Quanto maior a mais fina a imagem*/
            if (b > c) {

                float h = (back.getWidth() / img.getWidth()) * img.getHeight();
                float w = back.getWidth();
            } else if (c > b) {

            }
        } else {
            //retorna o mesmo
        }
        int[] a = new int[4];
        return a;
    }

    private void imageSaver(BufferedImage bufferedImage, String imageType) throws IOException {

        File imagesDefaultDir = new File(imagesDefaultPath);
        File imagesThumbnailDir = new File(imagesThumbnailPath);

        if (!imagesDefaultDir.exists() || !imagesThumbnailDir.exists()) {
            imagesDefaultDir.mkdirs();
            //System.out.println("Pata criada em: " + imagesDefaultDir.getAbsolutePath() + " - " + imagesDefaultDir.exists());
            imagesThumbnailDir.mkdirs();
        }

        switch (imageType) {

            case ImageBuilder.IMAGE_DEFAULT:
                ImageIO.write(bufferedImage, "PNG", new File(
                        imagesDefaultPath + "IMG-" + imageRename() + ".png"));
                break;

            case ImageBuilder.THUMBNAIL_IMAGE:
                ImageIO.write(bufferedImage, "PNG", new File(
                        imagesThumbnailPath + "thumb-" + imageRename() + ".png"));
                break;
        }
    }

    /**
     * Formata a data e hora atuais para o tipo: ddMMyyyyHHmmss que irá compor o
     * nome da imagem
     *
     * @return String new name
     */
    public String imageRename() {

        Date currentDate = new Date();

        String date = new SimpleDateFormat("ddMMyyyy").format(currentDate);
        String hour = new SimpleDateFormat("HHmmss").format(currentDate);

        return date + hour;
    }

    private BufferedImage resizingImage(BufferedImage img, int initWidth, int initHeight, int modo) {

        Image temp = img.getScaledInstance(initWidth, initHeight, modo);
        int width = temp.getWidth(null);
        int height = temp.getHeight(null);

        BufferedImage newImagem = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics graphic = newImagem.createGraphics();
        graphic.drawImage(temp, 0, 0, null);
        graphic.dispose();

        return newImagem;
    }

    public void size(int size, int width, int height) {

        float percent = (width > height ? (size / width) : (size / height));

        resizeWidth = width * percent;
        resizeHeight = height * percent;
    }
}
