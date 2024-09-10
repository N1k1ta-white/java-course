package bg.sofia.uni.fmi.mjt.photoalbum;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelMonochromeAlbumCreator implements MonochromeAlbumCreator {
    private Queue<Image> loaded;
    private Queue<Path> images;
    private final int countOfTreads;
    private static final List<String> FILE_EXTENSIONS;
    private File resDirectory;
    private int countImages;

    static  {
        FILE_EXTENSIONS = List.of("png", "jpeg", "jpg");
    }

    private class Loader extends Thread {
        private static AtomicInteger count;

        static {
            count = new AtomicInteger(0);
        }

        @Override
        public void run() {
            while (!count.compareAndSet(countImages, countImages)) {
                Path imagePath;
                synchronized (images) {
                    System.out.println("Thread Loader " + super.getName() + " started to load");
                    imagePath = images.poll();
                }
                if (imagePath != null) {
                    try {
                        BufferedImage imageData = ImageIO.read(imagePath.toFile());
                        Image image = new Image(imagePath.getFileName().toString(), imageData);
                        synchronized (loaded) {
                            count.incrementAndGet();
                            loaded.add(image);
                            loaded.notifyAll();
                        }
                    } catch (IOException e) {
                        throw new UncheckedIOException(String.format("Failed to load image %s",
                                imagePath.toString()), e);
                    }
                }
            }
        }
    }

    private class Consumer extends Thread {
        private static AtomicInteger count;

        static {
            count = new AtomicInteger(0);
        }

        private Image convertToBlackAndWhite(Image image) {
            BufferedImage processedData = new BufferedImage(image.data.getWidth(),
                    image.data.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            processedData.getGraphics().drawImage(image.data, 0, 0, null);
            return new Image(image.name, processedData);
        }

        private void saveImage(Image image) {
            try {
                ImageIO.write(image.data, "png", new File(resDirectory, image.name));
            } catch (IOException e) {
                throw new UncheckedIOException(String.format("While saving image %s", image.name), e);
            }
        }

        @Override
        public void run() {
            while (!count.compareAndSet(countImages, countImages)) {
                Image img;
                synchronized (loaded) {

                    System.out.println("Thread Consume " + super.getName() + " start consume");

                    try {
                        if (loaded.isEmpty()) {
                            loaded.wait();
                        }
                        img = loaded.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (img != null) {
                    count.incrementAndGet();
                    saveImage(convertToBlackAndWhite(img));
                }
            }
        }
    }

    private boolean hasRightExtension(String file) {
        for (String ext : FILE_EXTENSIONS) {
            if (file.substring(file.lastIndexOf('.') + 1).equals(ext))
                return true;
        }
        return false;
    }

    public ParallelMonochromeAlbumCreator(int count) {
        countOfTreads = count;
        loaded = new ArrayDeque<>();
        images = new ArrayDeque<>();
    }

    @Override
    public void processImages(String sourceDirectory, String outputDirectory) {
        Path dir = Path.of(sourceDirectory);
        resDirectory = Path.of(outputDirectory).toFile();
        if (!resDirectory.isDirectory()) {
            resDirectory.mkdir();
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path filePath : stream) {
                if (hasRightExtension(filePath.toString())) {
                    images.add(filePath);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Something going wrong :/", e);
        }
        countImages = images.size();
        for (int i = 0; i < countOfTreads; i++) {
            new Loader().start();
            new Consumer().start();
        }
    }
}

