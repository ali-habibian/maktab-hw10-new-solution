package q1.model;

public enum UserType {
    ADMIN(1), PATIENT(2);

    private final int type;

    UserType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
