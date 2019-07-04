package ru.geekbrains.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.Exploision;

public class ExplosionPool extends SpritesPool<Exploision> {

   private TextureRegion explosionRegion;
   private Sound sound;

    public ExplosionPool(TextureAtlas atlas, Sound sound) {
        explosionRegion = atlas.findRegion("explosion");
        this.sound = sound;
    }

    @Override
    protected Exploision newObject() {
        return new Exploision(explosionRegion, 9,9,74, sound);
    }
}
