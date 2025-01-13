package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class affirmation_adventures extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Viewport viewport;

    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(800, 500);

        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        if (screen != null) {
            screen.dispose();
        }
    }
}
