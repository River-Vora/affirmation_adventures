package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final affirmation_adventures game;

    public Texture playerTexture;
    public Sprite playerSprite;
    public OrthogonalTiledMapRenderer mapRenderer;
    public TiledMap map;
    public OrthographicCamera camera;

    public Rectangle playerBounds;

    int torch1X = 903;
    int torch1Y = 137;
    int treasure1X = 712;
    int treasure1Y = 73;
    int treasure2X = 566;
    int treasure2Y = 186;
    int torch2X = 791;
    int torch2Y = 308;
    int torch3X = 581;
    int torch3Y = 730;
    int torch4X = 376;
    int torch4Y = 811;
    int torch5X = 600;
    int torch5Y = 1335;
    int treasure3X = 464;
    int treasure3Y = 1518;

    public GameScreen(final affirmation_adventures game) {
        this.game = game;

        playerTexture = new Texture("player.png");

        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(5, 5);

        playerBounds = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());


        map = new TmxMapLoader().load("Dungeon.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);


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
        camera.position.set(playerSprite.getX(), playerSprite.getY(), 0);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        playerSprite.draw(game.batch);
        game.batch.end();

    }

    private void logic() {
        playerBounds.setPosition(playerSprite.getX(), playerSprite.getY());
    }

    private void input() {
        float speed = 100f;
        float delta = Gdx.graphics.getDeltaTime();

        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX = speed * delta;
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveX = -speed * delta;
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveY = speed * delta;
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveY = -speed * delta;
        }

        playerBounds.setPosition(playerSprite.getX() + moveX, playerSprite.getY() + moveY);

        boolean collision = false;

        MapObjects objects = map.getLayers().get("Collision").getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                if (object.getProperties().containsKey("collidable") && (boolean) object.getProperties().get("collidable")) {
                    if (playerBounds.overlaps(rect)) {
                        collision = true;
                        break;
                    }
                }
            }
        }

        if (!collision) {
            playerSprite.translate(moveX, moveY);
        }
    }



    @Override
    public void resize(int width, int height) {
       camera.setToOrtho(false, 30, 20);
       camera.update();
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
