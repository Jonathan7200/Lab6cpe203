package pt2.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.*;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator()
   {
      List<Song> expected = new ArrayList<>();
      expected.add(new Song("Avett Brothers", "Talk on Indolence", 2006));
      expected.add(new Song("City and Colour", "Sleeping Sickness", 2007));
      expected.add(new Song("Decemberists", "The Mariner's Revenge Song", 2005));
      expected.add(new Song("Foo Fighters", "Baker Street", 1997));
      expected.add(new Song("Gerry Rafferty", "Baker Street", 1998));
      expected.add(new Song("Gerry Rafferty", "Baker Street", 1978));
      expected.add(new Song("Queen", "Bohemian Rhapsody", 1975));
      expected.add(new Song("Rogue Wave", "Love's Lost Guarantee", 2005));

      Comparator<Song> compare_artist = new ArtistComparator();
      List<Song> songs_list = new ArrayList<>(Arrays.asList(songs));

      assertTrue(compare_artist.compare(songs[2], songs[1]) < 0);
      assertTrue(compare_artist.compare(songs[1], songs[0]) > 0);
      assertTrue(compare_artist.compare(songs[3], songs[7]) == 0);


      Collections.sort(songs_list, compare_artist);

      assertEquals(expected, songs_list);



   }

   @Test
   public void testLambdaTitleComparator()
   {
      Comparator<Song> compare_titles = (Song s1, Song s2) ->s1.getTitle().compareTo(s2.getTitle());
      assertTrue(compare_titles.compare(songs[0], songs[1]) > 0);
      assertTrue(compare_titles.compare(songs[3], songs[2]) < 0);
      assertTrue(compare_titles.compare(songs[3], songs[5]) == 0);

   }

   @Test
   public void testYearExtractorComparator()
   {
      Comparator<Song> compare_years = Comparator.comparing(Song::getYear);
      compare_years = compare_years.reversed();
      assertTrue(compare_years.compare(songs[1], songs[2]) > 0);
      assertTrue(compare_years.compare(songs[2], songs[0]) < 0);
      assertTrue(compare_years.compare(songs[0], songs[1]) == 0);

   }

   @Test
   public void testComposedComparator()
   {
      Comparator<Song> c1 = Comparator.comparing(Song::getTitle);
      Comparator<Song> c2 = Comparator.comparing(Song::getYear);
      Comparator<Song> ComposedComparator = new ComposedComparator(c1,c2);

      assertTrue(ComposedComparator.compare(songs[3], songs[7]) > 0);
      assertTrue(ComposedComparator.compare(songs[5], songs[5]) == 0);
      assertTrue(ComposedComparator.compare(songs[3], songs[5]) > 0);
      assertTrue(ComposedComparator.compare(songs[7], songs[3]) < 0);

      Comparator<Song> c3 = Comparator.comparing(Song::getYear);
      Comparator<Song> c4 = Comparator.comparing(Song::getArtist);
      Comparator<Song> ComposedComparator2 = new ComposedComparator(c3,c4);

      assertTrue(ComposedComparator2.compare(songs[0], songs[1]) < 0);
   }

   @Test
   public void testThenComparing()
   {
      Comparator<Song> compare_then = Comparator.comparing(Song::getTitle).thenComparing(Song::getArtist);



   }

   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );

      songList.sort(Comparator.comparing(Song::getArtist).thenComparing(Song::getTitle).thenComparing(Song::getYear));

      assertEquals(songList, expectedList);
   }
}
