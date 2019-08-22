import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * @Author: zx
 * @Date: 2019/7/31 9:38
 */
public class TextureAtlasTest extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite badlogic, badlogicSmall, star;
    TextureAtlas atlas;
    TextureAtlas jumpAtlas;
    Animation<TextureRegion> jumpAnimation;
    BitmapFont font;
    float time = 0;
    ShapeRenderer renderer;

    public void create () {
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();

        atlas = new TextureAtlas(Gdx.files.internal("data/pack"));
        jumpAtlas = new TextureAtlas(Gdx.files.internal("data/jump.txt"));

        jumpAnimation = new Animation<TextureRegion>(0.25f, jumpAtlas.findRegions("ALIEN_JUMP_"));

        badlogic = atlas.createSprite("badlogicslice");
        badlogic.setPosition(50, 50);

        // badlogicSmall = atlas.createSprite("badlogicsmall");
        badlogicSmall = atlas.createSprite("badlogicsmall-rotated");
        badlogicSmall.setPosition(10, 10);

        TextureAtlas.AtlasRegion region = atlas.findRegion("badlogicsmall");
        System.out.println("badlogicSmall original size: " + region.originalWidth + ", " + region.originalHeight);
        System.out.println("badlogicSmall packed size: " + region.packedWidth + ", " + region.packedHeight);

        star = atlas.createSprite("particle-star");
        star.setPosition(10, 70);

        font = new BitmapFont(Gdx.files.internal("data/font.fnt"), atlas.findRegion("font"), false);

        Gdx.gl.glClearColor(0, 1, 0, 1);

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyUp (int keycode) {
                if (keycode == Input.Keys.UP) {
                    badlogicSmall.flip(false, true);
                } else if (keycode == Input.Keys.RIGHT) {
                    badlogicSmall.flip(true, false);
                } else if (keycode == Input.Keys.LEFT) {
                    badlogicSmall.setSize(512, 512);
                } else if (keycode == Input.Keys.DOWN) {
                    badlogicSmall.rotate90(true);
                }
                return super.keyUp(keycode);
            }
        });
    }

    public void render () {
        time += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.rect(10, 10, 256, 256);
        renderer.end();

        batch.begin();
        // badlogic.draw(batch);
        // star.draw(batch);
        // font.draw(batch, "This font was packed!", 26, 65);
        badlogicSmall.draw(batch);
        // batch.draw(jumpAnimation.getKeyFrame(time, true), 100, 100);
        batch.end();
    }

    public static void main (String[] args) throws Exception {
        new LwjglApplication(new TextureAtlasTest());
    }
}
