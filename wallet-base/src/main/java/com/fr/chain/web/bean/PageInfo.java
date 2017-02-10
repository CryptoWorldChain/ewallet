package com.fr.chain.web.bean;

import lombok.Getter;
import lombok.Setter;

public class PageInfo {

	private @Setter boolean page;
	private @Setter Integer skip;
	private @Setter Integer limit;
	private @Setter @Getter boolean sortModed;
	private @Setter String sort;
	public Integer getSkip() {
		return skip==null?0:skip;
	}
	public Integer getLimit() {
		return limit==null?Integer.MAX_VALUE:limit;
	}
	public String getSort() {
		return sort;
	}
	public boolean isPage() {
		return page;
	}
	
}
