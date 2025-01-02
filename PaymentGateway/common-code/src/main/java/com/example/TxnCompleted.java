package com.example;

public class TxnCompleted {

    private Long id;
    private Boolean success;
    private String reason;
    private String requestId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "TxnCompleted{" +
                "id=" + id +
                ", success=" + success +
                ", reason='" + reason + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
