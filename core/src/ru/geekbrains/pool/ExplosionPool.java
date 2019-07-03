package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.Exploision;

public class ExplosionPool extends SpritesPool<Exploision> {

   private TextureRegion explosionRegion;

    public ExplosionPool(TextureAtlas atlas) {
        explosionRegion = atlas.findRegion("explosion");
    }

    @Override
    protected Exploision newObject() {
        return new Exploision(explosionRegion, 9,9,74);
    }
}
