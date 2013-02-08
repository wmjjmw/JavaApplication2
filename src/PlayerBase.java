import	java.io.File;
import	java.io.IOException;
import	javax.sound.sampled.AudioFormat;
import	javax.sound.sampled.AudioInputStream;
import	javax.sound.sampled.AudioSystem;
import	javax.sound.sampled.LineUnavailableException;
import	javax.sound.sampled.SourceDataLine;
import	javax.sound.sampled.DataLine;

public class PlayerBase implements Runnable {
        private static final int BUFFER_SIZE = 4;
        public String filetoPlay;
        private  boolean threadExit = false;
        private  boolean stopped = true;
        private  boolean paused = false;
        private  boolean playing = false;
        public   Object synch = new Object();
        private Thread playerThread = null;

        public PlayerBase() {
        }
        public void run() {
            while (! threadExit)  {
                waitforSignal();
                if (! stopped)
                    playMusic();
            }
        }
        public void endThread() {
            threadExit = true;
            synchronized(synch) {
                synch.notifyAll();
            }
            try {
                Thread.sleep(50);
            } catch (Exception ex) {}
        }
        public void waitforSignal() {
            try {
               synchronized(synch) {
                   synch.wait();
               }
            } catch (Exception ex) {}
        }
        public void play(String s) {
            if ((playerThread == null)||(!filetoPlay.equals(s)))  {
                filetoPlay = s;
                playerThread = new Thread(this);
                playerThread.start();
                try {
                    Thread.sleep(500);
                } catch (Exception ex) {}
            }
            synchronized(synch) {
                stopped = false;
                paused = false;
                synch.notifyAll();
            }
        }
        public void playMusic(){
            byte[] audioData = new byte[BUFFER_SIZE];
            AudioInputStream ais = null;
            SourceDataLine dataLine = null;
            try{
	        ais = AudioSystem.getAudioInputStream(new File(filetoPlay));
                ais.skip(244+1470*20);
            }catch (Exception e) {}
            AudioFormat af = ais.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class,af);
            try{
                dataLine = (SourceDataLine) AudioSystem.getLine(info);
                dataLine.open(af);
            }catch(Exception e) {}
            playing = true;
            dataLine.start();
            int readBytes = 0;
            while ((readBytes != -1) && (!stopped) && (!threadExit)){
                try {
		    readBytes = ais.read(audioData, 0, BUFFER_SIZE);
                    if(readBytes >= 0){
                        int outBytes = dataLine.write(audioData, 0, readBytes);
                        if(paused){waitforSignal(); }
                    }
		}catch (IOException e){e.printStackTrace();}
            }
            playing = false;
            dataLine.drain();
            dataLine.stop();
            dataLine.close();
            playing = false;
        }
        public void stop() {
            if(paused) return;
            stopped = true;
            playerThread.stop();
            filetoPlay = "yzp";
            //waitForPlayToStop();
        }
        public void waitForPlayToStop() {
            while( playing)
                try {
                    Thread.sleep(100);
                } catch (Exception ex) {}
        }
        public void pause() {
            if (stopped) return;
            synchronized(synch) {
                paused = true;
                synch.notifyAll();
            }
        }
    }
