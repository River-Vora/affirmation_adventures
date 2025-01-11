package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

/** First screen of the application. Displayed after the application is created. */
public class MainMenuScreen implements Screen {
    
    Texture playButton;
    Texture playButtonPressed;
    Texture menuBackground;

    final affirmation_adventures game;

    public MainMenuScreen(final affirmation_adventures game) {
        this.game = game;

        playButton = new Texture("playbutton.png");
        playButtonPressed = new Texture("playbuttonpressed.png");
        menuBackground = new Texture("Menu_Backdrop.png");
    }
    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        draw();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        game.batch.draw(menuBackground, 0, 0, worldWidth, worldHeight);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
