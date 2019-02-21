package teo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

//import com.badlogic.gdx.audio.Sound;

public class GameScreen implements Screen {

	final Awesome game;
	OrthographicCamera camera;
	Texture poncicImage;
	Texture smileImage;
	Texture cosmosImage;
	//Sound dropSound;
	//Music coolMusic;
	Rectangle smile;
	Vector3 touchPos;
	Array<Rectangle> raindrops;
	long lastDropTime;
	int dropsGatchered;


	public GameScreen (final Awesome gam) {
		this.game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);

		touchPos = new Vector3();


		cosmosImage = new Texture("cosmos.png");
		poncicImage = new Texture("poncic.png");
		smileImage = new Texture("smile.png");

		//dropSound = Gdx.audio.newSound(Gdx.files.internal(".wav"));
		//coolMusic = Gdx.audio.newMusic(Gdx.files.internal("cool.mp3"));

		//coolMusic.setLooping(true);
		//coolMusic.play();

		smile = new Rectangle();
		smile.x = 480 / 2 - 64 / 2;
		smile.y = 20;
		smile.width = 64;
		smile.height = 64;

		raindrops = new Array<Rectangle>();
		spawnRainponcic();

	}

	private void spawnRainponcic(){
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 480-64);
		raindrop.y = 800;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		game.font.draw(game.batch, "Nr#: " + dropsGatchered, 0, 0);
		game.batch.draw(cosmosImage , 0 ,0);
		game.batch.draw(smileImage, smile.x, smile.y);
		for (Rectangle rainponcic: raindrops){
			game.batch.draw(poncicImage, rainponcic.x, rainponcic.y);
		}
		game.batch.end();

		if(Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			smile.x = (int) (touchPos.x -64 / 2);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) smile.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) smile.x += 200 * Gdx.graphics.getDeltaTime();

		if (smile.x < 0) smile.x = 0;
		if (smile.x > 480 - 64) smile.x = 480 - 64;

		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRainponcic();

		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()){
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) iter.remove();
			if (raindrop.overlaps(smile)){
				dropsGatchered++;
				//dropSound.play();
				iter.remove();
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		poncicImage.dispose();
		cosmosImage.dispose();
		smileImage.dispose();
		//dropSound.dispose();
		//coolMusic.dispose();
	}

	@Override
	public void show() {
		//coolMusic.play();
	}
}