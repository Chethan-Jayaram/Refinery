
package com.application.refinary.pojo.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("articleSource")
    @Expose
    private String articleSource;
    @SerializedName("articleAuthor")
    @Expose
    private Object articleAuthor;
    @SerializedName("articleTitle")
    @Expose
    private String articleTitle;
    @SerializedName("articleDescription")
    @Expose
    private String articleDescription;
    @SerializedName("articleURL")
    @Expose
    private String articleURL;
    @SerializedName("articleCoverImage")
    @Expose
    private String articleCoverImage;
    @SerializedName("articlePublishedAt")
    @Expose
    private String articlePublishedAt;

    public String getArticleSource() {
        return articleSource;
    }

    public void setArticleSource(String articleSource) {
        this.articleSource = articleSource;
    }

    public Object getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(Object articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public String getArticleCoverImage() {
        return articleCoverImage;
    }

    public void setArticleCoverImage(String articleCoverImage) {
        this.articleCoverImage = articleCoverImage;
    }

    public String getArticlePublishedAt() {
        return articlePublishedAt;
    }

    public void setArticlePublishedAt(String articlePublishedAt) {
        this.articlePublishedAt = articlePublishedAt;
    }

}
