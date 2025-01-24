package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameScreen implements Screen {
    final affirmation_adventures game;

    Texture playerTexture;
    Texture backgroundTexture;
    Sprite playerSprite;
    int torch1X = 903;
    int torch1Y = 137;
    int torch2X = 712;
    int torch2Y = 73;
    int torch3X = 566;
    int torch3Y = 186;
    int torch4X = 791;
    int torch4Y = 308;
    int torch5X = 581;
    int torch5Y = 730;
    int torch6X = 376;
    int torch6Y = 811;
    int torch7X = 600;
    int torch7Y = 1335;
    int torch8X = 464;
    int torch8Y = 1518;

    public GameScreen(final affirmation_adventures game) {
        this.game = game;

        backgroundTexture = new Texture("Dungeon.png");
        playerTexture = new Texture("player.png");

        playerSprite = new Sprite(playerTexture);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();

    }

    private void draw() {

    }

    private void logic() {

    }

    private void input() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
