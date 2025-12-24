package qiangtai.rfid.context;


public final class UserContext {
    private static final ThreadLocal<UserInfo> HOLDER = new ThreadLocal<>();

    public static void set(UserInfo info) {
        HOLDER.set(info);
    }

    public static UserInfo get() {
        return HOLDER.get();
    }

    public static void clear() {
        HOLDER.remove();
    }

    public static class UserInfo {
        private final String userId;
        private final String companyId;

        public UserInfo(String userId, String companyId) {
            this.userId = userId;
            this.companyId = companyId;
        }
        public String getUserId() { return userId; }
        public String getCompanyId() { return companyId; }
    }
}