/*
 * Copyright 2025 River Vora
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Represents the game screen where the main gameplay occurs.
 */

public class GameScreen implements Screen {
    final affirmation_adventures game;

    public Texture playerTexture;
    public Sprite playerSprite;
    public OrthogonalTiledMapRenderer mapRenderer;
    public OrthographicCamera camera = new OrthographicCamera();
    public TiledMap tiledMap;

    public Rectangle playerBounds;
    public Texture WindowTexture;
    public Sprite Windowsprite;

    /**
     * Constructs a new GameScreen.
     *
     * @param game the main game instance
     */

    public GameScreen(final affirmation_adventures game) {
        this.game = game;

        try {
            playerTexture = new Texture("player.png");
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Failed to load player texture", e);
        }

        if (playerTexture != null) {
            playerSprite = new Sprite(playerTexture);
            playerSprite.setSize(1, 1);
            playerSprite.setPosition(33.613308f, 49.745155f);
            playerBounds = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
        }

        try {
            tiledMap = new TmxMapLoader().load("Dungeon.tmx");
            if (tiledMap == null) {
                Gdx.app.error("GameScreen", "Failed to load map: Dungeon.tmx");
            } else {
                Gdx.app.log("GameScreen", "Map loaded successfully");
                mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 16f);
                if (mapRenderer == null) {
                    Gdx.app.error("GameScreen", "Failed to initialize map renderer");
                } else {
                    Gdx.app.log("GameScreen", "MapRenderer created successfully");
                }
            }
        } catch (Exception e) {
            Gdx.app.error("GameScreen", "Exception loading map", e);
        }

        WindowTexture = new Texture("popup_imageEdited.png");
        Windowsprite = new Sprite(WindowTexture);
        Windowsprite.setSize(10, 10);

        if (tiledMap != null) {
            int mapWidth = tiledMap.getProperties().get("width", Integer.class);
            int mapHeight = tiledMap.getProperties().get("height", Integer.class);
            int tilePixelWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
            int tilePixelHeight = tiledMap.getProperties().get("tileheight", Integer.class);
            float centerX = (mapWidth * tilePixelWidth) / 2f;
            float centerY = (mapHeight * tilePixelHeight) / 2f;

            camera.position.set(centerX, centerY, 0);
            camera.setToOrtho(false, 70, 70);
            camera.update();
        }
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();

    }

    /**
     * Handles the drawing of the game screen.
     */

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        if (camera != null && playerSprite != null) {
            camera.position.set(playerSprite.getX(), playerSprite.getY(), 0);
            camera.update();
        }

        if (mapRenderer != null) {
            mapRenderer.setView(camera);
            mapRenderer.render();
        } else {
            Gdx.app.error("GameScreen", "MapRenderer is null, cannot render map");
        }

        if (game.batch != null && camera != null) {
            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            if (playerSprite != null) {
                playerSprite.draw(game.batch);
            }
            Windowsprite.draw(game.batch);
            game.batch.end();
        }
    }

    /**
     * Handles the game logic.
     */

    private void logic() {
        if (playerSprite != null)
            playerBounds.setPosition(playerSprite.getX(), playerSprite.getY());
    }

    /**
     * Handles the user input.
     */

    private void input() {
        float speed = 20f;
        float delta = Gdx.graphics.getDeltaTime();

        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveX = speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveX = -speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveY = speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveY = -speed * delta;
        }

        if (playerSprite != null) {
            float oldX = playerSprite.getX();
            float oldY = playerSprite.getY();

            playerSprite.translate(moveX, moveY);
            playerBounds.setPosition(playerSprite.getX(), playerSprite.getY());

            Gdx.app.log("Player Position", "Player moved to: (" + playerSprite.getX() + ", " + playerSprite.getY() + ")");

            // Check for collisions
            boolean collision = false;
            if (tiledMap != null) {
                for (MapLayer layer : tiledMap.getLayers()) {
                    MapObjects objects = layer.getObjects();
                    for (MapObject object : objects) {
                        if (object instanceof RectangleMapObject) {
                            Rectangle rect = ((RectangleMapObject) object).getRectangle();
                            if (object.getProperties().containsKey("collidable") && (boolean) object.getProperties().get("collidable")) {
                                if (playerBounds.overlaps(rect)) {
                                    collision = true;
                                    Gdx.app.log("Collision", "Collision detected with object: " + object);
                                    break;
                                }
                            }
                        }
                    }
                    if (collision) break;

                    // Check for collisions with regular tiles
                    if (layer instanceof TiledMapTileLayer) {
                        TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;

                        float scale = 1f / 16f; // Modify the scale if using a different factor
                        int tileWidth = tileLayer.getTileWidth();
                        int tileHeight = tileLayer.getTileHeight();

                        float playerCenterX = playerBounds.x + playerBounds.width / 2f;
                        float playerCenterY = playerBounds.y + playerBounds.height / 2f;

                        // Convert to tile coordinate space
                        int col = (int)(playerCenterX / (tileWidth * scale));
                        int row = (int)(playerCenterY / (tileHeight * scale));

                        Gdx.app.log("Tile Check", "Checking tile at: (" + col + ", " + row + ")");

                        TiledMapTileLayer.Cell cell = tileLayer.getCell(col, row);
                        if (cell != null) {
                            TiledMapTile tile = cell.getTile();
                            if (tile != null) {
                                Gdx.app.log("Tile Property", "Tile at (" + col + ", " + row + ") properties: " + tile.getProperties());
                            }
                            if (tile.getProperties().containsKey("collidable") &&
                                Boolean.TRUE.equals(tile.getProperties().get("collidable"))) {
                                collision = true;
                                Gdx.app.log("Collision", "Collision detected with tile at (" + col + ", " + row + ")");
                                break;
                            }
                            if (tile.getProperties().containsKey("treasure") && tile.getProperties().containsKey("collidable")) {
                                Gdx.app.log("Treasure", "Treasure found at (" + col + ", " + row + ")");
                                tile.getProperties().put("collidable", false);
                                break;
                            }
                        } else {
                            Gdx.app.log("Tile", "Tile in cell (" + col + ", " + row + ") is null");
                        }
                    }
                }
            }

            // Revert position if collision detected
            if (collision) {
                playerSprite.setPosition(oldX, oldY);
                playerBounds.setPosition(oldX, oldY);
            }
        }
    }



    @Override
    public void resize(int width, int height) {
        if (camera != null) {
            camera.setToOrtho(false, 100, 100);
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
        if (tiledMap != null) {
            tiledMap.dispose();
        }
        /*
        if (assetManager != null) {
            assetManager.dispose();
        }

         */
    }
}
