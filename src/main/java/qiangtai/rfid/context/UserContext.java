package qiangtai.rfid.context;


import lombok.Getter;

/**
 * @author FEI
 * &#064;description:  用户上下文
 */
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

    @Getter
    public static class UserInfo {
        private final Integer userId;
        private final Integer companyId;
        private final String companyName;

        public UserInfo(Integer userId, Integer companyId, String companyName) {
            this.userId = userId;
            this.companyId = companyId;
            this.companyName = companyName;
        }
    }
}