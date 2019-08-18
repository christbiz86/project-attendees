package com.attendee.attendee.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageService {

    void init();

    void store(MultipartFile file,String name);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

	void deleteOne(String name) throws IOException;

}
