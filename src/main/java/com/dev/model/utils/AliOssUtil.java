package com.dev.model.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.dev.model.context.BizException;
import com.dev.model.properties.AliOssProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Slf4j
public class AliOssUtil implements InitializingBean, DisposableBean {

    private OSS ossClient;

    static List<String> imageSuffixes = List.of(".jpg", ".jpeg", ".png", ".webp");

    static List<String> suffixes = List.of(".jpg", ".jpeg", ".png", ".webp", ".mp4", ".mkv", ".xls", ".xlsx", ".doc", ".docx", ".zip");

    @Resource
    private AliOssProperties aliOssProperties;


    @Override//    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        this.ossClient = new OSSClientBuilder().build(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret());
    }

    @Override// @PreDestroy
    public void destroy() throws Exception {
        ossClient.shutdown();
    }


    public String upload(InputStream inputStream, String objectName) {
        try {
            // 创建PutObject请求。
            ossClient.putObject(aliOssProperties.getBucketName(), objectName, inputStream);
        } catch (OSSException oe) {
            log.warn("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.warn("Error Message:{}", oe.getErrorMessage());
            log.warn("Error Code:{}", oe.getErrorCode());
            log.warn("Request ID:{}", oe.getRequestId());
            log.warn("Host ID:{}", oe.getHostId());
        } catch (ClientException ce) {
            log.warn("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.warn("Error Message:{}", ce.getMessage());
        }

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(aliOssProperties.getBucketName())
                .append(".")
                .append(aliOssProperties.getEndpoint())
                .append("/")
                .append(objectName);
        log.info("文件上传到:{}", stringBuilder);

        return stringBuilder.toString();
    }

    public String uploadImage(Integer mark, InputStream file, String ext) throws IOException {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String key = mark.toString() + "/image/" + uuid + ext;
        return upload(file, key);
    }

    private static void checkImageName(String fileName) {
        if (Objects.isNull(fileName) || !fileName.contains(".") || !imageSuffixes.contains(fileName.substring(fileName.lastIndexOf(".")).toLowerCase())) {
            log.warn("校验文件格式失败，文件名：{}", fileName);
            throw new BizException("文件格式只支持：" + imageSuffixes + "格式");
        }
    }

    public static void checkFileSuffix(String fileName) {
        if (Objects.isNull(fileName) || !fileName.contains(".") || !suffixes.contains(fileName.substring(fileName.lastIndexOf(".")).toLowerCase())) {
            log.warn("校验文件格式失败，文件名：{}", fileName);
            throw new BizException("文件格式只支持：" + suffixes + "格式");
        }
    }

}
