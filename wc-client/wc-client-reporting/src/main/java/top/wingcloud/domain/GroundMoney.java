package top.wingcloud.domain;

/**
 * 环比增长速度实体类
 */
public class GroundMoney {
    private String datetime;
    private Double flow;

    public GroundMoney(String datetime, Double flow) {
        this.datetime = datetime;
        this.flow = flow;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Double getFlow() {
        return flow;
    }

    public void setFlow(Double flow) {
        this.flow = flow;
    }

    @Override
    public String toString() {
        return "GroundMoney{" +
                "datetime='" + datetime + '\'' +
                ", flow=" + flow +
                '}';
    }
}
