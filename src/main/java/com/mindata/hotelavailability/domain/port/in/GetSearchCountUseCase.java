package com.mindata.hotelavailability.domain.port.in;

import com.mindata.hotelavailability.domain.model.Search;

public interface GetSearchCountUseCase {
    SearchCountResult getCount(String searchId);
    
    class SearchCountResult {
        private final Search search;
        private final long count;
        
        public SearchCountResult(Search search, long count) {
            this.search = search;
            this.count = count;
        }
        
        public Search getSearch() {
            return search;
        }
        
        public long getCount() {
            return count;
        }
    }
}
