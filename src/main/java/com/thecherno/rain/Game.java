package com.thecherno.rain;

import com.thecherno.rain.graphics.Screen;
import com.thecherno.rain.input.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game  extends Canvas implements Runnable{
    private boolean isRunning=false;
    private JFrame frame;
    private Thread thread;
    public static int width=300;
    public static int height=width/16*9;
    public static int scale=3;
    private int x=0,y=0;

    public static String title = "Rain";

    private Screen screen;
    private Keyboard keyboard;

    private BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);

    private int[] pixels=((DataBufferInt)image.getRaster().getDataBuffer()).getData();


    public Game() {
        Dimension size = new Dimension(width*scale, height*scale);
        setPreferredSize(size);
        screen = new Screen(width,height);
        frame = new JFrame();
        keyboard=new Keyboard();

        addKeyListener(keyboard);

    }

    private void start(){
        isRunning=true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    private void stop(){
        isRunning=false;


        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs==null){

            createBufferStrategy(3);
            return;
        }


        screen.clear();
        screen.render(x,y);

        for(int i=0; i<pixels.length;i++){
            pixels[i]=screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        //g.setColor(Color.BLACK);
        //g.fillRect(0,0, getWidth(), getHeight());
        g.drawImage(image,0,0,getWidth(),getHeight(),null);
        g.dispose();
        bs.show();




    }

    public void update(){

        keyboard.update();
        if(keyboard.isUp())y--;
        if(keyboard.isDown())y++;
        if(keyboard.isLeft())x--;
        if(keyboard.isRight())x++;
    }


    public void run() {
        long lastTime = System.nanoTime();
        final double ns= 1000000000.0 / 60.0;
        long timer=System.currentTimeMillis();
        double delta =0;

        int frames=0;
        int updates=0;

        requestFocus();
        while(isRunning) {
            long now = System.nanoTime();
            delta += (now-lastTime) /ns;
            lastTime=now;
            while(delta >=1){
                update();
                updates++;
                delta--;
            }

            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer+=1000;

                frame.setTitle( Game.title + "| " + updates + " ups, " + frames + " fps");
                updates=0;
                frames=0;
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false);

        game.frame.setTitle(Game.title);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();

    }

}
