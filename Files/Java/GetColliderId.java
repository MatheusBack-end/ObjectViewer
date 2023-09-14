public class GetColliderId
{
    public Vector3Buffer model_vertices;
    public List<Vector3> model_triangles;
    public Vector3Buffer collider_vertices;
    
    public GetColliderId(Collider collider, SpatialObject source)
    {
        Vertex collider_vertex = collider.getVertex();
        ModelRenderer source_renderer = source.findComponent(ModelRenderer.class);
        Vertex model_vertex = source_renderer.getVertex();
        
        this.model_vertices = new Vector3Buffer(model_vertex.getVerticesBuffer());
        this.model_triangles = model_vertex.getTriangles();
        this.collider_vertices = new Vector3Buffer(collider_vertex.getVerticesBuffer());
    }
    
    public int get_id()
    {
        for(int i = 0; i < model_triangles.size(); i++)
        {
            Vector3 vector = model_triangles.get(i);
            Point3 face = new Point3((int)vector.getX(), (int)vector.getY(), (int)vector.getZ());
            boolean is_equals = true;
            Vector3Buffer vertices = new Vector3Buffer(3);
            vertices.put(model_vertices.get(face.getX()));
            vertices.put(model_vertices.get(face.getY()));
            vertices.put(model_vertices.get(face.getZ()));
            
            for(int x = 0; x < 3; x++)
            {
                if(!collider_vertices.get(x).equals(vertices.get(x)))
                {
                    is_equals = false;
                }
            }
            
            if(is_equals)
            {
                return i;
            }
            
            else
            {
                is_equals = true;
                
                if(!collider_vertices.get(0).equals(vertices.get(2)))
                {
                    is_equals = false;
                }
                
                if(!collider_vertices.get(1).equals(vertices.get(1)))
                {
                    is_equals = false;
                }
                
                if(!collider_vertices.get(2).equals(vertices.get(0)))
                {
                    is_equals = false;
                }
                
                if(is_equals)
                {
                    return i;
                }
            }
        }
        
        return -1;
    }
}





















