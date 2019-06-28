package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class Spaceship extends Sprite {

    private static final  int INVALID_POINTER = -1;

    private final Vector2 v0 = new Vector2(0.5f,0f);
    private Vector2 v1 = new Vector2();

    private boolean pressedLeft;
    private boolean pressedRight;

    public int leftPointer = INVALID_POINTER;
    public int rightPointer = INVALID_POINTER;

    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureAtlas atlas;

    Sound soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));//
    Sound soundLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));//
    Sound soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));//


    public Spaceship(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"),1,2,2);
        this.bulletPool = bulletPool;
        this.atlas = atlas;
        setHeightProportion(0.2f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.05f);
    }


    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                //frame = 1;
                shoot();
                soundLaser.play();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight){
                    moveRight();
                } else {stop();}
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft){
                    moveLeft();
                } else {stop();}
                break;
            case Input.Keys.UP:
                //frame = 0;
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x){
            if (leftPointer != INVALID_POINTER){
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER){
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer){
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER){
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer){
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER){
                moveRight();
            } else {
                stop();
            }
        }
        return false;
    }



    @Override
    public void update(float delta) {
        checkWindowBorders(delta);
        pos.mulAdd(v1,delta);
    }

    public void checkWindowBorders (float delta){
        if (worldBounds.getRight() < getRight()){//проверка чтобы ограничить перемещение по правой части экрана
            setRight(worldBounds.getRight());
            stop();
        }
        if (worldBounds.getLeft() > getLeft()){//проверка чтобы ограничить перемещение по левой части экрана
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    private void moveRight(){
        v1.set(v0);
    }
    private void moveLeft(){
        v1.set(v0).rotate(180);
    }
    private void stop(){
        v1.setZero();
    }

    public void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, atlas.findRegion("bulletMainShip"), pos, new Vector2(0,0.5f),0.01f, worldBounds, 1);
    }

}
