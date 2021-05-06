package net.ftxf.dapps.emqx;

public class PublishRequest {
  private String topic = "auction/bid";

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public int getQos() {
    return qos;
  }

  public void setQos(int qos) {
    this.qos = qos;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public String getRetain() {
    return retain;
  }

  public void setRetain(String retain) {
    this.retain = retain;
  }

  public String getClientid() {
    return clientid;
  }

  public void setClientid(String clientid) {
    this.clientid = clientid;
  }

  private int qos = 1;
  private String payload;
  private String retain;
  private String clientid = "backend";
}
