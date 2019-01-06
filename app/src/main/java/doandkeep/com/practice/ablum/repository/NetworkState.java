package doandkeep.com.practice.ablum.repository;

public class NetworkState {

    private Status status;
    private String msg;

    public NetworkState(Status status) {
        this.status = status;
    }

    public NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}

