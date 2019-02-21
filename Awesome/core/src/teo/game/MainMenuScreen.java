package teo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;


/**
 * Created by Vitaly on 13.10.2015.
 */
public class MainMenuScreen implements Screen {
    Music coolMusic;
    final Awesome game;
    OrthographicCamera camera;
    Texture cosmosImage;



    public MainMenuScreen(final Awesome gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        coolMusic = Gdx.audio.newMusic(Gdx.files.internal("cool.mp3"));
        cosmosImage = new Texture("cosmos2.png");
    }

    @Override
    public void show() {coolMusic.play();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 2, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
       // game.font.draw(game.batch, "Laboratorul #5 MIDPS!", 330, 400);
        //game.font.draw(game.batch, "The DROP", 360, 300);
       // game.font.draw(game.batch, "Tap to Start!", 355, 200);
        game.batch.draw(cosmosImage , 0 ,0);
        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
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

    }

}