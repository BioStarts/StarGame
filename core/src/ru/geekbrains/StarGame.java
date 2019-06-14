package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img,space,rocket;
	TextureRegion face;
	Stage stage;
	int imgpos = 0;
	int imgposdir = 1;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		space = new Texture("space.jpg");
		rocket = new Texture("rocket.png");
		face = new TextureRegion(img,30,50,200,140);
		stage = new Stage();
	}

	@Override
	public void render () {
		imgpos+=5*imgposdir;
		if (imgpos<0) imgposdir=-imgposdir;
		if (imgpos>540) imgposdir=-imgposdir;
		//Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.getBatch().begin();
		stage.getBatch().draw(space, 0, 0);
		stage.getBatch().end();
		stage.draw();

		batch.begin();
		//batch.setColor(0,0.5f,0,1);
		//batch.draw(space, 0, 0);
		batch.draw(rocket, imgpos, 100,100,100);
		//batch.setColor(0,0.5f,0,1);
		batch.draw(face, 240, 350);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
