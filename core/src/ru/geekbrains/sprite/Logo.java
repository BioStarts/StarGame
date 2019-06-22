package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;

public class Logo extends Sprite {

    private static final float V_LEN = 0.01f;
    private Vector2 v;
    private Vector2 buf;
    private Vector2 touch;

    public Logo(TextureRegion region) {
        super(region);
        setHeightProportion(0.4f);
        v = new Vector2();
        buf = new Vector2();
        touch = new Vector2();
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
        buf.set(touch);
        if (buf.sub(pos).len() >= V_LEN){
            pos.add(v);
        } else {
            pos.set(touch);
        }
        pos.mulAdd(v, delta);
    }
}
