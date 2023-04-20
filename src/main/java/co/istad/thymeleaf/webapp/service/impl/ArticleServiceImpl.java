package co.istad.thymeleaf.webapp.service.impl;

import co.istad.thymeleaf.webapp.model.Article;
import co.istad.thymeleaf.webapp.model.FileUpload;
import co.istad.thymeleaf.webapp.repository.ArticleMapper;
import co.istad.thymeleaf.webapp.service.ArticleService;
import co.istad.thymeleaf.webapp.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;
    private final FileUploadService fileUploadService;

    @Override
    public List<Article> findAll() {
        // TODO:
        // Your logic
        return articleMapper.select();
    }

    @Override
    public Article findByUuid(String uuid) {
        return  articleMapper.selectByUuid(uuid);
    }

    @Override
    public boolean save(Article article, MultipartFile file) {
        FileUpload fileUpload = fileUploadService.uploadSingle(file);
        if (fileUpload.isSucceed()) {
            article.setUuid(UUID.randomUUID().toString());
            article.setThumbnail(fileUpload.fileName());
            articleMapper.insert(article);
        }
        return true;
    }
}
