package doandkeep.com.practice.ablum.vo;

public class Album implements AlbumItem {

    public long id;
    public Temp owner;

    @Override
    public boolean isSection() {
        return false;
    }
}
