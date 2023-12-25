package com.bookwise.sembackend.elastic_search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ESBook {
    private String uuid;
    private String name;
    private String author;
    private String category;
    private String publisher;
    private Integer pages;
    private Integer publishYear;
    private String bookLanguage;
    private String isbn;
    private String imageUrl;
    private String source;

    private static final Map<String, String> LANGUAGE_CODE_MAP = new HashMap<>();

    static {
        LANGUAGE_CODE_MAP.put("en", "English");
        LANGUAGE_CODE_MAP.put("es", "Spanish");
        LANGUAGE_CODE_MAP.put("fr", "French");
        LANGUAGE_CODE_MAP.put("de", "German");
        LANGUAGE_CODE_MAP.put("zh", "Chinese");
        LANGUAGE_CODE_MAP.put("ja", "Japanese");
        LANGUAGE_CODE_MAP.put("ko", "Korean");
        LANGUAGE_CODE_MAP.put("ru", "Russian");
        LANGUAGE_CODE_MAP.put("ar", "Arabic");
        LANGUAGE_CODE_MAP.put("hi", "Hindi");
        LANGUAGE_CODE_MAP.put("pt", "Portuguese");
        LANGUAGE_CODE_MAP.put("it", "Italian");
        LANGUAGE_CODE_MAP.put("nl", "Dutch");
        LANGUAGE_CODE_MAP.put("sv", "Swedish");
        // Add more language code mappings as needed
    }

    // Predefined lists of real book names, categories, and publishers for demonstration
    private static final String[] REAL_BOOK_NAMES = {
            "To Kill a Mockingbird",
            "1984",
            "The Great Gatsby",
            "Pride and Prejudice",
            "The Catcher in the Rye",
            "The Hobbit",
            "The Lord of the Rings",
            "Harry Potter and the Sorcerer's Stone",
            "The Da Vinci Code",
            "The Hunger Games"
    };

    private static final String[] REAL_CATEGORIES = {
            "Fiction",
            "Science Fiction",
            "Classic",
            "Romance",
            "Mystery",
            "Fantasy",
            "Adventure",
            "Thriller",
            "Young Adult"
    };

    private static final String[] REAL_PUBLISHERS = {
            "Penguin Books",
            "HarperCollins",
            "Simon & Schuster",
            "Random House",
            "Hachette Book Group",
            "Macmillan Publishers",
            "Scholastic",
            "Bloomsbury Publishing",
            "Doubleday",
            "Oxford University Press"
    };

    // Generate a random ESBook instance with real book data
    public static ESBook generateRandomBook() {
        String randomUuid = UUID.randomUUID().toString();
        String randomName = getRandomRealBookName();
        String randomCategory = getRandomRealCategory();
        String randomPublisher = getRandomRealPublisher();
        Integer randomPages = ThreadLocalRandom.current().nextInt(100, 1000); // Random pages between 100 and 1000
        Integer randomPublishYear = ThreadLocalRandom.current().nextInt(1900, 2023); // Random year between 1900 and 2023
        String randomLanguageCode = getRandomLanguageCode();
        // Map the language code to a language name
        String randomBookLanguage = LANGUAGE_CODE_MAP.getOrDefault(randomLanguageCode, "Unknown");
        String randomISBN = generateRandomString(13); // Generating a random 13-character ISBN
        String randomImageUrl = generateRandomImageUrl();
        String randomSource = getRandomRealSource(); // Add this line to get a random real source

        return new ESBook(randomUuid, randomName, "", randomCategory, randomPublisher,
                randomPages, randomPublishYear, randomBookLanguage, randomISBN, randomImageUrl, randomSource);
    }

    // Get a random language code from the predefined list
    private static String getRandomLanguageCode() {
        // Language codes based on http://www.lingoes.cn/zh/translator/langcode.htm
        String[] languageCodes = {"en", "es", "fr", "de", "zh", "ja", "ko", "ru", "ar", "hi", "pt", "it", "nl", "sv"};

        int randomIndex = ThreadLocalRandom.current().nextInt(languageCodes.length);
        return languageCodes[randomIndex];
    }

    // Get a random real source from the predefined list
    private static String getRandomRealSource() {
        int randomIndex = ThreadLocalRandom.current().nextInt(REAL_SOURCES.length);
        return REAL_SOURCES[randomIndex];
    }

    // Predefined list of real sources for demonstration
    private static final String[] REAL_SOURCES = {
            "Amazon",
            "Barnes & Noble",
            "Books-A-Million",
            "Book Depository",
            "IndieBound",
            "Powell's Books",
            "ThriftBooks",
            "Bookfinder",
            "AbeBooks",
            "Booktopia"
    };

    // Generate a real valid image URL using Lorem Picsum
    private static String generateRandomImageUrl() {
        int width = ThreadLocalRandom.current().nextInt(300, 800); // Random width between 300 and 800
        int height = ThreadLocalRandom.current().nextInt(300, 800); // Random height between 300 and 800
        int imageId = ThreadLocalRandom.current().nextInt(1, 1000); // Random image ID

        return String.format("https://picsum.photos/%d/%d?image=%d", width, height, imageId);
    }

    // Get a random real book name from the predefined list
    private static String getRandomRealBookName() {
        int randomIndex = ThreadLocalRandom.current().nextInt(REAL_BOOK_NAMES.length);
        return REAL_BOOK_NAMES[randomIndex];
    }

    // Get a random real category from the predefined list
    private static String getRandomRealCategory() {
        int randomIndex = ThreadLocalRandom.current().nextInt(REAL_CATEGORIES.length);
        return REAL_CATEGORIES[randomIndex];
    }

    // Get a random real publisher from the predefined list
    private static String getRandomRealPublisher() {
        int randomIndex = ThreadLocalRandom.current().nextInt(REAL_PUBLISHERS.length);
        return REAL_PUBLISHERS[randomIndex];
    }

    // Generate a random string of a given length
    private static String generateRandomString(int length) {
        String characters = "0123456789";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }

        return randomString.toString();
    }
}
