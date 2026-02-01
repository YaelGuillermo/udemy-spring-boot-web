package com.ideapool.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
    private String url;
    private Page<T> page;
    
    private int totalPages;
    
    private int numElementsPerPage;
    private int currentPage;
    
    private List<PageItem> pages;
    
    private final int PAGES_TO_SHOW = 5;
    
    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.pages = new ArrayList<PageItem>();
        
        numElementsPerPage = page.getSize();
        totalPages = page.getTotalPages();
        currentPage = page.getNumber() + 1;
        
        int from, to;
        
        if (totalPages <= PAGES_TO_SHOW) {
            from = 1;
            to = totalPages;
        } else {
            int halfPagesToShow = PAGES_TO_SHOW / 2;
            
            if (currentPage <= halfPagesToShow) {
                from = 1;
                to = PAGES_TO_SHOW;
            } else if (currentPage >= totalPages - halfPagesToShow) {
                from = totalPages - PAGES_TO_SHOW + 1;
                to = totalPages;
            } else {
                from = currentPage - halfPagesToShow;
                to = currentPage + halfPagesToShow;
            }
        }
        
        for (int i = from; i <= to; i++) {
            pages.add(new PageItem(i, currentPage == i));
        }
    }

    public String getUrl() {
        return url;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<PageItem> getPages() {
        return pages;
    }
    
    public boolean isFirst() {
        return page.isFirst();
    }
    
    public boolean isLast() {
        return page.isLast();
    }
    
    public boolean isHasNext() {
        return page.hasNext();
    }
    
    public boolean isHasPrevious() {
        return page.hasPrevious();
    }
    
    public boolean hasContent() {
        return page.hasContent();
    }
    
    public long getTotalElements() {
        return page.getTotalElements();
    }
}