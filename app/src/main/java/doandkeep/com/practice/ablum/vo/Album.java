package doandkeep.com.practice.ablum.vo;

public class Album implements AlbumItem {

    public int stargazers_count;

    @Override
    public boolean isSection() {
        return false;
    }
}
