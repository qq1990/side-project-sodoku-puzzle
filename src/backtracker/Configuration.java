package backtracker;
import java.util.Collection;

/**
 * @author Quan Quy
 */
public interface Configuration {

    public Collection<Configuration> getSuccessors();

    public boolean isValid();

    public boolean isGoal();
}