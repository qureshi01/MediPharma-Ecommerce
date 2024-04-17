package com.example.utility;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

public interface StorageService {

	List<String> loadAll();
	Resource load(String fileName);
	void delete(String fileName);
	
}
