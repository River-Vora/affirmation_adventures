package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class affirmation_adventures extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));

        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(8, 5);
    }
}
