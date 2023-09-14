public float sensitivity = 11.0f;
private Vector2 touch;
private SpatialObject pivot_x, camera;
private Touch touch_0, touch_1;
private boolean in_zoom = false;
private float last_distance = 0;

@Override
public void start()
{
    touch = Input.getAxisValue("touch");
    pivot_x = myObject.findChildObject("pivot-x");
    camera = pivot_x.findChildObject("camera");
    touch_0 = Input.getTouch(0);
    touch_1 = Input.getTouch(1);
}

@Override
public void repeat()
{
    zoom();
    
    if(!in_zoom)
    {
        myTransform.rotateInSeconds(0, -touch.getX() * sensitivity, 0);
        pivot_x.rotateInSeconds(touch.getY() * sensitivity, 0, 0);
    }
}

private void apply_zoom(float value)
{
    camera.moveInSeconds(0, 0, value);
    in_zoom = true;
}

private void zoom()
{
    if(touch_0.isPressed() && touch_1.isPressed())
    {
        float distance = touch_0.getPosition().distance(touch_1.getPosition());
        
        if(last_distance == 0)
        {
            last_distance = distance;
        }
        
        float zoom_value = distance - last_distance;
        apply_zoom(zoom_value);
        
        last_distance = distance;
        
    }
    
    else
    {
        last_distance = 0;
        in_zoom = false;
    }
}
