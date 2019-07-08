package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class Spaceship extends Ship {

    private static final  int INVALID_POINTER = -1;
    private static final  int HP = 1;

    private boolean pressedLeft;
    private boolean pressedRight;

    public int leftPointer = INVALID_POINTER;
    public int rightPointer = INVALID_POINTER;

    public Spaceship(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound) {
        super(atlas.findRegion("main_ship"),1,2,2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.v = new Vector2();
        this.v0 =  new Vector2(0.5f,0f);
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletV = new Vector2(0,0.5f);
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.shootSound = shootSound;
        this.reloadInterval = 0.25f;
        this.hp = HP;
        setHeightProportion(0.2f);
    }

    public void setToNewGame(Rect worldBounds){
        flushDestroy();
        hp = HP;
        this.pos.x = worldBounds.pos.x;
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
                frame = 1;
                shoot();
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
                frame = 0;
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
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        checkWindowBorders(delta);
        super.update(delta);
    }

    private void checkWindowBorders (float delta){
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
        v.set(v0);
    }
    private void moveLeft(){
        v.set(v0).rotate(180);
    }
    private void stop(){
        v.setZero();
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() >  pos.y
                || bullet.getTop() < getBottom());
    }

    @Override
    public void destroy() {
        super.destroy();
        stop();
        pressedLeft = false;
        pressedRight = false;
        leftPointer = INVALID_POINTER;
        rightPointer = INVALID_POINTER;
    }
}
