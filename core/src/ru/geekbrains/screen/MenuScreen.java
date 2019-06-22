package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture img,space,rocket;
    private TextureRegion face;
    private Background background;
    private Logo logo;

    private Vector2 touch;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 v2;
    private boolean touchDown;


    @Override
    public void show() {
        super.show();
        space = new Texture("space.jpg");
        img = new Texture("badlogic.jpg");
        face = new TextureRegion(img,30,50,200,140);
        pos = new Vector2();
        background = new Background(new TextureRegion(space));
        logo = new Logo(new TextureRegion(face));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);//в методе происходит все изменения/логика объектов для последующей отрисовки
        draw();// отрисовка
    }

    public void update(float delta){
        logo.update(delta);
    }

    public void draw(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        //batch.draw(face, moveTouch().x, moveTouch().y,0.2f,0.2f);
        logo.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }

    @Override
    public void dispose() {
        img.dispose();
        space.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        logo.touchDown(touch, pointer);
        return false;
    }


}
