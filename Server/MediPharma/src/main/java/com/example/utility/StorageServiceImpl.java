package com.example.utility;

import java.io.File;
import java.util.Arrays;
import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
public class StorageServiceImpl implements StorageService {

	private String BASEPATH;
	
	
	@Override
	public List<String> loadAll() {
		File dirPath = new File(BASEPATH);
		return Arrays.asList(dirPath.list());
	}


	@Override
	public Resource load(String fileName) {
		File filePath = new File(BASEPATH, fileName);
		if(filePath.exists())
			return new FileSystemResource(filePath);
		return null;
	}

	@Override
	public void delete(String fileName) {
		File filePath = new File(BASEPATH, fileName);
		if(filePath.exists())
			filePath.delete();
	}

}
