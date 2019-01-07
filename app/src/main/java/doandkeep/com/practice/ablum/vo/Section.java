package doandkeep.com.practice.ablum.vo;

/**
 * Created by zhangtao on 2019/1/7.
 */
public class Section implements AlbumItem {

    public String title;

    public Section(String title) {
        this.title = title;
    }

    @Override
    public boolean isSection() {
        return true;
    }
}
