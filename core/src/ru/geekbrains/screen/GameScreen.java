package ru.geekbrains.screen;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Star;

public class GameScreen extends BaseScreen {

    private Background background;
    private Texture space;
    private TextureAtlas atlas;

    private Star star;


    @Override
    public void show() {
        super.show();
        space = new Texture("textures/space.jpg");
        background = new Background(new TextureRegion(space));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        star = new Star(atlas);
    }



    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);//в методе происходит все изменения/логика объектов для последующей отрисовки
        draw();// отрисовка
    }

    public void update(float delta){
        star.update(delta);
    }

    public void draw(){
        batch.begin();
        background.draw(batch);
        //for(Star star : star.getStars()) {
            star.draw(batch);
        //}
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        star.resize(worldBounds);
    }

    @Override
    public void dispose() {
        space.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }
}
