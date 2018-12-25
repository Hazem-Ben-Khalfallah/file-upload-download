package com.blacknebula.file.repository.mysql;

import com.blacknebula.file.model.mysql.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsqlDBFileRepository extends JpaRepository<DBFile, String> {

}