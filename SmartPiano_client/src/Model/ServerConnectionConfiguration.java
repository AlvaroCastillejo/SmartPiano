
package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerConnectionConfiguration {

    @SerializedName("port")
    @Expose
    private Integer port;
    @SerializedName("ip")
    @Expose
    private String ip;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
