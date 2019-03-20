package com.lhq.studentmall.enume;
/**
 * @author Leon
 * @ClassName: ShopExecution
 * @Description: 店铺补充拓展类，用枚举表述常量数据字典
 * @date 2019/3/3 15:30
 */
public enum ShopStateEnum {

    CHECK(0, "审核中"), OFFLINE(-1, "非法商铺"), SUCCESS(1, "操作成功"), PASS(2, "通过认证"), INNER_ERROR(
            -1001, "操作失败"), NULL_SHOPID(-1002, "ShopId为空"), NULL_SHOP(
            -1003, "传入了空的Shop信息");

    //状态
    private int state;
    //标识
    private String stateInfo;

    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * 功能描述:根据传入的状态值返回enum值
     *
     * @param: index状态值
     * @return: enum值
     **/
    public static ShopStateEnum stateOf(int index) {
        for (ShopStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

}