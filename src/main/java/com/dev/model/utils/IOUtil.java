package com.dev.model.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.dev.model.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Component
public class IOUtil {
    @Resource
    private  MinioClient minioClient;

    @Resource
    private MinioConfig minioConfig;

    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);
    public  boolean isBucketExist(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            ExceptionUtil.stacktraceToString(e);
            return false;
        }
    }
    public String uploadFile(String bucketName, String objectName, InputStream file) {
        try (file) {
            ObjectWriteResponse owr = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file, file.available(), -1)
                    .build());
            return owr.object();
        } catch (Exception e) {
            logger.error(ExceptionUtil.stacktraceToString(e));
            return null;
        }
    }

    public String uploadFile(String bucketName, String objectName, String fileData) {
        try {
            ObjectWriteResponse owr = minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .filename(fileData)
                            .build());
            return owr.object();
        } catch (Exception e) {
            ExceptionUtil.stacktraceToString(e);
            return null;
        }
    }

    public boolean isObjectExist(String bucketName, String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
            return true;
        } catch (Exception e) {
            logger.info("MinIO对象不存在: {}/{}", bucketName, objectName);
            return false;
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

    public void delFile(String bucketName, java.util.List<String> objectNames) {
        List<DeleteObject> deleteObjects = new ArrayList<>();
        objectNames.forEach(objectName -> {
            deleteObjects.add(new DeleteObject(objectName));
        });
        try {
            boolean bucketExist = isBucketExist(bucketName);
            if (!bucketExist){
                return;
            }
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(RemoveObjectsArgs.builder()
                    .bucket(bucketName)
                    .objects(deleteObjects)
                    .build());
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                logger.error("minio 删除错误 {}；{}" , error.objectName() , error.message());
            }
        } catch (Exception e) {
            logger.error(ExceptionUtil.stacktraceToString(e));
        }
    }

    public  String getUrl(String bucketName, String objectName) {
        try {
            return minioConfig.getEndpoint() + "/" + bucketName + "/" + objectName;
        } catch (Exception e) {
            ExceptionUtil.stacktraceToString(e);
            return null;
        }
    }

    /**
     * 获取文件扩展名*
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getExtension(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i < 0) return null;

        return fileName.substring(i+1);
    }

    /**
     * 获取文件扩展名*
     * @param file 文件对象
     * @return 扩展名
     */
    public static String getExtension(File file) {
        if (file == null) return null;

        if (file.isDirectory()) return null;

        String fileName = file.getName();
        return getExtension(fileName);
    }
    /**
     * png图片转jpg*
     * @param pngImage png图片对象
     * @param jpegFile jpg图片对象
     * @return 转换是否成功
     */
    public static boolean png2jpeg(File pngImage, File jpegFile) {
        BufferedImage bufferedImage;

        try {
            bufferedImage = ImageIO.read(pngImage);

            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            ImageIO.write(bufferedImage, "jpg", jpegFile);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 判断文件是否是图片*
     * @param imgFile 文件对象
     * @return
     */
    public static boolean isImage(File imgFile) {
        try {
            BufferedImage image = ImageIO.read(imgFile);
            return image != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据系统当前时间，返回时间层次的文件夹结果，如：upload/2015/01/18/0.jpg
     * @return
     */
    public static String getTimeFilePath(){
        return new SimpleDateFormat("/yyyy/MM/dd").format(new Date())+"/";
    }
    /**
     * 将文件头转换成16进制字符串
     *
     * @param src 原生byte
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 得到文件头
     *
     * @param file 文件
     * @return 文件头
     * @throws IOException
     */
    private static String getFileContent(File file) throws IOException {

        byte[] b = new byte[28];

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }


}
