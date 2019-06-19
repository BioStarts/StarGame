package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img,space,rocket;
    private TextureRegion face;

    private Vector2 touch;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 v2;


    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        face = new TextureRegion(img,30,50,200,140);
        touch = new Vector2();
        pos = new Vector2();
        batch.getProjectionMatrix().idt();//привели матрице к единичному виду
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(face, -0.5f, -0.5f,1f,1f);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX,Gdx.graphics.getHeight() - screenY);
        return false;
    }

}
