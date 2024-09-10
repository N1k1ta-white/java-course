package bg.sofia.uni.fmi.mjt.photoalbum;

public class Main {
    public static void main(String[] args) {
        ParallelMonochromeAlbumCreator monochromeAlbumCreator = new ParallelMonochromeAlbumCreator(20);
        monochromeAlbumCreator.processImages("C:\\Users\\detix\\Desktop\\archive\\MJT\\lab9\\src\\img", 
                "C:\\Users\\detix\\Desktop\\archive\\MJT\\lab9\\src\\res");
    }
}
