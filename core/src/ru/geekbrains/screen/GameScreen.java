package ru.geekbrains.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Spaceship;
import ru.geekbrains.sprite.Star;

public class GameScreen extends BaseScreen {

    private Background background;
    private Texture space;
    private TextureAtlas atlas;
    private TextureAtlas mainAtlas;

    private final static int COUNT_STARS = 100;

    private BulletPool bulletPool;
    private Spaceship spaceship;

    private Star[] stars;


    @Override
    public void show() {
        super.show();
        space = new Texture("textures/space.jpg");
        background = new Background(new TextureRegion(space));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        mainAtlas = new TextureAtlas("textures/mainAtlas.tpack");
        bulletPool = new BulletPool();
        spaceship = new Spaceship(mainAtlas,bulletPool);


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
    }

    public void freeAllDestroyedSprites(){
        bulletPool.freeAllDestroyedActiveSprites();
    }

    public void draw(){
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) {
            stars[i].draw(batch);
        }
        spaceship.draw(batch);
        bulletPool.drawActiveSprites(batch);
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
