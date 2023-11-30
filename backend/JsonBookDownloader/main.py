# JSON Book Downloader
# By Felix An

import socks
import socket
import requests
import uuid
import json

# Google Books API Key
API_KEY = "AIzaSyAlAe-nRuAokMNKXC0Z1qQBlfNxDBSUaAE"

# Proxy settings (if you use a SOCKS5 proxy to go over the wall, set these accordingly)
# Warning: This script relies on the Google Books API, which requires going over the wall.
# The image URLs also rely on Google, which requires going over the wall.
USE_SOCKS_PROXY = True
SOCKS_HOST = "127.0.0.1"
SOCKS_PORT = 8080


# Function to fetch books from Google Books API
def fetch_books(query, max_results=1000):
    books = []
    base_url = "https://www.googleapis.com/books/v1/volumes"
    start_index = 0
    results_per_request = 40  # Maximum allowed by Google Books API

    while len(books) < max_results:
        params = {
            "q": query, 
            "startIndex": start_index,
            "maxResults": results_per_request
        }

        print(f"Start at {start_index}: ", end="")

        response = requests.get(base_url, params=params)
        data = response.json()
        books_batch = data.get('items', [])
        books.extend(books_batch)

        # Debug print to track progress
        print(f"Fetched {len(books_batch)} books. Total books fetched: {len(books)}")

        if "items" not in data or len(books_batch) < results_per_request:
            print("No more books to fetch or reached the end of the dataset.")
            break  # Break if there are no more items to fetch

        start_index += results_per_request

    return books[:max_results]  # Return only up to max_results


# Function to format the book data
def format_books(book_items):
    print("Formatting books...")
    books = []
    for item in book_items:
        volume_info = item.get('volumeInfo', {})
        formatted_book = {
            "uuid": str(uuid.uuid4()),
            "name": volume_info.get("title", ""),
            "category": ", ".join(volume_info.get("categories", ["Unknown"])),
            "publisher": volume_info.get("publisher", ""),
            "pages": volume_info.get("pageCount", 0),
            "publishYear": volume_info.get("publishedDate", "").split('-')[0],
            "bookLanguage": volume_info.get("language", ""),
            "isbn": next((identifier['identifier'] for identifier in volume_info.get("industryIdentifiers", []) if identifier['type'] == 'ISBN_13'), ""),
            "imageUrl": volume_info.get("imageLinks", {}).get("thumbnail", ""),
            "source": "Google Books"
        }
        books.append(formatted_book)
    return books


# Main function to execute the program
def main():
    query = "fiction"  # Example query
    data = fetch_books(query)
    formatted_data = format_books(data)
    print("Saving books...")
    with open('books.json', 'w', encoding='utf-8') as f:
        json.dump(formatted_data, f, ensure_ascii=False, indent=4)
        print("Books saved.")

# Run the program
if __name__ == "__main__":
    # Apply the proxy
    if USE_SOCKS_PROXY:
        socks.set_default_proxy(socks.SOCKS5, SOCKS_HOST, SOCKS_PORT)
        socket.socket = socks.socksocket
    # Run the main() function
    main()
