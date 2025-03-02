package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Represents the options screen where the player can change settings.
 */

public class OptionsScreen implements Screen {
    final affirmation_adventures game;

    public Stage stage;
    public Music music;
    /**
     * Constructs a new OptionsScreen.
     *
     * @param game  the main game instance
     * @param music Music for the game
     */

    public OptionsScreen(final affirmation_adventures game, Music music) {
        this.game = game;
        this.music = music;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create a table to organize the layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Create the volume slider
        Slider volumeSlider = new Slider(0f, 1f, 0.1f, false, skin, "default-horizontal");
        volumeSlider.setValue(music.getVolume()); // Set initial value to current music volume
        volumeSlider.addListener(event -> {
            music.setVolume(volumeSlider.getValue());
            return false;

        });

        // Add the slider to the table
        table.add("Volume").pad(10);
        table.add(volumeSlider).width(200).pad(10);
    }

    /**
     * Prepares the screen for display.
     */

    @Override
    public void show() {

    }

    /**
     * Renders the screen.
     * @param delta the time in seconds since the last render
     */

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    /**
     * Resizes the screen.
     * @param width the new width of the screen
     * @param height the new height of the screen
     */

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Pauses the screen.
     */

    @Override
    public void pause() {

    }

    /**
     * Resumes the screen after pausing.
     */
    @Override
    public void resume() {

    }

    /**
     * Hides the screen.
     */

    @Override
    public void hide() {

    }

    /**
     * Disposes of the screen and its assets when they are no longer needed.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
