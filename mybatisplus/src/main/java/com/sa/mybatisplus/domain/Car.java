package com.sa.mybatisplus.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName t_car
 */
@TableName(value ="t_car")
@Data
public class Car implements Serializable {
    /**
     * 主键自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 汽车编号
     */
    private String carNum;

    /**
     * 汽车品牌
     */
    private String brand;

    /**
     * 厂家指导价
     */
    private BigDecimal guidePrice;

    /**
     * 生产日期
     */
    private String produceTime;

    /**
     * 汽车类型
     */
    private String carType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Car other = (Car) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCarNum() == null ? other.getCarNum() == null : this.getCarNum().equals(other.getCarNum()))
            && (this.getBrand() == null ? other.getBrand() == null : this.getBrand().equals(other.getBrand()))
            && (this.getGuidePrice() == null ? other.getGuidePrice() == null : this.getGuidePrice().equals(other.getGuidePrice()))
            && (this.getProduceTime() == null ? other.getProduceTime() == null : this.getProduceTime().equals(other.getProduceTime()))
            && (this.getCarType() == null ? other.getCarType() == null : this.getCarType().equals(other.getCarType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCarNum() == null) ? 0 : getCarNum().hashCode());
        result = prime * result + ((getBrand() == null) ? 0 : getBrand().hashCode());
        result = prime * result + ((getGuidePrice() == null) ? 0 : getGuidePrice().hashCode());
        result = prime * result + ((getProduceTime() == null) ? 0 : getProduceTime().hashCode());
        result = prime * result + ((getCarType() == null) ? 0 : getCarType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", carNum=").append(carNum);
        sb.append(", brand=").append(brand);
        sb.append(", guidePrice=").append(guidePrice);
        sb.append(", produceTime=").append(produceTime);
        sb.append(", carType=").append(carType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}