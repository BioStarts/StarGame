package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;

public class Exploision extends Sprite {

    private float animateInterval = 0.02f;
    private float animateTimer;

    private Sound sound;


    public Exploision(TextureRegion region, int rows, int cols, int frames, Sound sound) {
        super(region, rows, cols, frames);
        this.sound = sound;
    }

    public void set (float height, Vector2 pos) {
        sound.play();
        setHeightProportion( height);
        this.pos.set(pos);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= animateInterval){
            animateTimer = 0f;
            if (++frame == regions.length){
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }

}
