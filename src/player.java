import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class player extends javax.swing.JFrame {

        // video object
    class video{
        String videoFileName;
        int videoFrameNum;
    }

    class audio{
        String audioFileName;
        int audioFrameNum;
    }

    // image object
    class image{
        String imageFileName;
        boolean goWebsite;
        boolean displayObj;
    }

    // frame properties
    class frame{
        boolean videoExist = false;
        boolean imageExist = false;
        boolean isTracking = false;
        video importVideo = new video();
        image importImage = new image();
        audio importAudio = new audio();
    }

    // object positions
    class objectPos{
        double startPoint[] = new double[2];
        double size[] = new double[2];
    }

    /* global variables */
    int totalFrameNum=1;  // add value
    int currentFrameNum = 0;
    double mediaScaleFactor[] = {1.0, 1.0};
    frame[] publishFile = new frame[5000];
    objectPos mediaframePosition_crt = new objectPos();
    objectPos screenframePosition_crt = new objectPos();
    objectPos imagePosition_crt = new objectPos();
    objectPos button1Position_crt = new objectPos();
    objectPos button2Position_crt = new objectPos();
    objectPos button3Position_crt = new objectPos();

    // variables to store button image
    String playButtonImgName = new String(); // add value
    String stopButtonImgName = new String();  // add value
    String pauseButtonImgName = new String();  // add value
    boolean playButtonExist = false;  // add value
    boolean stopButtonExist = false;  // add value
    boolean pauseButtonExist = false;  // add value
    BufferedImage playButtonImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
    BufferedImage stopButtonImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
    BufferedImage pauseButtonImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);

    // variable to store playStatus
    boolean playStatus = false;

    // variable to store rectangular position
    objectPos rectPosition = new objectPos();

    public void readdata(){
        System.out.println("start to read file");
        try
        {
            DataInputStream dataIn = new DataInputStream(new FileInputStream("data.txt"));
            totalFrameNum = dataIn.readInt();
            for (int i = 0; i < totalFrameNum; i++){
                publishFile[i] = new frame();
            }
            for(int i=0;i<totalFrameNum;i++){
                publishFile[i].importVideo.videoFileName = dataIn.readUTF();
                publishFile[i].importVideo.videoFrameNum = dataIn.readInt();
                publishFile[i].importAudio.audioFileName = dataIn.readUTF();
                publishFile[i].importAudio.audioFrameNum = dataIn.readInt();
                publishFile[i].videoExist = dataIn.readBoolean();
                publishFile[i].imageExist = dataIn.readBoolean();
                publishFile[i].isTracking = dataIn.readBoolean();
                publishFile[i].importImage.imageFileName = dataIn.readUTF();
                publishFile[i].importImage.displayObj = dataIn.readBoolean();
                publishFile[i].importImage.goWebsite = dataIn.readBoolean();
            }

            screenframePosition_crt.startPoint[0] = dataIn.readDouble();
            screenframePosition_crt.startPoint[1] = dataIn.readDouble();
            screenframePosition_crt.size[0] = dataIn.readDouble();
            screenframePosition_crt.size[1] = dataIn.readDouble();

            mediaframePosition_crt.startPoint[0]=dataIn.readDouble();
            mediaframePosition_crt.startPoint[1]=dataIn.readDouble();
            mediaframePosition_crt.size[0]=dataIn.readDouble();
            mediaframePosition_crt.size[1]=dataIn.readDouble();

            imagePosition_crt.startPoint[0]=dataIn.readDouble();
            imagePosition_crt.startPoint[1]=dataIn.readDouble();
            imagePosition_crt.size[0]=dataIn.readDouble();
            imagePosition_crt.size[1]=dataIn.readDouble();

            button1Position_crt.startPoint[0]=dataIn.readDouble();
            button1Position_crt.startPoint[1]=dataIn.readDouble();
            button1Position_crt.size[0]=dataIn.readDouble();
            button1Position_crt.size[1]=dataIn.readDouble();
            playButtonExist=dataIn.readBoolean();
            playButtonImgName=dataIn.readUTF();

            button2Position_crt.startPoint[0]=dataIn.readDouble();
            button2Position_crt.startPoint[1]=dataIn.readDouble();
            button2Position_crt.size[0]=dataIn.readDouble();
            button2Position_crt.size[1]=dataIn.readDouble();
            stopButtonExist = dataIn.readBoolean();
            stopButtonImgName = dataIn.readUTF();

            button3Position_crt.startPoint[0]=dataIn.readDouble();
            button3Position_crt.startPoint[1]=dataIn.readDouble();
            button3Position_crt.size[0]=dataIn.readDouble();
            button3Position_crt.size[1]=dataIn.readDouble();
            pauseButtonExist = dataIn.readBoolean();
            pauseButtonImgName = dataIn.readUTF();

            mediaScaleFactor[0] = dataIn.readDouble();
            mediaScaleFactor[1] = dataIn.readDouble();

            dataIn.close();
            System.out.println("finish");
        }catch(IOException e){
            System.out.println("Problem creating file");
        }
    }

    /** Creates new form player */
    public player() {
        initComponents();
        // positions of all the objects, add value here
        mediaframePosition_crt.startPoint[0] = (double)MediaPane.getBounds().x;
        mediaframePosition_crt.startPoint[1] = (double)MediaPane.getBounds().y;
        mediaframePosition_crt.size[0] = (double)MediaPane.getBounds().width;
        mediaframePosition_crt.size[1] = (double)MediaPane.getBounds().height;
        screenframePosition_crt.startPoint[0] = (double)screenLabel.getBounds().x;
        screenframePosition_crt.startPoint[1] = (double)screenLabel.getBounds().y;
        screenframePosition_crt.size[0] = (double)screenLabel.getBounds().width;
        screenframePosition_crt.size[1] = (double)screenLabel.getBounds().height;
        imagePosition_crt.startPoint[0] = (double)imageLabel.getBounds().x;
        imagePosition_crt.startPoint[1] = (double)imageLabel.getBounds().y;
        imagePosition_crt.size[0] = (double)imageLabel.getBounds().width;
        imagePosition_crt.size[1] = (double)imageLabel.getBounds().height;
        button1Position_crt.startPoint[0] = (double)playButton.getBounds().x;
        button1Position_crt.startPoint[1] = (double)playButton.getBounds().y;
        button1Position_crt.size[0] = (double)playButton.getBounds().width;
        button1Position_crt.size[1] = (double)playButton.getBounds().height;
        button2Position_crt.startPoint[0] = (double)pauseButton.getBounds().x;
        button2Position_crt.startPoint[1] = (double)pauseButton.getBounds().y;
        button2Position_crt.size[0] = (double)pauseButton.getBounds().width;
        button2Position_crt.size[1] = (double)pauseButton.getBounds().height;
        button3Position_crt.startPoint[0] = (double)stopButton.getBounds().x;
        button3Position_crt.startPoint[1] = (double)stopButton.getBounds().y;
        button3Position_crt.size[0] = (double)stopButton.getBounds().width;
        button3Position_crt.size[1] = (double)stopButton.getBounds().height;
        // set buttons invisible
        playButton.setVisible(false);
        pauseButton.setVisible(false);
        stopButton.setVisible(false);
        // initialize file buffer
        readdata();

        // update image position
        imageLabel.setBounds((int)imagePosition_crt.startPoint[0], (int)imagePosition_crt.startPoint[1], (int)imagePosition_crt.size[0], (int)imagePosition_crt.size[1]);
        // update screen position
        screenLabel.setBounds((int)screenframePosition_crt.startPoint[0], (int)screenframePosition_crt.startPoint[1], (int)screenframePosition_crt.size[0], (int)screenframePosition_crt.size[1]);
        // update media pane size
        MediaPane.setBounds(0,0,(int)mediaframePosition_crt.size[0], (int)mediaframePosition_crt.size[1]);
        // update window size
        this.setPreferredSize(new Dimension((int)mediaframePosition_crt.size[0]+40, (int)mediaframePosition_crt.size[1]+40));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        // add play, pause, stop button, if there is any
        if (playButtonExist){
            // save image to image buffer
            saveButtonImage(playButtonImg, playButtonImgName);
            // add image to play button
            updateButtonImage(0);
            // set play button visible
            playButton.setVisible(true);
        }
        if (pauseButtonExist){
            // save image to image buffer
            saveButtonImage(pauseButtonImg, pauseButtonImgName);
            // add image to play button
            updateButtonImage(1);
            // set play button visible
            pauseButton.setVisible(true);
        }
        if (stopButtonExist){
            // save image to image buffer
            saveButtonImage(stopButtonImg, stopButtonImgName);
            // add image to play button
            updateButtonImage(2);
            // set play button visible
            stopButton.setVisible(true);
        }
        display();
    }

    PlayerBase pBase = new PlayerBase();
 
    public void startAnimation(){
        class AnimationRunnable implements Runnable{
            public void run(){
                try {
                    for(int i = currentFrameNum; i < totalFrameNum; i++){
                        if(!playStatus){
                            break;
                        }
                        currentFrameNum = i;
                        if(currentFrameNum == 0){
                            pBase.play(publishFile[0].importAudio.audioFileName);
                        }
                        if((currentFrameNum!=0)&&(publishFile[currentFrameNum].importAudio.audioFrameNum == 0)){
                            pBase.stop();
                            Thread.sleep(100);
                            pBase.play(publishFile[i].importAudio.audioFileName);
                        }
                        display();
                        Thread.sleep(22);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(NewJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Runnable r = new AnimationRunnable();
        Thread t = new Thread(r);
        t.start();
    }

    public void saveButtonImage(BufferedImage img, String fileName){
        int height = 64;
        int width = 64;

        // read image file
        int ind = 0;
        try {
            File file = new File(fileName);
            InputStream is = new FileInputStream(file);

            byte[] bytes = new byte[height*width*3];

            int offset = 0;
            int numRead = 0;

            // read current frame
            while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }

            // save current frame to image buffer
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
                // close input stream
                is.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("cannot find button image file");
        } catch (IOException e) {
            System.out.println("cannot find button image file");
        }
    }

    public void updateButtonImage(int buttonType){
        if (buttonType == 0){
            // scale image
            Image scaledImg = playButtonImg.getScaledInstance((int)button1Position_crt.size[0], (int)button1Position_crt.size[1],Image.SCALE_FAST);
            // add image to play button
            playButton.setIcon((new ImageIcon(scaledImg)));
            playButton.setBounds((int)button1Position_crt.startPoint[0], (int)button1Position_crt.startPoint[1], (int)button1Position_crt.size[0], (int)button1Position_crt.size[1]);
        }
        if (buttonType == 1){
            // scale image
            Image scaledImg = pauseButtonImg.getScaledInstance((int)button2Position_crt.size[0], (int)button2Position_crt.size[1],Image.SCALE_FAST);
            // add image to play button
            pauseButton.setIcon((new ImageIcon(scaledImg)));
            pauseButton.setBounds((int)button2Position_crt.startPoint[0], (int)button2Position_crt.startPoint[1], (int)button2Position_crt.size[0], (int)button2Position_crt.size[1]);
        }
        if (buttonType == 2){
            // scale image
            Image scaledImg = stopButtonImg.getScaledInstance((int)button3Position_crt.size[0], (int)button3Position_crt.size[1],Image.SCALE_FAST);
            // add image to play button
            stopButton.setIcon((new ImageIcon(scaledImg)));
            stopButton.setBounds((int)button3Position_crt.startPoint[0], (int)button3Position_crt.startPoint[1], (int)button3Position_crt.size[0], (int)button3Position_crt.size[1]);
        }
    }

    public void displayVideo(){
        // set panel size
        int panelWidth = 352;
        int panelHeight = 288;

        // screen frame buffer
        BufferedImage img = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_RGB);

        int ind = 0;
        try {
            File file = new File(publishFile[currentFrameNum].importVideo.videoFileName);
            InputStream is = new FileInputStream(file);

            long len = file.length();

            byte[] bytes = new byte[352*288*3];

            // video frame size
            int height = 288;
            int width = 352;

            long bitsPerFrame = height*width*3;

            int offset = 0;
            int numRead = 0;

            // skip the bits before current frame
            is.skip(bitsPerFrame*publishFile[currentFrameNum].importVideo.videoFrameNum);

            // read current frame
            while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }

            // save current frame to image buffer
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

                // close input stream
                is.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("cannot find video file");
        } catch (IOException e) {
            System.out.println("cannot find video file");
        }

        // draw rectangle
        if (publishFile[currentFrameNum].isTracking == true){
            Graphics2D g = img.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.setColor(Color.red);
            g.setStroke(new BasicStroke(3));
            g.drawRect((int)rectPosition.startPoint[0], (int)rectPosition.startPoint[1], (int)rectPosition.size[0], (int)rectPosition.size[1]);
            g.dispose();
        }
        else if (publishFile[currentFrameNum].isTracking == false){
            // disable the tracking area
            rectPosition.startPoint[0] = 0;
            rectPosition.startPoint[1] = 0;
            rectPosition.size[0] = 0;
            rectPosition.size[1] = 0;
        }

        // scale video frame
        Image scaledImg = img.getScaledInstance((int)screenframePosition_crt.size[0], (int)screenframePosition_crt.size[1],Image.SCALE_FAST);

        // display current scaled video frame on screenLabel
        screenLabel.setIcon((new ImageIcon(scaledImg)));
    }

    public void displayImage(){

        // check if there is go to website function added on image
        // if yes, add mouse click listener
        if (publishFile[currentFrameNum].importImage.goWebsite){
            if(imageLabel.getMouseListeners().length < 1){
                imageLabel.addMouseListener((new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        goToWebsite(evt);
                    }
                }));
            }
        }
        else{
            // if there is go to website listener, remove it
            if (imageLabel.getMouseListeners().length >= 1){
                for (int i = 1; i<imageLabel.getMouseListeners().length; i++){
                    imageLabel.removeMouseListener(imageLabel.getMouseListeners()[i]);
                }
            }
        }

        int height = 288;
        int width = 352;

        // screen frame buffer
        BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);

        int ind = 0;
        try {
            File file = new File(publishFile[currentFrameNum].importImage.imageFileName);
            InputStream is = new FileInputStream(file);

            byte[] bytes = new byte[height*width*3];

            int offset = 0;
            int numRead = 0;

            // read current frame
            while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }

            // save current frame to image buffer
            for(int y = 0; y < 64; y++){
                for(int x = 0; x < 64; x++){
                    byte a = 0;
                    byte r = bytes[width*y+x];
                    byte g = bytes[width*y+x+height*width];
                    byte b = bytes[width*y+x+height*width*2];

                    int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                    //int pix = ((a << 24) + (r << 16) + (g << 8) + b);
                    img.setRGB(x,y,pix);
                    ind++;
                }

                // close input stream
                is.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("cannot find image file");
        } catch (IOException e) {
            System.out.println("cannot find image file");
        }

        // scale image
        Image scaledImg = img.getScaledInstance((int)imagePosition_crt.size[0], (int)imagePosition_crt.size[1],Image.SCALE_FAST);

        // display current scaled video frame on screenLabel
        imageLabel.setIcon((new ImageIcon(scaledImg)));
    }

    public void readRectInfo(){
        // open corresponding file
        if (publishFile[currentFrameNum].importVideo.videoFileName.equals("interview_352_288_30.576v.rgb")){
            try {
                FileInputStream file = new FileInputStream("track1.txt");
                DataInputStream is = new DataInputStream(file);

                // skip the bits before current frame
                is.skip((8*4)*publishFile[currentFrameNum].importVideo.videoFrameNum);

                rectPosition.startPoint[0] = is.readDouble();
                rectPosition.startPoint[1] = is.readDouble();
                rectPosition.size[0] = is.readDouble();
                rectPosition.size[1] = is.readDouble();

                // close input stream
                is.close();
            } catch (FileNotFoundException e) {
                System.out.println("cannot find track file");
            } catch (IOException e) {
                System.out.println("cannot find track file");
            }
        }
        else if(publishFile[currentFrameNum].importVideo.videoFileName.equals("gollum_352_288_1min.rgb")){
            try {
                FileInputStream file = new FileInputStream("track2.txt");
                DataInputStream is = new DataInputStream(file);

                // skip the bits before current frame
                is.skip((8*4)*publishFile[currentFrameNum].importVideo.videoFrameNum);

                rectPosition.startPoint[0] = is.readDouble();
                rectPosition.startPoint[1] = is.readDouble();
                rectPosition.size[0] = is.readDouble();
                rectPosition.size[1] = is.readDouble();

                // close input stream
                is.close();
            } catch (FileNotFoundException e) {
                System.out.println("cannot find track file");
            } catch (IOException e) {
                System.out.println("cannot find track file");
            }
        }
    }

    public void display(){
        // check if there is video in selected frame
        // if no, return
        if (publishFile[currentFrameNum].videoExist == false){
            return;
        }

        // check if there is object tracking
        if (publishFile[currentFrameNum].isTracking == true){
            readRectInfo();
        }

        // display video frame
        displayVideo();

        // display image
        if(publishFile[currentFrameNum].imageExist == false){
            if(MediaPane.getComponentCountInLayer(JLayeredPane.PALETTE_LAYER) == 1){
                MediaPane.remove(imageLabel);
            }
        }
        if(publishFile[currentFrameNum].imageExist == true){
            if(MediaPane.getComponentCountInLayer(JLayeredPane.PALETTE_LAYER) == 0){
                MediaPane.add(imageLabel);
            }
            displayImage();
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MediaPane = new javax.swing.JLayeredPane();
        screenLabel = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();
        playButton = new javax.swing.JLabel();
        pauseButton = new javax.swing.JLabel();
        stopButton = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        screenLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        screenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                displayPostion(evt);
            }
        });
        screenLabel.setBounds(0, 0, 352, 288);
        MediaPane.add(screenLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        imageLabel.setBounds(270, 20, 64, 64);
        MediaPane.add(imageLabel, javax.swing.JLayeredPane.PALETTE_LAYER);

        playButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        playButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playMedia(evt);
            }
        });
        playButton.setBounds(420, 20, 64, 64);
        MediaPane.add(playButton, javax.swing.JLayeredPane.MODAL_LAYER);

        pauseButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pauseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pauseMedia(evt);
            }
        });
        pauseButton.setBounds(420, 110, 64, 64);
        MediaPane.add(pauseButton, javax.swing.JLayeredPane.MODAL_LAYER);

        stopButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        stopButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopMedia(evt);
            }
        });
        stopButton.setBounds(420, 200, 64, 64);
        MediaPane.add(stopButton, javax.swing.JLayeredPane.MODAL_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MediaPane, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MediaPane, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void playMedia(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playMedia
        if(playStatus == false){
            playStatus = true;
            startAnimation();
            if(currentFrameNum!=0){
                pBase.play(publishFile[currentFrameNum].importAudio.audioFileName);
            }
        }
}//GEN-LAST:event_playMedia

    private void pauseMedia(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pauseMedia
        // TODO add your handling code here:
        pBase.pause();
        playStatus = false;
    }//GEN-LAST:event_pauseMedia

    private void stopMedia(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopMedia
        // TODO add your handling code here:
        if (!playStatus) return;
        playStatus = false;
        currentFrameNum = 0;
        display();
        pBase.stop();
    }//GEN-LAST:event_stopMedia

    private void displayPostion(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayPostion
        // TODO add your handling code here:
        Point p = evt.getPoint();

        // if clicked position is inside the rectangular
        if ((p.x >= rectPosition.startPoint[0]*mediaScaleFactor[0])&&(p.x <= rectPosition.startPoint[0]*mediaScaleFactor[0]+rectPosition.size[0]*mediaScaleFactor[0])&&(p.y >= rectPosition.startPoint[1]*mediaScaleFactor[1])&&(p.y <= rectPosition.startPoint[1]*mediaScaleFactor[1]+rectPosition.size[1]*mediaScaleFactor[1])){
            try{
                String command = "C:\\Program Files\\Internet Explorer\\Iexplore.exe www.google.com";
                Runtime.getRuntime().exec(command);
            } catch(IOException ex){
                System.out.println("cannot open Internet Explorer on this computer");
            }
        }
}//GEN-LAST:event_displayPostion

    /**
    * @param args the command line arguments
    */

    private void goToWebsite(java.awt.event.MouseEvent evt){
       int clickNum = evt.getClickCount();
       if (clickNum >= 1){
           try{
               String command = "C:\\Program Files\\Internet Explorer\\Iexplore.exe www.google.com";
               Runtime.getRuntime().exec(command);
           }
           catch(IOException ex){
               System.out.println("cannot open Internet Explorer on this computer");
           }
       }
    }

    public static void main(String args[]) {
        player screen = new player();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane MediaPane;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel pauseButton;
    private javax.swing.JLabel playButton;
    private javax.swing.JLabel screenLabel;
    private javax.swing.JLabel stopButton;
    // End of variables declaration//GEN-END:variables

}
