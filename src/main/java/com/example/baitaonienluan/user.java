package com.example.baitaonienluan;

public class user {
    private Integer id;
    private String taikhoan;
    private String matkhau;
    private Integer tongdiem;

    public Integer getTongdiem() {
        return tongdiem;
    }

    public void setTongdiem(Integer tongdiem) {
        this.tongdiem = tongdiem;
    }
    public user(String taikhoan, Integer tongdiem){
        this.taikhoan=taikhoan;
        this.tongdiem=tongdiem;
    }
    public user(Integer id, String taikhoan, String matkhau, Integer tongdiem){
        this.id=id;
        this.taikhoan=taikhoan;
        this.matkhau=matkhau;
        this.tongdiem=tongdiem;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

}
