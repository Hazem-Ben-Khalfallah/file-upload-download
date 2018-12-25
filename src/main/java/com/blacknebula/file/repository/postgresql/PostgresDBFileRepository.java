package com.blacknebula.file.repository.postgresql;

import com.blacknebula.file.model.postgresql.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresDBFileRepository extends JpaRepository<DBFile, String> {

}