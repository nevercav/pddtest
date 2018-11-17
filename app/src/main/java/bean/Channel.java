package bean;

public class Channel {
    private int imageId1;
    private String name1;
    private int imageId2;
    private String name2;

    public Channel(int imageId1, String name1, int imageId2, String name2) {
        this.imageId1 = imageId1;
        this.name1 = name1;
        this.imageId2 = imageId2;
        this.name2 = name2;
    }

    public int getImageId1() {
        return imageId1;
    }

    public void setImageId1(int imageId1) {
        this.imageId1 = imageId1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public int getImageId2() {
        return imageId2;
    }

    public void setImageId2(int imageId2) {
        this.imageId2 = imageId2;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
