package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class Enemy extends Ship {

    private enum  State { DESCENT, FIGHT }

    private State state;

    private Vector2 descentV = new Vector2(0,-0.15f); //начальная скорость появления для большого корабля

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = shootSound;
        this.worldBounds = worldBounds;
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        /*if ((this.getHeight() == 0.2f) && ((this.pos.y + this.getHalfHeight()) >= worldBounds.getTop())){ // увеличиваем скорость большого корабля для адекватного появления на поле
            this.v.set(0, -0.03f);
        } else if ((this.getHeight() == 0.2f) && ((this.pos.y + this.getHalfHeight()) < worldBounds.getTop())) {
            this.v.set(0, -0.005f);
        }*/

        switch (state){
            case DESCENT:
                if (getTop() < worldBounds.getTop()) {
                v.set(v0);
                    state = State.FIGHT;
            }
                break;
            case FIGHT:
                if (getBottom() < worldBounds.getBottom()) {
                    destroy();
                }
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                break;
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        v.set(descentV);
        reloadTimer = reloadInterval;
        state = State.DESCENT;
    }
}