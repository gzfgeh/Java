package com.gzfgeh.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.gzfgeh.domain.News;
import com.gzfgeh.service.VideoNewsService;

public class VideoNewsServiceImpl implements VideoNewsService {

	@Override
	public List<News> getLastNews() {
		// TODO Auto-generated method stub
		List<News> newes = new ArrayList<News>();
		newes.add(new News(66,"gggg",100));
		newes.add(new News(88,"zzzz",120));
		newes.add(new News(86,"ffff",180));
		return newes;
	}

}
