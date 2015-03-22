package play.st13nod.myapp;


import android.content.Intent;
import android.os.Bundle;

import org.ros.address.InetAddressFactory;
import org.ros.android.RosActivity;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;


public class MainActivityRos extends RosActivity {

    public static RosStringSendNode newFrameNamePublisher = new RosStringSendNode( "frame_name_add", "frame_name_add_topic");
    public static RosStringSendNode gotoFramePublisher;

    public MainActivityRos() {
        super("Ranger App", "Ranger App");

        //create node objects
        newFrameNamePublisher = new RosStringSendNode( "frame_name_add", "frame_name_add_topic");
        gotoFramePublisher = new RosStringSendNode("frame_goto_node", "frame_goto_topic");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        // get node configuration
        NodeConfiguration nodeConfiguration =
                NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress(),
                        getMasterUri());

        // execute nodes
        nodeMainExecutor.execute(newFrameNamePublisher, nodeConfiguration);
        nodeMainExecutor.execute(gotoFramePublisher, nodeConfiguration);

        //start new activity with swipe views
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1 );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 ) {
            //finishing this activity whenever MainActivity is paused or finished.
            finish();
        }
    }
}
