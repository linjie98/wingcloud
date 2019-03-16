package top.wingcloud.domain;

/**
 * 微服务信息实体类
 */
public class ServiceMsg {
    private String servicename;
    private String serviceport;

    public ServiceMsg(String servicename, String serviceport) {
        this.servicename = servicename;
        this.serviceport = serviceport;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServiceport() {
        return serviceport;
    }

    public void setServiceport(String serviceport) {
        this.serviceport = serviceport;
    }

    @Override
    public String toString() {
        return "ServiceMsg{" +
                "servicename='" + servicename + '\'' +
                ", serviceport='" + serviceport + '\'' +
                '}';
    }
}
