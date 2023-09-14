import de.javagl.obj.Obj;
import de.javagl.obj.Objs;
import de.javagl.obj.ObjWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;

public class ObjectVertexWriter
{
    private Vertex vertex;
    private String file_path;
    
    /**
     * Class constructor.
     *
     * @param file_name  nome do arquivo a ser salvo.
     * @param vertex     malha do objeto a ser salvo.
     */
    public ObjectVertexWriter(String file_name, Vertex vertex)
    {
        this.file_path = Directories.internal() + "Files/" + file_name;
        this.vertex = vertex;
    }
    
    /**
     * Salva a malha do objeto no arquivo passado no construtor.
     *
     * @see Obj.Objs
     */
    public void write()
    {
        IntBuffer triangles = BuffersUtils.to_buffer(vertex.getTrianglesArray());
        FloatBuffer vertices = BuffersUtils.to_buffer(vertex.getVerticesArray());
        
        Obj obj = Objs.createFromIndexedTriangleData(triangles, vertices, null, null);
        
        try
        {
            FileWriter writer = new FileWriter(file_path);
            ObjWriter.write(obj, writer);
            Toast.showText(file_path + " save successfully", 0);
        }
        
        catch(IOException e)
        {
            Console.log(e);
        }
    }
}
