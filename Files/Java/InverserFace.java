private Laser laser = new Laser();
private Touch touch;
public SpatialObject viewer;
public Camera camera;

@Override
public void start()
{
    touch = Input.getTouch(0);
}

private int[] inverser(int idx, int[] triangles)
{
    int offset = idx * 3;
    int a = triangles[offset];
    int b = triangles[offset + 2];
    triangles[offset] = b;
    triangles[offset + 2] = a;
    
    return triangles;
}

@Override
public void repeat()
{
    if(touch.isDown())
    {
        Vector3 direction = camera.screenPointNormal(touch.getPosition().getX(), touch.getPosition().getY());
        LaserHit hit = laser.trace(myTransform.getGlobalPosition(), direction, 0);
        
        if(hit != null)
        {
            Collider collider = hit.getCollider();
            Vertex vertex = collider.getVertex();
            ModelRenderer renderer = hit.object.findComponent(ModelRenderer.class);
            Vertex model_vertex = renderer.getVertex();
            
            GetColliderId id = new GetColliderId(collider, hit.object);
            model_vertex.setTriangles(inverser(id.get_id(), model_vertex.getTrianglesArray()));
            model_vertex.apply();
        }
    }
}





















