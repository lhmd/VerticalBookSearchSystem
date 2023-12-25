import json
from elasticsearch import Elasticsearch

def is_convertible_to_int(value):
    try:
        int(value)
        return True
    except (ValueError, TypeError):
        return False

infile = open("combined_books.json", "r")
combined_books = json.load(infile)

es = Elasticsearch([{'host': '10.73.103.130', 'port': 9200, 'scheme': 'http'}])
index_name = 'books'


for i in range(0, len(combined_books)):
    print("i: {}, Book name: {}".format(i, combined_books[i]["name"]))

    if (combined_books[i]["publishYear"] == ""):
        combined_books[i]["publishYear"] = 0
    if not is_convertible_to_int(combined_books[i]["pages"]):
        combined_books[i]["pages"] = 0
    combined_books[i]['publishYear'] = int(combined_books[i]['publishYear'])
    # if (combined_books[i]["author"] is None):
    #     combined_books[i]["author"] = ""

    es.index(index=index_name, document=combined_books[i])

outfile = open("data.json", "w")
json.dump(combined_books, outfile, ensure_ascii=False)