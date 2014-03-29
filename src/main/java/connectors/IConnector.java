package connectors;
import org.hibernate.cfg.Configuration;

public interface IConnector {

    void setConfigurationProperties(Configuration conf);
}
