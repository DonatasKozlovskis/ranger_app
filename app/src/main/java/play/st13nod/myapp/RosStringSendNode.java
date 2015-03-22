package play.st13nod.myapp;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

public class RosStringSendNode extends AbstractNodeMain {
    private String node_name;
    private String topic_name;

    private Publisher<std_msgs.String> publisher;

    public RosStringSendNode() {
        node_name = "publisher_node";
        topic_name = "publisher_topic";

    }

    public RosStringSendNode(String nodeName, String topicName)
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