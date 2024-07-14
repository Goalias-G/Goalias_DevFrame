package com.dev.model.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;
@Component
public class MinIOUtil {
    @Resource
    private  MinioClient minioClient;
    public  boolean isExist(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            ExceptionUtil.stacktraceToString(e);
            return false;
        }
    }
    public  String uploadFile(String bucketName, String objectName, FileInputStream file) {
        try {
            minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucketName).object(objectName).build());
            ObjectWriteResponse owr = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file, file.available(), -1)
                    .build());
            return owr.object();
        } catch (Exception e) {
            ExceptionUtil.stacktraceToString(e);
            return null;
        }
    }
    public  void downloadFile(String bucketName, String objectName, String targetFilePath) {
        try {
            minioClient.downloadObject(DownloadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .filename(targetFilePath).build());
        } catch (Exception e) {
            ExceptionUtil.stacktraceToString(e);
        }
    }
    public  void downloadFile2(String bucketName, String objectName, String targetFilePath) {
        try(FileOutputStream fos = new FileOutputStream(targetFilePath)) {
            GetObjectResponse object = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
            FileInputStream fis = new FileInputStream(object.bucket());
            BufferedInputStream bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            ExceptionUtil.stacktraceToString(e);
        }
    }

    public  String getUrl(String bucketName, String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .expiry(60 * 30, TimeUnit.SECONDS)
                    .method(Method.GET)
                    .build());
        } catch (Exception e) {
            ExceptionUtil.stacktraceToString(e);
            return null;
        }
    }

}
