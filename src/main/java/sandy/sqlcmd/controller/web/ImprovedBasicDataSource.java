package sandy.sqlcmd.controller.web;

import org.apache.commons.dbcp.BasicDataSource;

public class ImprovedBasicDataSource extends BasicDataSource {

    public ImprovedBasicDataSource(String driverClassName, String url, String username, String password) {
        super();
        this.setDriverClassName(driverClassName);
        this.setUrl(url);
        this.setUsername(username);
        this.setPassword(password);
    }
}
