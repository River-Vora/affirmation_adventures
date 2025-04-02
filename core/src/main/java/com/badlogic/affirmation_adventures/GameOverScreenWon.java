package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Screen displayed when the player wins the game.
 */
public class GameOverScreenWon implements Screen {
    final affirmation_adventures game;

    /**
     * Constructs a new GameOverScreenWon.
     *
     * @param game the main game instance
     */
    public GameOverScreenWon(final affirmation_adventures game) {
        this.game = game;
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        game.font.draw(game.batch, "You won! Great job! Hope you learned about yourself and boosted your self-confidence!", game.viewport.getWorldWidth() / 2 - 300, game.viewport.getWorldHeight() / 2);
        game.font.draw(game.batch, "Always remember, you are strong, you are brave, you are lovable, you are worthy.", game.viewport.getWorldWidth() / 2 - 300, game.viewport.getWorldHeight() / 2 - 20);
        game.font.draw(game.batch, "Press Enter to return to the main menu. Press space to play again.", game.viewport.getWorldWidth() / 2 - 300, game.viewport.getWorldHeight() / 2 - 40);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MainMenuScreen(game));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }
    }

    /**
     * Called when the screen is resized.
     *
     * @param width  the new width
     * @param height the new height
     */
    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    /**
     * Called when the game is paused.
     */
    @Override
    public void pause() {

    }

    /**
     * Called when the game is resumed from a paused state.
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}
