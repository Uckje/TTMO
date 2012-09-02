package nl.ttmo.lib;

/**
 * A static utility class for dynamically instantiating an object
 * @author Maarten Slenter
 */
public class ObjectFactory
{
	/**
	 * Dynamically instantiates an object
	 * @param mainPackage The main package (client, lib, messages or server) that this class is in
	 * @param name The name (including path from the main package down) to the class
	 * @return An instance of the requested class
	 */
	public static Object createObject(String mainPackage, String name)
    {
        Object object = null;

        try
        {
            object = (Object) Class.forName("nl.ttmo."+mainPackage+"."+name).newInstance();
        }
        catch (InstantiationException ex)
        {
            System.out.println(ex);
        }
        catch (IllegalAccessException ex)
        {
            System.out.println(ex);
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println(ex);
        }

        return object;
    }
}
