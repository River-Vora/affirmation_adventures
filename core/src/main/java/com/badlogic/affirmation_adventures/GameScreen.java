package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameScreen implements Screen {
    final affirmation_adventures game;

    Texture playerTexture;
    Texture backgroundTexture;
    Sprite playerSprite;


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
