package org.linlinjava.litemall.db.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.db.domain.LitemallGoodsProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class LitemallGoodsProductDAOTest {
    @Autowired
    private LitemallGoodsProductDAO litemallGoodsProductDAO;
    @Test
    public void reduceStock1() throws Exception {
        Short number=100;
       int result= litemallGoodsProductDAO.reduceStock(230,number);
        Assert.assertEquals(-1,result);
    }
    @Test
    public void reduceStock2() throws Exception {
        Short number=20;
        int result= litemallGoodsProductDAO.reduceStock(244,number);
        Assert.assertEquals(1,result);
    }
    @Test
    public void selectByPrimaryKey() throws Exception {
        LitemallGoodsProduct litemallGoodsProduct= litemallGoodsProductDAO.selectByPrimaryKey(220);
       System.out.println(litemallGoodsProduct.toString());
        Assert.assertNotNull(litemallGoodsProduct);

    }


}