package org.davic.resources;

/**
 * This class is the parent class for events reporting changes in the status of
 * resources.
 */
public class ResourceStatusEvent extends java.util.EventObject {

    /**
     * This constructs a resource status event relating to the specified
     * resource. The precise class of the object will depend on the individual
     * API using the resource notification API.
     *
     * @param source the object whose status was changed
     */
    public ResourceStatusEvent(Object source) {
        super(source);
    }

}
