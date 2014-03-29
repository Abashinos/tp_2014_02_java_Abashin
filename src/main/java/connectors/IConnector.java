package connectors;
import org.hibernate.cfg.Configuration;

public interface IConnector {

    public void setConfigurationProperties(Configuration conf);
}
