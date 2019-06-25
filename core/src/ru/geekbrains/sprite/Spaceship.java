package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Spaceship extends Sprite {

    private static final float V_LEN = 0.01f;
    private Vector2 v;
    private Vector2 buf;
    private Vector2 touch;
    private Rect worldBounds;

    public Spaceship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        setHeightProportion(0.2f);
        v = new Vector2();
        buf = new Vector2();
        touch = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch = touch;
        v = touch.cpy().sub(pos);//нашли вектор для перемещения от исходной точки до клика по экрану
        v.setLength(V_LEN);// уменьшили его
        return false;
    }



    @Override
    public void update(float delta) {

        checkWindowBorders(delta);

        buf.set(touch);
        if (buf.sub(pos).len() >= V_LEN){
            pos.add(v);
        } else {
            pos.set(touch);
        }
        pos.mulAdd(v, delta);
    }

    public void checkWindowBorders (float delta){
        if (worldBounds.getBottom() > getBottom()){//проверка чтобы ограничить перемещение по нижней части экрана
            v.setZero();
            setBottom(worldBounds.getBottom());
            return;
        }
        if (worldBounds.getTop() < getTop()){//проверка чтобы ограничить перемещение по верхней части экрана
            v.setZero();
            setTop(worldBounds.getTop());
            return;
        }
        if (worldBounds.getRight() < getRight()){//проверка чтобы ограничить перемещение по правой части экрана
            v.setZero();
            setRight(worldBounds.getRight()-0.0001f);
            return;
        }
        if (worldBounds.getLeft() > getLeft()){//проверка чтобы ограничить перемещение по левой части экрана
            v.setZero();
            setLeft(worldBounds.getLeft()+0.0001f);
            return;
        }
    }
}
