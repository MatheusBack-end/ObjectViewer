public ObjectFile button;
public WorldFile default_world;
public SpatialObject sui_interface;
private java.io.File internal;

@Override
public void start()
{
    internal = new java.io.File(Directories.internal() + "Files");
    
    SUIText debug = sui_interface.getChildAt(1).getChildAt(0).findComponent(SUIText.class);
    if(!internal.isDirectory())
    {
        debug.setText("internal not found");
    }
    
    String[] files = internal.list();
    
    
    SpatialObject previous_button = sui_interface.getChildAt(1);
    
    for(String file: files)
    {
        previous_button = create_sui_button(file, previous_button);
    }
}

private SpatialObject create_sui_button(String file_name, SpatialObject previous_button)
{
    SpatialObject sui_button = myObject.instantiateHasChild(button, sui_interface);
    
    SUIRect rect = sui_button.findComponent(SUIRect.class);
    SUIText text = sui_button.findChildObject("text").findComponent(SUIText.class);
    SUIKeyEventListener key = sui_button.findComponent(SUIKeyEventListener.class);
    
    rect.setTopMargin(1);
    text.setText(file_name);
    key.setKeyName(file_name);
        
    
    if(previous_button == null)
        return sui_button;
        
    SVerticalConstraintTarget target_entry = SVerticalConstraintTarget.ToBottomOf;
    rect.setTopAnchorTarget(target_entry);
    rect.setTopAnchorObject(previous_button);
    
    return sui_button;
}

@Override
public void onKeyDown(Key key)
{
    String key_name = key.getName();
    
    if(key_name.equals("back"))
    {
        WorldController.loadWorld(default_world);
        return;
    }
    
    ViewerData.model_file_path = key_name;
    WorldController.loadWorld(default_world);
}
