# Cover Reuploader
# By Felix An
# Downloads all book covers so that I can host them myself without relying on Google.

import json
import requests
import os
import socks
import socket

# Constants
JSON_FILE_PATH = 'books.json'  # Path to the JSON file
UPDATED_JSON_FILE_PATH = 'books_updated.json'  # Path to the updated JSON file
SAVE_PATH = 'C:\\Users\\cutek\\Documents\\GitHub\\fffelix-jan.github.io\\direct\\bookcovers'  # Replace with the actual save path
WEB_PATH = 'https://www.felixan.ca/direct/bookcovers/'  # Replace with the actual web path

# Proxy settings
USE_SOCKS_PROXY = True
SOCKS_HOST = "127.0.0.1"
SOCKS_PORT = 8080

# Apply the proxy if enabled
if USE_SOCKS_PROXY:
    socks.set_default_proxy(socks.SOCKS5, SOCKS_HOST, SOCKS_PORT)
    socket.socket = socks.socksocket

# Function to download and save an image
def save_image(image_url, file_path):
    print(f"Saving {image_url} to {file_path}...", end='')
    response = requests.get(image_url)
    if response.status_code == 200:
        with open(file_path, 'wb') as file:
            file.write(response.content)
        print("done!")

# Read JSON file
with open(JSON_FILE_PATH, 'r', encoding='utf-8') as file:
    books = json.load(file)

# Process each book
for book in books:
    uuid = book['uuid']
    image_url = book['imageUrl']
    filename = uuid + '.jpg'
    if image_url:
        save_image(image_url, os.path.join(SAVE_PATH, filename))
    book['imageUrl'] = WEB_PATH + filename

# Save the updated JSON
with open(UPDATED_JSON_FILE_PATH, 'w') as file:
    json.dump(books, file, indent=4)

print("Images downloaded and JSON file updated.")
