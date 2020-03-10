package com.my.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	String uploadPic(byte[] imgBytes, String fileName);

	String uploadArtPic(byte[] imgBytes, String fileName);

}
