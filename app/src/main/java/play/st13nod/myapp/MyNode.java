package play.st13nod.myapp;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;
import org.w3c.dom.Node;

public class MyNode extends AbstractNodeMain {
    private String node_name;
    private String topic_name;

    private Publisher<std_msgs.String> publisher;

    public MyNode() {
        node_name = "frame_publisher_node";
        topic_name = "frame_publisher_topic";

    }

    public MyNode(String nodeName, String topicName)
    {
        node_name= nodeName;
        topic_name = topicName;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of(node_name);
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        publisher = connectedNode.newPublisher(topic_name, std_msgs.String._TYPE);
    }

    public void publishStringName(String text) {
        publish(text);
    }

    private void publish(String text) {
        if (publisher != null) {
            std_msgs.String message = publisher.newMessage();
            message.setData(text);
            publisher.publish(message);
        }else{

        }
    }

    @Override
    public void onShutdown(org.ros.node.Node node) {
        super.onShutdown(node);
        publisher = null;
    }
}