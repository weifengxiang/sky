package org.sky.sys.utils;

import java.util.Comparator;

public class ComparatorTS implements Comparator {
	@Override
	public int compare(Object o1,Object o2){
		TreeStru t1=(TreeStru)o1;
		TreeStru t2=(TreeStru)o2;
		if(t1.getSeq()>t2.getSeq()){
			return 1;
		}else{
			return -1;
		}
	}

}
