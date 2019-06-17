package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img,space,rocket;
    private TextureRegion face;
    private Stage stage;
    private int imgpos = 0;
    private int imgposdir = 1;

    private Vector2 touch;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 curPos;


    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        space = new Texture("space.jpg");
        rocket = new Texture("rocket.png");
        face = new TextureRegion(img,30,50,200,140);
        stage = new Stage();
        touch = new Vector2();
        pos = new Vector2();
        v = new Vector2();
        curPos = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);


       v = touch.cpy().sub(pos.cpy()); // получаем вектор скорости от позиции к касанию
       v.nor();// делаем из него вектор направления
       if (touch.cpy().sub(pos.cpy()).len2() >= 2){ // т.к. идеальной точности не достичь сравниваем расстояние между точками
           pos.add(v.scl(5f));// добавляем к позиции величину вектора направления и задаем скорость скалируя вектор
       }


        imgpos+=5*imgposdir;
        if (imgpos<0) imgposdir=-imgposdir;
        if (imgpos>540) imgposdir=-imgposdir;

        /*if (Gdx.graphics.getHeight() > pos.y + face.getRegionHeight()){
            pos.add(v);
        }*/
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        stage.getBatch().draw(space, 0, 0);
        stage.getBatch().end();
        stage.draw();

        batch.begin();
        batch.draw(rocket, imgpos, 100,100,100);
        batch.draw(face, pos.x, pos.y);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        stage.dispose();
        space.dispose();
        rocket.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX,Gdx.graphics.getHeight() - screenY);
        System.out.println("touchDown screenX = " + touch.x + " screenY = " + touch.y);
        return false;
    }

}
