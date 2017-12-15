package com.example.pedestrian.rosservice;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import square.square;
import square.squareRequest;
import square.squareResponse;
import org.ros.node.service.ServiceResponseBuilder;

/**
 * Created by pedestrian-username on 17-11-25.
 */

public class Server extends AbstractNodeMain {

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("/service/square/server");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        connectedNode.newServiceServer("/service/square", square._TYPE,
                new ServiceResponseBuilder<squareRequest, squareResponse>() {
                    @Override
                    public void build(squareRequest request, squareResponse response) {
                        response.setResponse(request.getRequest() * request.getRequest());
                    }
                });
    }
}
