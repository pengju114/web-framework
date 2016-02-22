/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pj.admin.beans;

import com.pj.jdbc.annotation.Column;
import com.pj.jdbc.annotation.Table;
import java.util.Date;

/**
 *
 * @author luzhenwen
 */
@Table(name = "t_article")
public class Article {
    @Column(name = "article_id", save = false)
    private Integer articleId;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "article_title")
    private String articleTitle;
    @Column(name = "article_subtitle")
    private String articleSubtitle;
    @Column(name = "article_url")
    private String articleUrl;
    @Column(name = "article_author_id")
    private Integer articleAuthorId;
    @Column(name = "article_author_name")
    private String articleAuthorName;
    @Column(name = "article_create_date")
    private Date articleCreateDate;
    @Column(name = "article_content")
    private String articleContent;
    @Column(name = "article_keywords")
    private String articleKeywords; 
    @Column(name = "article_abstract")
    private String articleAbstract; 
    @Column(name = "article_show_flag")
    private Integer articleShowFlag;
    @Column(name = "article_level")
    private Integer articleLevel;
    @Column(name = "article_publisher_id")
    private Integer articlePublisherId;
    @Column(name = "article_publish_date")
    private Date articlePublishDate;
    @Column(name = "article_comment_flag")
    private Integer articleCommentFlag; 
    @Column(name = "article_status")
    private Integer articleStatus;
    @Column(name = "article_last_modify_date")
    private Date articleLastModifyDate;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public Integer getArticleAuthorId() {
        return articleAuthorId;
    }

    public void setArticleAuthorId(Integer articleAuthorId) {
        this.articleAuthorId = articleAuthorId;
    }

    public String getArticleAuthorName() {
        return articleAuthorName;
    }

    public void setArticleAuthorName(String articleAuthorName) {
        this.articleAuthorName = articleAuthorName;
    }

    public Integer getArticleCommentFlag() {
        return articleCommentFlag;
    }

    public void setArticleCommentFlag(Integer articleCommentFlag) {
        this.articleCommentFlag = articleCommentFlag;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Date getArticleCreateDate() {
        return articleCreateDate;
    }

    public void setArticleCreateDate(Date articleCreateDate) {
        this.articleCreateDate = articleCreateDate;
    }

    public String getArticleKeywords() {
        return articleKeywords;
    }

    public void setArticleKeywords(String articleKeywords) {
        this.articleKeywords = articleKeywords;
    }

    public Date getArticleLastModifyDate() {
        return articleLastModifyDate;
    }

    public void setArticleLastModifyDate(Date articleLastModifyDate) {
        this.articleLastModifyDate = articleLastModifyDate;
    }

    public Integer getArticleLevel() {
        return articleLevel;
    }

    public void setArticleLevel(Integer articleLevel) {
        this.articleLevel = articleLevel;
    }

    public Date getArticlePublishDate() {
        return articlePublishDate;
    }

    public void setArticlePublishDate(Date articlePublishDate) {
        this.articlePublishDate = articlePublishDate;
    }

    /**
     * @return the categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the articleTitle
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * @param articleTitle the articleTitle to set
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * @return the articleSubtitle
     */
    public String getArticleSubtitle() {
        return articleSubtitle;
    }

    /**
     * @param articleSubtitle the articleSubtitle to set
     */
    public void setArticleSubtitle(String articleSubtitle) {
        this.articleSubtitle = articleSubtitle;
    }

    /**
     * @return the articleUrl
     */
    public String getArticleUrl() {
        return articleUrl;
    }

    /**
     * @param articleUrl the articleUrl to set
     */
    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    /**
     * @return the articleShowFlag
     */
    public Integer getArticleShowFlag() {
        return articleShowFlag;
    }

    /**
     * @param articleShowFlag the articleShowFlag to set
     */
    public void setArticleShowFlag(Integer articleShowFlag) {
        this.articleShowFlag = articleShowFlag;
    }

    /**
     * @return the articlePublisherId
     */
    public Integer getArticlePublisherId() {
        return articlePublisherId;
    }

    /**
     * @param articlePublisherId the articlePublisherId to set
     */
    public void setArticlePublisherId(Integer articlePublisherId) {
        this.articlePublisherId = articlePublisherId;
    }

    /**
     * @return the articleStatus
     */
    public Integer getArticleStatus() {
        return articleStatus;
    }

    /**
     * @param articleStatus the articleStatus to set
     */
    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }
    
    
}
