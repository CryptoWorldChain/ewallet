package com.fr.chain.web.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 主菜单信息
 * 
 */
public @Data class MenuInfo {
	private String name;
	private String icon;
	private String url;
	private List<MenuInfo> children = new ArrayList<>();
}
