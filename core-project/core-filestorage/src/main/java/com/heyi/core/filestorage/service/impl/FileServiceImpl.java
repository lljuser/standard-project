package com.heyi.core.filestorage.service.impl;

import com.heyi.core.common.PageQuery;
import com.heyi.core.common.PageResult;
import com.heyi.core.common.UUIDHexGenerator;
import com.heyi.core.filestorage.entity.FsFile;
import com.heyi.core.filestorage.entity.FsFolder;
import com.heyi.core.filestorage.repository.FileRepository;
import com.heyi.core.filestorage.repository.FolderRepository;
import com.heyi.core.filestorage.service.FileService;
import com.heyi.core.filestorage.util.FileUtil;
import com.heyi.core.filestorage.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FolderRepository folderRepository;

    // 设置根节点信息
    private final String PARENT_NODE = UUIDHexGenerator.generateEmpty();

    @Value("${app.file.rootPath}")
    String rootPath;

    // 排序节点
    @Value("${app.endpoints.order:127.0.0.1}:${app.ports.file:8103}")
    String orderNode;

    public List<FsFile> getAll() {
        return fileRepository.findAll();
    }

    public PageResult<FsFile> getAll(PageQuery pageQuery, final FsFile fsFile) {
        int pageNumber = pageQuery.getPageNumber() - 1 < 0 ? 0 : pageQuery.getPageNumber() - 1;
        // 查询的信息按照升序显示
        Pageable pageable = PageRequest.of(pageNumber, pageQuery.getPageSize());
        Page<FsFile> filePage;
        // 设置查询的信息为空，则返回根节点的文件
        if (fsFile == null) {
            filePage = fileRepository.getAll(PARENT_NODE, pageable);
        } else {
            // 查询的姓名
            String name = "";
            if (!StringUtils.isEmpty(fsFile.getName())) {
                name = fsFile.getName();
            }
            // 查询的父节点
            String folderId = PARENT_NODE;
            if (!ObjectUtils.isEmpty(fsFile.getFolder())) {
                folderId = fsFile.getFolder().getId();
            }
            filePage = fileRepository.getAll(folderId, name, pageable);
        }
        return PageResult.build(filePage);
    }

    @Override
    public boolean deleteFile(String code) {
        try {
            // 查找到文件信息
            Optional<FsFile> data = fileRepository.findFsFileByCode(code);
            if (!data.isPresent()) return false;
            FsFile fsFile = data.get();
            // 将数据库中的信息删除，再将本地的文件删除
            fileRepository.delete(fsFile);
            if (!ObjectUtils.isEmpty(fsFile)) {
                String path = FileUtil.getFilePath(rootPath, fsFile);
                FileUtil.deleteFile(path);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public FsFile saveFile(MultipartFile file, String folderId, HttpServletRequest request) {
        try {
            // 获取上传信息
            FsFile uploadFile = getUploadFile(file, folderId, request);

            // 获取文件夹信息
            Optional<FsFolder> fsFolder = folderRepository.findById(folderId);
            if (fsFolder.isPresent())
                uploadFile.setFolder(fsFolder.get());
            else
                return null;

            // 保存信息
            fileRepository.save(uploadFile);

            // 上传文件名
            String filePath = FileUtil.getFilePath(rootPath, uploadFile);
            // 保存文件
            FileUtil.saveFile(file, filePath);
            return uploadFile;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 根据文件名和后缀判断是否有相同的文件
     *
     * @param name
     * @param extName
     * @return
     */
    private FsFile findByNameAndExtName(String name, String extName) {
        // 判断文件是否已经存过了(文件名和后缀名)
        Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "id");
        Page<FsFile> allFile = fileRepository.findAll((Specification) (root, criteriaQuery, criteriaBuilder) -> {
            // 设置查询条件（按姓名查找相似的文件）
            List<Predicate> list = new ArrayList<>();
            if (!StringUtils.isEmpty(name)) {
                list.add(criteriaBuilder.equal(root.get("name").as(String.class), name));
            }
            if (!StringUtils.isEmpty(extName)) {
                list.add(criteriaBuilder.equal(root.get("extName").as(String.class), extName));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        }, pageable);
        return ObjectUtils.isEmpty(allFile.getContent()) ? null : allFile.getContent().get(0);
    }

    /**
     * 获取上传的文件信息
     *
     * @param file
     * @param folderId
     * @return
     */
    private FsFile getUploadFile(MultipartFile file, String folderId, HttpServletRequest request) {
        // 获取文件的基本信息
        String name = FileUtil.getFileName(file);
        String extName = FileUtil.getFileSuffix(file);
        FsFile saveFile = findByNameAndExtName(name, extName);
        // 要上传的文件
        FsFile uploadFile = new FsFile();
        uploadFile.setEncryption(FileUtil.getFileEncryption(file));
        uploadFile.setCode(FileUtil.getFileCode());
        uploadFile.setExtName(extName);
        uploadFile.setSize(FileUtil.getFileSize(file));
        uploadFile.setName(name);
        uploadFile.setUploadIp(RequestUtil.getClientIpAddress(request));
        // 文件未确认
        uploadFile.setStatus(0);
        // 没有相同的文件
        if (ObjectUtils.isEmpty(saveFile)) {
            uploadFile.setVersion("1");
        } else {
            uploadFile.setId(uploadFile.getId());
            uploadFile.setVersion(FileUtil.getFileVersion(saveFile));
        }
//        FsFolder folder = new FsFolder();
//        folder.setId( folderId );
//        uploadFile.setFolder( folder );
        return uploadFile;
    }

    @Override
    public String getFsFileByCode(String code) {
        try {
            // 查找到文件信息
            Optional<FsFile> data = fileRepository.findFsFileByCode(code);
            if (!data.isPresent()) return "";
            FsFile fsFile = data.get();
            if (!ObjectUtils.isEmpty(fsFile))
                return FileUtil.getFilePath(rootPath, fsFile);
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void updateFileStatus(String fileCode) throws Exception {
        try {
            // 查找到文件信息
            Optional<FsFile> data = fileRepository.findFsFileByCode(fileCode);
            if (!data.isPresent()) return;
            FsFile fsFile = data.get();
            if (fsFile.getStatus() == FsFile.Status.UNCONFIRMED.getValue()) {
                fsFile.setStatus(FsFile.Status.CONFIRMED.getValue());
            }
            fileRepository.save(fsFile);
        } catch (IllegalArgumentException e) {
            throw new Exception("file code 不能为空");
        } catch (EmptyResultDataAccessException e) {
            throw new Exception("未找到文件");
        }
    }

    @Override
    public FsFile findFsFileByCode(String fileCode) {
        Optional<FsFile> file = fileRepository.findFsFileByCode(fileCode);
        if (file.isPresent()) return file.get();
        return null;
    }

}
