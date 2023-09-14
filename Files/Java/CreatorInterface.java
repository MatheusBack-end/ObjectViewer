public WorldFile file_manager_world;
private ModelRenderer model_renderer;

@Override
public void start()
{
    if(ViewerData.model_file_path == null)
        return;
    
    model_renderer = myObject.findComponent(ModelRenderer.class);
    ObjectVertexLoader vertex = new ObjectVertexLoader(ViewerData.model_file_path);
    Vertex vertex_loaded = vertex.get_object();
    
    if(!vertex.has_object_group())
    {
        model_renderer.setVertex(vertex_loaded);
    }
    
    else
    {
        for(Vertex object: vertex.get_object_list())
        {
            SpatialObject spatial = new SpatialObject(myObject);
            ModelRenderer renderer = new ModelRenderer();
            renderer.setVertex(object);
            renderer.setMaterial(model_renderer.getMaterial());
            spatial.addComponent(renderer);
        }
    }
}

@Override
public void onKeyDown(Key key)
{
    if(key.getName().equals("load-model"))
    {
        WorldController.loadWorld(file_manager_world);
    }
    
    if(key.getName().equals("save-model"))
    {
        if(ViewerData.model_file_path == null)
            return;
        
        ObjectVertexWriter writer = new ObjectVertexWriter(ViewerData.model_file_path, model_renderer.getVertex());
        writer.write();
    }
}


















