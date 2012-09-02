package nl.ttmo.engine.server.application;

import com.jme3.app.Application;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * The base application class for the TTMO server
 * @author Maarten Slenter
 */
public abstract class TTMOServerApplication extends Application
{
    public TTMOServerApplication() {
        super();
    }

	/**
	 * Starts the application
	 */
    @Override
    public void start()
    {
        Logger logger = Logger.getLogger(TTMOServerApplication.class.getName());
		logger.getParent().setLevel(Level.WARNING);

        if (settings == null) {
            setSettings(new AppSettings(true));
        }

        setSettings(settings);
        super.start();
    }

	/**
	 * Initializes the application
	 */
    @Override
    public void initialize() {
        super.initialize();
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

        stateManager.render(renderManager);
        renderManager.render(tpf, context.isRenderable());
        TTMORender(renderManager);
        stateManager.postRender();
    }

	/**
	 * Stops the application
	 */
	@Override
	public void stop()
	{
		TTMOStop();
		super.stop();
	}

	/**
	 * Placeholder for the initialization code of the game itself
	 */
    public abstract void initApp();

	/**
	 * Placeholder for the update code of the game itself
	 * @param tpf The time per frame
	 */
    public void TTMOUpdate(float tpf)
    {

    }

	/**
	 * Placeholder for the render code of the game itself
	 * @param rm The render manager
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
}