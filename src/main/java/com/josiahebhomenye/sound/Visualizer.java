package com.josiahebhomenye.sound;

import javax.sound.sampled.AudioFormat;
import javax.swing.*;
import java.awt.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public class Visualizer extends JFrame {
    private Canvas canvas;
    private JSlider slider;
    private Timer timer;
    private int t;
    private AudioFormat format;

    public Visualizer(byte[] data, AudioFormat format){
        super("Sound visualizer");
        t = 1;
        this.format = format;
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice dev = env.getDefaultScreenDevice();
        DisplayMode mode = dev.getDisplayMode();
        setSize(mode.getWidth(), 400);
        canvas = new Canvas(data, format);
        int max = (int)Math.ceil(1/canvas.resolution);
        slider = new JSlider(1, max, (int)(canvas.resolution * max));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setPaintTrack(true);

        slider.addChangeListener(e -> {
            SwingUtilities.invokeLater(() -> {
                canvas.resolution = slider.getValue() * (1.0f/slider.getMaximum());
                canvas.updateView();
                canvas.repaint();
            });
        });

        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        add(slider, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    //    timer = new Timer(t, e -> canvas.repaint());
    //    timer.start();
    }

    class Canvas extends JPanel{
        private float resolution;
        private byte[] data;
        private byte[] view;
        private AudioFormat format;
        ByteBuffer buffer;
        int pos;

        public Canvas(byte[] data, AudioFormat format){
            resolution = 0.001f;
            this.data = data;
            this.format = format;
            updateView();

            setBackground(Color.BLACK);
            int width = (int)(Visualizer.this.getWidth());
            int height = (int)(Visualizer.this.getHeight());
            setSize(width, height);
        }

        public void updateView(){
            int length =  (int)(data.length * resolution);
            view = new byte[length];
            buffer = ByteBuffer.wrap(view);
            if(format.isBigEndian()){
                buffer.order(ByteOrder.BIG_ENDIAN);
            }else{
                buffer.order(ByteOrder.LITTLE_ENDIAN);
            }


            System.arraycopy(data, 0, view, 0, length);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            ShortBuffer sb = buffer.asShortBuffer();
            int length = sb.limit();
            float steps = (float)getWidth()/length;
            int prevX = 0;
            int prevY = getHeight()/2;
            clear(g, Color.BLACK);
            g.setColor(Color.GREEN);
            int nChannels = format.getChannels();
            for(int i = 0; i < length; i+=nChannels){
                float sample = sb.get(i);
                int x = (int)(steps * i);
                int y = (int)((sample / (float)Short.MAX_VALUE) * getHeight() * 0.5) + (int)(getHeight() * 0.5);


                g.drawLine(prevX, prevY, x, y);
                prevX = x;
                prevY = y;
            }
//            if(pos < view.length) {
//                System.arraycopy(view, 0, view, (Short.BYTES * t), view.length - (Short.BYTES * t));
//                System.arraycopy(data, pos * (Short.BYTES * t), view, 0, (Short.BYTES * t));
//                pos++;
//            }else {
//                timer.stop();
//            }
       //     pos %= data.length;
        //    System.arraycopy(data, pos, view, 0, length);
        }

        protected void clear(Graphics g, Color color){
            g.setColor(color);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
