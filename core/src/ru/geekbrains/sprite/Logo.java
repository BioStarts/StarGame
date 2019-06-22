package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Logo extends Sprite {

    private static final float V_LEN = 0.01f;
    private Vector2 v;
    private Vector2 buf;
    private Vector2 touch;
    private Rect worldBounds;

    public Logo(TextureRegion region) {
        super(region);
        setHeightProportion(0.4f);
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

        if (worldBounds.getBottom() > getBottom()){//проверка чтобы ограничить перемещение по нижней части экрана
            v.setZero();
            setBottom(worldBounds.getBottom());
            return;
        }

        buf.set(touch);
        if (buf.sub(pos).len() >= V_LEN){
            pos.add(v);
        } else {
            pos.set(touch);
        }
        pos.mulAdd(v, delta);
    }
}
