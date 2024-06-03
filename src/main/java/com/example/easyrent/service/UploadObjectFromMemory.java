package com.example.easyrent.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UploadObjectFromMemory
{
    @Value("${google.bucket.name}")
    String bucketName;
    private final Storage storage;
    public void uploadImageToGCS(String fileName, byte[] imageData)
    {

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, imageData);
    }

    public void removeImageFromGCS(String fileName)
    {
        BlobId blobId = BlobId.of(bucketName, extractFileNameFromUrl(fileName));
        boolean deleted = storage.delete(blobId);
        if (!deleted)
            throw new RuntimeException("Failed to delete file");
    }

    private String extractFileNameFromUrl(String url)
    {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}