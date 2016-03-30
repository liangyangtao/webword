package com.web.tags.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.database.bean.Tags;
import com.database.bean.TagsExample;
import com.database.dao.TagsMapper;

@Service
public class TagsService {
	@Autowired
	TagsMapper tagsMapper;

	/**
	 *  * 插入标签库
	 * @param tags
	 * @return
	 */
	@Transactional
	public int saveTags(String tags) {
		int count = 0;
		if (!"".equals(tags)) {
			for (String str : tags.split(" ")) {
				if (str != null && !"".equals(str.trim())) {
					TagsExample example = new TagsExample();
					example.or().andNameEqualTo(str.trim());
					List<Tags> list = tagsMapper.selectByExample(example);
					if (list.isEmpty()) {
						Tags tag = new Tags();
						tag.setName(str.trim());
						tag.setCount(0);
						tagsMapper.insert(tag);
					} else {
						Tags tag = list.get(0);
						tag.setCount(tag.getCount() + 1);
						tagsMapper.updateByPrimaryKey(tag);
					}
					count++;
				}
			}
		}
		return count;
	}
}
