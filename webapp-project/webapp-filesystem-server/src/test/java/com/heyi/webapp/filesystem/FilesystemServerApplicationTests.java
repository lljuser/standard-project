package com.heyi.webapp.filesystem;

import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import com.heyi.core.filestorage.entity.FsFile;
import com.heyi.core.filestorage.entity.FsFolder;
import com.heyi.core.filestorage.repository.FileRepository;
import com.heyi.core.filestorage.service.BcTempFileTransactionService;
import com.heyi.core.filestorage.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilesystemServerApplicationTests {

    @Autowired
    private FileService fileService;

    @Test
    public void contextLoads() {
        FsFile fsFile = new FsFile();
        // fsFile.setName( "ww" );
        FsFolder fsFolder = new FsFolder();
        fsFolder.setId( "ss" );
        fsFile.setFolder( fsFolder );
        PageResult<FsFile> data = fileService.getAll( new PageQuery(), fsFile );
        System.out.println( "结果是： ====== " );
        data.getContent().forEach( System.out::println );
    }

    @Autowired
    private BcTempFileTransactionService bcTempFileTransactionService;

    @Test
    public void testTx() {
        bcTempFileTransactionService.updateStatusByTxId( "8a81831b65898dd00165898dd0960000" );
    }

    @Test
    public void testGetTx() {
        Object data = bcTempFileTransactionService.getTxInfo( "8a818318658f4b7e01658f4b7e580000" );
        System.out.println( data );
    }

    @Autowired
    private FileRepository fileRepository;

    @Test
    public void testCode() {
        Optional<FsFile> file = fileRepository.findFsFileByCode( "377de6bf-a03d-4eba-b068-0677d64d5f39" );
    }

    @Test
    public void testBTX() {
        System.out.println( "----- = " + ObjectUtils.isEmpty( bcTempFileTransactionService.getByFileCode( "4ea9bed8-2f3b-4d1a-a0b6-11254172d6cd" ) ) );
    }

}
