package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class affirmation_adventures extends Game {
    @Override
    public void create() {
        setScreen(new MainMenuScreen());
    }
}
