package cn.meshed.cloud.doc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>Knife4j 配置信息</h1>
 *
 * @author Vincent Vic
 * @version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "knife4j.doc")
public class Knife4jProperties {

    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;
    /**
     * 服务Url
     */
    private String serviceUrl;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 联系人名称
     */
    private String contactName;
    /**
     * 联系人URL
     */
    private String contactUrl;
    /**
     * 联系人Email
     */
    private String contactEmail;
    /**
     * 版本
     */
    private String version;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Knife4jProperties{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", serviceUrl='" + serviceUrl + '\'' +
                ", groupName='" + groupName + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactUrl='" + contactUrl + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
