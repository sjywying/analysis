package com.analysis.web.bean;

public class Role {
	
    private Long id;
    private String name;
    private String role;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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