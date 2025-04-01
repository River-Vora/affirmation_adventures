package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * First screen of the application. Displayed after the application is created.
 */

public class MainMenuScreen implements Screen {

    Texture playButton;
    Texture menuBackground;
    Music music;
    Texture instructionsTexture;
    boolean showInstructions = true;
    Rectangle playButtonBounds;


    final affirmation_adventures game;

    /**
     * Constructs a new MainMenuScreen.
     * @param game the main game instance
     */

    public MainMenuScreen(final affirmation_adventures game) {
        this.game = game;

        instructionsTexture = new Texture("instructions.png");
        playButton = new Texture("playbutton.png");
        menuBackground = new Texture("Menu_Backdrop.png");
        menuBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        music = Gdx.audio.newMusic(Gdx.files.internal("BattleTheme.mp3"));

        playButtonBounds = new Rectangle();

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

        playButtonBounds.set(playButtonX, playButtonY, playButton.getWidth(), playButton.getHeight());

        game.batch.draw(playButton, playButtonX, playButtonY);

        float instructionsButtonX = (worldWidth - instructionsTexture.getWidth()) / 2;
        float instructionsButtonY = (worldHeight - instructionsTexture.getHeight()) / 2;

        /* if (showInstructions) {
            game.batch.draw(instructionsTexture, instructionsButtonX, instructionsButtonY);
        }


         */
        game.batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.viewport.getCamera().unproject(touchPos);
            float touchX = touchPos.x;
            float touchY = touchPos.y;

            touchY = worldHeight - touchY;

            if (playButtonBounds.contains(touchX, touchY)) {
                game.setScreen(new GameScreen(game));
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
        menuBackground.dispose();
        playButton.dispose();
        menuBackground.dispose();
    }
}
