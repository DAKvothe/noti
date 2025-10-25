package com.santander.cpe.porweb.integration.indigitall.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "indigitall")
public class IndigitallProperties {
    private String baseUrl;
    private String serverKey;
    private int timeoutMs = 10000;

    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public String getServerKey() { return serverKey; }
    public void setServerKey(String serverKey) { this.serverKey = serverKey; }
    public int getTimeoutMs() { return timeoutMs; }
    public void setTimeoutMs(int timeoutMs) { this.timeoutMs = timeoutMs; }
}
