package ru.geekbrains.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.Background;
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

    private EnemyGenerator enemyGenerator;

    private Spaceship spaceship;

    private Star[] stars;

    private Music music;
    private Sound soundLaser;//
    private Sound soundBullet;//

    //Sound soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));//
    //Sound soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));//


    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        soundLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));

        music.setLooping(true);
        music.play();

        space = new Texture("textures/space.jpg");
        background = new Background(new TextureRegion(space));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        mainAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        bulletPool = new BulletPool();
        enemyPool = new EnemyPool(bulletPool, soundBullet, worldBounds);
        enemyGenerator = new EnemyGenerator(mainAtlas, enemyPool, worldBounds);

        spaceship = new Spaceship(mainAtlas, bulletPool, soundLaser);

        stars = new Star[COUNT_STARS];//

        for (int i = 0; i < stars.length ; i++) {
            stars[i] = new Star(atlas);
        }

    }



    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);//в методе происходит все изменения/логика объектов для последующей отрисовки
        freeAllDestroyedSprites();
        draw();// отрисовка
    }

    public void update(float delta){
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(delta);
        }
        spaceship.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyGenerator.generate(delta);
    }

    public void freeAllDestroyedSprites(){
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    public void draw(){
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        spaceship.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
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
        music.dispose();
        soundLaser.dispose();
        soundBullet.dispose();
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
