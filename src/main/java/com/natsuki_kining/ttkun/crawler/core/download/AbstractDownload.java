package com.natsuki_kining.ttkun.crawler.core.download;

import com.natsuki_kining.ttkun.crawler.common.excption.DownloadException;
import com.natsuki_kining.ttkun.crawler.common.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 下载
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 11:34
 **/
@Slf4j
public abstract class AbstractDownload {

    public void download(String url, String referer, String savePath, String fileName) {
        try {
            log.info("开始下载文件：{}", url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            httpGet.setHeader("Upgrade-Insecure-Requests", "1");
            httpGet.setHeader(HttpHeaders.REFERER, referer);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = response = httpClient.execute(httpGet);
            byte[] btImg = readInputStream(response);//得到资源的二进制数据
            writeImageToDisk(btImg, savePath, fileName);
        } catch (Exception e) {
            log.error("{} 下载失败。", url);
            log.error(e.getMessage(), e);
        }
    }

    private void writeImageToDisk(byte[] img, String filePath, String fileName) {
        FileUtil.createPath(filePath);
        try (OutputStream out = new FileOutputStream(filePath + "\\" + fileName);) {
            out.write(img);
            out.flush();
        } catch (Exception e) {
            throw new DownloadException(e.getMessage(), e);
        }
    }

    /**
     * 从输入流中获取数据
     *
     * @param response 响应数据
     * @return
     * @throws Exception
     */
    private byte[] readInputStream(HttpResponse response) throws Exception {
        String threadName = Thread.currentThread().getName();
        boolean isMainThread = threadName.equals("main");

        Long intSize = response.getEntity().getContentLength();
        byte[] byteArray = null;
        try (InputStream inStream = response.getEntity().getContent(); ByteArrayOutputStream outStream = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[2048];
            int len = 0;
            int outSize = 0;//写入的大小
            double zjs = 0;//中间数
            int count = 0;//进度条个数计数
            if (isMainThread) {
                System.out.println("下载进度：---------------------");
                System.out.print("下载进度：=");
            }
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
                if (isMainThread) {
                    outSize = outStream.toByteArray().length;
                    double p = Math.floor(100 * (outSize / (intSize + 0.01)));
                    if (p > zjs && p % 5 == 0) {
                        System.out.print("=");
                        zjs = p;
                        count++;
                    }
                    if (outSize == intSize) {
                        //补全进度条
                        while (count < 20) {
                            count++;
                            System.out.print("=");
                        }
                        System.out.println();
                    }
                }
            }
            byteArray = outStream.toByteArray();
        } catch (Exception e) {
            throw new DownloadException(e.getMessage(), e);
        }
        return byteArray;
    }
}
