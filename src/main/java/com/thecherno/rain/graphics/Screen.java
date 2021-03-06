package com.thecherno.rain.graphics;

import java.util.Random;

public class Screen {
    private int width, height;
    public int[] pixels;
    private int counter=0;
    public final int MAP_SIZE=64;
    public final int MAP_SIZE_MASK=MAP_SIZE-1;


    private Random random = new Random();

    private int[] tiles = new int[MAP_SIZE * MAP_SIZE];


    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels= new int[width * height];

        for(int i=0; i < MAP_SIZE * MAP_SIZE; i++){
            tiles[i]= random.nextInt(0xFFFFFF);
            tiles[0]=0;
        }

    }

    public void clear(){
        for(int i=0; i<pixels.length;i++){
            pixels[i]=0;
        }
    }

    public void render(int xOffSet, int yOffset){


        for(int y=0; y< height;y++){
            int yy= y + yOffset;
            //if(y <0 || y >=height)break;
            int yp=y + yOffset;
            if(yp <0 || yp >= height)continue;
            for (int x=0; x< width;x++){
                //if(x<0 || x >=width)break;
                //int indexTile = x / 16 + y /16 * 64;
                int xx = x + xOffSet;
                int xp=x + xOffSet;
                //int indexTile = ((xx >> 4) & MAP_SIZE_MASK) + ((yy >> 4) & MAP_SIZE_MASK) * MAP_SIZE;
                //pixels[x + (y * width)] = tiles[indexTile];
                if(xp < 0 || xp >=width) continue;

                pixels[xp + (yp * width)] = Sprite.grass.getPixels()[(x&15) +( y & 15) * Sprite.grass.getSIZE()];


            }
        }
    }
}
