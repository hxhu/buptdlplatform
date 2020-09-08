package com.bupt.dlplatform.util;

import cn.hutool.extra.ftp.Ftp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FtpUtil {
    private FTPClient ftpClient;
    private boolean is_connected;

    public FtpUtil(){
        ftpClient = new FTPClient();
        is_connected = false;
    }
    public FtpUtil (int defaultTimeoutSecond, int connectTimeoutSecond, int dataTimeoutSecond){
        ftpClient = new FTPClient();
        is_connected = false;
        ftpClient.setDefaultTimeout(defaultTimeoutSecond*1000);
        ftpClient.setConnectTimeout(connectTimeoutSecond*1000);
        ftpClient.setDataTimeout(dataTimeoutSecond*1000);

    }

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;


    public void initFtpClient() {

        ftpClient.setControlEncoding("utf-8");
        try {
            log.info("connecting...ftp服务器:" + host + ":" + 21);
            if(!ftpClient.isConnected()){
                ftpClient.connect(host, 21);
            }
            //Check response after connection attempt
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                disconnect();
                log.info("connect failed...ftp服务器:" + host + ":" + 21);
            }
            log.info("connect successful...ftp服务器:" + host + ":" + 21);
            //Login.
            if(!ftpClient.login(username, password)){
                is_connected = false;
                disconnect();
                log.info("Can't login to FTP server");

            }else {
                is_connected=true;
            }
            // Set data tramsfer mode.
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Disconnects from the FTP server
     */
    public void disconnect() throws IOException{
        if(!ftpClient.isConnected()){
            try{
                ftpClient.logout();
                ftpClient.disconnect();
                is_connected=false;
            }catch (IOException e){

            }
        }
    }


    /**
     * 上传文件
     *
     * @param pathname    ftp服务保存地址
     * @param fileName    上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    public boolean uploadFile(String pathname, String fileName, InputStream inputStream) {
        boolean flag = false;
        initFtpClient();
        try {
            log.info("开始上传文件");
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            CreateDirecroty(pathname);
            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
            log.info("上传文件成功");
        } catch (Exception e) {
            log.info("上传文件失败");
            e.printStackTrace();

        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


    //改变目录路径
    public boolean changeWorkingDirectory(String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                log.info("进入文件夹" + directory + " 成功！");

            } else {
                log.info("进入文件夹" + directory + " 失败！开始创建文件夹");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }

    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public boolean CreateDirecroty(String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        log.info("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    //判断ftp服务器文件是否存在
    public boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    //创建目录
    public boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                log.info("创建文件夹" + dir + " 成功！");

            } else {
                log.info("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 下载文件 *
     *
     * @return
     */
    public boolean downloadFile(String pathname, String localpath) {
        boolean flag = false;
        OutputStream os = null;
        try {
            log.info("开始下载文件");
            initFtpClient();

            // Use passive mode to pass firewalls.
            ftpClient.enterLocalPassiveMode();

            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                File localFile = new File(localpath + "/" + file.getName());
                os = new BufferedOutputStream(new FileOutputStream(localFile));
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.retrieveFile(file.getName(), os);
                os.close();

            }
            ftpClient.logout();
            flag = true;
            log.info("下载文件成功");
        } catch (Exception e) {
            log.info("下载文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 删除文件 *
     *
     * @param pathname FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return
     */
    public boolean deleteFile(String pathname, String filename) {
        boolean flag = false;
        try {
            log.info("开始删除文件");
            //切换FTP目录
            initFtpClient();
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
            log.info("删除文件成功");
        } catch (Exception e) {
            log.info("删除文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     *
     * @param resultpath
     * @param picName
     * @return
     */

    public byte[] getPicStream(String resultpath, String picName){
        try{
            log.info("开始获取图片数据流");
            initFtpClient();
            InputStream image=null;
            //String dir = new String(resultpath.getBytes("GBK"), "iso-8859-1");
            if(!ftpClient.changeWorkingDirectory(resultpath)){
                log.info("切换目录失败"+resultpath);
            }
            // 一定要加上字符集指定，因为获取文件时有中文，会出现乱码而获取不到。
            String fileName = new String(picName.getBytes("GBK"), "iso-8859-1");
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            // 每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据，ftp server可能每次开启不同的端口来传输数据，
            // 但是在Linux上，由于安全限制，可能某些端口没有开启，所以就出现阻塞。
            ftpClient.enterLocalPassiveMode();
            image = ftpClient.retrieveFileStream(fileName);

            byte[] bytes = IOUtils.toByteArray(image);
            if (image != null) {
                image.close();
            }
            ftpClient.completePendingCommand();

            return bytes;
        }catch (Exception e){
            log.info("读取图片流失败");
            e.printStackTrace();
        }finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public List<Map<String, byte[]>> getPicMap(String picpath) {

        List<Map<String, byte[]>> picList = new ArrayList<>();

        try{
            log.info("开始获取图片列表");
            initFtpClient();
            if(!ftpClient.changeWorkingDirectory(picpath)){
                log.info("切换目录失败"+picpath);
            }
            ftpClient.changeWorkingDirectory(picpath);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for(FTPFile file : ftpFiles){
                InputStream image=null;
                Map<String, byte[]> picMap = new HashMap<String, byte[]>();
                String filename = file.getName();
                ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
                // 每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据，ftp server可能每次开启不同的端口来传输数据，
                // 但是在Linux上，由于安全限制，可能某些端口没有开启，所以就出现阻塞。
                ftpClient.enterLocalPassiveMode();
                image = ftpClient.retrieveFileStream(filename);
                byte[] bytes = IOUtils.toByteArray(image);

                picMap.put(filename,bytes);
                picList.add(picMap);
                if (image != null) {
                    image.close();
                }
                ftpClient.completePendingCommand();
                log.info(String.valueOf(ftpClient.getReplyCode()));
            }

        } catch (Exception e){
            log.info("读取图片流失败");
            e.printStackTrace();
        }finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

        }
        return picList;
    }


    /**
     *
     * @param directory
     * @return
     */
    public InputStream getPicStream(String directory) {

        try {
            log.info("开始获取图片数据流");
            initFtpClient();
            InputStream image = null;
            ftpClient.changeWorkingDirectory(directory);

            FTPFile[] ftpFiles = ftpClient.listFiles();

            String filename = ftpFiles[0].getName();
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            image = ftpClient.retrieveFileStream(filename);
            //ftpClient.logout();
            ftpClient.disconnect();
            return image;
        } catch (Exception e) {
            log.info("读取图片流失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取图片所有路径
     *
     * @param resultlocation FTP服务器保存目录
     * @return
     */
    public List<String> getPicPath(String resultlocation) {

        List<String> listpath = new ArrayList();
        ;
        try {
            log.info("开始获取图片路径列表");
            initFtpClient();
            //切换FTP 目录
            ftpClient.changeWorkingDirectory(resultlocation);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                String filename = file.getName();
                String pathOne = resultlocation + filename;
                listpath.add(pathOne);
            }
            ftpClient.logout();
        } catch (Exception e) {
            log.info("获取路径列表失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return listpath;
    }


}

