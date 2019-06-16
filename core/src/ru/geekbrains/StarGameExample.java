package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StarGameExample extends ApplicationAdapter {
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
		// +
		Vector2 v1 = new Vector2(2,2);
		Vector2 v2 = new Vector2(0,-1);
		v1.add(v2);
		System.out.println("v1.x & v1.y = " + v1.x+" & "+v1.y);
		// -
		v1.set(8,5);
		v2.set(3,4);
		v1.sub(v2);
		System.out.println("v1.x & v1.y = " + v1.x+" & "+v1.y);
		System.out.println("v1.len() = " + v1.len()); // length
		// scalirovanie
		v1.scl(0.5f);
		System.out.println("v1.len() = " + v1.len());
		//normirovanie
		v1.nor();
		System.out.println("v1.len() = " + v1.len());

		// find uncle
		v1.set(2,5);
		v2.set(3,3);
		System.out.println("v1.len() = " + v1.len());
		System.out.println("v2.len() = " + v2.len());
		v1.nor();
		v2.nor();
		System.out.println(Math.acos(v1.dot(v2)));


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
