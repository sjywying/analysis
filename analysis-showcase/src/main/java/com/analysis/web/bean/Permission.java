package com.analysis.web.bean;

public class Permission {
	
    private Long id;
    private String name;
    private String permission;
    private String description;
    private Boolean isShown;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getIsShown() {
		return isShown;
	}
	public void setIsShown(Boolean isShown) {
		this.isShown = isShown;
	}
}