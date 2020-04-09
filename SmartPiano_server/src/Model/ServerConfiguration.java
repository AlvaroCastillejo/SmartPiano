package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerConfiguration {

    @SerializedName("databasePort")
    @Expose
    private Integer databasePort;
    @SerializedName("databaseIP")
    @Expose
    private String databaseIP;
    @SerializedName("databaseName")
    @Expose
    private String databaseName;
    @SerializedName("databaseUser")
    @Expose
    private String databaseUser;
    @SerializedName("databasePassword")
    @Expose
    private String databasePassword;
    @SerializedName("clientPort")
    @Expose
    private Integer clientPort;

    public Integer getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(Integer databasePort) {
        this.databasePort = databasePort;
    }

    public String getDatabaseIP() {
        return databaseIP;
    }

    public void setDatabaseIP(String databaseIP) {
        this.databaseIP = databaseIP;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public Integer getClientPort() {
        return clientPort;
    }

    public void setClientPort(Integer clientPort) {
        this.clientPort = clientPort;
    }

}
