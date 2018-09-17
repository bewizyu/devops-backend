package com.bewizyu.health;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/gafas")
public class GafaController {

    @Autowired
    private CloseableHttpClient client;

    @RequestMapping("/status")
    public List<Status> getCurrentStatus() throws IOException{
        List<Status> status = new ArrayList<>();

        status.add(Status.builder(client)
                .name("Google")
                .url("https://www.google.fr")
                .urlLogo("https://www.google.fr/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png").build());

        status.add(Status.builder(client)
                .name("Amazon")
                .url("https://www.amazon.com")
                .urlLogo("https://www.seeklogo.net/wp-content/uploads/2016/10/amazon-logo-preview.png").build());

        status.add(Status.builder(client)
                .name("Facebook")
                .url("https://www.facebook.com")
                .urlLogo("http://www.valuewalk.com/wp-content/uploads/2015/10/Facebook-logo.png").build());

        status.add(Status.builder(client)
                .name("Apple")
                .url("https://www.apple.com")
                .urlLogo("http://isource.com/wp-content/uploads/2013/07/Apple-logo1.jpg").build());

        return status;
    }


}
