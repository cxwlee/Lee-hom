package com.powernode.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powernode.exception.ResultException;
import com.powernode.mapper.WealthMapper;
import com.powernode.pojo.Wealth;
import com.powernode.pojo.WealthExample;
import com.powernode.service.WealthService;
import com.powernode.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WealthServiceImpl implements WealthService {
    @Autowired
    private WealthMapper wealthMapper;
    @Override
    public PageResult selectAll(int pageNo,int pageSize,String item,String mname) {
        try {
            WealthExample example = new WealthExample();
            WealthExample.Criteria criteria = example.createCriteria();
            if (item!=null&& !"".equals(item)){
                criteria.andItemLike("%"+item+"%");
            }
            if (mname!=null&& !"".equals(mname)){
                criteria.andMnameLike("%"+mname+"%");
            }
            PageHelper.startPage(pageNo,pageSize);
            List<Wealth> wealth = wealthMapper.selectByExample(example);
            PageInfo<Wealth> pageInfo = new PageInfo<>(wealth);
            PageResult pageResult = new PageResult(pageInfo.getTotal(),pageInfo.getList());
            return pageResult;
        } catch (Exception e) {
            throw new ResultException("未找到数据");
        }
    }

    @Override
    public void delectById(int id) {
        try {
            wealthMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new ResultException("删除失败");
        }

    }

    @Override
    public void insertOne(Wealth wealth) {
        try {
            wealthMapper.insertSelective(wealth);
        } catch (Exception e) {
            throw new ResultException("添加失败");
        }
    }

    @Override
    public Map<String,Object> select() {
        Map<String,Object> map =new HashMap<String,Object>();
        List<Wealth> wealth = wealthMapper.selectByExample(null);
        List list = new ArrayList<>();
        List<Map> list1 = new ArrayList();
        for (Wealth wealth1 : wealth) {
            Map map1 = new HashMap();
            String item = wealth1.getItem();
            Integer id = wealth1.getId();
            map1.put("value",id);
            map1.put("name",item);
            list.add(item);
            list1.add(map1);
        }
        map.put("name",list);
        map.put("value",list1);
        return map;
    }

    @Override
    public Map<String, Object> selectBy() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Wealth> wealth = wealthMapper.selectByExample(null);
        List list = new ArrayList();
        List list1 = new ArrayList();
        for (Wealth wealth1 : wealth) {
            String mmoney = String.valueOf(wealth1.getMmoney());
            list.add(mmoney);
            Integer mstart = wealth1.getMstart();
            list1.add(mstart);
        }
        map.put("money", list);
        map.put("start", list1);
        return map;
    }
}
