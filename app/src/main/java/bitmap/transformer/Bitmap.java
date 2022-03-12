package bitmap.transformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Bitmap {

    private File inputFilePath;
    private File outputFilePath;
    private String transfromName;

    public Bitmap(String inputFilePath, String outputFilePath, String transfromName) {
        this.inputFilePath = new File(inputFilePath);
        this.outputFilePath = new File(outputFilePath + "/" + transfromName );
        this.transfromName = transfromName;
    }

    public void outputFile (BufferedImage bit) {
        try {
            ImageIO.write(bit, "bmp", this.outputFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bitTransformBlackWhite () {
        try {
            BufferedImage image = ImageIO.read(this.inputFilePath);

            for(int i=0; i <image.getHeight(); i++) {
                for(int j=0; j<image.getWidth(); j++){
                    Color color = new Color(image.getRGB(i,j));
                    int r = (int)(color.getRed()*0.299);
                    int g = (int)(color.getGreen()*0.587);
                    int b = (int)(color.getBlue()*0.114);
                    Color newBitColor = new Color(r+b+g,r+b+g,r+b+g);

                    image.setRGB( i , j , newBitColor.getRGB());
                }
            }
            this.outputFile(image);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waterMark(){
        // Read image
        try {
            BufferedImage image = ImageIO.read(this.inputFilePath);

            // create BufferedImage object of same width and
            // height as of input image
            BufferedImage temp = new BufferedImage(
                    image.getWidth(), image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            // Create graphics object and add original
            // image to it
            Graphics graphics = temp.getGraphics();
            graphics.drawImage(image, 0, 0, null);


            // Set font for the watermark text
            graphics.setFont(new Font("Arial", Font.PLAIN, 80));
            graphics.setColor(new Color(255, 0, 0, 40));

            // Setting watermark text
            String watermark = "WaterMark generated";

            // Add the watermark text at (width/5, height/3)
            // location
            graphics.drawString(watermark, image.getWidth() / 5,
                    image.getHeight() / 3);
            // releases any system resources that it is using
            graphics.dispose();

            //save to
            this.outputFile(image);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public void getSetPixels() {
        // read image
        try {
            BufferedImage image = ImageIO.read(this.inputFilePath);

            // get image width and height
            int width = image.getWidth();
            int height = image.getHeight();


            int p = image.getRGB(0, 0);

            // get alpha
            int a = (p >> 24) & 0xff;

            // get red
            int r = (p >> 16) & 0xff;

            // get green
            int g = (p >> 8) & 0xff;

            // get blue
            int b = p & 0xff;

            // for simplicity we will set the ARGB
            // value to 255, 100, 150 and 200 respectively.

            a = 255;
            r = 100;
            g = 150;
            b = 200;

            // set the pixel value
            p = (a << 24) | (r << 16) | (g << 8) | b;
            image.setRGB(0, 0, p);

            //save to
            this.outputFile(image);

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
