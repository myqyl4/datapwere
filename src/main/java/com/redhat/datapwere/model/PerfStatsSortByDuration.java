package com.redhat.datapwere.model;

import java.util.Comparator;

public class PerfStatsSortByDuration  implements Comparator<PerfStats> {
	
	
    public int compare(PerfStats a, PerfStats b) {
        if ( a.getDuration() < b.getDuration() ) return -1;
        else if ( a.getDuration() == b.getDuration() ) return 0;
        else return 1;
    }
    
    
}
