package nl.ttmo.engine.serverClient.application;

import com.jme3.app.Application;
import com.jme3.collision.MotionAllowedListener;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext.Type;

import java.util.logging.Logger;
import java.util.logging.Level;

public abstract class TTMOServerClientApplication extends Application {

    public static final String INPUT_MAPPING_EXIT = "SIMPLEAPP_Exit";

    protected Node rootNode = new Node("Root Node");
    protected Node guiNode = new Node("Gui Node");
    protected BitmapText fpsText;
    protected BitmapFont guiFont;
    protected FlyByCamera flyCam;
    private AppActionListener actionListener = new AppActionListener();

    private class AppActionListener implements ActionListener {

        public void onAction(String name, boolean value, float tpf) {
            if (!value) {
                return;
            }

            if (name.equals(INPUT_MAPPING_EXIT)) {
                stop();
            }
        }
    }

    public TTMOServerClientApplication() {
        super();
    }

    @Override
    public void start()
    {
        Logger logger = Logger.getLogger(TTMOServerClientApplication.class.getName());
		logger.getParent().setLevel(Level.WARNING);

        if (settings == null) {
            setSettings(new AppSettings(true));
        }

		settings.setResolution(1280, 720);
		settings.setTitle("TTMO Client");

        setSettings(settings);
        super.start();
    }

    public FlyByCamera getFlyByCamera() {
        return flyCam;
    }

    public Node getGuiNode() {
        return guiNode;
    }

    public Node getRootNode() {
        return rootNode;
    }

    @Override
    public void initialize() {
        super.initialize();

        guiNode.setQueueBucket(Bucket.Gui);
        guiNode.setCullHint(CullHint.Never);
        viewPort.attachScene(rootNode);
        guiViewPort.attachScene(guiNode);

        if (inputManager != null) {
			MotionAllowedListener motionAllowedListener = new TTMOMotionAllowedListener();

            flyCam = new FlyByCamera(cam);
            flyCam.setMoveSpeed(10f);
            flyCam.registerWithInput(inputManager);
			flyCam.setMotionAllowedListener(motionAllowedListener);
			flyCam.setDragToRotate(true);
			inputManager.deleteMapping("FLYCAM_RotateDrag");
			inputManager.addMapping("FLYCAM_RotateDrag", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
			inputManager.addListener(flyCam, "FLYCAM_RotateDrag");

            if (context.getType() == Type.Display) {
                inputManager.addMapping(INPUT_MAPPING_EXIT, new KeyTrigger(KeyInput.KEY_ESCAPE));
            }

            inputManager.addListener(actionListener, INPUT_MAPPING_EXIT);

        }

        initApp();
    }

    @Override
    public void update() {
        super.update();
        if (speed == 0 || paused) {
            return;
        }

        float tpf = timer.getTimePerFrame() * speed;

        stateManager.update(tpf);

        TTMOUpdate(tpf);
        rootNode.updateLogicalState(tpf);
        guiNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        guiNode.updateGeometricState();

        stateManager.render(renderManager);
        renderManager.render(tpf, context.isRenderable());
        TTMORender(renderManager);
        stateManager.postRender();
    }

	@Override
	public void stop()
	{
		TTMOStop();
		super.stop();
	}

    public abstract void initApp();

    public void TTMOUpdate(float tpf)
    {

    }

    public void TTMORender(RenderManager rm)
    {

    }

	public void TTMOStop()
	{

	}

	class TTMOMotionAllowedListener implements MotionAllowedListener
	{
		public void checkMotionAllowed(Vector3f position, Vector3f velocity)
		{
			velocity.setY(0);
			position.addLocal(velocity);
		}
	}
}