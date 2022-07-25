package models;

import java.util.Objects;

public class Project{

    private int id;
    private String name, type, url, link;

    public Project(String name, String type, String url, String link) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(getName(), project.getName()) && Objects.equals(getType(), project.getType()) && Objects.equals(getUrl(), project.getUrl()) && Objects.equals(getLink(), project.getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getUrl(), getLink());
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}