package org.linlinjava.litemall.db.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.db.domain.LitemallGoodsProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class LitemallGoodsProductMapperTest {
    @Autowired
    private LitemallGoodsProductMapper litemallGoodsProductMapper;
    @Test
    public void countByExample() throws Exception {
    }

    @Test
    public void deleteByExample() throws Exception {
    }

    @Test
    public void deleteByPrimaryKey() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void insertSelective() throws Exception {
    }

    @Test
    public void selectOneByExample() throws Exception {
    }

    @Test
    public void selectOneByExampleSelective() throws Exception {
    }

    @Test
    public void selectByExampleSelective() throws Exception {
    }

    @Test
    public void selectByExample() throws Exception {
    }

    @Test
    public void selectByPrimaryKeySelective() throws Exception {
    }

    @Test
    public void updateByPrimaryKeySelective() throws Exception {
        LitemallGoodsProduct litemallGoodsProduct=new LitemallGoodsProduct();
        litemallGoodsProduct.setId(220);
        litemallGoodsProduct.setNumber(100);
        int result=litemallGoodsProductMapper.updateByPrimaryKeySelective(litemallGoodsProduct);
        System.out.println(result);
    }

    @Test
    public void updateByPrimaryKey() throws Exception {
    }

    @Test
    public void logicalDeleteByExample() throws Exception {
    }

    @Test
    public void logicalDeleteByPrimaryKey() throws Exception {
    }

}