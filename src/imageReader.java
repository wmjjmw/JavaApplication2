import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class imageReader {
    public static void main(String[] args) {

	String fileName = args[0];
   	int width = Integer.parseInt(args[1]);
	int height = Integer.parseInt(args[2]);

        // present video frame number
        int presentFrameNum = 1;
        long bitsPerFrame = 352*288*3;

        if (args.length < 3) {
            System.err.println("usage: java imageReader sample_gray_image1.rgb 512 512 sound_file_name");
            return;
        }

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        try {
            File file = new File(args[0]);
	    InputStream is = new FileInputStream(file);

	    long len = file.length();
            System.out.println(len);
	    byte[] bytes = new byte[352*288*3];

	    int offset = 0;
            int numRead = 0;

            is.skip(bitsPerFrame*(presentFrameNum - 1));

            while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }

            int ind = 0;

            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    byte a = 0;
                    byte r = bytes[ind];
                    byte g = bytes[ind+height*width];
                    byte b = bytes[ind+height*width*2];

                    int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                    //int pix = ((a << 24) + (r << 16) + (g << 8) + b);
                    img.setRGB(x,y,pix);
                    ind++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Use a label to display the image
        JFrame frame = new JFrame();
        JLabel label = new JLabel(new ImageIcon(img));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

     // sound section
 //-----------------------
        String sfilename = args[3];

	// opens the inputStream
	FileInputStream inputStream;
	try {
	    inputStream = new FileInputStream(sfilename);
	} catch (FileNotFoundException e) {
            e.printStackTrace();
	    return;
	}

	// initializes the playSound Object
        PlaySound playSound = new PlaySound(inputStream);

	// plays the sound
	try {
	    playSound.play();
	} catch (PlayWaveException e) {
	    e.printStackTrace();
		return;
	}
  // sound section  over
  //-----------------------
   }
}