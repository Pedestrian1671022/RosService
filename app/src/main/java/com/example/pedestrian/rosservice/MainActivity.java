package com.example.pedestrian.rosservice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.ros.android.RosActivity;
import org.ros.message.MessageFactory;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;
import org.ros.node.parameter.ParameterListener;
import org.ros.node.parameter.ParameterTree;

public class MainActivity extends RosActivity implements NodeMain {

//    private Server server;
//    private Client client;
    private TextView textView;
    private Button button;
    private ConnectedNode connectedNode;
    private MessageFactory messageFactory;
    private ParameterTree parameterTree;

    public MainActivity() {
        super("service", "service");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        server = new Server();
//        client = new Client();
        textView = (TextView)findViewById(R.id.response);
        button = (Button)findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parameterTree.has("/is_ready_to_serve")){
                    parameterTree.set("/is_ready_to_serve", true);
                }
            }
        });
    }


    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname());
        nodeConfiguration.setMasterUri(getMasterUri());
//        nodeMainExecutor.execute(server, nodeConfiguration);
//        nodeMainExecutor.execute(client, nodeConfiguration);
        nodeMainExecutor.execute(this, nodeConfiguration);
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("/service/square/client");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        this.connectedNode = connectedNode;
        this.parameterTree = connectedNode.getParameterTree();
        parameterTree.addParameterListener("/is_ready_to_serve", new ParameterListener() {
            @Override
            public void onNewValue(final Object o) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(o.toString());
                    }
                });
            }
        });
        this.messageFactory = connectedNode.getTopicMessageFactory();
//        ServiceClient<squareRequest, squareResponse> serviceClient;
//        try {
//            serviceClient = connectedNode.newServiceClient("/service/square", square._TYPE);
//        } catch (ServiceNotFoundException e) {
//            throw new RosRuntimeException(e);
//        }
//        final squareRequest request = serviceClient.newMessage();
//        request.setRequest(61);
//        serviceClient.call(request, new ServiceResponseListener<squareResponse>() {
//            @Override
//            public void onSuccess(final squareResponse response) {
//                runOnUiThread(new Runnable(){
//                    @Override
//                    public void run() {
//                        textView.setText(String.format("%d", response.getResponse()));
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(RemoteException e) {
//                throw new RosRuntimeException(e);
//            }
//        });
    }

    @Override
    public void onShutdown(Node node) {

    }

    @Override
    public void onShutdownComplete(Node node) {

    }

    @Override
    public void onError(Node node, Throwable throwable) {

    }
}
