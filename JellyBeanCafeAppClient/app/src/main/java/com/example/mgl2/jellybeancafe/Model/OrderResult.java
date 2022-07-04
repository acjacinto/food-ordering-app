package com.example.mgl2.jellybeancafe.Model;

public class OrderResult {
    public int OrderId;
    public String OrderDate;
    public int OrderStatus;
    public float OrderPrice;
    public String OrderDetail,OrderComment,OrderAddress,UserPhone;

    public OrderResult() {
    }

    public OrderResult(int orderId, String orderDate, int orderStatus, float orderPrice, String orderDetail, String orderComment, String orderAddress, String userPhone) {
        OrderId = orderId;
        OrderDate = orderDate;
        OrderStatus = orderStatus;
        OrderPrice = orderPrice;
        OrderDetail = orderDetail;
        OrderComment = orderComment;
        OrderAddress = orderAddress;
        UserPhone = userPhone;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public float getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(float orderPrice) {
        OrderPrice = orderPrice;
    }

    public String getOrderDetail() {
        return OrderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        OrderDetail = orderDetail;
    }

    public String getOrderComment() {
        return OrderComment;
    }

    public void setOrderComment(String orderComment) {
        OrderComment = orderComment;
    }

    public String getOrderAddress() {
        return OrderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        OrderAddress = orderAddress;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }
}
