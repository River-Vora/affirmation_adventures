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

    public GameScreen(final affirmation_adventures game) {
        this.game = game;

        try {
            playerTexture = new Texture("player.png");
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Failed to load player texture", e);
        }

        if (playerTexture != null) {
            playerSprite = new Sprite(playerTexture);
            playerSprite.setSize(5, 5);
            playerBounds = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
        }

        try {
            map = new TmxMapLoader().load("Dungeon.tmx");
            if (map == null) {
                Gdx.app.error("GameScreen", "Map is null after loading");
            }
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Failed to load map", e);
        }

        if (map != null) {
            mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
        }


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
        if (camera != null && playerSprite != null) {
            camera.position.set(playerSprite.getX(), playerSprite.getY(), 0);
            camera.update();
        }

        if (mapRenderer != null) {
            mapRenderer.setView(camera);
            mapRenderer.render();
        }

        if (game.batch != null && camera != null) {
            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            if (playerSprite != null) {
                playerSprite.draw(game.batch);
            }
            game.batch.end();
        }
    }

    private void logic() {
        if (playerSprite != null)
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

        if (playerSprite != null)
            playerBounds.setPosition(playerSprite.getX() + moveX, playerSprite.getY() + moveY);

        boolean collision = false;

        MapObjects objects= null;
        if (map != null && map.getLayers().get("Torches and Treasures") != null) {
            objects = map.getLayers().get("Torches and Treasures").getObjects();
        }
        if (objects !=null) {
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
        }

        if (!collision && playerSprite != null) {
            playerSprite.translate(moveX, moveY);
        }
    }



    @Override
    public void resize(int width, int height) {
        if (camera != null) {
            camera.setToOrtho(false, 30, 20);
            camera.update();
        }

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
        if (playerTexture != null) {
            playerTexture.dispose();
        }
        if (mapRenderer != null) {
            mapRenderer.dispose();
        }
        if (map != null) {
            map.dispose();
        }
    }
}
