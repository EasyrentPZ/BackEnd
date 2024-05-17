package com.example.easyrent.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;

@Configuration
public class GcsConfiguration
{
    @Bean
    public Storage storage() throws IOException
    {
        Resource resource = new ClassPathResource("easyrent_googlekey.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }
}