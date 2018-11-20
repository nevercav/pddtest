package bean;

public class Type {
    String imageCode;
    String imageName;

    public Type(String imageCode, String imageName) {
        this.imageCode = imageCode;
        this.imageName = imageName;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
