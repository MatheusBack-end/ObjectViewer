import de.javagl.obj.ObjData;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjUtils;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjSplitting;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;

public class ObjectVertexLoader
{
    private String file_path;
    private Obj obj;
    
    /**
     * Class constructor.
     * 
     * @param file_name nome do arquivo .obj a ser gerado a malha, ele faz essa
     * pequena adição no diretorio do arquivo a ser lido para o diretorio
     * Andoid/data.../Files
     */
    public ObjectVertexLoader(String file_name)
    {
        this.file_path = Directories.internal() + "Files/" + file_name;
    }
    
    /**
     * Comunicação entre a biblioteca Obj para ler o conteudo 
     * do arquivo e gerar uma malha do objeto pronto para se 
     * ultilizado no motor.
     *
     * @param void
     * @return The {@link JAVARuntime.Vertex}
     * @see Vertex
     */
    public Vertex get_vertex_object()
    {
        FileReader reader = null;
        
        try
        {
            reader = new FileReader(file_path);
            obj = ObjUtils.convertToRenderable(ObjReader.read(reader));
            reader.close(); //* @see ObjReader.read() */
        }
        
        catch(Exception e)
        {
            Console.log(e);
        }
        
        return obj_to_vertex(obj);
    }
    
    /**
     * Verifica se o objeto tem um grupo de materiais.
     * 
     * @return boolean  se houver algum retornara true.
     */
    public boolean has_object_group()
    {
        return obj.getNumMaterialGroups() > 0;
    }

    /**
     * Pega todos os objetos e divide pelos grupos de materiais.
     *
     * @return List<Vertex>  a lista de malhas por grupo.
     */
    public List<Vertex> get_object_list()
    {
        List<Vertex> object_list = new ArrayList();
        Map<String, Obj> group = ObjSplitting.splitByMaterialGroups(obj);
        
        for(Map.Entry<String, Obj> object: group.entrySet())
        {
            object_list.add(obj_to_vertex(object.getValue()));
        }

        return object_list;
    }
    
    /**
    * Um metodo utilitario para não precisar converter manualmente uma malha do tipo {@link Obj.ObjData}
    * para a malha ultilizada no motor {@link JAVARuntime.Vertex}.
    *
    * @param obj      Malha do tipo Obj a ser convertida.
    * @return Vertex  Malha ja convertida.
    *
    * @see Obj.ObjData
    */
    private Vertex obj_to_vertex(Obj obj)
    {
        Vertex vertex = new Vertex();
        IntBuffer indices = ObjData.getFaceVertexIndices(obj);
        FloatBuffer vertices = ObjData.getVertices(obj);
        vertex.setVertices(BuffersUtils.to_array(vertices));
        vertex.setTriangles(BuffersUtils.to_array(indices));

        return vertex;
    }
}
