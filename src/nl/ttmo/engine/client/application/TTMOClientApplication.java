package nl.ttmo.engine.client.application;

import com.jme3.app.Application;
import com.jme3.collision.MotionAllowedListener;
import com.jme3.input.FlyByCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.system.AppSettings;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base Application class for TTMO Client
 * @author Maarten Slenter
 */
public abstract class TTMOClientApplication extends Application {

    public static final String INPUT_MAPPING_EXIT = "SIMPLEAPP_Exit";

	/**
	 * Node onto which the user interface can be attached
	 */
    protected Node rootNode = new Node("Root Node");

	/**
	 * Node onto which the scene can be attached
	 */
    protected Node guiNode = new Node("Gui Node");

	/**
	 * The camera used in the client
	 */
    protected FlyByCamera flyCam;

	/**
	 * Listener for the escape key, to stop the client
	 */
    private AppActionListener actionListener = new AppActionListener();

	/**
	 * Listener for the escape key, to stop the client
	 */
    private class AppActionListener implements ActionListener {

		/**
		 * Called when an inputMapping is pressed
		 *
		 * @param name Name of the inputMapping
		 * @param value Value of the inputMapping
		 * @param tpf Time per frame
		 */
        @Override
        public void onAction(String name, boolean value, float tpf) {
            if (!value) {
                return;
            }

            if (name.equals(INPUT_MAPPING_EXIT)) {
                stop();
            }
        }
    }

    public TTMOClientApplication() {
        super();
    }

	/**
	 * Start the application.
	 */
    @Override
    public void start()
    {
		Logger logger = Logger.getLogger(TTMOClientApplication.class.getName());
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

	/**
	 * Initialize the application, set up the camera and map the right mouse button to rotate the camera
	 */
    @Override
    public void initialize() {
        super.initialize();

        guiNode.setQueueBucket(Bucket.Gui);
        guiNode.setCullHint(CullHint.Never);
        viewPort.attachScene(rootNode);
        guiViewPort.attachScene(guiNode);

        if (inputManager != null) {
			MotionAllowedListener motionAllowedListener = new TTMOMotionAllowedListener();

            flyCam = new TTMOCamera(cam);
            flyCam.setMoveSpeed(10f);
            flyCam.registerWithInput(inputManager);
			flyCam.setMotionAllowedListener(motionAllowedListener);
			flyCam.setDragToRotate(true);

            inputManager.addMapping(INPUT_MAPPING_EXIT, new KeyTrigger(KeyInput.KEY_ESCAPE));
            inputManager.addListener(actionListener, INPUT_MAPPING_EXIT);
        }

        initApp();
    }

	/**
	 * Update the game
	 */
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

	/**
	 * Stop the application
	 */
	@Override
	public void stop()
	{
		TTMOStop();
		super.stop();
	}

	/**
	 * Placeholder for initialization code of the game itself
	 */
    public abstract void initApp();

	/**
	 * Placeholder for the update code of the game itself
	 *
	 * @param tpf Time per frame
	 */
    public void TTMOUpdate(float tpf)
    {

    }

	/**
	 * Placeholder for the render code of the game itself
	 *
	 * @param rm The RenderManager used by JME
	 */
    public void TTMORender(RenderManager rm)
    {

    }

	/**
	 * Placeholder for the stop code of the game itself
	 */
	public void TTMOStop()
	{

	}

	/**
	 * Listener for movement of the camera.
	 * Prevents the camera from moving up or down but keeps its momentum,
	 * thus ensuring the camera will still move just as fast when looking down as when looking into the distance.
	 */
	class TTMOMotionAllowedListener implements MotionAllowedListener
	{
		public void checkMotionAllowed(Vector3f position, Vector3f velocity)
		{
			double length = velocity.length();

			velocity.setY(0);

			double ratio = length/velocity.length();

			velocity.setX((float) ratio * velocity.getX());
			velocity.setZ((float) ratio * velocity.getZ());

			position.addLocal(velocity);
		}
	}
}