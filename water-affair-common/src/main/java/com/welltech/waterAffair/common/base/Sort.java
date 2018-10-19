package com.welltech.waterAffair.common.base;

import java.util.Comparator;

public class Sort implements Comparator<Object[]> {
	public final static int UP = 1;

	public final static int DOWM = -1;

	private int state;

	private int index;//指Object[]数组内部判定的下标依据

	public Sort(int state,int index) {
		this.state = state;
		this.index=index;
	}

	public Sort() {

	}

	//默认升序
	public int compare(Object[] o1, Object[] o2) {
		if (state == Sort.DOWM) {
			return sortDown(o1, o2);
		}
		return sortUp(o1, o2);
	}

	private int sortUp(Object[] o1, Object[] o2) {
		Float tmp1=Float.parseFloat(o1[index].toString());
		Float tmp2=Float.parseFloat(o2[index].toString());
		if (tmp1.compareTo(tmp2) < 0) {
			return -1;
		} else if (tmp1.compareTo(tmp2) > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	private int sortDown(Object[] o1, Object[] o2) {
		Float tmp1=Float.parseFloat(o1[index].toString());
		Float tmp2=Float.parseFloat(o2[index].toString());
		if (tmp1.compareTo(tmp2) > 0) {
			return -1;
		} else if (tmp1.compareTo(tmp2) < 0) {
			return 1;
		} else {
			return 0;
		}
	}
}