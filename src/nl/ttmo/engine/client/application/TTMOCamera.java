package nl.ttmo.engine.client.application;

import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import nl.ttmo.engine.client.gui.Constants;

/**
 * The camera for the client, supplies the following behaviour:
 * - WASD only make it move around in the x/z plane, not the y axis. The speed depends on how far down the camera is looking
 * - Instead of zooming when the scroll wheel is turned, turning the scroll wheel makes the camera move on the y axis. The speed at which it moves along that axis depends on how far it is from the normal y level. The y level has a lower bound, not an upper bound.
 * - The camera won't be able to turn upside down. It can only look down to an angle about 80 degrees.
 * @author Maarten Slenter
 */
public class TTMOCamera extends FlyByCamera
{
    public TTMOCamera(Camera cam)
    {
        super(cam);
    }
    
    @Override
    public void registerWithInput(InputManager inputManager)
    {
        super.registerWithInput(inputManager);
        inputManager.deleteMapping("FLYCAM_RotateDrag");
        inputManager.addMapping("FLYCAM_RotateDrag", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(this, "FLYCAM_RotateDrag");
    }
    
    /**
     * Rotates the camera.
     * Also restricts the camera to an angle between GuiConstants#minAngle and GuiConstants#maxAngle (in radians) with the horizontal plane
     */
    @Override
    protected void rotateCamera(float value, Vector3f axis)
    {
        if (dragToRotate){
            if (canRotate){
                
            }else{
                return;
            }
        }

        Matrix3f mat = new Matrix3f();
        mat.fromAngleNormalAxis(rotationSpeed * value, axis);

        Vector3f up = cam.getUp();
        Vector3f left = cam.getLeft();
        Vector3f dir = cam.getDirection();

        mat.mult(up, up);
        mat.mult(left, left);
        mat.mult(dir, dir);

        Quaternion q = new Quaternion();
        q.fromAxes(left, up, dir);
        q.normalizeLocal();
        
        float[] angles = q.toAngles(null);
        if(angles[0] > Constants.maxAngle)
        {
            angles[0] = Constants.maxAngle;
            q.fromAngles(angles);
        }
        else if(angles[0] < Constants.minAngle)
        {
            angles[0] = Constants.minAngle;
            q.fromAngles(angles);
        }

        cam.setAxes(q);
    }
    
    @Override
    protected void moveCamera(float value, boolean sideways)
    {
        Vector3f vel = new Vector3f();
        Vector3f pos = cam.getLocation().clone();

        if (sideways)
        {
            cam.getLeft(vel);
        }
        else
        {
            cam.getDirection(vel);
        }
        
        vel.multLocal(value * (float) Math.exp(-vel.getY()) * moveSpeed);
        vel.setY(0);
        
        if (motionAllowed != null)
        {
            motionAllowed.checkMotionAllowed(pos, vel);
        }
        else
        {
            pos.addLocal(vel);
        }

        cam.setLocation(pos);
    }
    
    @Override
    protected void zoomCamera(float value)
    {
        Vector3f pos = cam.getLocation().clone();
        float change = Math.signum(value) * Math.min((float) (1 - Math.exp(-Math.pow(pos.getY() + Constants.cameraBorder + 1f, 2) / 20)), 0.3f);
        pos.addLocal(0, change, 0);
        if(pos.getY() >  -Constants.cameraBorder)
        {
            cam.setLocation(pos);
        }
    }
}
