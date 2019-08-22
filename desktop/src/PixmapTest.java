import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @Author: zx
 * @Date: 2019/8/8 11:06
 */
public class PixmapTest extends ApplicationAdapter {

    Pixmap pixmap;
    Texture texture;
    SpriteBatch batch;
    TextureRegion region;

    public void create () {
        // Create an empty dynamic pixmap
        pixmap = new Pixmap(800, 480, Pixmap.Format.RGBA8888); // Pixmap.Format.RGBA8888);

        // Create a texture to contain the pixmap
        texture = new Texture(1024, 1024, Pixmap.Format.RGBA8888); // Pixmap.Format.RGBA8888);
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
        texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);

        pixmap.setColor(1.0f, 0.0f, 0.0f, 1.0f); // Red
        pixmap.drawLine(0, 0, 100, 100);

        pixmap.setColor(0.0f, 0.0f, 1.0f, 1.0f); // Blue
        pixmap.drawLine(100, 100, 200, 0);

        pixmap.setColor(0.0f, 1.0f, 0.0f, 1.0f); // Green
        pixmap.drawLine(100, 0, 100, 100);

        pixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f); // White
        pixmap.drawCircle(400, 300, 100);

        // Blit the composited overlay to a texture
        texture.draw(pixmap, 0, 0);
        region = new TextureRegion(texture, 0, 0, 800, 480);
        batch = new SpriteBatch();

        Pixmap pixmap = new Pixmap(512, 1024, Pixmap.Format.RGBA8888);
        for (int y = 0; y < pixmap.getHeight(); y++) { // 1024
            for (int x = 0; x < pixmap.getWidth(); x++) { // 512
                pixmap.getPixel(x, y);
            }
        }
        pixmap.dispose();
    }

    public void render () {
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(region, 0, 0);
        batch.end();
    }

    public static void main (String[] args) throws Exception {
        new LwjglApplication(new PixmapTest());
    }
}
