private Vector3Buffer vertices;
private NativeIntBuffer triangles;
public int index = 0;
public List<Vertex> background_vertexs;

@Override
public void start()
{
    background_vertexs = new ArrayList();
    ModelRenderer model_renderer = myObject.findComponent(ModelRenderer.class);
    Vertex vertex = model_renderer.getVertex();
    
    if(vertex == null)
        return;
        
    vertices = new Vector3Buffer(vertex.getVerticesBuffer());
    triangles = vertex.getTrianglesBuffer();
    
    TimeCounter debug = new TimeCounter();
    debug.start();
    generate();
    debug.finish();
    Console.log(debug.getElapsedMilliseconds() + "Ms Collider time");
}

private void face(Vertex vertex)
{   
    Collider collider = new Collider();
    collider.setVertex(vertex);
    myObject.addComponent(collider);
}

private Vertex make_vertex(int idx)
{
    Vertex vertex = new Vertex();
    Vector3Buffer face_vertices = new Vector3Buffer(3);
    NativeIntBuffer face_triangles = new NativeIntBuffer(3);
    
    for(int i = 0; i < 3; i++)
    {
        face_vertices.put(vertices.get(triangles.get(idx + i)));
        face_triangles.put(i);
    }
    
    vertex.setVertices(face_vertices);
    vertex.setTriangles(face_triangles);
    
    return vertex;
}

private void generate()
{
    new AsyncTask(new AsyncRunnable(){
        
        public Object onBackground(Object intput)
        {
            for(index = 0; index < triangles.capacity(); index += 3)
            {
                background_vertexs.add(make_vertex(index));    
            }
            
            return background_vertexs;
        }
        
        public void onEngine(Object result)
        {
            for(Vertex vertex: (List<Vertex>)result)
            {
                Collider collider = new Collider();
                collider.setVertex(vertex);
                myObject.addComponent(collider);
            }
        }
    });
}
