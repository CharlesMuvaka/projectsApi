package models;

import java.util.Objects;

public class Project{

    private int id;
    private String name, type, status, language_used, url1, url2, url3, created_at, link;

    public Project(String name, String type, String status, String language_used, String url1, String url2, String url3, String created_at, String link) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.language_used = language_used;
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
        this.created_at = created_at;
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(getName(), project.getName()) && Objects.equals(getType(), project.getType()) && Objects.equals(getStatus(), project.getStatus()) && Objects.equals(getLanguage_used(), project.getLanguage_used()) && Objects.equals(getUrl1(), project.getUrl1()) && Objects.equals(getUrl2(), project.getUrl2()) && Objects.equals(getUrl3(), project.getUrl3()) && Objects.equals(getCreated_at(), project.getCreated_at()) && Objects.equals(getLink(), project.getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getStatus(), getLanguage_used(), getUrl1(), getUrl2(), getUrl3(), getCreated_at(), getLink());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLanguage_used() {
        return language_used;
    }

    public void setLanguage_used(String language_used) {
        this.language_used = language_used;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}