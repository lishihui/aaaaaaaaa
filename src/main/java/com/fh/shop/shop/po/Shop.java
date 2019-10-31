package com.fh.shop.shop.po;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class Shop implements Serializable {
    private Long id;
    private String shopName;
    private BigDecimal price;
    private String imgPath;
    private Date productiveTime;
    private Long stock;
    private Integer isHot;
    private Integer isShelf;
    private Long brandId;

    public Shop(Long id, String shopName) {
        this.id = id;
        this.shopName = shopName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Date getProductiveTime() {
        return productiveTime;
    }

    public void setProductiveTime(Date productiveTime) {
        this.productiveTime = productiveTime;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getIsShelf() {
        return isShelf;
    }

    public void setIsShelf(Integer isShelf) {
        this.isShelf = isShelf;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
