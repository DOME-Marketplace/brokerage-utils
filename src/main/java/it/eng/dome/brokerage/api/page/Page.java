package it.eng.dome.brokerage.api.page;

import java.util.List;

public class Page<T> {

	private List<T> content;
    private int offset;
    private int limit;
    private boolean hasNext;
    
    public Page(List<T> content, int offset, int limit, boolean hasNext) {
        this.content = content;
        this.offset = offset;
        this.limit = limit;
        this.hasNext = hasNext;
    }
    
    public List<T> getContent() {
		return content;
	}

	public int getOffset() {
		return offset;
	}

	public int getLimit() {
		return limit;
	}

	public boolean hasNext() {
		return hasNext;
	}

}
