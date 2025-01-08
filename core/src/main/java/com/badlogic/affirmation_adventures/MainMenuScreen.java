package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

/** First screen of the application. Displayed after the application is created. */
public class MainMenuScreen implements Screen {
    Texture playButton;
    Texture playButtonPressed;

    final affirmation_adventures game;

    public MainMenuScreen(final affirmation_adventures game) {
        this.game = game;
        playButton = new Texture("playbutton.png");
        playButtonPressed = new Texture("playbuttonpressed.png");
    }
    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
		ScreenUtils.clear(Color.BLACK);

		game.viewport.apply();
		game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
		game.batch.begin();
        // Draw your screen here. "delta" is the time since last render in seconds.
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
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
