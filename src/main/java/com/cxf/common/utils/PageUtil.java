package com.cxf.common.utils;

import java.io.Serializable;
import java.util.List;

/**
 * paginationツール
 *
 * @author cxf
 */
public class PageUtil implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * リスト総数
	 */
	private int totalCount;
	/**
	 *　現在のページ
	 */
	private int curPage;
	/**
	 * ページごとに表す数
	 */
	private int pageSize;
	/**
	 * ページ総数
	 */
	private int totalPage;
	/**
	 * 結果リスト
	 */
	private List<?> list;

//	/**
//	 * 分页
//	 * @param list        結果リスト
//	 * @param totalCount  リスト総数
//	 * @param pageSize    ページごとに表す数
//	 * @param curPage    現在のページ
//	 */
//	public PageUtil(List<?> list, int totalCount, int pageSize, int curPage) {
//		this.list = list;
//		this.totalCount = totalCount;
//		this.pageSize = pageSize;
//		this.curPage = curPage;
//		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
//	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @return the curPage
	 */
	public int getCurPage() {
		return curPage;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @return the list
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @param curPage the curPage to set
	 */
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<?> list) {
		this.list = list;
	}
	
	

}
