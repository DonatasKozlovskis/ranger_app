package play.st13nod.myapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ros.address.InetAddressFactory;
import org.ros.android.BitmapFromCompressedImage;
import org.ros.android.RosActivity;
import org.ros.android.view.RosImageView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import java.util.ArrayList;


public class MainActivityRos extends RosActivity {

    private RosImageView<sensor_msgs.CompressedImage> image;
    public final MyNode myNode;
    private Button button;

    public MainActivityRos() {
        super("Ranger App", "Ranger App");
        myNode = new MyNode();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        image = (RosImageView<sensor_msgs.CompressedImage>) findViewById(R.id.image);
//        image.setTopicName("/usb_cam/image_raw/compressed");
//        image.setMessageType(sensor_msgs.CompressedImage._TYPE);
//        image.setMessageToBitmapCallable(new BitmapFromCompressedImage());
    }

    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        NodeConfiguration nodeConfiguration =
                NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress(),
                        getMasterUri());
        nodeMainExecutor.execute(myNode, nodeConfiguration);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1 ) {
            finish();//finishing activity
        }

    }
}
