package com.my.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Service;

@Service
@PropertySource("classpath:/properties/myImage.properties")
public class ImageServiceImpl implements ImageService {
	
	@Value("${image.fileDirPath}")
	private String fileDirPath; 
	
	@Value("${image.urlPath}")
	private String urlPath;
	
	@Override
	public String uploadPic(byte[] imgBytes, String fileName) {
		InputStream inputStream = new ByteArrayInputStream(imgBytes);
		MultipartFile fileImage = null;
		try {
			fileImage = new MockMultipartFile(MediaType.APPLICATION_OCTET_STREAM_VALUE.toString(), inputStream);
		} catch (IOException e1) {
			return null;
		}
		fileName = fileName.toLowerCase();
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			return null;
		}
		try {
			BufferedImage image = ImageIO.read(fileImage.getInputStream());
			int width = image.getWidth();
			int height = image.getHeight();
			
			if(width == 0 || height == 0) {
				return null;
			}
			
			String datePath = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now());
			String path = fileDirPath + datePath;
			File file = new File(path);
			if(!file.exists()) {
				file.mkdirs();
			}
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			String realName = uuid + fileType;
			String copyPath = path + "/" + realName;
			File copy = new File(copyPath);
			if(copy.exists()) return null;
			fileImage.transferTo(copy);
			String virtualPath = urlPath + datePath + "/" + realName;
			System.out.println(virtualPath);
			return virtualPath;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String uploadArtPic(byte[] imgBytes, String fileName) {
		return uploadPic(imgBytes, fileName);
	}


}
