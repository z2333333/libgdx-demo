import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @Author: zx
 * @Date: 2019/7/31 10:06
 */
public class ViewportTest3 extends ApplicationAdapter {

    Array<Viewport> viewports;
    Viewport viewport;
    Array<String> names;
    String name;

    private PerspectiveCamera camera;
    public Environment environment;
    public DirectionalLight shadowLight;
    public ModelBuilder modelBuilder;
    public ModelBatch modelBatch;
    public ModelInstance boxInstance;

    public void create () {
        modelBatch = new ModelBatch();
        modelBuilder = new ModelBuilder();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1.f));
        shadowLight = new DirectionalLight();
        shadowLight.set(0.8f, 0.8f, 0.8f, -0.5f, -1f, 0.7f);
        environment.add(shadowLight);

        modelBatch = new ModelBatch();

        camera = new PerspectiveCamera();
        camera.fieldOfView = 67;
        camera.near = 0.1f;
        camera.far = 300f;
        camera.position.set(0, 0, 100);
        camera.lookAt(0, 0, 0);

        viewports = ViewportTest1.getViewports(camera);
        viewport = viewports.first();

        names = ViewportTest1.getViewportNames();
        name = names.first();

        ModelBuilder modelBuilder = new ModelBuilder();
        Model boxModel = modelBuilder.createBox(50f, 50f, 50f, new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        boxInstance = new ModelInstance(boxModel);
        boxInstance.transform.rotate(1, 0, 0, 30);
        boxInstance.transform.rotate(0, 1, 0, 30);

        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown (int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    int index = (viewports.indexOf(viewport, true) + 1) % viewports.size;
                    name = names.get(index);
                    viewport = viewports.get(index);
                    resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                }
                return false;
            }
        });
    }

    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        modelBatch.render(boxInstance, environment);
        modelBatch.end();
    }

    public void resize (int width, int height) {
        System.out.println(name);
        viewport.update(width, height);
    }

    public static void main (String[] args) throws Exception {
        new LwjglApplication(new ViewportTest3());
    }
}
