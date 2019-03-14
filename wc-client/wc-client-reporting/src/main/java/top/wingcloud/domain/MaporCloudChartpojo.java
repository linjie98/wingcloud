package top.wingcloud.domain;

public class MaporCloudChartpojo {
    private String name;
    private Long value;

    public MaporCloudChartpojo(String name, Long value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MapChartpojo{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
