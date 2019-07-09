package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public abstract class Ship extends Sprite {

    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected Rect worldBounds;
    protected Sound shootSound;

    protected Vector2 v0;
    protected Vector2 v;
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    protected TextureRegion bulletRegion;

    protected float reloadInterval;
    protected float reloadTimer;

    private float damageAnimateInterval = 0.1f;
    private float damageAnimateTimer = damageAnimateInterval;


    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public Ship() {
    }

    protected void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
        shootSound.play();
    }

    protected void boom() {
        Exploision exploision = explosionPool.obtain();
        exploision.set(getHeight(), pos);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            frame = 0;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public int getHp() {
        return hp;
    }

    public Vector2 getV() {
        return v;
    }

    public void damage(int damage){
        damageAnimateTimer = 0f;
        frame = 1;
        hp -= damage;
        if (hp <= 0){
            hp = 0;
            destroy();
        }
    }
}
