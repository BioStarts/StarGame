package ru.geekbrains.sprite;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class Star extends Sprite {

    private Vector2 v = new Vector2();

    public Rect getWorldBounds() {//
        return worldBounds;
    }

    private Rect worldBounds;


    float posX;
    float posY;



    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        //float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        //float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX,posY);
    }

    public Star(TextureAtlas atlas,float posX, float posY) {
        super(atlas.findRegion("star"));
        this.posX = posX;//
        this.posY = posY;//
        v.set(Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.01f));//задаем вектора x,y для звезды
        if (Math.abs(v.y/35) <= 0.005f){
            setHeightProportion(0.005f);//задаем минимальный размер звезды
        } else {
            setHeightProportion(v.y/35);//для создания визуального ощущения что чем дальше звезда тем меньше относительная скорость
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }

    /*private void MakeStars() {
        int starsToAdd = 10 - stars.size;
        //Star s = createStar();
        for (int i = 0; i < starsToAdd ; i++) {
            stars.add(createStar());
        }
    }

    public Star createStar() {
        Star star = new Star(atlas);
        return star;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(atlas.findRegion("star"),v.x,v.y);
    }*/
}
