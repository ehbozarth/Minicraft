package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 100;
    final int HEIGHT = 100;

    SpriteBatch batch;
    TextureRegion down, up, right, left;
    FitViewport viewport;

    float x;
    float y;
    //V means Velocity
    float xV = 0;
    float yV = 0;
    float time = 0;
    final float MAX_VELOCITY = 100; //100 Pixels per Second
    final int DRAW_WIDTH = WIDTH;
    final int DRAW_HEIGHT = HEIGHT;

    @Override
    public void create () {
        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][2];
        left = grid[6][3];
    }//End of Create Method

    @Override
    public void render () {
        move();
        draw();
    }//End of Render Method

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }//End of Override Resize Method

    public void move(){
        if(x >= 0 && y >= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                yV = MAX_VELOCITY;
                //y++;
            }//UP Arrow KEY
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                yV = MAX_VELOCITY * -1;
                //y--;
            }//Down Arrow Key
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                xV = MAX_VELOCITY;
                //x++;
            }//Right Arrow Key
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                xV = MAX_VELOCITY * -1;
                //x--;
            }//Left Arrow Key

            float oldX = x;
            float oldY = y;
            //Acceleration with Velocity per refreshed Frame
            x += xV * Gdx.graphics.getDeltaTime();
            y += yV * Gdx.graphics.getDeltaTime();

            //Setting of the outer limits of the window
            //Cannot cross invisible walls
            if(x< 0 || x> (Gdx.graphics.getWidth()-WIDTH)){
                x = oldX;
            }
            if(y < 0 || y > (Gdx.graphics.getHeight()-HEIGHT)){
                y = oldY;
            }
            //Dampening Velocity
            xV *= 0.9;
            yV *= 0.9;

        }//End of If
    }//End of Move Method

    void draw(){
        time += Gdx.graphics.getDeltaTime();
        TextureRegion img;
        //This is the player movement and direction facing
        if(Math.abs(yV) > Math.abs(xV)){
            if(yV > 0){
                img = up;
            }
            else{
                img = down;
            }
        }//End of if statement
        else{
            if (xV > 0) {
                img = right;
            }
            else{
                img = left;
            }
        }//End of outer Else statement
        Gdx.gl.glClearColor(0,0.5f,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // draw here
        //This is to flip the image
        if(xV>=0){
            batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
        }
        else{
            batch.draw(img, x + DRAW_WIDTH,y, DRAW_WIDTH * -1, DRAW_HEIGHT);
        }
        batch.end();
    }//End of Draw Method

}//End of MiniCraft Class
