package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen{
    final affirmation_adventures game;

    public Texture playerTexture;
    public Sprite playerSprite;
    public OrthogonalTiledMapRenderer mapRenderer;
    public TiledMap map;
    public OrthographicCamera camera;

    int torch1X = 903;
    int torch1Y = 137;
    int torch2X = 712;
    int torch2Y = 73;
    int torch3X = 566;
    int torch3Y = 186;
    int torch4X = 791;
    int torch4Y = 308;
    int torch5X = 581;
    int torch5Y = 730;
    int torch6X = 376;
    int torch6Y = 811;
    int torch7X = 600;
    int torch7Y = 1335;
    int torch8X = 464;
    int torch8Y = 1518;

    public GameScreen(final affirmation_adventures game) {
        this.game = game;

        playerTexture = new Texture("player.png");

        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(5, 5);

        map = new TmxMapLoader().load("Dungeon.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 2 / 16f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 30, 20);
        camera.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();

    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        camera.position.x = playerSprite.getX();
        camera.position.y = playerSprite.getY();
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.begin();
        playerSprite.draw(game.batch);
        game.batch.end();

    }

    private void logic() {

    }

    private void input() {
        float speed = 100f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerSprite.translateX(speed * delta);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerSprite.translateX(-speed * delta);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            playerSprite.translateY(speed * delta);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            playerSprite.translateY(-speed * delta);
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
        playerTexture.dispose();
        mapRenderer.dispose();
        map.dispose();

    }
}
