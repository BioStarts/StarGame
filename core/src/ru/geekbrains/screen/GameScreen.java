package ru.geekbrains.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.ButtonNewGame;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.sprite.GameOver;
import ru.geekbrains.sprite.Spaceship;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyGenerator;
import ru.geekbrains.utils.Font;

public class GameScreen extends BaseScreen {

    private static final String FRAGS = "Frags:";
    private static final String HP = "HP:";
    private static final String Level = "Level:";
    private final static int COUNT_STARS = 100;

    private enum State {PLAYING, PAUSE, GAME_OVER};
    private State state;
    private State oldState;

    private Background background;
    private Texture space;
    private TextureAtlas atlas;
    private TextureAtlas mainAtlas;

    private Font font;
    private int frags = 0;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    private GameOver gameOver;
    private ButtonNewGame buttonNewGame;


    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemyGenerator enemyGenerator;

    private Spaceship spaceship;

    private Star[] stars;

    private Music music;
    private Sound soundLaser;//
    private Sound soundBullet;//
    private Sound soundExplosion;//

    //Sound soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));//
    //Sound soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));//


    @Override
    public void show() {
        super.show();

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.025f);

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        soundLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        music.setLooping(true);
        music.play();

        state = State.PLAYING;
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();

        space = new Texture("textures/space.jpg");
        background = new Background(new TextureRegion(space));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        mainAtlas = new TextureAtlas("textures/mainAtlas.tpack");

        gameOver = new GameOver(mainAtlas);
        buttonNewGame = new ButtonNewGame(mainAtlas,this);

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(mainAtlas, soundExplosion);
        enemyPool = new EnemyPool(bulletPool, explosionPool, soundBullet, worldBounds);
        enemyGenerator = new EnemyGenerator(mainAtlas, enemyPool, worldBounds);

        spaceship = new Spaceship(mainAtlas, bulletPool, explosionPool,  soundLaser);

        stars = new Star[COUNT_STARS];//

        for (int i = 0; i < stars.length ; i++) {
            stars[i] = new Star(atlas);
        }

    }



    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);//в методе происходит все изменения/логика объектов для последующей отрисовки
        checkCollisions();
        freeAllDestroyedActiveSprites();
        draw();// отрисовка
    }

    private void checkCollisions() {
        if (state != State.PLAYING){
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()){
                continue;
            }
            float minDist = enemy.getHalfWidth() + spaceship.getHalfWidth();
            if (spaceship.pos.dst(enemy.pos) < minDist){
                enemy.destroy();
                spaceship.damage(spaceship.getHp());
                state = State.GAME_OVER;
            }
        }
         List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() == spaceship) {
                for (Enemy enemy : enemyList) {
                    if (enemy.isDestroyed()) {
                        continue;
                    }
                    if (enemy.isBulletCollision(bullet)) {
                        enemy.damage(bullet.getDamage());
                        if (enemy.isDestroyed()){
                            frags++;
                        }
                        bullet.destroy();
                    }
                }
            } else { // начинается проверка коллизий уже для нашего корабля и вражеских пуль
                if (spaceship.isBulletCollision(bullet)){
                    spaceship.damage(bullet.getDamage());
                    if (spaceship.isDestroyed()){
                        state = State.GAME_OVER;
                    }
                    bullet.destroy();
                }
            }
        }
    }

    private void update(float delta){
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING){
            spaceship.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyGenerator.generate(delta, frags);
        } else {
            gameOver.update(delta);
        }
    }

    private void freeAllDestroyedActiveSprites(){
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        if (state == State.PLAYING){
            spaceship.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER){
            gameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbHp.append(HP).append(spaceship.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbLevel.append(Level).append(enemyGenerator.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        spaceship.resize(worldBounds);
        gameOver.resize(worldBounds);
    }

    @Override
    public void dispose() {
        space.dispose();
        atlas.dispose();
        mainAtlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        soundLaser.dispose();
        soundBullet.dispose();
        soundExplosion.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING){
            spaceship.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            spaceship.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            spaceship.touchDown(touch, pointer);
        } else if (state == State.GAME_OVER){
            buttonNewGame.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            spaceship.touchUp(touch, pointer);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer);
        }
        return false;
    }

    @Override
    public void pause() {
        super.pause();
        oldState = state;
        state = State.PAUSE;
        music.pause();
    }

    @Override
    public void resume() {
        super.resume();
        state = oldState;
        music.play();
    }

    public void startNewGame() {
        spaceship.setToNewGame(worldBounds);

        state = State.PLAYING;
        frags = 0;
        enemyGenerator.setLevel(1);


        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
    }
}
