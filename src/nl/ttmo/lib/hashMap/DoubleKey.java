package nl.ttmo.lib.hashMap;

/**
 * Represents a HashMap key that consists of two keys
 * @author Maarten Slenter
 */
public class DoubleKey<K>
{
	/**
	 * The first key
	 * This is ALWAYS the key with the lowest hash code
	 * This is done to ensure that the hash code of this double key is always the same when the same two keys are used
	 */
	K key1;

	/**
	 * The second key
	 * This is ALWAYS the key with the highest hash code
	 */
	K key2;

	public DoubleKey(K key1, K key2)
	{
		//Always put the smallest hashCode first
		if(key2.hashCode() < key1.hashCode())
		{
			this.key1 = key2;
			this.key2 = key1;
		}
		else
		{
			this.key1 = key1;
			this.key2 = key2;
		}
	}

	public K getKey1()
	{
		return key1;
	}

	public K getKey2()
	{
		return key2;
	}

	/**
	 * Returns key1 if the supplied key matches key2 and visa versa.
	 * If it matches neither, null is returned
	 * @param key The key to check
	 * @return key1 if the supplied key matches key2 and visa versa, null if it matches neither
	 */
	public K getOtherKey(K key)
	{
		if(key1.equals(key))
		{
			return key2;
		}
		else if(key2.equals(key))
		{
			return key1;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Checks if this object is equal to the supplied object
	 * This is required for using DoubleKey as a HashMap key
	 * @param obj The object to compare this object to
	 * @return True if the two objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final DoubleKey<K> other = (DoubleKey<K>) obj;

		if (this.key1 != other.key1 && (this.key1 == null || !this.key1.equals(other.key1))) {
			return false;
		}
		if (this.key2 != other.key2 && (this.key2 == null || !this.key2.equals(other.key2))) {
			return false;
		}
		return true;
	}

	/**
	 * Generates a hash code for this double key
	 * This is required for using DoubleKey as a HashMap key
	 * @return The generated hash code
	 */
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 79 * hash + (this.key1 != null ? this.key1.hashCode() : 0);
		hash = 79 * hash + (this.key2 != null ? this.key2.hashCode() : 0);
		return hash;
	}


}
