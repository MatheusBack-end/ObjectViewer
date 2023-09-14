public class BuffersUtils
{
    private BuffersUtils()
    {
        
    }
    
    public static int[] to_array(IntBuffer value)
    {
        if(value.hasArray())
            return value.array();
            
        int[] array = new int[value.capacity()];
        
        for(int i = 0; i < value.capacity(); i++)
        {
            array[i] = value.get(i);
        }
        
        return array;
    }
    
    public static float[] to_array(FloatBuffer value)
    {
        if(value.hasArray())
            return value.array();
            
        float[] array = new float[value.capacity()];
        
        for(int i = 0; i < value.capacity(); i++)
        {
            array[i] = value.get(i);
        }
        
        return array;
    }
    
    public static IntBuffer to_buffer(int[] value)
    {
        IntBuffer buffer = IntBuffer.allocate(value.length);
        
        for(int i = 0; i < value.length; i++)
        {
            buffer.put(i, value[i]); 
        }
        
        return buffer;
    }
    
    public static FloatBuffer to_buffer(float[] value)
    {
        FloatBuffer buffer = FloatBuffer.allocate(value.length);
        
        for(int i = 0; i < value.length; i++)
        {
            buffer.put(i, value[i]); 
        }
        
        return buffer;
    }
}
