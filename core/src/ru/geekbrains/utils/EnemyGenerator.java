package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import java.util.logging.Level;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.sprite.Enemy;

public class EnemyGenerator {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.015f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MIDDLE_HEIGHT = 0.1f;
    private static final float ENEMY_MIDDLE_BULLET_HEIGHT = 0.022f;
    private static final float ENEMY_MIDDLE_BULLET_VY = -0.25f;
    private static final int ENEMY_MIDDLE_DAMAGE = 5;
    private static final float ENEMY_MIDDLE_RELOAD_INTERVAL = 4f;
    private static int ENEMY_MIDDLE_HP = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.033f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private static int ENEMY_BIG_HP = 10;

    private TextureRegion[] enemySmallRegion;
    private TextureRegion[] enemyMiddleRegion;
    private TextureRegion[] enemyBigRegion;

    private Vector2 enemySmallV;
    private Vector2 enemyMiddleV;
    private Vector2 enemyBigV;

    private TextureRegion bulletRegion;
    private EnemyPool enemyPool;

    private float generateInterval = 4f;
    private float generateTimer;

    private int level;

    private Rect worldBounds;

    public EnemyGenerator(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        this.enemySmallV = new Vector2(0f, -0.2f);
        this.enemyMiddleV = new Vector2(0f, -0.03f);
        this.enemyBigV = new Vector2(0f, -0.005f);
        TextureRegion region0 = atlas.findRegion("enemy0");
        enemySmallRegion = Regions.split(region0,1,2,2);
        TextureRegion region1 = atlas.findRegion("enemy1");
        enemyMiddleRegion = Regions.split(region1,1,2,2);
        TextureRegion region2 = atlas.findRegion("enemy2");
        enemyBigRegion = Regions.split(region2,1,2,2);
        bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generate(float delta, int frags){
        level = frags / 10 + 1;
        generateTimer += delta;
        if (generateTimer >= generateInterval){
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float)Math.random();
            if (type < 0.6f) {
                enemy.set(
                        enemySmallRegion,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_DAMAGE * level, // в зависимости уровня увеличиваем дамагу
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HP,
                        ENEMY_SMALL_HEIGHT
                );
            } else if (type < 0.85f){
                enemy.set(
                        enemyMiddleRegion,
                        enemyMiddleV,
                        bulletRegion,
                        ENEMY_MIDDLE_BULLET_HEIGHT,
                        ENEMY_MIDDLE_BULLET_VY,
                        ENEMY_MIDDLE_DAMAGE * level,
                        ENEMY_MIDDLE_RELOAD_INTERVAL,
                        ENEMY_MIDDLE_HP,
                        ENEMY_MIDDLE_HEIGHT
                );
            } else {
                enemy.set(
                        enemyBigRegion,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_DAMAGE * level,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HP,
                        ENEMY_BIG_HEIGHT
                );
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            //enemy.setBottom(worldBounds.getTop() - 2*enemy.getHalfHeight()); // быстрый способ
            enemy.setBottom(worldBounds.getTop());
            /*if ((enemy.getHeight() == 0.2f) & ((enemy.pos.y + enemy.getHalfHeight()) >= worldBounds.getTop())){
                System.out.println("БОЛЬШОЙ корабль на горизонте");
                System.out.println("enemy.pos.y = " + enemy.pos.y + " worldBounds.getTop() = " + worldBounds.getTop() + " enemy.getHalfHeight() = " + enemy.getHalfHeight());
                this.enemyBigV.set(0, -0.9f);
            }
            this.enemyBigV.set(0, -0.005f);*/
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
