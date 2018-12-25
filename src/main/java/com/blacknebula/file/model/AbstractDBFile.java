package com.blacknebula.file.model;

public interface AbstractDBFile {
    String getId();

    String getFileName();

    String getFileType();

    byte[] getData();
}