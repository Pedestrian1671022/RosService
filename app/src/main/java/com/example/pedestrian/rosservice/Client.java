package com.example.pedestrian.rosservice;

import org.ros.exception.RemoteException;
import org.ros.exception.RosRuntimeException;
import org.ros.exception.ServiceNotFoundException;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceClient;
import org.ros.node.service.ServiceResponseListener;

import square.square;
import square.squareRequest;
import square.squareResponse;

/**
 * Created by pedestrian-username on 17-11-25.
 */

public class Client extends AbstractNodeMain {

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("/service/square/client");
    }

    @Override
    public void onStart(final ConnectedNode connectedNode) {
        ServiceClient<squareRequest, squareResponse> serviceClient;
        try {
            serviceClient = connectedNode.newServiceClient("/service/square", square._TYPE);
        } catch (ServiceNotFoundException e) {
            throw new RosRuntimeException(e);
        }
        final squareRequest request = serviceClient.newMessage();
        request.setRequest(51);
        serviceClient.call(request, new ServiceResponseListener<squareResponse>() {
            @Override
            public void onSuccess(squareResponse response) {
                connectedNode.getLog().info(String.format("%d", response.getResponse()));
            }

            @Override
            public void onFailure(RemoteException e) {
                throw new RosRuntimeException(e);
            }
        });
    }
}
