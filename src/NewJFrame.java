import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import	javax.sound.sampled.*;


public class NewJFrame extends javax.swing.JFrame{

    // video object
    class video{
        String videoFileName = "yzp";
        int videoFrameNum;
    }

    // image object
    class image{
        String imageFileName = "yzp";
        boolean goWebsite;
        boolean displayObj;
    }

    // audio object
    class audio{
        String audioFileName = "yzp";
        int audioFrameNum;
    }

    // frame properties
    class frame{
        boolean videoExist = false;
        boolean imageExist = false;
        boolean isTracking = false;
        double frameWidthScale = 1.0;
        double frameHeightScale = 1.0;
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
    int totalFrameNum = 0;
    int frameNumMax = 5000;
    int currentFrameNum = 0;
    //int imageNumMax = 1;
    double mediaScaleFactor[] = {1.0, 1.0};
    frame[] publishFile = new frame[frameNumMax];
    // variables to store position parameters
    objectPos mediaframePosition_org = new objectPos();
    objectPos screenframePosition_org = new objectPos();
    objectPos mediaframePosition_crt = new objectPos();
    objectPos screenframePosition_crt = new objectPos();
    objectPos imagePosition_org = new objectPos();
    objectPos imagePosition_crt = new objectPos();
    objectPos button1Position_org = new objectPos();
    objectPos button1Position_crt = new objectPos();
    objectPos button2Position_org = new objectPos();
    objectPos button2Position_crt = new objectPos();
    objectPos button3Position_org = new objectPos();
    objectPos button3Position_crt = new objectPos();

    // variables to store drag position
    double mousePressVideo[] = {0.0, 0.0};
    double mousePressImage[] = {0.0, 0.0};
    double mousePressPlayButton[] = {0.0, 0.0};
    double mousePressPauseButton[] = {0.0, 0.0};
    double mousePressStopButton[] = {0.0, 0.0};

    // variables to store button image
    String playButtonImgName = new String();
    String stopButtonImgName = new String();
    String pauseButtonImgName = new String();
    boolean playButtonExist = false;
    boolean stopButtonExist = false;
    boolean pauseButtonExist = false;
    BufferedImage playButtonImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
    BufferedImage stopButtonImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
    BufferedImage pauseButtonImg = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);

    // variable to store playStatus
    boolean playStatus = false;

    // variable to store rectangular position
    objectPos rectPosition = new objectPos();

    /** Creates new form NewJFrame */
    public NewJFrame() {
        initComponents();

        // get the orginal media frame position
        mediaframePosition_org.startPoint[0] = (double)MediaPane.getBounds().x;
        mediaframePosition_org.startPoint[1] = (double)MediaPane.getBounds().y;
        mediaframePosition_org.size[0] = (double)MediaPane.getBounds().width;
        mediaframePosition_org.size[1] = (double)MediaPane.getBounds().height;

        // get the orginal screen frame position
        screenframePosition_org.startPoint[0] = (double)screenLabel.getBounds().x;
        screenframePosition_org.startPoint[1] = (double)screenLabel.getBounds().y;
        screenframePosition_org.size[0] = (double)screenLabel.getBounds().width;
        screenframePosition_org.size[1] = (double)screenLabel.getBounds().height;
        imagePosition_org.startPoint[0] = (double)imageLabel.getBounds().x;
        imagePosition_org.startPoint[1] = (double)imageLabel.getBounds().y;
        imagePosition_org.size[0] = (double)imageLabel.getBounds().width;
        imagePosition_org.size[1] = (double)imageLabel.getBounds().height;
        button1Position_org.startPoint[0] = (double)playButton.getBounds().x;
        button1Position_org.startPoint[1] = (double)playButton.getBounds().y;
        button1Position_org.size[0] = (double)playButton.getBounds().width;
        button1Position_org.size[1] = (double)playButton.getBounds().height;
        button2Position_org.startPoint[0] = (double)pauseButton.getBounds().x;
        button2Position_org.startPoint[1] = (double)pauseButton.getBounds().y;
        button2Position_org.size[0] = (double)pauseButton.getBounds().width;
        button2Position_org.size[1] = (double)pauseButton.getBounds().height;
        button3Position_org.startPoint[0] = (double)stopButton.getBounds().x;
        button3Position_org.startPoint[1] = (double)stopButton.getBounds().y;
        button3Position_org.size[0] = (double)stopButton.getBounds().width;
        button3Position_org.size[1] = (double)stopButton.getBounds().height;

        // initialize current object positions
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

        // initialize slider bar
        jSlider1.setMaximum(1);
        jSlider1.setMinimum(1);

        // initialize file buffer
        for (int i = 0; i < frameNumMax; i++){
            publishFile[i] = new frame();
        }

        display();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        videoSelection = new javax.swing.JComboBox();
        AddVideo = new javax.swing.JButton();
        addAudio = new javax.swing.JButton();
        audioSelection = new javax.swing.JComboBox();
        AddImage = new javax.swing.JButton();
        imageSelection = new javax.swing.JComboBox();
        ScaleMediaLabel = new javax.swing.JLabel();
        ScaleMediaXLabel = new javax.swing.JLabel();
        ScaleMediaYLabel = new javax.swing.JLabel();
        ScaleMediaSliderX = new javax.swing.JSlider();
        ScaleMediaSliderY = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        imageFuncSelection = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        ButtonImage = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        ButtonFunctionSelection = new javax.swing.JComboBox();
        AddButtom = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        trackObject = new javax.swing.JButton();
        publishButton = new javax.swing.JButton();
        MediaPane = new javax.swing.JLayeredPane();
        screenLabel = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();
        playButton = new javax.swing.JLabel();
        pauseButton = new javax.swing.JLabel();
        stopButton = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Authoring Tool"); // NOI18N

        jSlider1.setName(""); // NOI18N
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                timelineStateChanged(evt);
            }
        });

        jLabel2.setText("Action:");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        videoSelection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "interview", "gollum" }));

        AddVideo.setText("Add");
        AddVideo.setActionCommand("addVideoButton");
        AddVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVideoButton(evt);
            }
        });

        addAudio.setText("Add");
        addAudio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addAudioButton(evt);
            }
        });

        audioSelection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "interview", "gollum" }));

        AddImage.setText("Add");
        AddImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddImageButton(evt);
            }
        });

        imageSelection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sponsor Image 1", "Sponsor Image 2" }));

        ScaleMediaLabel.setText("Scale Media:");

        ScaleMediaXLabel.setText("X-axis:");

        ScaleMediaYLabel.setText("Y-axis:");

        ScaleMediaSliderX.setMinimum(1);
        ScaleMediaSliderX.setValue(100);
        ScaleMediaSliderX.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ScaleMediaX(evt);
            }
        });

        ScaleMediaSliderY.setMinimum(1);
        ScaleMediaSliderY.setValue(100);
        ScaleMediaSliderY.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ScaleMediaY(evt);
            }
        });

        jLabel1.setText("Import Video:");

        jLabel4.setText("Import Audio:");

        jLabel5.setText("Import Image:");

        jLabel7.setText("On mouse click:");

        imageFuncSelection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Go to website", "Display object", "Both" }));

        jLabel6.setText("Import Button:");

        ButtonImage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Play Buttom", "Pause Buttom", "Stop Buttom" }));

        jLabel8.setText("On mouse click:");

        ButtonFunctionSelection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Play Scene", "Pause Scene", "Stop Scene" }));

        AddButtom.setText("Add");
        AddButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButton(evt);
            }
        });

        jLabel3.setText("Track object:");

        trackObject.setText("Yes");
        trackObject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trackObjectClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ScaleMediaLabel)
                        .addContainerGap(249, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ScaleMediaXLabel)
                        .addContainerGap(279, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ScaleMediaYLabel)
                        .addContainerGap(279, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(ButtonFunctionSelection, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(ButtonImage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(imageFuncSelection, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(audioSelection, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(videoSelection, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(imageSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(addAudio, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                                        .addComponent(AddVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(AddImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(trackObject, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(AddButtom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ScaleMediaSliderY, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                                    .addComponent(ScaleMediaSliderX, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))))
                        .addGap(88, 88, 88))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(videoSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddVideo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(audioSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(addAudio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(imageSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(imageFuncSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddImage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(ButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(ButtonFunctionSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddButtom))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ScaleMediaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ScaleMediaXLabel)
                            .addComponent(ScaleMediaSliderX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ScaleMediaYLabel)
                            .addComponent(ScaleMediaSliderY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3))
                    .addComponent(trackObject))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        publishButton.setText("Publish File");
        publishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                publishButtonActionPerformed(evt);
            }
        });

        MediaPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        screenLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        screenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                displayPosition(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pressVideo(evt);
            }
        });
        screenLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                dragVideo(evt);
            }
        });
        screenLabel.setBounds(10, 10, 352, 288);
        MediaPane.add(screenLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pressImage(evt);
            }
        });
        imageLabel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                drageImage(evt);
            }
        });
        imageLabel.setBounds(270, 20, 64, 64);
        MediaPane.add(imageLabel, javax.swing.JLayeredPane.PALETTE_LAYER);

        playButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        playButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playMedia(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pressPlayButton(evt);
            }
        });
        playButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                dragPlayButton(evt);
            }
        });
        playButton.setBounds(420, 20, 64, 64);
        MediaPane.add(playButton, javax.swing.JLayeredPane.MODAL_LAYER);

        pauseButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pauseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pauseMedia(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pressPauseButton(evt);
            }
        });
        pauseButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                dragPauseButton(evt);
            }
        });
        pauseButton.setBounds(420, 110, 64, 64);
        MediaPane.add(pauseButton, javax.swing.JLayeredPane.MODAL_LAYER);

        stopButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        stopButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stopMedia(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pressStopButton(evt);
            }
        });
        stopButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                dragStopButton(evt);
            }
        });
        stopButton.setBounds(420, 200, 64, 64);
        MediaPane.add(stopButton, javax.swing.JLayeredPane.MODAL_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(publishButton)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                    .addComponent(MediaPane, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(MediaPane, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(publishButton)))
                .addContainerGap())
        );

        jSlider1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void publishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_publishButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("start to write file");
        try
        {
            DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("data.txt"));
            dataOut.writeInt(totalFrameNum);
            for(int i=0;i<totalFrameNum;i++){
                dataOut.writeUTF(publishFile[i].importVideo.videoFileName);
                dataOut.writeInt(publishFile[i].importVideo.videoFrameNum);
                dataOut.writeUTF(publishFile[i].importAudio.audioFileName);
                dataOut.writeInt(publishFile[i].importAudio.audioFrameNum);
                dataOut.writeBoolean(publishFile[i].videoExist);
                dataOut.writeBoolean(publishFile[i].imageExist);
                dataOut.writeBoolean(publishFile[i].isTracking);
                dataOut.writeUTF(publishFile[i].importImage.imageFileName);
                dataOut.writeBoolean(publishFile[i].importImage.displayObj);
                dataOut.writeBoolean(publishFile[i].importImage.goWebsite);
            }

            dataOut.writeDouble(screenframePosition_crt.startPoint[0]);
            dataOut.writeDouble(screenframePosition_crt.startPoint[1]);
            dataOut.writeDouble(screenframePosition_crt.size[0]);
            dataOut.writeDouble(screenframePosition_crt.size[1]);

            dataOut.writeDouble(mediaframePosition_crt.startPoint[0]);
            dataOut.writeDouble(mediaframePosition_crt.startPoint[1]);
            dataOut.writeDouble(mediaframePosition_crt.size[0]);
            dataOut.writeDouble(mediaframePosition_crt.size[1]);

            dataOut.writeDouble(imagePosition_crt.startPoint[0]);
            dataOut.writeDouble(imagePosition_crt.startPoint[1]);
            dataOut.writeDouble(imagePosition_crt.size[0]);
            dataOut.writeDouble(imagePosition_crt.size[1]);

            dataOut.writeDouble(button1Position_crt.startPoint[0]);
            dataOut.writeDouble(button1Position_crt.startPoint[1]);
            dataOut.writeDouble(button1Position_crt.size[0]);
            dataOut.writeDouble(button1Position_crt.size[1]);
            dataOut.writeBoolean(playButtonExist);
            dataOut.writeUTF(playButtonImgName);

            dataOut.writeDouble(button2Position_crt.startPoint[0]);
            dataOut.writeDouble(button2Position_crt.startPoint[1]);
            dataOut.writeDouble(button2Position_crt.size[0]);
            dataOut.writeDouble(button2Position_crt.size[1]);
            dataOut.writeBoolean(stopButtonExist);
            dataOut.writeUTF(stopButtonImgName);

            dataOut.writeDouble(button3Position_crt.startPoint[0]);
            dataOut.writeDouble(button3Position_crt.startPoint[1]);
            dataOut.writeDouble(button3Position_crt.size[0]);
            dataOut.writeDouble(button3Position_crt.size[1]);
            dataOut.writeBoolean(pauseButtonExist);
            dataOut.writeUTF(pauseButtonImgName);

            dataOut.writeDouble(mediaScaleFactor[0]);
            dataOut.writeDouble(mediaScaleFactor[1]);

            dataOut.close();
            System.out.println("finish");
        }catch(IOException e){
            System.out.println("Problem creating file");
        }
        try
        {
            DataOutputStream track1 = new DataOutputStream(new FileOutputStream("track1.txt"));
            for(double i=0;i<100;i++){
                track1.writeDouble(0.0);track1.writeDouble(0.0);track1.writeDouble(0.0);track1.writeDouble(0.0);
            }
            for(double i=0;i<100;i++){
                track1.writeDouble(150+i*0.1); track1.writeDouble(20.0); track1.writeDouble(100.0); track1.writeDouble(140.0);
            }
            for(double i=0;i<640;i++){
                track1.writeDouble(160.0-i*(10.0/640.0)); track1.writeDouble(20.0); track1.writeDouble(100.0); track1.writeDouble(140.0+i*(10.0/640.0));
            }
            for(double i=0;i<60;i++){
                track1.writeDouble(100.0); track1.writeDouble(20.0); track1.writeDouble(100.0); track1.writeDouble(150.0);
            }
            for(double i=0;i<190;i++){
                track1.writeDouble(150.0); track1.writeDouble(20.0); track1.writeDouble(100.0); track1.writeDouble(150.0);
            }
            for(double i=0;i<710;i++){
                track1.writeDouble(0.0);track1.writeDouble(0.0);track1.writeDouble(0.0);track1.writeDouble(0.0);
            }
            track1.close();
        }catch(IOException e){
            System.out.println("Problem creating file");
        }
        try
        {
            DataOutputStream track1 = new DataOutputStream(new FileOutputStream("track2.txt"));
            for(int i=0;i<400;i++){
                track1.writeDouble(28.0);track1.writeDouble(95.0);track1.writeDouble(300);track1.writeDouble(105.0);
            }
            for(double i=0;i<50;i++){
                track1.writeDouble(0.0); track1.writeDouble(0.0); track1.writeDouble(0.0); track1.writeDouble(0.0);
            }
            for(double i=0;i<250;i++){
                track1.writeDouble(70.0); track1.writeDouble(130.0); track1.writeDouble(210.0); track1.writeDouble(15.0);
            }
            for(double i=0;i<300;i++){
                track1.writeDouble(0.0); track1.writeDouble(0.0); track1.writeDouble(0.0); track1.writeDouble(0.0);
            }
            for(double i=0;i<150;i++){
                track1.writeDouble(230.0-i*(15.0/150.0)); track1.writeDouble(85.0+i*(10.0/150.0)); track1.writeDouble(20.0); track1.writeDouble(20.0);
            }
            for(double i=0;i<650;i++){
                track1.writeDouble(0.0);track1.writeDouble(0.0);track1.writeDouble(0.0);track1.writeDouble(0.0);
            }
            track1.close();
        }catch(IOException e){
            System.out.println("Problem creating file");
        }
    }//GEN-LAST:event_publishButtonActionPerformed

    /**
     * start an animation thread
     */

    public void startAnimation(){
        class AnimationRunnable implements Runnable{
            public void run(){
                try {
                    for(int i = currentFrameNum; i < totalFrameNum; i++){
                        if(!playStatus){
                            break;
                        }
                        currentFrameNum = i;
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
            if(imageLabel.getMouseListeners().length <= 1){
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
            if (imageLabel.getMouseListeners().length > 1){
                for (int i = 1; i<imageLabel.getMouseListeners().length; i++){
                    imageLabel.removeMouseListener(imageLabel.getMouseListeners()[i]);
                }
            }
        }

        // display imageIcon
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
                System.out.println("Cannot find track1 text");
            } catch (IOException e) {
                System.out.println("Cannot find track1 text");
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
                System.out.println("Cannot find track2 text");
            } catch (IOException e) {
                System.out.println("Cannot find track2 text");
            }
        }
    }

    public void display(){

        // update image position
        imageLabel.setBounds((int)imagePosition_crt.startPoint[0], (int)imagePosition_crt.startPoint[1], (int)imagePosition_crt.size[0], (int)imagePosition_crt.size[1]);
        // update screen position
        screenLabel.setBounds((int)screenframePosition_crt.startPoint[0], (int)screenframePosition_crt.startPoint[1], (int)screenframePosition_crt.size[0], (int)screenframePosition_crt.size[1]);
        // change media pane size
        MediaPane.setBounds((int)mediaframePosition_org.startPoint[0], (int)mediaframePosition_org.startPoint[1], (int)mediaframePosition_crt.size[0], (int)mediaframePosition_crt.size[1]);

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

    public void addVideo(String fileName){
        int videoLength;   
        // read the video file
         try {
            File file = new File(fileName);
	    InputStream is = new FileInputStream(file);

	    long len = file.length();

            // calculate total video frame number
            videoLength = (int) (len / 352 / 288 / 3);

            // check if the published file will exceed the maximum frame number,
            // if yes, return
            if (currentFrameNum + videoLength > frameNumMax){
                JOptionPane.showMessageDialog(null, "frame number exceed, you cannot insert video here");
                return;
            }
            // if no, update published file frames information
            else{
                totalFrameNum = currentFrameNum + videoLength;
                for (int i = currentFrameNum; i < totalFrameNum; i++){
                    publishFile[i].videoExist = true;
                    publishFile[i].importVideo.videoFileName = fileName;
                    publishFile[i].importVideo.videoFrameNum = i  - currentFrameNum;
                }

                // update sliderBarlength
                jSlider1.setMaximum(totalFrameNum);

                // refresh screen
                display();
            }
            is.close();
        }catch (FileNotFoundException e) {
            System.out.println("Cannot find video file");
        } catch (IOException e) {
            System.out.println("Cannot find video file");
        }
    }

    public void addImage(String fileName){
        // read the image file
         try {
            File file = new File(fileName);
	    InputStream is = new FileInputStream(file);

            // set function for the imported image
            int selectedImageFunc = imageFuncSelection.getSelectedIndex();
            
            // add go to website function
            if(selectedImageFunc == 0){
                publishFile[currentFrameNum].imageExist = true;
                publishFile[currentFrameNum].importImage.imageFileName = fileName;
                publishFile[currentFrameNum].importImage.goWebsite = true;
            }
            
            // add display object function
            if(selectedImageFunc == 1){
                // update image information for the next 60 frames
                // if it exceeds the total frame number
                if(currentFrameNum + 60 > totalFrameNum){
                    for (int i = currentFrameNum; i < totalFrameNum; i++){
                        publishFile[i].imageExist = true;
                        publishFile[i].importImage.imageFileName = fileName;
                    }
                }

                else{
                    for (int i = currentFrameNum; i < currentFrameNum + 60; i++){
                        publishFile[i].imageExist = true;
                        publishFile[i].importImage.imageFileName = fileName;
                    }
                }
            }
            
            // add both function
            if(selectedImageFunc == 2){
                // update image information for the next 60 frames
                // if it exceeds the total frame number
                if(currentFrameNum + 60 > totalFrameNum){
                    for (int i = currentFrameNum; i < totalFrameNum; i++){
                        publishFile[i].imageExist = true;
                        publishFile[i].importImage.imageFileName = fileName;
                        publishFile[i].importImage.goWebsite = true;
                    }
                }

                else{
                    for (int i = currentFrameNum; i < currentFrameNum + 60; i++){
                        publishFile[i].imageExist = true;
                        publishFile[i].importImage.imageFileName = fileName;
                        publishFile[i].importImage.goWebsite = true;
                    }
                }
            }
            display();
            is.close();
        }catch (FileNotFoundException e) {
            System.out.println("Cannot find image file");
        } catch (IOException e) {
            System.out.println("Cannot find image file");
        }
    }

    public void addAudio(String fileName){
         int audioLength;
         try {
            File file = new File(fileName);
	    InputStream is = new FileInputStream(file);
	    long len = file.length();
            audioLength = (int) ((len-244)/1470);
            
            if (currentFrameNum + audioLength > frameNumMax){
                JOptionPane.showMessageDialog(null, "frame number exceed, you cannot insert audio here");
                return;
            }
            else{
                totalFrameNum = currentFrameNum + audioLength;
                for (int i = currentFrameNum; i < totalFrameNum; i++){
                    publishFile[i].importAudio.audioFileName = fileName;
                    publishFile[i].importAudio.audioFrameNum = i  - currentFrameNum;
                }
                jSlider1.setMaximum(totalFrameNum);
            }
            is.close();
        }catch (FileNotFoundException e) {
            System.out.println("cannot find audio file");
        } catch (IOException e) {
            System.out.println("cannot find audio file");
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

    private void addVideoButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVideoButton
        // TODO add your handling code here:
        int selectedVideo = videoSelection.getSelectedIndex();

        // if select the first video entry
        if (selectedVideo == 0)
        {
            String fileName = "interview_352_288_30.576v.rgb";
            // add video to the published file
            addVideo(fileName);
        }
        // if select the second video entry
        if (selectedVideo == 1)
        {
            String fileName = "gollum_352_288_1min.rgb";
            // add video to the published file
            addVideo(fileName);
        }
    }//GEN-LAST:event_addVideoButton

    private void timelineStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_timelineStateChanged
        // TODO add your handling code here:
        // update current frame number if slider knob is dragged
        if (jSlider1.getValueIsAdjusting() == true){
            currentFrameNum = jSlider1.getValue() - 1;
            
            // refresh screen
            display();
        }
    }//GEN-LAST:event_timelineStateChanged
    
    private void ScaleMediaX(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ScaleMediaX
        // update media size in x axis if slider knob is dragged
        mediaScaleFactor[0] = 0.01 * ScaleMediaSliderX.getValue();

        // update video size
        screenframePosition_crt.startPoint[0] = screenframePosition_org.startPoint[0]*mediaScaleFactor[0];
        screenframePosition_crt.size[0] = screenframePosition_org.size[0]*mediaScaleFactor[0];

        // update media pane size
        mediaframePosition_crt.size[0] = mediaframePosition_org.size[0]*mediaScaleFactor[0];

        // update image size
        imagePosition_crt.startPoint[0] = imagePosition_org.startPoint[0]*mediaScaleFactor[0];
        imagePosition_crt.size[0] = imagePosition_org.size[0]*mediaScaleFactor[0];

        // update button image1 size
        button1Position_crt.startPoint[0] = button1Position_org.startPoint[0]*mediaScaleFactor[0];
        button1Position_crt.size[0] = button1Position_org.size[0]*mediaScaleFactor[0];

        // update button image2 size
        button2Position_crt.startPoint[0] = button2Position_org.startPoint[0]*mediaScaleFactor[0];
        button2Position_crt.size[0] = button2Position_org.size[0]*mediaScaleFactor[0];

        // update button image1 size
        button3Position_crt.startPoint[0] = button3Position_org.startPoint[0]*mediaScaleFactor[0];
        button3Position_crt.size[0] = button3Position_org.size[0]*mediaScaleFactor[0];

        // refresh screen
        display();
        updateButtonImage(0);
        updateButtonImage(1);
        updateButtonImage(2);
    }//GEN-LAST:event_ScaleMediaX

    private void ScaleMediaY(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ScaleMediaY
        // update media size in y axis if slider knob is dragged
        mediaScaleFactor[1] = 0.01 * ScaleMediaSliderY.getValue();

        // update video size
        screenframePosition_crt.startPoint[1] = screenframePosition_org.startPoint[1]*mediaScaleFactor[1];
        screenframePosition_crt.size[1] = screenframePosition_org.size[1]*mediaScaleFactor[1];

        // update media pane size
        mediaframePosition_crt.size[1] = mediaframePosition_org.size[1]*mediaScaleFactor[1];

        // update image size
        imagePosition_crt.startPoint[1] = imagePosition_org.startPoint[1]*mediaScaleFactor[1];
        imagePosition_crt.size[1] = imagePosition_org.size[1]*mediaScaleFactor[1];

        // update button image1 size
        button1Position_crt.startPoint[1] = button1Position_org.startPoint[1]*mediaScaleFactor[1];
        button1Position_crt.size[1] = button1Position_org.size[1]*mediaScaleFactor[1];

        // update button image2 size
        button2Position_crt.startPoint[1] = button2Position_org.startPoint[1]*mediaScaleFactor[1];
        button2Position_crt.size[1] = button2Position_org.size[1]*mediaScaleFactor[1];

        // update button image1 size
        button3Position_crt.startPoint[1] = button3Position_org.startPoint[1]*mediaScaleFactor[1];
        button3Position_crt.size[1] = button3Position_org.size[1]*mediaScaleFactor[1];
        
        // refresh screen
        display();
        updateButtonImage(0);
        updateButtonImage(1);
        updateButtonImage(2);
    }//GEN-LAST:event_ScaleMediaY

    private void dragVideo(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dragVideo
        // TODO add your handling code here:
        Point p = evt.getPoint();
        double videoMoveVector[] = new double[2];
        videoMoveVector[0] = p.x - mousePressVideo[0];
        videoMoveVector[1] = p.y - mousePressVideo[1];

        // update screen frame position
        screenframePosition_crt.startPoint[0] = screenframePosition_crt.startPoint[0] + videoMoveVector[0];
        screenframePosition_crt.startPoint[1] = screenframePosition_crt.startPoint[1] + videoMoveVector[1];
        screenframePosition_org.startPoint[0] = screenframePosition_org.startPoint[0] + videoMoveVector[0];
        screenframePosition_org.startPoint[1] = screenframePosition_org.startPoint[1] + videoMoveVector[1];

        // refresh screen
        display();
    }//GEN-LAST:event_dragVideo

    private void pressVideo(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pressVideo
        // TODO add your handling code here:
        Point p = evt.getPoint();
        mousePressVideo[0] = p.x;
        mousePressVideo[1] = p.y;
    }//GEN-LAST:event_pressVideo

    private void AddImageButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddImageButton
        // TODO add your handling code here:
        int selectedImage = imageSelection.getSelectedIndex();

        // if select the first image entry
        if (selectedImage == 0){
            String fileName = "image1.rgb";
            // add video to the published file
            addImage(fileName);
        }

        // if select the second image entry
        if (selectedImage == 1){
            String fileName = "image2.rgb";
            // add video to the published file
            addImage(fileName);
        }       
    }//GEN-LAST:event_AddImageButton

    private void drageImage(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drageImage
        // TODO add your handling code here:
        Point p = evt.getPoint();
        double imageMoveVector[] = new double[2];
        imageMoveVector[0] = p.x - mousePressImage[0];
        imageMoveVector[1] = p.y - mousePressImage[1];

        // update screen frame position
        imagePosition_crt.startPoint[0] = imagePosition_crt.startPoint[0] + imageMoveVector[0];
        imagePosition_crt.startPoint[1] = imagePosition_crt.startPoint[1] + imageMoveVector[1];
        imagePosition_org.startPoint[0] = imagePosition_org.startPoint[0] + imageMoveVector[0];
        imagePosition_org.startPoint[1] = imagePosition_org.startPoint[1] + imageMoveVector[1];

        // refresh screen
        display();
    }//GEN-LAST:event_drageImage

    private void pressImage(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pressImage
        // TODO add your handling code here:
        Point p = evt.getPoint();
        mousePressImage[0] = p.x;
        mousePressImage[1] = p.y;
    }//GEN-LAST:event_pressImage

    private void AddButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButton
        // TODO add your handling code here:
        int selectedFunction = ButtonFunctionSelection.getSelectedIndex();
        int selectedImage = ButtonImage.getSelectedIndex();
        String fileName = new String();
        // select button image
        if (selectedImage == 0){
            fileName = "small_play.v576.rgb";
        } 
        else if (selectedImage == 1){
            fileName = "small_pause.v576.rgb";
        }
        else if (selectedImage == 2){
            fileName = "small_stop.v576.rgb";
        }

        // add play button
        if (selectedFunction == 0){
            playButtonExist = true;
            playButtonImgName = fileName;

            // save image to image buffer
            saveButtonImage(playButtonImg, fileName);
            
            // add image to play button
            updateButtonImage(0);

            // set play button visible
            playButton.setVisible(true);
        }

        // add pause button
        else if(selectedFunction == 1)
        {
            pauseButtonExist = true;
            pauseButtonImgName = fileName;

            // save image to image buffer
            saveButtonImage(pauseButtonImg, fileName);

            // add image to play button
            updateButtonImage(1);

            // set play button visible
            pauseButton.setVisible(true);
        }

        // add stop button
        else if(selectedFunction == 2)
        {
            stopButtonExist = true;
            stopButtonImgName = fileName;
            
            // save image to image buffer
            saveButtonImage(stopButtonImg, fileName);

            // add image to play button
            updateButtonImage(2);

            // set play button visible
            stopButton.setVisible(true);
        }
    }//GEN-LAST:event_AddButton

    private void dragPlayButton(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dragPlayButton
        // TODO add your handling code here:
        Point p = evt.getPoint();
        double imageMoveVector[] = new double[2];
        imageMoveVector[0] = p.x - mousePressPlayButton[0];
        imageMoveVector[1] = p.y - mousePressPlayButton[1];

        // update screen frame position
        button1Position_crt.startPoint[0] = button1Position_crt.startPoint[0] + imageMoveVector[0];
        button1Position_crt.startPoint[1] = button1Position_crt.startPoint[1] + imageMoveVector[1];
        button1Position_org.startPoint[0] = button1Position_org.startPoint[0] + imageMoveVector[0];
        button1Position_org.startPoint[1] = button1Position_org.startPoint[1] + imageMoveVector[1];

        playButton.setBounds((int)button1Position_crt.startPoint[0], (int)button1Position_crt.startPoint[1], (int)button1Position_crt.size[0], (int)button1Position_crt.size[1]);
    }//GEN-LAST:event_dragPlayButton

    private void pressPlayButton(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pressPlayButton
        // TODO add your handling code here:
        Point p = evt.getPoint();
        mousePressPlayButton[0] = p.x;
        mousePressPlayButton[1] = p.y;
    }//GEN-LAST:event_pressPlayButton

    private void dragPauseButton(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dragPauseButton
        // TODO add your handling code here:
        Point p = evt.getPoint();
        double imageMoveVector[] = new double[2];
        imageMoveVector[0] = p.x - mousePressPauseButton[0];
        imageMoveVector[1] = p.y - mousePressPauseButton[1];

        // update screen frame position
        button2Position_crt.startPoint[0] = button2Position_crt.startPoint[0] + imageMoveVector[0];
        button2Position_crt.startPoint[1] = button2Position_crt.startPoint[1] + imageMoveVector[1];
        button2Position_org.startPoint[0] = button2Position_org.startPoint[0] + imageMoveVector[0];
        button2Position_org.startPoint[1] = button2Position_org.startPoint[1] + imageMoveVector[1];

        pauseButton.setBounds((int)button2Position_crt.startPoint[0], (int)button2Position_crt.startPoint[1], (int)button2Position_crt.size[0], (int)button2Position_crt.size[1]);
    }//GEN-LAST:event_dragPauseButton

    private void pressPauseButton(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pressPauseButton
        // TODO add your handling code here:
        Point p = evt.getPoint();
        mousePressPauseButton[0] = p.x;
        mousePressPauseButton[1] = p.y;
    }//GEN-LAST:event_pressPauseButton

    private void dragStopButton(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dragStopButton
        // TODO add your handling code here:
        Point p = evt.getPoint();
        double imageMoveVector[] = new double[2];
        imageMoveVector[0] = p.x - mousePressStopButton[0];
        imageMoveVector[1] = p.y - mousePressStopButton[1];

        // update screen frame position
        button3Position_crt.startPoint[0] = button3Position_crt.startPoint[0] + imageMoveVector[0];
        button3Position_crt.startPoint[1] = button3Position_crt.startPoint[1] + imageMoveVector[1];
        button3Position_org.startPoint[0] = button3Position_org.startPoint[0] + imageMoveVector[0];
        button3Position_org.startPoint[1] = button3Position_org.startPoint[1] + imageMoveVector[1];

        stopButton.setBounds((int)button3Position_crt.startPoint[0], (int)button3Position_crt.startPoint[1], (int)button3Position_crt.size[0], (int)button2Position_crt.size[1]);
    }//GEN-LAST:event_dragStopButton

    private void pressStopButton(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pressStopButton
        // TODO add your handling code here:
        Point p = evt.getPoint();
        mousePressStopButton[0] = p.x;
        mousePressStopButton[1] = p.y;
    }//GEN-LAST:event_pressStopButton

    private void playMedia(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_playMedia
        if(playStatus == false){
            playStatus = true;
            startAnimation();
        }
    }//GEN-LAST:event_playMedia

    private void pauseMedia(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pauseMedia
        // TODO add your handling code here:
        playStatus = false;
    }//GEN-LAST:event_pauseMedia

    private void stopMedia(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stopMedia
        // TODO add your handling code here:
        if (!playStatus) return;
        playStatus = false;
        currentFrameNum = 0;
        display();
    }//GEN-LAST:event_stopMedia
    
    private void displayPosition(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayPosition
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
    }//GEN-LAST:event_displayPosition

    private void addAudioButton(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addAudioButton
        // TODO add your handling code here:
        int selectedAudio = audioSelection.getSelectedIndex();
        // if select the first audio entry
        if (selectedAudio == 0)
        {
            String fileName = "interview_22050_16.wav";
            // add audio to the published file
            addAudio(fileName);
        }
        // if select the second audio entry
        if (selectedAudio == 1)
        {
            String fileName = "gollum_22050hz_16bit_mono_1min.wav";
            // add audio to the published file
            addAudio(fileName);
        }
    }//GEN-LAST:event_addAudioButton

    private void trackObjectClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trackObjectClicked
        // TODO add your handling code here:
        for (int i = currentFrameNum; i < currentFrameNum + 300; i++){
            publishFile[i].isTracking = true;
        }
        display();
    }//GEN-LAST:event_trackObjectClicked

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
        NewJFrame screen = new NewJFrame();
        screen.pack();
        screen.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButtom;
    private javax.swing.JButton AddImage;
    private javax.swing.JButton AddVideo;
    private javax.swing.JComboBox ButtonFunctionSelection;
    private javax.swing.JComboBox ButtonImage;
    private javax.swing.JLayeredPane MediaPane;
    private javax.swing.JLabel ScaleMediaLabel;
    private javax.swing.JSlider ScaleMediaSliderX;
    private javax.swing.JSlider ScaleMediaSliderY;
    private javax.swing.JLabel ScaleMediaXLabel;
    private javax.swing.JLabel ScaleMediaYLabel;
    private javax.swing.JButton addAudio;
    private javax.swing.JComboBox audioSelection;
    private javax.swing.JComboBox imageFuncSelection;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JComboBox imageSelection;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel pauseButton;
    private javax.swing.JLabel playButton;
    private javax.swing.JButton publishButton;
    private javax.swing.JLabel screenLabel;
    private javax.swing.JLabel stopButton;
    private javax.swing.JButton trackObject;
    private javax.swing.JComboBox videoSelection;
    // End of variables declaration//GEN-END:variables

}
