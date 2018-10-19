package com.welltech.waterAffair.common.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Properties {
	@Value("${upload.path}")
	public String path;
	@Value("${m.index.showCol}")
	public String showCol;
	@Value("${m.index.ndata}")
	public String ndata;
	@Value("${m.index.wt4300}")
	public String wt4300;
}
