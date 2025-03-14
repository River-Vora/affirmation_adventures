package com.badlogic.affirmation_adventures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;
/**
 * Represents the game screen where the main gameplay occurs.
 */

public class GameScreen implements Screen {
    final affirmation_adventures game;

    public boolean showWindowSprite = false;
    public Texture playerTexture;
    public Sprite playerSprite;
    public OrthogonalTiledMapRenderer mapRenderer;
    public OrthographicCamera camera = new OrthographicCamera();
    public TiledMap tiledMap;
    Texture WindowTexture;
    Sprite Windowsprite;
    public Rectangle playerBounds;
    Music music;
    float affirmationCounter;
    float windowTimer;
    final float WINDOW_DISPLAY_TIME = 3f;

    // Variables for random affirmations.
    private BitmapFont font;
    private String[] affirmations;
    private String currentAffirmation;
    private Random random;
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

        music = Gdx.audio.newMusic(Gdx.files.internal("BattleTheme.mp3"));
        music.setLooping(true);

        if (playerTexture != null) {
            playerSprite = new Sprite(playerTexture);
            playerSprite.setSize(1, 1);
            playerSprite.setPosition(33.613308f, 49.745155f);
            playerBounds = new Rectangle(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
        }

        // Initialization of the fonts.
        font = new BitmapFont();
        font.getData().setScale(0.1f);
        // Set linear filtering for the font texture
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Strings for the random affirmations.
        affirmations = new String[] {
            "Y o u  a r e  h a r d w o rk i n g .",
            "Y o u  a r e  e n o u g h .",
            "Y o u  a r e  a w e s o m e.",
            "Y o u  a r e  w o r t h y .",
            "Y o u  a r e  a m a z i n g .",
            "Y o u  a r e  l o v e d .",
            "Y o u  a r e  b o l d .",
            "Y o u  a r e  b r a v e .",
            "Y o u  a r e  s t r o n g .",
            "Y o u  a r e  d e t e r m i n e d .",
            "Y o u  a r e  r e s i l i e n t .",
            "Y o u  a r e  i n t e l l i g e n t .",
            "Y o u  a r e  s u c c e s s f u l ."
        };

        random = new Random();

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

        WindowTexture = new Texture("popup_imageOriginal.png");
        Windowsprite = new Sprite(WindowTexture);
        Windowsprite.setSize(15, 15);

        font.getData().setScale(0.1f);

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
        music.play();
    }
    @Override
    public void render(float delta) {
        input();
        draw();
        logic(delta);
    }

    private void logic(float delta) {
        // Affirmation window counter.
        affirmationCounter += delta;
        if (!showWindowSprite && affirmationCounter >= 10f) {
            showWindowSprite = true;
            currentAffirmation = affirmations[random.nextInt(affirmations.length)];
            Windowsprite.setPosition(playerSprite.getX(), playerSprite.getY());
            windowTimer = WINDOW_DISPLAY_TIME;
        }

        if (showWindowSprite) {
            windowTimer -= delta;
            if (windowTimer <= 0) {
                showWindowSprite = false;
                affirmationCounter = 0;
            }
        }
    }

    /**
     * Handles the drawing of the game screen.
     */

    public void draw() {
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
            // Draws the window sprite if it is visible.
            if (showWindowSprite) {
                Windowsprite.draw(game.batch);
                font.setColor(Color.BLACK);
                font.draw(game.batch, currentAffirmation, playerSprite.getX() + 1, playerSprite.getY() + 8);
            }

            game.batch.end();
        }
    }

    /**
     * Handles the user input.
     */

    public void input() {

        if (!showWindowSprite) {
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
                            int col = (int) (playerCenterX / (tileWidth * scale));
                            int row = (int) (playerCenterY / (tileHeight * scale));

                            Gdx.app.log("Tile Check", "Checking tile at: (" + col + ", " + row + ")");

                            TiledMapTileLayer.Cell cell = tileLayer.getCell(col, row);
                            if (cell != null) {
                                TiledMapTile tile = cell.getTile();
                                if (tile != null) {
                                    Gdx.app.log("Tile Property", "Tile at (" + col + ", " + row + ") properties: " + tile.getProperties());
                                    if (tile.getProperties().containsKey("collidable") &&
                                        Boolean.TRUE.equals(tile.getProperties().get("collidable"))) {
                                        collision = true;
                                        Gdx.app.log("Collision", "Collision detected with tile at (" + col + ", " + row + ")");
                                        break;
                                    }
                                }
                                if (tile != null) {
                                    int tileId = tile.getId();
                                    Gdx.app.log("Tile ID", "Tile at (" + col + ", " + row + ") has ID: " + tileId);

                                    // Check for specific tile ID
                                    if (tileId == 34) { // Replace 7798 with the desired tile ID
                                        collision = true;
                                        Gdx.app.log("Treasure", "Treasure with tile ID: " + tileId);
                                        break;
                                    }
                                    if (tileId == 35) {
                                        collision = true;
                                        Gdx.app.log("Treasure", "Treasure with tile ID: " + tileId);
                                        break;
                                    }
                                } else {
                                    Gdx.app.log("Tile", "Tile in cell (" + col + ", " + row + ") is null");
                                }
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
        // Checking if the window was clicked.
        if (showWindowSprite && Gdx.input.justTouched()) {
            // Convert the touch coordinates to world coordinates.
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (Windowsprite.getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
                showWindowSprite = false;
                affirmationCounter = 0;
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
        // Disposing of resources when the screen is no longer needed.
        if (playerTexture != null) {
            playerTexture.dispose();
        }
        if (mapRenderer != null) {
            mapRenderer.dispose();
        }
        if (tiledMap != null) {
            tiledMap.dispose();
        }
        if (WindowTexture != null) {
            WindowTexture.dispose();
        }
    }
}
