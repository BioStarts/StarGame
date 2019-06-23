package ru.geekbrains.screen;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;


import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ButtonExit;
import ru.geekbrains.sprite.ButtonPlay;


public class MenuScreen extends BaseScreen {

    private Game game;

    private Texture space;
    private Background background;
    private TextureAtlas atlas;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;



    public MenuScreen (Game game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        space = new Texture("textures/space.jpg");
        background = new Background(new TextureRegion(space));
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);//в методе происходит все изменения/логика объектов для последующей отрисовки
        draw();// отрисовка
    }

    public void update(float delta){
    }

    public void draw(){
        batch.begin();
        background.draw(batch);
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public void dispose() {
        space.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonPlay.touchDown(touch,pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch, pointer);
        buttonPlay.touchUp(touch,pointer);
        return false;
    }
}
