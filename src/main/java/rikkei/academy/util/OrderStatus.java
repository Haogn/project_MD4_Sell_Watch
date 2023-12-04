package rikkei.academy.util;

public enum OrderStatus {
    CHỜ_XÁC_NHẬN(0, "Chờ xác nhận"),
    XÁC_NHẬN(1, "Xác nhận"),
    ĐÃ_GIAO(2, "Đã giao"),
    HUỶ(3, "Huỷ");

    private final int databaseValue;
    private final String displayName;

    OrderStatus(int databaseValue, String displayName) {
        this.databaseValue = databaseValue;
        this.displayName = displayName;
    }

    public int getDatabaseValue() {
        return databaseValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Phương thức chuyển đổi từ BIT sang enum
    public static OrderStatus fromDatabaseValue(int databaseValue) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.databaseValue == databaseValue) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid database value for OrderStatus: " + databaseValue);
    }
}

