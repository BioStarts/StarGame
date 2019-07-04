package ru.geekbrains.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.sprite.Spaceship;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyGenerator;

public class GameScreen extends BaseScreen {

    private Background background;
    private Texture space;
    private TextureAtlas atlas;
    private TextureAtlas mainAtlas;

    private final static int COUNT_STARS = 100;

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
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        soundLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        music.setLooping(true);
        music.play();

        space = new Texture("textures/space.jpg");
        background = new Background(new TextureRegion(space));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        mainAtlas = new TextureAtlas("textures/mainAtlas.tpack");
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
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()){
                continue;
            }
            float minDist = enemy.getHalfWidth() + spaceship.getHalfWidth();
            if (spaceship.pos.dst(enemy.pos) < minDist){
                enemy.destroy();
            }
        }
        /*for (int i = 0; i < enemyList.size(); i++) {
            if (!enemyPool.getActiveObjects().get(i).isOutside(spaceship)){
                enemyPool.getActiveObjects().get(i).destroy();
                soundExplosion.play();
            }
        }*/
    }

    private void update(float delta){
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        spaceship.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyGenerator.generate(delta);
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
        spaceship.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) {
            stars[i].resize(worldBounds);
        }
        spaceship.resize(worldBounds);
    }

    @Override
    public void dispose() {
        space.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        soundLaser.dispose();
        soundBullet.dispose();
        soundExplosion.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        spaceship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        spaceship.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        spaceship.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        spaceship.touchUp(touch, pointer);
        return false;
    }
}
