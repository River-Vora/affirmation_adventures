package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * First screen of the application. Displayed after the application is created.
 */

public class MainMenuScreen implements Screen {

    Texture playButton;
    Texture playButtonPressed;
    Texture menuBackground;
    Texture OptionsButton;
    Texture OptionsButtonPressed;
    Music music;

    final affirmation_adventures game;

    /**
     * Constructs a new MainMenuScreen.
     * @param game the main game instance
     */

    public MainMenuScreen(final affirmation_adventures game) {
        this.game = game;

        OptionsButton = new Texture("OptionsButton.png");
        OptionsButtonPressed = new Texture("OptionsButtonPressed.png");
        playButton = new Texture("playbutton.png");
        playButtonPressed = new Texture("playbuttonpressed.png");
        menuBackground = new Texture("Menu_Backdrop.png");
        menuBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        music = Gdx.audio.newMusic(Gdx.files.internal("BattleTheme.mp3"));
    }
    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        draw();
    }

    /**
     * Handles the drawing of the main menu screen.
     */

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        game.batch.draw(menuBackground, 0, 0, worldWidth, worldHeight);

        float playButtonX = (worldWidth - playButton.getWidth()) / 2;
        float playButtonY = (worldHeight - playButton.getHeight()) / 2;
        game.batch.draw(playButton, playButtonX, playButtonY);

        float optionsButtonX = (worldWidth - OptionsButton.getWidth()) / 2;
        float optionsButtonY = playButtonY - OptionsButton.getHeight() - 20; // 10 pixels below the Play button
        game.batch.draw(OptionsButton, optionsButtonX, optionsButtonY);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = worldHeight - touchY;

            if (touchX >= playButtonX && touchX <= playButtonX + playButton.getWidth() &&
                touchY >= playButtonY && touchY <= playButtonY + playButton.getHeight()) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
            else if (touchX >= optionsButtonX && touchX <= optionsButtonX + OptionsButton.getWidth() &&
                touchY >= optionsButtonY && touchY <= optionsButtonY + OptionsButton.getHeight()) {
                game.setScreen(new OptionsScreen(game, music));
                dispose();
            }
        }
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
        playButton.dispose();
        playButtonPressed.dispose();
        menuBackground.dispose();
        // Destroy screen's assets here.
        playButton.dispose();
        playButtonPressed.dispose();
        menuBackground.dispose();
        OptionsButton.dispose();
        OptionsButtonPressed.dispose();
    }
}
