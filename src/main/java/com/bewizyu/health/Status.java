package com.bewizyu.health;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Status {

    private String name;
    private String urlLogo;
    private String urlService;
    private boolean code;
    private Long responseTime;

    private Status(Builder builder) {
        this.name = builder.name;
        this.urlService = builder.urlService;
        this.urlLogo = builder.urlLogo;
        this.code = builder.code;
        this.responseTime = builder.responseTime;
    }

    public static Named builder(CloseableHttpClient client) {
        return new Builder(client);
    }

    public static class Builder implements Named, Url, Build{
        private String name;
        private String urlService;
        private String urlLogo;
        private boolean code;
        private Long responseTime;

        private CloseableHttpClient http;

        public Builder(CloseableHttpClient client){
            http = client;
        }

        public Url name(String name) {
            this.name = name;
            return this;
        }

        public Build url(String url) {
            this.urlService = url;
            return this;
        }

        public Build urlLogo(String urlLogo) {
            this.urlLogo = urlLogo;
            return this;
        }

        public Status build() throws IOException {

            HttpGet method = new HttpGet(urlService);

            Instant before = Instant.now();
            HttpClientContext context = HttpClientContext.create();

            try {

                HttpResponse response = http.execute(method, context);

                    try {
                        code = StatusCode.convertFromHTTP(response.getStatusLine().getStatusCode());
                    }finally {
                        ((CloseableHttpResponse) response).close();
                    }

            }catch (IOException ex) {
                code = false;
                ex.printStackTrace();
            }finally {
                Instant after = Instant.now();
                responseTime = Long.valueOf(ChronoUnit.MILLIS.between(before, after));
            }



            return new Status(this);
        }
    }

    interface Named {
        public Url name(String name);
    }

    interface Url {
        public Build url(String url);
    }

    interface Build {
        public Build urlLogo(String urlLogo);
        public Status build() throws IOException;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public String getUrlService() {
        return urlService;
    }

    public void setUrlService(String urlService) {
        this.urlService = urlService;
    }
}
