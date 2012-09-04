package ru.lexx.acsystem.backend.task.estimation;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 24.05.2006
 * Time: 1:16:29
 */
public class EstimationManager {

    public static IEstimator getEstemator(String name) {
        if ("TimeEstimator".equalsIgnoreCase(name))
            return new TimeEstimator();
        return null;
    }

}
