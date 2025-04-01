package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreenLostByNotEnough implements Screen {
    final affirmation_adventures game;

    public GameOverScreenLostByNotEnough(final affirmation_adventures game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        game.font.draw(game.batch, "Game Over. You must collect all the treasures before the time is up. It's okay. You can play again!", game.viewport.getWorldWidth() / 2 - 300, game.viewport.getWorldHeight() / 2);
        game.font.draw(game.batch, "Remember, it's just a game. You can always try again!", game.viewport.getWorldWidth() / 2 - 300, game.viewport.getWorldHeight() / 2 - 20);
        game.font.draw(game.batch, "You are strong, you are brave, you are lovable, you are worthy.", game.viewport.getWorldWidth() / 2 - 300, game.viewport.getWorldHeight() / 2 - 40);
        game.font.draw(game.batch, "Press Enter to return to the main menu. Press space to play again.", game.viewport.getWorldWidth() / 2 - 300, game.viewport.getWorldHeight() / 2 - 60);

        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MainMenuScreen(game));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
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
