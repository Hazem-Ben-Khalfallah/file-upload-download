package com.blacknebula.file.service;

import com.blacknebula.file.exception.FileStorageException;
import com.blacknebula.file.exception.MyFileNotFoundException;
import com.blacknebula.file.model.AbstractDBFile;
import com.blacknebula.file.model.DBStorageEnum;
import com.blacknebula.file.repository.mysql.MsqlDBFileRepository;
import com.blacknebula.file.repository.postgresql.PostgresDBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DBStorageService {

    @Autowired
    private MsqlDBFileRepository msqlDbFileRepository;
    @Autowired
    private PostgresDBFileRepository postgresDBFileRepository;

    public AbstractDBFile storeFile(MultipartFile file, DBStorageEnum dbStorageEnum) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }


            if (DBStorageEnum.MYSQL.equals(dbStorageEnum)) {
                final com.blacknebula.file.model.mysql.DBFile dbFile = new com.blacknebula.file.model.mysql.DBFile(fileName, file.getContentType(), file.getBytes());
                return msqlDbFileRepository.save(dbFile);
            }
            final com.blacknebula.file.model.postgresql.DBFile dbFile = new com.blacknebula.file.model.postgresql.DBFile(fileName, file.getContentType(), file.getBytes());
            return postgresDBFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public AbstractDBFile getFile(String fileId, DBStorageEnum dbStorageEnum) {
        if (DBStorageEnum.MYSQL.equals(dbStorageEnum)) {
            return msqlDbFileRepository.findById(fileId)
                    .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
        }
        return postgresDBFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}