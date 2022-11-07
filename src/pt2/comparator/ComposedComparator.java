package pt2.comparator;

import java.util.Comparator;

public class ComposedComparator implements Comparator<Song> {
    public Comparator<Song> comparator_1;
    public Comparator<Song> comparator_2;

    public ComposedComparator(Comparator c1, Comparator c2){
        this.comparator_1 = c1;
        this.comparator_2 = c2;
    }

    @Override
    public int compare(Song s1, Song s2) {
        if(comparator_1.compare(s1, s2) == 0) {
            return comparator_2.compare(s1,s2);
        }
        else {
            return comparator_1.compare(s1,s2);
        }
    }

}
