package com.example.test.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {

    @Autowired
    private AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // S3로 업로드
    private String putS3(File uploadFile, String path) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, path+uploadFile.getName(), uploadFile).withCannedAcl(
                CannedAccessControlList.PublicRead));
        return "{ \"result\" : \"success\", \"msg\" : \"success\" }";
    }

    // 폴더 생성
    public String folderAdd(String path){
        try{
            File file = new File(System.getProperty("user.home") + "/" + "/"+"README.md");
            FileOutputStream fos = new FileOutputStream(file,true);

            amazonS3Client.putObject(new PutObjectRequest(bucket, path+"README.md", file).withCannedAcl(
                    CannedAccessControlList.PublicRead));
            file.delete();
            return "{ \"result\" : \"success\", \"msg\" : \"success\" }";
        }catch (IOException e){
            e.printStackTrace();
            return "{ \"result\" : \"error\", \"msg\" : \""+e.getMessage()+"\" }";
        }
    }

    // 파일 삭제
    public String deleteFile(List<String> fileList){

        for(int i=0;i<fileList.size();i++) {
            if (fileList.get(i).split("")[fileList.get(i).split("").length - 1].equals("/")) {
                amazonS3Client.deleteObject(bucket, fileList.get(i) + "README.md");

                ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
                listObjectsRequest.setBucketName(bucket);
                if (!fileList.get(i).equals("")) {
                    listObjectsRequest.setPrefix(fileList.get(i));
                }
                listObjectsRequest.setDelimiter("/");
                ObjectListing s3Objects = amazonS3Client.listObjects(listObjectsRequest);

                List<String> fileList2 = new ArrayList<>();
                for (S3ObjectSummary s3ObjectSummary : s3Objects.getObjectSummaries()) {
                    fileList2.add(s3ObjectSummary.getKey());
                }
                fileList2.addAll(s3Objects.getCommonPrefixes());
                deleteFile(fileList2);
            } else {
                amazonS3Client.deleteObject(bucket, fileList.get(i));
            }
        }
        return "{ \"result\" : \"success\", \"msg\" : \"success\" }";
    }

    // 파일 + 폴더 목록
    public String fileList(String path, String filter){
        String fileList = "";
        fileList += "{\"result\":[";
        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
            listObjectsRequest.setBucketName(bucket);
            if (!path.equals("")) {
                listObjectsRequest.setPrefix(path);
            }
            listObjectsRequest.setDelimiter("/");
            ObjectListing s3Objects;
            int c = 0;
            do {
                s3Objects = amazonS3Client.listObjects(listObjectsRequest);
                // 파일 목록
                for (S3ObjectSummary s3ObjectSummary : s3Objects.getObjectSummaries()) {

                    File file = new File(amazonS3Client.getUrl(bucket,s3ObjectSummary.getKey()).getFile());
                    String fileName = s3ObjectSummary.getKey().split("/")[s3ObjectSummary.getKey().split("/").length-1];
                    if(!fileName.equals("README.md")) {
                        if(fileName.indexOf(filter) == 0) {
                            DateFormat dateFomatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            if (c != 0) {
                                fileList += ",";
                            }
                            fileList += "{ \"name\":\"" + fileName + "\", " +
                                    "\"udate\":\"" + dateFomatter.format(s3ObjectSummary.getLastModified().getTime()) + "\", " +
                                    "\"category\":\"" + URLConnection.guessContentTypeFromName(file.getPath()) + "\", " +
                                    "\"size\":\"" + ((s3ObjectSummary.getSize() / 1024 / 1024) < 1 ? (s3ObjectSummary.getSize() / 1024) + "KB" : ((s3ObjectSummary.getSize() / 1024 / 1024 / 1024) < 1 ? (s3ObjectSummary.getSize() / 1024 / 1024) + "MB" : (s3ObjectSummary.getSize() / 1024 / 1024) + "GB")) + "\"}";
                            c += 1;
                        }
                    }
                }

                // 폴더 목록 가져오기
                for (String commonPrefixes : s3Objects.getCommonPrefixes()) {
                    String folderName;
                    if (path.equals("")) {
                        folderName = commonPrefixes.split("/")[0];
                    } else {
                        folderName = commonPrefixes.split("/")[1];
                    }
                    if(folderName.indexOf(filter) == 0) {
                        if (c != 0) {
                            fileList += ",";
                        }
                        fileList += "{ \"name\":\"" + folderName + "\", ";
                        fileList += "\"udate\":\"\", " +
                                "\"category\":\"\", " +
                                "\"size\":\"\"}";
                        c += 1;
                    }
                }

                listObjectsRequest.setMarker(s3Objects.getNextMarker());
            } while (s3Objects.isTruncated());

            fileList += "]}";

            return fileList;
        }catch (Exception e){
            fileList += "]}";
            return fileList;
        }
    }

    // 폴더 목록만 가져오기
    public String folderList(String path){
        String fileList = "";
        fileList += "{\"result\":[";
        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
            listObjectsRequest.setBucketName(bucket);
            if (!path.equals("")) {
                listObjectsRequest.setPrefix(path);
            }
            listObjectsRequest.setDelimiter("/");
            ObjectListing s3Objects;
            int c = 0;
            do {

                s3Objects = amazonS3Client.listObjects(listObjectsRequest);
                for (String commonPrefixes : s3Objects.getCommonPrefixes()) {
                    if(c != 0){
                        fileList += ",";
                    }

                    if (path.equals("")) {
                        fileList += "\""+commonPrefixes.split("/")[0]+"\"";
                    }else{
                        fileList += "\""+commonPrefixes.split("/")[1]+"\"";
                    }

                    c += 1;
                }
                listObjectsRequest.setMarker(s3Objects.getNextMarker());
            } while (s3Objects.isTruncated());

            fileList += "]}";
            return fileList;
        }catch (Exception e){
            fileList += "]}";
            return fileList;
        }
    }

    // 특정 파일 가져오기
    public URL getFile(String key) {
        try {
            return amazonS3Client.getUrl(bucket,key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
